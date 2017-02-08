
import spark.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import com.google.common.io.CharStreams;
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
	private static File parseToHTMLUsingApacheTikka(String file)
			throws IOException, SAXException, TikaException {
		// determine extension
		String ext = FilenameUtils.getExtension(file);
		String outputFileFormat = "";
		// ContentHandler handler;
		if (ext.equalsIgnoreCase("html") | ext.equalsIgnoreCase("pdf")
				| ext.equalsIgnoreCase("doc") | ext.equalsIgnoreCase("docx")) {
			outputFileFormat = ".html";
			// handler = new ToXMLContentHandler();
		} else if (ext.equalsIgnoreCase("txt") | ext.equalsIgnoreCase("rtf")) {
			outputFileFormat = ".txt";
		} else {
			System.out.println("Input format of the file " + file
					+ " is not supported.");
			return null;
		}
		String OUTPUT_FILE_NAME = FilenameUtils.removeExtension(file)
				+ outputFileFormat;
		ContentHandler handler = new ToXMLContentHandler();
		// ContentHandler handler = new BodyContentHandler();
		// ContentHandler handler = new BodyContentHandler(
		// new ToXMLContentHandler());
		InputStream stream = new FileInputStream(file);
		AutoDetectParser parser = new AutoDetectParser();
		Metadata metadata = new Metadata();
		try {
			parser.parse(stream, handler, metadata);
			FileWriter htmlFileWriter = new FileWriter(OUTPUT_FILE_NAME);
			htmlFileWriter.write(handler.toString());
			htmlFileWriter.flush();
			htmlFileWriter.close();
			return new File(OUTPUT_FILE_NAME);
		} finally {
			stream.close();
		}
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