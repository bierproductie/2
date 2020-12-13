package dk.bierproductie.opc_ua_client;
import com.sun.net.httpserver.HttpServer;
import dk.bierproductie.opc_ua_client.handlers.HTTPHandler;
import dk.bierproductie.opc_ua_client.handlers.HandlerFactory;

import java.net.InetSocketAddress;

public class HTTPServer {

    public static void main(String[] args) {
        try{
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
            server.createContext("/batch", new HTTPHandler());
            HandlerFactory.getInstance(true);
            server.start();
            System.out.println("Server Started");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
