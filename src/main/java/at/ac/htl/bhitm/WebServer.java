package at.ac.htl.bhitm;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class WebServer {

    @GET
    @Path("/welcome")
    @Produces(MediaType.TEXT_HTML)
    public String welcome() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>FindersTrace</title>
                <link rel="stylesheet" href="./style.css">
            </head>
            <body>
                <img src="./img/logo.png" alt="" id="welcomeLogo">
                <div id="welcomeDIv">
                    <h1 id="title">FindersTrace</h1>    
                    <a href="../overview/index.html" id="◀">get started</a>
                </div>
            </body>
            </html>
        """;
    }
}
