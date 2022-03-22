package edu.escuelaing.arep;


import static spark.Spark.*;

import java.io.IOException;
import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import edu.escuelaing.arep.services.LogService;

public class SparkWebServer {

    private static boolean isConnected = false;

    public static void main(String... args) throws IOException {
        port(getPort());
        init();
        get("/status", (req, res) -> isConnected);
        post("/push", (req, res) -> LogService.pushData(req, res));
        connection();
    }

    private static void connection() {
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            System.out.println("Sucessfull conected");
            if (!mongoClient.isLocked()) {
                isConnected = true;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
