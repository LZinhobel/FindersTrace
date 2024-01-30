package at.ac.htl.bhitm;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class WebServer {
    private ItemManager mng = new ItemManager();
    private ItemFactory factory = new ItemFactory();
    private int visits = 0;
    private void updateItems() {
        mng.AddItemsFromFile("./data/reportedItems.csv", factory);
    }

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
                    <a href="../overview/" id="◀">get started</a>
                </div>
            </body>
            </html>
        """;
    }

    @GET
    @Path("/overview")
    @Produces(MediaType.TEXT_HTML)
    public String overview() {
        if (visits == 0) {
            updateItems();
        }
        ++visits;
        String text =  """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>FindersTrace</title>
                <link rel="stylesheet" href="../style.css">
            </head>
            <body>
                <img src="../img/logo.png" alt="" id="logo">
                <nav>
                    <div class="sites">
                        <div class="linkDiv"
                        ><a href="../welcome" id="welcomeLink">Start</a></div>
                        <div class="linkDiv"><a href="../overview" id="overviewLink">Overview</a></div>
                        <!--<div class="linkDiv"><p>Max Mustermann</p></div>-->
                    </div>
                </nav>
                <select id="filter">
                    <option value="all">All</option>
                    <option value="lost">Lost</option>
                    <option value="found">Found</option>
                </select>
                <div id="📦">
                    """
                            + getAllItems() +
                            """
                </div>
             
                <div id="ReportItemButton" onclick="window.location='../report/'">Report<span> !</span></div>
            </body>
            </html>
                """;
        return text;
    }

    private String getAllItems() {
        String text = "";
        int index = 0;
        for (Item item : mng.getItems()) {
            text += "<div class=\"items\" onclick=\"window.location.href=\'../details?index="+ index + "'\">";

            text += """
                <div class="IImage">
                            <img src=\"
                            """+
                                getValidImgPath(item)
                             +"""
                                \" alt="image">
                        </div>
                        <div class="OInformation">
                            <!-- <div class="UInformation">
                                <h3>Max Mustermann</h3>
                            </div> -->
                            <div class="IIonformaion">
                                <h3>"""
                                + item.getTitle() +
                                """
                                    </h3>
                                <h5>"""
                                    + item.getCurrentStatus() + 
                                """
                                    </h5>
                                <h5>"""
                                    + item.getDatePretty() +
                                """
                                    </h5>
                            </div>
                        </div>
                    </div>
                    """;
            ++index;
        }
        if (text.isEmpty()) {
            text = "<h1>No Items found</h1>";
        }
        return text;
    }

    private String getValidImgPath(Item item) {
        String path = item.getImgPath();
        if (path.equals("No image available")) {
            path = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1200px-No_image_available.svg.png";
        }
        return path;
    }

    @GET
    @Path("/details")
    @Produces(MediaType.TEXT_HTML)
    public String details(@QueryParam("index") Integer index){
        if (visits == 0) {
            updateItems();
        }
        ++visits;
        String text =  """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>FindersTrace</title>
                <link rel="stylesheet" href="../style.css">
            </head>
            <body>
                <img src="../img/logo.png" alt="" id="logo">
                <nav>
                    <div class="sites">
                        <div class="linkDiv"><a href="../welcome" id="welcomeLink">Start</a></div>
                        <div class="linkDiv"><a href="../overview" id="overviewLink">Overview</a></div>
                        <!--<div class="linkDiv"><p>Max Mustermann</p></div>-->
                    </div>
                </nav>
                <select id="filter">
                    <option value="all">All</option>
                    <option value="lost">Lost</option>
                    <option value="found">Found</option>
                </select>
                <div id="details_body">""";
                
        if (index == null) {
                text += "<h1>Item not found</h1>";
            } else {
                Item item = mng.getItems().get(index);
                text += """
                
                        <div class="details_image">
                            <img src=\"
                                """
                                        +getValidImgPath(item)+
                                        """
                            \" alt="image">
                        </div>
                            <div class="IIonformaion">
                                <h3>"""
                                    +item.getTitle()+
                                """
                                    </h3>
                                    <h5>"""
                                    +item.getCurrentStatus()+
                                """
                                    </h5>
                                <h5>"""
                                            +item.getDescription()+
                                            """
                                    </h5>
                                <h5>"""
                                    +item.getDatePretty()+
                                """
                                </h5>
                            </div>
                        </div>
                """;
            }  
            text += """
                </div>
                <div id="ReportItemButton" onclick="window.location=''">Report<span> !</span></div>
            </body>
            </html>
                """;
        return text;
    }

    @GET
    @Path("/report")
    @Produces(MediaType.TEXT_HTML)
    public String report() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>FindersTrace</title>
                <link rel="stylesheet" href="../style.css">
            </head>
            <body>
                <img src="../img/logo.png" alt="" id="logo">
                <nav>
                    <div class="sites">
                        <div class="linkDiv"><a href="../welcome" id="welcomeLink">Start</a></div>
                        <div class="linkDiv"><a href="../overview" id="overviewLink">Overview</a></div>
                        <!--<div class="linkDiv"><p>Max Mustermann</p></div>-->
                    </div>
                </nav>
            </body>
            </html>
                """;
    }
}