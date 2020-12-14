package dk.bierproductie.opc_ua_client;

import com.sun.net.httpserver.HttpServer;
import com.sun.xml.bind.v2.model.core.LeafInfo;
import dk.bierproductie.opc_ua_client.handlers.HTTPCMDHandler;
import dk.bierproductie.opc_ua_client.handlers.HTTPHandler;
import dk.bierproductie.opc_ua_client.handlers.HandlerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTTPServer {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        LOGGER.setUseParentHandlers(false);
        CLIFormatter cliFormatter = new CLIFormatter();
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(cliFormatter);
        LOGGER.addHandler(consoleHandler);
        try {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
            server.createContext("/batch", new HTTPHandler());
            server.createContext("/command", new HTTPCMDHandler());
            server.setExecutor(threadPoolExecutor);
            HandlerFactory.getInstance(true);
            server.start();
            LOGGER.log(Level.INFO, "Server started.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
