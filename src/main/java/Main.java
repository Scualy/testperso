
import spark.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import com.google.common.io.CharStreams;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

	port(Integer.valueOf(System.getenv("PORT")));
		get("/", (req, res) -> {
		return "<form action='/upload' method='post' enctype='multipart/form-data'>\n"+
		"\t<input type='file' name='uploaded_file'/>\n"+
		"\t<input type='submit' value='Envoyer'/>\n"+
		"\t</form>\n";
	});

        post("/upload", (req, res) -> {

            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
			String theString = "Oups! ";
            try (InputStream input = req.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
				String FileName = req.raw().getPart("uploaded_file").getSubmittedFileName();
				theString = FileName;
			}
			return theString;
        });

    }
	private static String parseToHTMLUsingApacheTikka(String file,InputStream stream){
		//	throws IOException, SAXException, TikaException {
		// determine extension
		String ext = FilenameUtils.getExtension(file);
		String outputFileFormat = "";
		// ContentHandler handler;
		if (ext.equalsIgnoreCase("html") | ext.equalsIgnoreCase("pdf")
				| ext.equalsIgnoreCase("doc") | ext.equalsIgnoreCase("docx")) {
			outputFileFormat = ".html";
		} else if (ext.equalsIgnoreCase("txt") | ext.equalsIgnoreCase("rtf")) {
			outputFileFormat = ".txt";
		} else {
			return "Input format of the file " + file + " is not supported.";
		}

		ContentHandler handler = new ToXMLContentHandler();
		AutoDetectParser parser = new AutoDetectParser();
		Metadata metadata = new Metadata();
		parser.parse(stream, handler, metadata);
		return handler.toString();
	}
}



/*import spark.*;
/*import javax.servlet.*;
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

		InputStream is = null;
		Part = request.raw().getPart("uploaded_file");
		
		is = Part.getInputStream();
		/*try (is = request.raw().getPart("uploaded_file").getInputStream()) {
			//
		}
		try (InputStream is = request.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }*/
/*
		//return IOUtils.toString(is, "UTF-8");
		StringBuilder textBuilder = new StringBuilder();
		try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
			int c = 0;
			while ((c = reader.read()) != -1) {
				textBuilder.append((char) c);
			}
			theString = textBuilder.toString();
		}
		return theString;
	});
  }
}
*/