package com.vdp.rest.wss.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class RestService {

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
		String result = "Json saved" + runQuery("select * from LU_Parameter where ParameterId like '%pdf%'");
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

	private String runQuery(String _query) {

		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;
		//		PreparedStatement preparedStatement = null;

		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://192.168.2.17/COMPUFIRST_2?user=root&password=Tdfxkp89;)");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery(_query);
			return writeResultSet(resultSet);

			// PreparedStatements can use variables and are more efficient
			//            preparedStatement = connect.prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
			// "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
			// Parameters start with 1
			//            preparedStatement.setString(1, "Test");
			//            preparedStatement.setString(2, "TestEmail");
			//            preparedStatement.setString(3, "TestWebpage");
			//            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			//            preparedStatement.setString(5, "TestSummary");
			//            preparedStatement.setString(6, "TestComment");
			//            preparedStatement.executeUpdate();

			//			preparedStatement = connect.prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
			//			resultSet = preparedStatement.executeQuery();
			//			writeResultSet(resultSet);
			//
			//			// Remove again the insert comment
			//			preparedStatement = connect.prepareStatement("delete from feedback.comments where myuser= ? ; ");
			//			preparedStatement.setString(1, "Test");
			//			preparedStatement.executeUpdate();
			//			resultSet = statement.executeQuery("select * from feedback.comments");
			//			writeMetaData(resultSet);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connect, resultSet, statement);
		}
		return null;
	}

	//	private void writeMetaData(ResultSet resultSet) throws SQLException {
	//		//  Now get some metadata from the database
	//		// Result set get the result of the SQL query
	//
	//		System.out.println("The columns in the table are: ");
	//
	//		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
	//		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
	//			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
	//		}
	//	}

	private String writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		String result = "";
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			result += resultSet.getString("ParameterId") + ";";
			result += resultSet.getString("ParameterValue") + "\n";
		}
		return result;
	}

	// You need to close the resultSet
	private void close(Connection connect, ResultSet resultSet, Statement statement) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}
