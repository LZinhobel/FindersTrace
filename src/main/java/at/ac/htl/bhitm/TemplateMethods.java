package at.ac.htl.bhitm;

public class TemplateMethods {

    private final WebServer webServer;

    public TemplateMethods(WebServer webServer) {
        this.webServer = webServer;
    }

    public String getValidImgPath(Item item) {
        return webServer.getValidImgPath(item);
    }
}
