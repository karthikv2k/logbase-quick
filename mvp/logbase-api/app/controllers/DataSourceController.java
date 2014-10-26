package controllers;

import java.io.File;

import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

public class DataSourceController extends Controller {

	public static Result upload() {
		  MultipartFormData body = request().body().asMultipartFormData();
		  FilePart picture = body.getFile("datasource");
		  if (picture != null) {
		    String fileName = picture.getFilename();
		    String contentType = picture.getContentType(); 
		    File file = picture.getFile();
		    //TODO
		    //File ingestion
		    
		    return ok("File uploaded");
		  } else {
		    return internalServerError("File upload failed.");
		  }
		}
	
}
