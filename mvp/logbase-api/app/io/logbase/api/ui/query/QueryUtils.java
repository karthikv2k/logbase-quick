package io.logbase.api.ui.query;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import play.Logger;
import io.logbase.api.antlr.LbqlSqlTranslator;
import io.logbase.api.antlr.TranslatorUtils;
import io.logbase.node.Node;
import io.logbase.node.NodeConnector;
import io.logbase.node.impl.SimpleRealtimeNodeConnector;
import io.logbase.querying.optiq.LBSchema;
import io.logbase.querying.optiq.QueryExecutor;
import io.logbase.utils.InFilter;
import io.logbase.view.View;

public class QueryUtils {

	private static NodeConnector nodeConnector = new SimpleRealtimeNodeConnector();
	private static Node node = nodeConnector.connect();
	private static int reqid = 0;
	private static Map<Integer, QueryRequest> queryRequests = new HashMap<Integer, QueryRequest>();
	private static Map<Integer, List<String>> queryResults = new HashMap<Integer, List<String>>();
	private static Map<Integer, List<String>> tableRequests = new HashMap<Integer, List<String>>();
	private static Map<Integer, List<Map<String, Object>>> tableResults = new HashMap<Integer, List<Map<String, Object>>>();
	private static Map<Integer, List<String>> plotRequests = new HashMap<Integer, List<String>>();
	private static Map<Integer, List<Map<String, Object>>> plotResults = new HashMap<Integer, List<Map<String, Object>>>();

	public static int postQuery(QueryRequest queryRequest) {
		reqid++;
		// Store request somewhere
		queryRequests.put(reqid, queryRequest);
		Logger.info("No. of query requests in memory: " + queryRequests.size());
		return reqid;
	}

	public static boolean isValidRequest(int reqid) {
		return queryRequests.get(reqid) == null ? false : true;
	}

	public static List<String> getEvents(int reqid) {
		List<String> cachedEvents = queryResults.get(reqid);
		if (cachedEvents != null)
			return cachedEvents;
		else
			return fetchEvents(reqid);
	}

	public static List<String> getEventsP(int reqid, long offset, int max) {
		List<String> events = null;
		List<String> selectEvents = new ArrayList<String>();
		List<String> cachedEvents = queryResults.get(reqid);
		if (cachedEvents != null)
			events = cachedEvents;
		else
			events = fetchEvents(reqid);
		if (offset <= events.size()) {
			int start = (int) (offset - 1);
			int end = start + max - 1;
			if (end >= events.size())
				end = events.size() - 1;
			for (int i = start; i <= end; i++)
				selectEvents.add(events.get(i));
		}
		return selectEvents;
	}

	public static long getEventsCount(int reqid) {
		List<String> cachedEvents = queryResults.get(reqid);
		if (cachedEvents != null)
			return cachedEvents.size();
		else
			return fetchEvents(reqid).size();
	}

	private static List<String> fetchEvents(int reqid) {
		// TODO This needs to be improved with multi table support etc.
		QueryRequest queryRequest = queryRequests.get(reqid);
		List<String> events = new ArrayList<String>();
		View view = node.getReader().getViewFactory()
				.createView(new InFilter("Twitter"));
		LBSchema lbSchema = new LBSchema("TEST");
		lbSchema.addAsSmartTable("TWITTER", view);
		QueryExecutor queryExec = new QueryExecutor(lbSchema);
		String sql = null;
		if ((queryRequest.getQueryType() != null)
				&& (queryRequest.getQueryType().equals("sql"))) {
			// TODO Get raw events
			String tempSql = queryRequest.getQuery();
			int fromPosition = tempSql.toUpperCase().indexOf(" FROM ");
			sql = "SELECT \"RawEvent.String\" "
					+ tempSql.substring(fromPosition);
		} else {
			LbqlSqlTranslator translator = TranslatorUtils
					.translate(queryRequest.getQuery());
			sql = "SELECT \"RawEvent.String\" " + translator.getFromClause()
					+ " " + translator.getWhereClause();
			if (queryRequest.getFrom() > 0)
				sql = sql + " AND \"TimeStamp.Long.LBM\" > "
						+ queryRequest.getFrom();
			if (queryRequest.getFrom() > 0)
				sql = sql + " AND \"TimeStamp.Long.LBM\" < "
						+ queryRequest.getTo();
		}
		int resultCount = 0;
		try {
			ResultSet results = queryExec.execute(sql);
			while (results.next()) {
				resultCount++;
				String event = results.getString("RawEvent.String");
				events.add(event);
			}
		} catch (Exception e) {
			Logger.error("Error while executing optiq query: " + sql);
		}
		Logger.info("Result count: " + resultCount);
		queryResults.put(reqid, events);
		return events;
	}

