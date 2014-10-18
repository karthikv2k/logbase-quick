package controllers;

import io.logbase.api.ui.workspace.Workspace;
import io.logbase.api.ui.workspace.WorkspaceUtils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class WorkspaceController extends Controller {

  @BodyParser.Of(BodyParser.Json.class)
  public static Result saveWorkspace() {
    JsonNode req = request().body().asJson();
    if (req == null) {
      return badRequest("Expecting Json data");
    } else {
      ObjectMapper mapper = new ObjectMapper();
      Workspace workspace = null;
      Logger.info("Request received is: " + req.toString());
      try {
        workspace = mapper.readValue(req.toString(), Workspace.class);
      } catch (JsonParseException e) {
        Logger.error("Error while mapping to Query Request: " + e);
      } catch (JsonMappingException e) {
        Logger.error("Error while mapping to Query Request: " + e);
      } catch (IOException e) {
        Logger.error("Error while mapping to Query Request: " + e);
      }
      if ((workspace == null) || (workspace.getName() == null))
        return badRequest("Workspace name undefined");
      else {
        WorkspaceUtils.saveWorkspace(workspace);
        return ok();
      }
    }
  }

  public static Result getWorkspace(String wsName) {
    if (WorkspaceUtils.isSavedWorkspace(wsName)) {
      Workspace ws = WorkspaceUtils.getWorkspace(wsName);
      ObjectMapper mapper = new ObjectMapper();
      JsonNode result = mapper.valueToTree(ws);
      return ok(result);
    } else {
      return badRequest("Workspace does not exist");
    }
  }

  public static Result deleteWorkspace(String wsName) {
    WorkspaceUtils.deleteWorkspace(wsName);
    return ok();
  }

  public static Result checkWorkspaceExists(String wsName) {
    ObjectNode result = Json.newObject();
    if (WorkspaceUtils.isSavedWorkspace(wsName))
      result.put("exists", true);
    else
      result.put("exists", false);
    return ok(result);
  }

}
