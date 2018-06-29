package com.vdp.rest.wss.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;

@Path("/")
public class RestService {

	//	private Connection mysqlConnection;

	@PostConstruct
	public void postConstruct() {
		createConnection();
	}

	@PreDestroy
	public void preDestroy() {
		//		closeConnection(mysqlConnection);
	}

	@POST
	@Path("/restserv")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crunchifyREST(InputStream incomingData) {
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());

		// return HTTP response 200 in case of success
		//		return Response.status(200).entity(crunchifyBuilder.toString()).build();
		String result = "Json saved";
		return Response.status(201).entity(result).build();
	}

	// http://192.168.2.171:8080/com.vdp.rest.wss/api/verify

	@GET
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService() {
		String result = "CrunchifyRESTService Successfully started..";
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}

	private void createConnection() {
		//		try {
		//			Class.forName("com.mysql.jdbc.Driver");
		//			return DriverManager.getConnection("jdbc:mysql://192.168.2.17/COMPUFIRST_2?user=root&password=Tdfxkp89;)");
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//		return null;

		try {

			String dbURI = "mongodb://thierry:rgjlvdp@192.168.2.69:27017";
			MongoClient mongo = new MongoClient(new MongoClientURI(dbURI));

			//			MongoClient mongo = new MongoClient("192.168.2.69", 27017);

			// Creating Credentials 
			MongoCredential credential;
			credential = MongoCredential.createCredential("thierry", "dico", "rgjlvdp".toCharArray());
			// Accessing the database 
			MongoDatabase database = mongo.getDatabase("dico");
			System.out.println("Credentials ::" + credential);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//	private String runQuery(String _query) {
	//		Statement statement = null;
	//		ResultSet resultSet = null;
	//		String result = "";
	//		try {
	//			// Statements allow to issue SQL queries to the database
	//			statement = mysqlConnection.createStatement();
	//			// Result set get the result of the SQL query
	//			resultSet = statement.executeQuery(_query);
	//			while (resultSet.next()) {
	//				result += resultSet.getString("ParameterId") + ";";
	//				result += resultSet.getString("ParameterValue") + "\n";
	//			}
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		} finally {
	//			closeResultSet(statement, resultSet);
	//		}
	//		return result;
	//	}
	//
	//	private void closeConnection(Connection connect) {
	//		try {
	//			if (connect != null) {
	//				connect.close();
	//			}
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}
	//
	//	private void closeResultSet(Statement statement, ResultSet resultSet) {
	//		try {
	//			if (resultSet != null) {
	//				resultSet.close();
	//			}
	//			if (statement != null) {
	//				statement.close();
	//			}
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}

}
