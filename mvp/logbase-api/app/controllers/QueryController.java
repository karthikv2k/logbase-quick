package controllers;

import java.io.IOException;
import java.util.List;

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
      if ((queryRequest == null) || (queryRequest.getArgs() == null)) {
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
      // Fire query
      List<String> events = QueryUtils.getEvents(reqid);
      ArrayNode result = mapper.valueToTree(events);
      return ok(result);
    }
  }

  public static Result eventsP(int reqid, long offset, int max) {
    Logger.info("Processing events for query request: " + reqid);
    if (!QueryUtils.isValidRequest(reqid)) {
      return notFound("Request Id: " + reqid + " not found.");
    } else {
      ObjectMapper mapper = new ObjectMapper();
      // Fire query
      List<String> events = QueryUtils.getEventsP(reqid, offset, max);
      ArrayNode result = mapper.valueToTree(events);
      return ok(result);
    }
  }

  public static Result eventsCount(int reqid) {
    Logger.info("Processing events for query request: " + reqid);
    if (!QueryUtils.isValidRequest(reqid)) {
      return notFound("Request Id: " + reqid + " not found.");
    } else {
      // Fire query
      Long eventCount = QueryUtils.getEventsCount(reqid);
      ObjectNode result = Json.newObject();
      result.put("eventscount", eventCount);
      return ok(result);
    }
  }

}
