package com.vdp.rest.wss.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdp.rest.wss.api.FieldValue;
import com.vdp.rest.wss.api.Methodes;
import com.vdp.rest.wss.api.WssRequest;

@Path("/")
public class RestService {

	@POST
	@Path("/restserv")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crunchifyREST(InputStream incomingData) {
		WssRequest requete = null;
		WssRequest reponse = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			StringBuilder jsonString = new StringBuilder();
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				jsonString.append(line);
			}

			System.out.println(jsonString.toString());

			requete = mapper.readValue(jsonString.toString(), WssRequest.class);
			reponse = requete;
			FieldValue reponseField = new FieldValue();

			if (requete.getMethodName().equals(Methodes.LOGIN.toString())) {
				reponse.setPasswordToken("SUPERTOKENDELESPACE");
				reponseField.setFieldId("reponse");
				reponseField.setFieldValue("OK");
			}
			else {
				reponse.setPasswordToken("");
				reponseField.setFieldId("reponse");
				reponseField.setFieldValue("ERROR");
			}
			reponse.setParam(reponseField);
			String reponseString = mapper.writeValueAsString(reponse);
			System.out.println(reponseString);
			return Response.status(201).entity(reponseString).build();
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}

		// return HTTP response 200 in case of success
		//		return Response.status(200).entity(crunchifyBuilder.toString()).build();
		return Response.status(404).entity(null).build();
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

}
