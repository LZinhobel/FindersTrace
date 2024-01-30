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
        return """
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
                        <div class="linkDiv"><p>Max Mustermann</p></div>
                    </div>
                </nav>
                <select id="filter">
                    <option value="all">All</option>
                    <option value="lost">Lost</option>
                    <option value="found">Found</option>
                </select>
                <div id="📦">
                    <div class="items">
                        <div class="IImage">
                            <img src="https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/iphone-card-40-iphone15prohero-202309_FMT_WHH?wid=508&hei=472&fmt=p-jpg&qlt=95&.v=1693086369818" alt="image">
                        </div>
                        <div class="OInformation">
                            <!-- <div class="UInformation">
                                <h3>Max Mustermann</h3>
                            </div> -->
                            <div class="IIonformaion">
                                <h3>iPhone 13 Pro</h3>
                                <h5>LOST</h5>
                                <h5>01.01.2024</h5>
                            </div>
                        </div>
                    </div>
                    <div class="items">
                        <div class="IImage">
                            <img src="https://knize.at/cdn/shop/products/Knize_Wallet_Billfold_Schwarz_1_900x.jpg?v=1620213986" alt="image">
                        </div>
                        <div class="OInformation">
                            <!-- <div class="UInformation">
                                <h3>Max Mustermann</h3>
                            </div> -->
                            <div class="IIonformaion">
                                <h3>Geldtasche</h3>
                                <h5>FOUND</h5>
                                <h5>31.12.2023</h5>
                            </div>
                        </div>
                    </div>
                </div>
             
                <div id="ReportItemButton" onclick="window.location=''">Report<span> !</span></div>

            </body>
            </html>
                """;
    }



    @GET
    @Path("/details")
    @Produces(MediaType.TEXT_HTML)
    public String details() {
        return """
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
                        <div class="linkDiv"><p>Max Mustermann</p></div>
                    </div>
                </nav>
                <select id="filter">
                    <option value="all">All</option>
                    <option value="lost">Lost</option>
                    <option value="found">Found</option>
                </select>
                <div id="details_body">
                        <div class="details_image">
                            <img src="https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/iphone-card-40-iphone15prohero-202309_FMT_WHH?wid=508&hei=472&fmt=p-jpg&qlt=95&.v=1693086369818" alt="image">
                        </div>
                            <div class="IIonformaion">
                                <h3>iPhone 13 Pro</h3>
                                <h5>LOST</h5>
                                <h5>01.01.2024</h5>
                            </div>
                        </div>
                </div>
             
                <div id="ReportItemButton" onclick="window.location=''">Report<span> !</span></div>

            </body>
            </html>
                """;
    }
}
