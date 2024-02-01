package at.ac.htl.bhitm.server;

import at.ac.htl.bhitm.backend.Item;

public class TemplateMethods {

    private final WebServer webServer;

    public TemplateMethods(WebServer webServer) {
        this.webServer = webServer;
    }

    public String getValidImgPath(Item item) {
        return webServer.getValidImgPath(item);
    }
}
