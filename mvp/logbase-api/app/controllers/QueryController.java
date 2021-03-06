package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.logbase.api.ui.query.QueryRequest;
import io.logbase.api.ui.query.QueryUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.Logger;

public class QueryController extends Controller {

  @BodyParser.Of(BodyParser.Json.class)
  public static Result query() {
    JsonNode req = request().body().asJson();

    if (req == null) {
      return badRequest("Expecting Json data");
    } else {

      ObjectMapper mapper = new ObjectMapper();
      QueryRequest queryRequest = null;
      Logger.info("Request received is: " + req.toString());
      try {
        queryRequest = mapper.readValue(req.toString(), QueryRequest.class);
      } catch (JsonParseException e) {
        Logger.error("Error while mapping to Query Request: " + e);
      } catch (JsonMappingException e) {
        Logger.error("Error while mapping to Query Request: " + e);
      } catch (IOException e) {
        Logger.error("Error while mapping to Query Request: " + e);
      }

      // Input data validations
      if ((queryRequest == null) || (queryRequest.getQuery() == null)) {
        return badRequest("Search argument missing");
      }

      int reqid = QueryUtils.postQuery(queryRequest);
      ObjectNode result = Json.newObject();
      result.put("reqid", reqid);
      return ok(result);
    }
  }

  public static Result events(int reqid) {
    Logger.info("Processing events for query request: " + reqid);
    if (!QueryUtils.isValidRequest(reqid)) {
      return notFound("Request Id: " + reqid + " not found.");
    } else {
      ObjectMapper mapper = new ObjectMapper();
      List<String> events = QueryUtils.getEvents(reqid);
      ArrayNode result = mapper.valueToTree(events);
      return ok(result);
    }
  }

  public static Result eventsP(int reqid, long offset, int max) {
    Logger.info("Processing paginated events for query request: " + reqid);
    if (!QueryUtils.isValidRequest(reqid)) {
      return notFound("Request Id: " + reqid + " not found.");
    } else {
      ObjectMapper mapper = new ObjectMapper();
      List<String> events = QueryUtils.getEventsP(reqid, offset, max);
      ArrayNode result = mapper.valueToTree(events);
      return ok(result);
    }
  }

  public static Result eventsCount(int reqid) {
    Logger.info("Processing event count for query request: " + reqid);
    if (!QueryUtils.isValidRequest(reqid)) {
      return notFound("Request Id: " + reqid + " not found.");
    } else {
      Long eventCount = QueryUtils.getEventsCount(reqid);
      ObjectNode result = Json.newObject();
      result.put("eventscount", eventCount);
      return ok(result);
    }
  }

  public static Result columns(int reqid) {
    Logger.info("Processing columns for query request: " + reqid);
    if (!QueryUtils.isValidRequest(reqid)) {
      return notFound("Request Id: " + reqid + " not found.");
    } else {
      // Fire query
      List<String> columns = QueryUtils.getColumns(reqid);
      ObjectMapper mapper = new ObjectMapper();
      ArrayNode result = mapper.valueToTree(columns);
      return ok(result);
    }
  }
  
  @BodyParser.Of(BodyParser.Json.class)
  public static Result createTable() {
    JsonNode req = request().body().asJson();
    if (req == null) {
      return badRequest("Expecting Json data");
    } else {
      int reqid = req.findPath("reqid").intValue();
      List<String> columns = new ArrayList<String>();
      ArrayNode columnArray = (ArrayNode) req.findPath("columns");
      for (JsonNode jn : columnArray) {
        columns.add(jn.textValue());
      }
      QueryUtils.createTableColumns(reqid, columns);
      Logger.info("Created table with columns: " + columns + " and reqid: "
          + reqid);
      return ok();
    }
  }

  public static Result table(int reqid) {
    Logger.info("Processing table for query request: " + reqid);
    if (!QueryUtils.isValidTable(reqid)) {
      return notFound("Request Id: " + reqid + " not found.");
    } else {
      List<Map<String, Object>> tabularResults = QueryUtils.getTable(reqid);
      ObjectMapper mapper = new ObjectMapper();
      ArrayNode result = mapper.valueToTree(tabularResults);
      return ok(result);
    }
  }