	public static List<String> getColumns(int reqid) {
		View view = node.getReader().getViewFactory()
				.createView(new InFilter("Twitter"));
		List<String> columns = new ArrayList(view.getColumnNames());
		return columns;
	}

	public static void createTableColumns(int reqid, List<String> columns) {
		Logger.debug("Adding table for reqid: " + reqid);
		tableRequests.put(reqid, columns);
		// Remove any cached results
		tableResults.remove(reqid);
	}

	public static List<Map<String, Object>> getTable(int reqid) {
		if (tableResults.containsKey(reqid)) {
			return tableResults.get(reqid);
		} else {
			List<Map<String, Object>> results = null;
			// TODO
			// Execute query with the passed columns
			QueryRequest queryRequest = queryRequests.get(reqid);
			View view = node.getReader().getViewFactory()
					.createView(new InFilter("Twitter"));
			LBSchema lbSchema = new LBSchema("TEST");
			lbSchema.addAsSmartTable("TWITTER", view);
			QueryExecutor queryExec = new QueryExecutor(lbSchema);
			String sql = null;
			if ((queryRequest.getQueryType() != null)
					&& (queryRequest.getQueryType().equals("sql"))) {
				if (tableRequests.get(reqid) == null)
					sql = queryRequest.getQuery();
				else {
					String tempSql = queryRequest.getQuery();
					String selectClause = "";
					for (String column : tableRequests.get(reqid)) {
						if (selectClause.equals(""))
							selectClause = selectClause + "\"" + column + "\"";
						else
							selectClause = selectClause + ", \"" + column
									+ "\"";
					}
					Logger.info("Select clause replacement: " + selectClause);
					int fromClausePosition = tempSql.toUpperCase().indexOf(
							" FROM ");
					sql = "SELECT " + selectClause
							+ tempSql.substring(fromClausePosition);
				}
			} else {
				String selectClause = "";
				for (String column : tableRequests.get(reqid)) {
					if (selectClause.equals(""))
						selectClause = selectClause + "\"" + column + "\"";
					else
						selectClause = selectClause + ", \"" + column + "\"";
				}
				Logger.info("Select clause: " + selectClause);
				sql = "SELECT "
						+ selectClause
						+ " from \"TEST\".\"TWITTER\" where \"RawEvent.String\" LIKE '"
						+ queryRequest.getQuery() + "'";
			}
			Logger.debug("Going to fetch table for query: " + sql);
			try {
				ResultSet resultSet = queryExec.execute(sql);
				results = getEntitiesFromResultSet(resultSet);
			} catch (Exception e) {
				Logger.error("Error while executing optiq query for table: "
						+ sql);
			}
			tableResults.put(reqid, results);
			return results;
		}
	}

	public static List<Map<String, Object>> getTableP(int reqid, long offset,
			int max) {
		List<Map<String, Object>> result = null;
		List<Map<String, Object>> filteredResult = new ArrayList<Map<String, Object>>();
		if (tableResults.containsKey(reqid))
			result = tableResults.get(reqid);
		else
			result = getTable(reqid);

		// filter
		if (offset <= result.size()) {
			int start = (int) (offset - 1);
			int end = start + max - 1;
			if (end >= result.size())
				end = result.size() - 1;
			for (int i = start; i <= end; i++)
				filteredResult.add(result.get(i));
		}
		return filteredResult;
	}

	public static long getTableRowCount(int reqid) {
		if (tableResults.containsKey(reqid))
			return tableResults.size();
		else
			return getTable(reqid).size();
	}

	private static List<Map<String, Object>> getEntitiesFromResultSet(
			ResultSet resultSet) throws SQLException {
		List<Map<String, Object>> entities = new ArrayList<Map<String, Object>>();
		while (resultSet.next()) {
			entities.add(getEntityFromResultSet(resultSet));
		}
		return entities;
	}

