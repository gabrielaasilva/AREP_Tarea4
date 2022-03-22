package edu.escuelaing.arep.services;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import spark.Request;
import spark.Response;

public class LogService {

    static Gson gson = new Gson();

    public static List<String> pushData(Request req, Response res) throws UnknownHostException {
        List<String> lastTenDocuments = new ArrayList<>();
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB database = mongoClient.getDB("logs");
            DBCollection collection = database.getCollection("cadenas");
            collection.save(new BasicDBObject("cad", req.body()));
            DBCursor cursor = collection.find();
            while (cursor.hasNext()) {
                System.out.println(gson.toJson(cursor.next()));
                lastTenDocuments.add(gson.toJson(cursor.next()));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return lastTenDocuments;
    }
}
