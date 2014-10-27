package controllers;

import io.logbase.api.ui.upload.UploadUtils;

import java.io.File;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

public class DataSourceController extends Controller {
	  
	public static Result upload(String alias) {
		File file = request().body().asRaw().asFile();
		Logger.info("Received file at: " + file.getAbsolutePath());
		UploadUtils.ingestFile(file.getAbsolutePath(), alias);
		return ok("File uploaded");
	}
	
}