	private static Map<String, Object> getEntityFromResultSet(
			ResultSet resultSet) throws SQLException {
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();
		Map<String, Object> resultsMap = new HashMap<>();
		for (int i = 1; i <= columnCount; ++i) {
			String columnName = metaData.getColumnName(i).toLowerCase();
			Object object = resultSet.getObject(i);
			resultsMap.put(columnName, object);
		}
		return resultsMap;
	}

	public static boolean isValidTable(int reqid) {
		Logger.debug("Checking validity for table in reqid: " + reqid);
		if (queryRequests.get(reqid).getQueryType() == null
				|| !queryRequests.get(reqid).getQueryType().equals("sql"))
			return tableRequests.get(reqid) == null ? false : true;
		else
			return true;
	}

	public static void clearTable(int reqid) {
		tableRequests.remove(reqid);
		tableResults.remove(reqid);
	}

	public static void clearQuery(int reqid) {
		queryRequests.remove(reqid);
		queryResults.remove(reqid);
		tableRequests.remove(reqid);
		tableResults.remove(reqid);
	}

	public static List<List<Long>> getTimeline(int reqid) {
		List<Long> timeline = new ArrayList<Long>();
		QueryRequest queryRequest = queryRequests.get(reqid);
		View view = node.getReader().getViewFactory()
				.createView(new InFilter("Twitter"));
		LBSchema lbSchema = new LBSchema("TEST");
		lbSchema.addAsSmartTable("TWITTER", view);
		QueryExecutor queryExec = new QueryExecutor(lbSchema);
		String sql = "SELECT \"TimeStamp.Long.LBM\""
				+ " from \"TEST\".\"TWITTER\" where \"RawEvent.String\" LIKE '"
				+ queryRequest.getQuery() + "'";
		try {
			ResultSet results = queryExec.execute(sql);
			while (results.next()) {
				Long ts = results.getLong("TimeStamp.Long.LBM");
				timeline.add(ts);
			}
			Logger.info("Formed timeline with size: " + timeline.size());
		} catch (Exception e) {
			Logger.error("Error while executing optiq query: " + sql);
		}
		return timelineCount(timeline);
	}

	public static void createPlotColumns(int reqid, List<String> columns) {
		Logger.debug("Adding plot for reqid: " + reqid);
		plotRequests.put(reqid, columns);
		// Remove any cached results
		plotResults.remove(reqid);
	}

	public static List<Map<String, Object>> getPlot(int reqid) {
		if (plotResults.containsKey(reqid)) {
			return plotResults.get(reqid);
		} else {
			List<Map<String, Object>> results = null;
			// TODO
			// Execute query with the passed columns
			QueryRequest queryRequest = queryRequests.get(reqid);
			View view = node.getReader().getViewFactory()
					.createView(new InFilter("Twitter"));
			LBSchema lbSchema = new LBSchema("TEST");
			lbSchema.addAsSmartTable("TWITTER", view);
			QueryExecutor queryExec = new QueryExecutor(lbSchema);
			String sql = null;
			if ((queryRequest.getQueryType() != null)
					&& (queryRequest.getQueryType().equals("sql"))) {
				if (plotRequests.get(reqid) == null)
					sql = queryRequest.getQuery();
				else {
					String tempSql = queryRequest.getQuery();
					String selectClause = "";
					for (String column : plotRequests.get(reqid)) {
						if (selectClause.equals(""))
							selectClause = selectClause + "\"" + column + "\"";
						else
							selectClause = selectClause + ", \"" + column
									+ "\"";
					}
					Logger.info("Plot select clause replacement: "
							+ selectClause);
					int fromClausePosition = tempSql.toUpperCase().indexOf(
							" FROM ");
					sql = "SELECT " + selectClause
							+ tempSql.substring(fromClausePosition);
				}
			} else {
				String selectClause = "";
				for (String column : plotRequests.get(reqid)) {
					if (selectClause.equals(""))
						selectClause = selectClause + "\"" + column + "\"";
					else
						selectClause = selectClause + ", \"" + column + "\"";
				}
				Logger.info("Select clause: " + selectClause);
				sql = "SELECT "
						+ selectClause
						+ " from \"TEST\".\"TWITTER\" where \"RawEvent.String\" LIKE '"
						+ queryRequest.getQuery() + "'";
			}
			Logger.debug("Going to fetch plot for query: " + sql);
			try {
				ResultSet resultSet = queryExec.execute(sql);
				results = getEntitiesFromResultSet(resultSet);
			} catch (Exception e) {
				Logger.error("Error while executing optiq query for plot: "
						+ sql);
			}
			plotResults.put(reqid, results);
			return results;
		}
	}

