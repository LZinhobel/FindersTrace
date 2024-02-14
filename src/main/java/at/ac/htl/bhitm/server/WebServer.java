package at.ac.htl.bhitm.server;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import at.ac.htl.bhitm.backend.Item;
import at.ac.htl.bhitm.backend.ItemFactory;
import at.ac.htl.bhitm.backend.ItemManager;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
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

    @Inject
    @Location("overview/index.html")
    Template overviewTemplate;

    @GET
    @Path("/overview")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance overview(@QueryParam("filter") String filter) {
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

        return overviewTemplate.data("filteredItems", filteredItems)
        .data("filter", filter)
        .data("templateMethods", new TemplateMethods(this));
        }

    public String getValidImgPath(Item item) {
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
                        <div class="linkDiv"><a href="../" id="welcomeLink">Start</a></div>
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

    @Inject
    @Location("report/index.html")
    Template reportTemplate;

    @GET
    @Path("/report")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance report(@QueryParam("i") String line) {

        return reportTemplate.data("line", line)
        .data("mng", mng);
    }
    

    // @GET
    // @Path("/report")
    // @Produces(MediaType.TEXT_HTML)
    // public String report(@QueryParam("i") String line){
    //     String text = "";

    //     text += """
    //         <!DOCTYPE html>
    //         <html lang="en">
    //         <head>
    //             <meta charset="UTF-8">
    //             <title>FindersTrace</title>
    //             <link rel="stylesheet" href="../style.css">
    //         </head>
    //         <body>
    //             <div id="mainNav">
    //             <img src="../img/logo.png" alt="" id="logo">
    //             <nav  style="width: 85%;">
    //                 <div class="sites">
    //                     <div class="linkDiv"><a href="../" id="welcomeLink">Start</a></div>
    //                     <div class="linkDiv"><a href="../overview" id="overviewLink">Overview</a></div>
    //                     <!--<div class="linkDiv"><p>Max Mustermann</p></div>-->
    //                 </div>
    //             </nav>
    //             </div>""";


        
    //     if  (line != null) {
    //         try {
    //             mng.addItem(factory.createFromString(line));
    //             text += "<form><h1>Reported Item successfully</h1></form>";
    //             mng.AddItemsToFile("./data/reportedItems.csv");
    //         } catch (Exception e) {
    //             text += "<form><h1>Reported Item failed</h1></form>";
    //         }
    //     } else {
                        
    //     text += """
    //         <form id="reportForm" method="get" action="../report">
    //             <h1>Report an Item</h1>
    //             <div class="inputs">
    //                 <label for="RTitel">Titel</label>
    //                 <input id="RTitel" placeholder="TITEL"></input>
    //             </div>

    //             <div class="inputs">
    //                 <label for="RDescription">Description</label>
    //                 <textarea id="RDescription" placeholder="DESCRIPTION"></textarea>
    //             </div>

    //             <div class="inputs">
    //                 <label for="RImage">Image URL</label>
    //                 <input id="RImage" placeholder="IMAGE URL (optional) | x if no img available"></input>
    //             </div>

    //             <div id="LorF">
    //                 <input type="radio" id="lost" name="lostOrFound" value="LOST" checked>LOST</input>
    //                 <input type="radio" id="found" name="lostOrFound" value="FOUND">FOUND</input>
    //             </div>

    //             <input id="RSubmit" type="submit" value="SUBMIT"></input>
    //         </form>

    //         <script>
    //             document.getElementById('reportForm').addEventListener('submit', function(event) {
    //                 event.preventDefault();
    //                 var title = document.getElementById('RTitel').value ?? '';
    //                 var description = document.getElementById('RDescription').value;
    //                 var image = document.getElementById('RImage').value;
    //                 var status = document.querySelector('input[name="lostOrFound"]:checked').value;
    //                 if (title == '') {
    //                     console.log("Title is empty");
    //                     document.getElementById('RTitel').style.border = '2px solid red';
    //                     document.getElementById('RDescription').value = description;
    //                     document.getElementById('RImage').value = image;
    //                     return;
    //                 }

    //                 var csv = title + ';' + description + ';' + status + ';' + image;
    //                 window.location.href = this.action + '?i=' + encodeURIComponent(csv);
    //             });
    //         </script>
    //         """;
    //     }      
    //     text += """
    //     </body>
    //     </html>
    //         """;

    //     return text;
    // }
}