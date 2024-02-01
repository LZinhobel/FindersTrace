package at.ac.htl.bhitm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class WebServer {
    private ItemManager mng = new ItemManager();
    private ItemFactory factory = new ItemFactory();
    private boolean hasVisited = false;
    private void updateItems() {
        mng.AddItemsFromFile("./data/reportedItems.csv", factory);
    }

    @GET
    @Path("/overview")
    @Produces(MediaType.TEXT_HTML)
    public String overview(@QueryParam("filter") String filter) {
        if (!hasVisited) {
            updateItems();
            hasVisited = true;
        }

        List<Item> filteredItems;
        if ("LOST".equals(filter)) {
            filteredItems = mng.getItems().stream()
                .filter(item -> filter.equals(item.getCurrentStatus().toString()))
                .collect(Collectors.toList());
        } else if ("FOUND".equals(filter)) {
            filteredItems = mng.getItems().stream()
                .filter(item -> filter.equals(item.getCurrentStatus().toString()))
                .collect(Collectors.toList());
        } else {
            filteredItems = new ArrayList<>(mng.getItems());
        }

        String itemsHtml = getItemsHtml(filteredItems);

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
                <div id="mainNav">
                <img src="../img/logo.png" alt="" id="logo">
                <nav>
                    <div class="sites">
                        <div class="linkDiv"
                        ><a href="../welcome" id="welcomeLink">Start</a></div>
                        <div class="linkDiv"><a href="../overview" id="overviewLink">Overview</a></div>
                        <!--<div class="linkDiv"><p>Max Mustermann</p></div>-->
                    </div>
                </nav>""";
                        
        text += """
                <select id="filter" onchange="location.href = '?filter=' + this.value">
                    <option value="ALL" """
                            + ("ALL".equals(filter) ? " selected" : "") + 
                            """
                                >All</option>
                    <option value="LOST" """
                        + ("LOST".equals(filter) ? " selected" : "") + 
                        """
                            >Lost</option>
                    <option value="FOUND" """
                        + ("FOUND".equals(filter) ? " selected" : "") + 
                        """
                            >Found</option>
                </select>
                """;
                        
        text += """
                </div>
                <div id="📦">
                    """
                            + itemsHtml +
                            """
                </div>
             
                <div id="ReportItemButton" onclick="window.location='../report'">Report<span> !</span></div>
            </body>
            </html>
                """;
        return text;
    }

    private String getItemsHtml(List<Item> filteredItems) {
        String text = "";
        for (Item item : filteredItems) {
            text += "<div class=\"items\" onclick=\"window.location.href=\'../details?index="+ item.getId() + "'\">";

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
        if (!hasVisited) {
            updateItems();
            hasVisited = true;
        }

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
                <div id="mainNav">
                <img src="../img/logo.png" alt="" id="logo">
                <nav  style="width: 69%; margin-left: -15%;">
                    <div class="sites">
                        <div class="linkDiv"><a href="../welcome" id="welcomeLink">Start</a></div>
                        <div class="linkDiv"><a href="../overview" id="overviewLink">Overview</a></div>
                        <!--<div class="linkDiv"><p>Max Mustermann</p></div>-->
                    </div>
                </nav>
                </div>
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
                            <div class="IIonformaion" style="transform: scale(1.4);">
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
                <div id="ReportItemButton" onclick="window.location='../report'">Report<span> !</span></div>
            </body>
            </html>
                """;
        return text;
    }

    @GET
    @Path("/report")
    @Produces(MediaType.TEXT_HTML)
    public String report(@QueryParam("i") String line){
        String text = "";

        text += """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>FindersTrace</title>
                <link rel="stylesheet" href="../style.css">
            </head>
            <body>
                <div id="mainNav">
                <img src="../img/logo.png" alt="" id="logo">
                <nav  style="width: 85%;">
                    <div class="sites">
                        <div class="linkDiv"><a href="../welcome" id="welcomeLink">Start</a></div>
                        <div class="linkDiv"><a href="../overview" id="overviewLink">Overview</a></div>
                        <!--<div class="linkDiv"><p>Max Mustermann</p></div>-->
                    </div>
                </nav>
                </div>""";


        
        if  (line != null) {
            try {
                mng.addItem(factory.createFromString(line));
                text += "<form><h1>Reported Item successfully</h1></form>";
                mng.AddItemsToFile("./data/reportedItems.csv");
            } catch (Exception e) {
                text += "<form><h1>Reported Item failed</h1></form>";
            }
        } else {
                        
        text += """
            <form id="reportForm" method="get" action="../report">
                <h1>Report an Item</h1>
                <div class="inputs">
                    <label for="RTitel">Titel</label>
                    <input id="RTitel" placeholder="TITEL"></input>
                </div>

                <div class="inputs">
                    <label for="RDescription">Description</label>
                    <textarea id="RDescription" placeholder="DESCRIPTION"></textarea>
                </div>

                <div class="inputs">
                    <label for="RImage">Image URL</label>
                    <input id="RImage" placeholder="IMAGE URL (optional) | x if no img available"></input>
                </div>

                <div id="LorF">
                    <input type="radio" id="lost" name="lostOrFound" value="LOST" checked>LOST</input>
                    <input type="radio" id="found" name="lostOrFound" value="FOUND">FOUND</input>
                </div>

                <input id="RSubmit" type="submit" value="SUBMIT"></input>
            </form>

            <script>
                document.getElementById('reportForm').addEventListener('submit', function(event) {
                    event.preventDefault();
                    var title = document.getElementById('RTitel').value ?? '';
                    var description = document.getElementById('RDescription').value;
                    var image = document.getElementById('RImage').value;
                    var status = document.querySelector('input[name="lostOrFound"]:checked').value;
                    if (title == '') {
                        console.log("Title is empty");
                        document.getElementById('RTitel').style.border = '2px solid red';
                        document.getElementById('RDescription').value = description;
                        document.getElementById('RImage').value = image;
                        return;
                    }

                    var csv = title + ';' + description + ';' + status + ';' + image;
                    window.location.href = this.action + '?i=' + encodeURIComponent(csv);
                });
            </script>
            """;
        }      
        text += """
        </body>
        </html>
            """;

        return text;
    }
}