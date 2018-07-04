package com.vdp.rest.wss.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;

public class MongoApi {

	private MongoApi		instance;
	private String			dbURI	= "mongodb://thierry:rgjlvdp@192.168.2.120:27017";
	private MongoClient		mongo;
	private MongoCredential	credential;
	private MongoDatabase	database;

	public MongoApi() {
		createConnection();
	}

	public synchronized MongoApi getInstance() {
		if (instance == null) {
			instance = new MongoApi();
		}
		return instance;
	}

	private void createConnection() {
		try {

			mongo = new MongoClient(new MongoClientURI(dbURI));
			credential = MongoCredential.createCredential("thierry", "dico", "rgjlvdp".toCharArray());
			database = mongo.getDatabase("dico");

			System.out.println("Mongo ::" + mongo);
			System.out.println("Credentials ::" + credential);
			System.out.println("Database ::" + database);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
