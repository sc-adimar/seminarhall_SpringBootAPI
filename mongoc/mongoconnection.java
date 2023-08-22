package com.example.demo.mongoc;

import org.bson.Document;
import org.springframework.stereotype.Component;

//import com.demo.demooo.MongoDBSingleton;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Component
public class mongoconnection {
	
	private MongoCollection<Document> collection  = null;
	
	public MongoCollection<Document> getconn(String conn_name ){
		
		mongoconn mongoDBSingleton = mongoconn.getInstance();

        // Use the MongoDB client to perform database operations
        MongoClient mongoClient = mongoDBSingleton.getMongoClient();

        
        MongoDatabase database = mongoClient.getDatabase("College");
         collection = database.getCollection(conn_name);
         return collection;
		
	}

}
