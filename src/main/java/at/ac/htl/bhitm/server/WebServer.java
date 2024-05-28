package at.ac.htl.bhitm.server;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import at.ac.htl.bhitm.backend.item.*;
import at.ac.htl.bhitm.backend.user.Login;
import at.ac.htl.bhitm.backend.user.User;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class WebServer {
    private ItemManager mng = new ItemManager();
    private ItemFactory factory = new ItemFactory();
    private boolean hasVisited = false;
    private User user;
    Login login = Login.getInstance();
    
    private void updateItems() {
        mng.AddItemsFromFile("./data/reportedItems.csv", factory);
    }

    @Inject
    @Location("login/index.html")
    Template loginTemplate;

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance login(@QueryParam("username") String username, @QueryParam("message") String message) {


        if (username == null) {
            return loginTemplate.data("username", "")
                    .data("message", message != null ? message : "");
        } else if (user != null) {
            return overview(username);
        }

        return loginTemplate.data("username", username)
                .data("message", "User not found");
    }

    @POST
    @Path("/loginUser")
    public Response loginUser(@FormParam("username") String username) throws URISyntaxException {
        user = login.login(username);

        if (user != null) {
            return Response.seeOther(new URI("/overview")).build();
        } else {
            return Response.seeOther(new URI("/login?username=" + username)).build();
        }
    }

    @Inject
    @Location("register/index.html")
    Template registerTemplate;

    @GET
    @Path("/register")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance register() {

        return registerTemplate.data("username", null);
    }

    @POST
    @Path("/registerUser")
    public Response registerUser(@FormParam("username") String username, @FormParam("firstname") String firstname, @FormParam("lastname") String lastname) throws URISyntaxException {

        login.register(new User(firstname, lastname, username));

        return Response.seeOther(new URI("/login")).build();
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
        .data("filter", filter);
        }

    @Inject
    @Location("details/index.html")
    Template detailsTemplate;

    @GET
    @Path("/details")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance details(@QueryParam("index") Integer index){
        if (!hasVisited) {
            updateItems();
            hasVisited = true;
        }

        Item item = null;
        String lostOrFound = "";
        if (index != null) {
            item = mng.getItemById(index);
            lostOrFound = item.getCurrentStatus().toString().equals("LOST") ? "Verlust" : "Fund";
        }

        return detailsTemplate.data("item", item)
        .data("prefix", lostOrFound);    
    }                     

    @Inject
    @Location("report/index.html")
    Template reportTemplate;

    @GET
    @Path("/report")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance report(@QueryParam("i") String line) {
        String message = null;

        if (user == null) {
            return loginTemplate.data("username", "")
                    .data("message", "Login to Report an Item");
        }

        if (line != null) {
            try {
                mng.addItem(factory.createFromString(line));
                mng.AddItemsToFile("./data/reportedItems.csv");
                user.addItem(mng.getItems().get(mng.getItems().size() - 1));

                login.writeToFile();
                message = "Reported Item successfully";
            } catch (Exception e) {
                message = "Reported Item failed";
            }
        }
        return reportTemplate.data("line", line)
                             .data("mng", mng)
                             .data("message", message);
    }
 
    @Inject
    @Location("edit/index.html")
    Template editTemplate;

    @GET
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance edit(@QueryParam("index") Integer index, @QueryParam("title") String title, @QueryParam("desc") String description, @QueryParam("imgPath") String imgPath, @QueryParam("status") ItemLevel status){
        if (!hasVisited) {
            updateItems();
            hasVisited = true;
        }

        if (title != null && imgPath != null) {
            Item item = mng.getItemById(index);
            mng.editItem(item, title, description, status, imgPath);
            mng.AddItemsToFile("./data/reportedItems.csv");
        }

        Item item = null;
        String lostOrFound = "";
        if (index != null) {
            item = mng.getItemById(index);
            lostOrFound = item.getCurrentStatus().toString().equals("LOST") ? "Verlust" : "Fund";
        }

        return editTemplate.data("item", item)
        .data("prefix", lostOrFound);    
    }

    @Inject
    @Location("table/index.html")
    Template tableTemplate;

    @GET
    @Path("/table")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance table(@QueryParam("index") Integer index, @QueryParam("title") String title, @QueryParam("desc") String description, @QueryParam("imgPath") String imgPath, @QueryParam("status") ItemLevel status){
        if (!hasVisited) {
            updateItems();
            hasVisited = true;
        }

        List items = new ArrayList<>(mng.getItems());

        return tableTemplate.data("items", items);    
    }

    @Path("/data")
    public class DataResource {

        @GET
        @Path("/reportedItems.csv")
        @Produces(MediaType.TEXT_PLAIN)
        public Response getReportedItems() {
            File file = new File("./data/reportedItems.csv");
            if (!file.exists()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(file).header("Content-Disposition", "attachment; filename=\"reportedItems.csv\"").build();
        }
    }
}