	public static List<Map<String, Object>> getPlotP(int reqid, long offset,
			int max) {
		List<Map<String, Object>> result = null;
		List<Map<String, Object>> filteredResult = new ArrayList<Map<String, Object>>();
		if (plotResults.containsKey(reqid))
			result = plotResults.get(reqid);
		else
			result = getPlot(reqid);

		// filter
		if (offset <= result.size()) {
			int start = (int) (offset - 1);
			int end = start + max - 1;
			if (end >= result.size())
				end = result.size() - 1;
			for (int i = start; i <= end; i++)
				filteredResult.add(result.get(i));
		}
		return filteredResult;
	}

	public static long getPlotRowCount(int reqid) {
		if (plotResults.containsKey(reqid))
			return plotResults.size();
		else
			return getPlot(reqid).size();
	}

	public static boolean isValidPlot(int reqid) {
		Logger.debug("Checking validity for plot in reqid: " + reqid);
		if (queryRequests.get(reqid).getQueryType() == null
				|| !queryRequests.get(reqid).getQueryType().equals("sql"))
			return plotRequests.get(reqid) == null ? false : true;
		else
			return true;
	}

	public static void clearPlot(int reqid) {
		plotRequests.remove(reqid);
		plotResults.remove(reqid);
	}

	private static List<List<Long>> timelineCount(List<Long> timeline) {
		Collections.sort(timeline);

		List<List<Long>> countList = new ArrayList<List<Long>>();
		Long minTs = timeline.get(0);
		Long maxTs = timeline.get(timeline.size() - 1);

		Map<Long, Long> countMap = new HashMap<Long, Long>();
		Long newMinTs = minTs;

		// Convert this to countlist - find min, max, count stats and decide the
		// period
		int count = timeline.size();

		// If no ts
		if (count == 0) {
			// nothing to return
		}
		// If only one ts
		if ((count >= 1) && ((maxTs - minTs) == 0)) {
			countMap.put(timeline.get(0), 1L);
		}
		// If more than one ts
		if ((count > 1) && ((maxTs - minTs) > 0)) {
			Long timeDiff = maxTs - minTs;
			Long incr = null;
			// if timediff in secs
			// 5 means if less than 5 min, count is grouped every second.
			if (timeDiff < 60000 * 5) {
				incr = 1000L;
			}
			// mins
			else if (timeDiff < 60000 * 60 * 5) {
				incr = 1000L * 60;
				// TODO go back to the nearest lower min
				newMinTs = minTs / (1000 * 60);
				newMinTs = newMinTs * 1000 * 60;
			}
			// hours
			else if (timeDiff < 60000 * 60 * 24 * 5) {
				incr = 1000L * 60 * 60;
			}
			// days
			else if (timeDiff < 60000 * 60 * 24 * 31 * 5) {
				incr = 1000L * 60 * 60 * 24;
			}
			// months
			else if (timeDiff < 60000 * 60 * 24 * 31 * 12 * 5) {
				incr = 1000L * 60 * 60 * 24 * 31;
			}
			// years
			else {
				incr = 1000L * 60 * 60 * 24 * 31 * 12;
			}
			for (long i = newMinTs; i <= maxTs; i = i + incr) {
				// init countMap
				countMap.put(i, 0L);
			}
			// iterate timeline and add counts appropriately
			for (Long t : timeline) {
				Long diff = (t - newMinTs) / incr;
				System.out.println("Debug diff: " + diff);
				// Double key = incr + (Math.ceil(diff) * incr);
				Long key = newMinTs + (diff * incr);
				Long value = countMap.get(key);
				countMap.put(key.longValue(), value + 1);
			}
		}

		// convert countMap to countList for JS in UI
		for (Long t : new TreeSet<Long>(countMap.keySet())) {
			List<Long> coordinate = new ArrayList<Long>();
			coordinate.add(t);
			coordinate.add(countMap.get(t));
			countList.add(coordinate);
		}

		return countList;
	}

}
