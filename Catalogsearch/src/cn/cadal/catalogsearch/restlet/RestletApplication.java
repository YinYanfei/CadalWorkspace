/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.cadal.catalogsearch.restlet;

import java.io.InputStream;
import java.util.Properties;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.Route;
import org.restlet.Router;
import org.restlet.Server;
import org.restlet.data.Protocol;

/**
 *
 * @author Chenxing
 */
public class RestletApplication extends Application {

    private static final String defaultPath = "/catalogsearch";
    private static final int defaultPort = 8000;
    private static String path = defaultPath;
    private static int port = defaultPort;

    static {
        try {
            String configLocation = String.format("%s/config.properties", RestletApplication.class.getPackage().getName().replace('.', '/'));
            InputStream inputStream = RestletApplication.class.getClassLoader().getResourceAsStream(configLocation);
            if (inputStream != null) {
                Properties p = new Properties();
                p.load(inputStream);
                path = p.getProperty("path", defaultPath);
                String portString = p.getProperty("port");
                if (portString != null) {
                    port = Integer.parseInt(portString);
                }
            }
        } catch (Exception ex) {
            System.err.print(ex);
            ex.printStackTrace();
        }
    }

    public RestletApplication() {
        super(null);
    }

    public RestletApplication(Context context) {
        super(context);
    }

    @Override
    public Restlet createRoot() {
        Router router = new Router(getContext());
        Route route = router.attach(String.format("%s/{type}", path), RestletResource.class);
        route.extractQuery("start", "start", true);
        route.extractQuery("number", "number", true);
        route.extractQuery("query", "query", true);
        route.extractQuery("format", "format", true);
        route.extractQuery("jsoncallback", "jsoncallback", true);
        return router;
    }

    public static void main(String[] args) {
        Server server = new Server(Protocol.HTTP, port, new RestletApplication());
        System.out.println("Start catalog search REST Server...");
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}