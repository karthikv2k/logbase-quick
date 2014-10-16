package controllers;

import io.logbase.api.ui.query.QueryUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class QueryController extends Controller {

  @BodyParser.Of(BodyParser.Json.class)
  public static Result query() {
    JsonNode req = request().body().asJson();

    if (req == null) {
      return badRequest("Expecting Json data");
    } else {

      ObjectNode result = Json.newObject();
      String args = req.findPath("args").textValue();
      long from = req.findPath("from").longValue();
      long to = req.findPath("to").longValue();
      int dataset = req.findPath("dataset").intValue();
      String source = req.findPath("source").textValue();

      // Input data validations
      if (args == null) {
        return badRequest("Search argument missing");
      }
      
      int reqid = QueryUtils.postQuery(args, from, to, dataset, source);
      result.put("reqid", reqid);
      return ok(result);
    }
  }
}
