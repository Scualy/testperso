import static spark.Spark.*;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
	get("/", (req, res) -> {
		return "<form action='/upload' method='post' enctype='multipart/form-data'>\n"+
		"\t<input type='file' name='uploaded_file'/>\n"+
		"\t<input type='submit' value='Envoyer'/>\n"+
		"\t</form>\n");

    post("/upload", (request, response) -> {
		request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
			String theString = "oups!"
			try (InputStream is = request.raw().getPart("uploaded_file").getInputStream()) {
				theString = IOUtils.toString(myInputStream, "UTF-8");
			}
			return theString;
		});

  }

}
