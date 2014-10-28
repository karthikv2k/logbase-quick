package controllers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.logbase.node.Node;
import io.logbase.node.NodeConnector;
import io.logbase.node.impl.SimpleRealtimeNodeConnector;
import io.logbase.querying.optiq.LBSchema;
import io.logbase.querying.optiq.QueryExecutor;
import io.logbase.utils.InFilter;
import io.logbase.view.View;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

	public static Result preflight(String all) {
		response().setHeader("Access-Control-Allow-Origin", "*");
		response().setHeader("Allow", "*");
		response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
		response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
		return ok();
	}

}
