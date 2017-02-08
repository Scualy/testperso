import spark.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import static spark.Spark.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
	get("/", (req, res) -> {
		return "<form action='/upload' method='post' enctype='multipart/form-data'>\n"+
		"\t<input type='file' name='uploaded_file'/>\n"+
		"\t<input type='submit' value='Envoyer'/>\n"+
		"\t</form>\n";
	});
    post("/upload", (request, response) -> {
		request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/tmp"));
		String theString = "oups!";


		try (InputStream is = request.raw().getPart("uploaded_file").getInputStream()) {
			//
		}
/*
		//return IOUtils.toString(is, "UTF-8");
		StringBuilder textBuilder = new StringBuilder();
		try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
			int c = 0;
			while ((c = reader.read()) != -1) {
				textBuilder.append((char) c);
			}
			theString = textBuilder.toString();
		}*/
		return theString;
	});
  }
}