  public static Result tableP(int reqid, long offset, int max) {
    Logger.info("Processing paginated table for query request: " + reqid);
    if (!QueryUtils.isValidTable(reqid)) {
      return notFound("Request Id: " + reqid + " not found.");
    } else {
      ObjectMapper mapper = new ObjectMapper();
      List<Map<String, Object>> tabularResults = QueryUtils.getTableP(reqid,
          offset, max);
      ArrayNode result = mapper.valueToTree(tabularResults);
      return ok(result);
    }
  }

  public static Result tableRowCount(int reqid) {
    Logger.info("Processing table row count for query request: " + reqid);
    if (!QueryUtils.isValidTable(reqid)) {
      return notFound("Request Id: " + reqid + " not found.");
    } else {
      Long tableRowCount = QueryUtils.getTableRowCount(reqid);
      ObjectNode result = Json.newObject();
      result.put("tablerowcount", tableRowCount);
      return ok(result);
    }
  }

  public static Result clearTable(int reqid) {
    Logger.info("Clearing table query request: " + reqid);
    QueryUtils.clearTable(reqid);
    return ok();
  }

  public static Result clearQuery(int reqid) {
    Logger.info("Clearing query request: " + reqid);
    QueryUtils.clearQuery(reqid);
    return ok();
  }

  public static Result timeline(int reqid) {
    Logger.info("Processing timeline for query request: " + reqid);
    if (!QueryUtils.isValidRequest(reqid)) {
      return notFound("Request Id: " + reqid + " not found.");
    } else {
      ObjectMapper mapper = new ObjectMapper();
      List<List<Long>> timeline = QueryUtils.getTimeline(reqid);
      ArrayNode result = mapper.valueToTree(timeline);
      return ok(result);
    }
  }
  
  @BodyParser.Of(BodyParser.Json.class)
  public static Result createPlot() {
    JsonNode req = request().body().asJson();
    if (req == null) {
      return badRequest("Expecting Json data");
    } else {
      int reqid = req.findPath("reqid").intValue();
      List<String> columns = new ArrayList<String>();
      ArrayNode columnArray = (ArrayNode) req.findPath("columns");
      for (JsonNode jn : columnArray) {
        columns.add(jn.textValue());
      }
      QueryUtils.createPlotColumns(reqid, columns);
      Logger.info("Created plot with columns: " + columns + " and reqid: "
          + reqid);
      return ok();
    }
  }

  public static Result plot(int reqid) {
    Logger.info("Processing plot for query request: " + reqid);
    if (!QueryUtils.isValidPlot(reqid)) {
      return notFound("Request Id: " + reqid + " not found.");
    } else {
      List<Map<String, Object>> plotResults = QueryUtils.getPlot(reqid);
      ObjectMapper mapper = new ObjectMapper();
      ArrayNode result = mapper.valueToTree(plotResults);
      return ok(result);
    }
  }

  public static Result plotP(int reqid, long offset, int max) {
    Logger.info("Processing paginated plot for query request: " + reqid);
    if (!QueryUtils.isValidPlot(reqid)) {
      return notFound("Request Id: " + reqid + " not found.");
    } else {
      ObjectMapper mapper = new ObjectMapper();
      List<Map<String, Object>> plotResults = QueryUtils.getPlotP(reqid,
          offset, max);
      ArrayNode result = mapper.valueToTree(plotResults);
      return ok(result);
    }
  }

  public static Result plotRowCount(int reqid) {
    Logger.info("Processing plot row count for query request: " + reqid);
    if (!QueryUtils.isValidPlot(reqid)) {
      return notFound("Request Id: " + reqid + " not found.");
    } else {
      Long plotRowCount = QueryUtils.getPlotRowCount(reqid);
      ObjectNode result = Json.newObject();
      result.put("plotrowcount", plotRowCount);
      return ok(result);
    }
  }

  public static Result clearPlot(int reqid) {
    Logger.info("Clearing plot query request: " + reqid);
    QueryUtils.clearPlot(reqid);
    return ok();
  }


}
