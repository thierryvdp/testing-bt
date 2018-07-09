package com.vdp.rest.wss.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdp.rest.wss.api.CompuDoc;
import com.vdp.rest.wss.api.CompuDocId;
import com.vdp.rest.wss.api.CompuFieldType;
import com.vdp.rest.wss.api.CompuFieldValue;
import com.vdp.rest.wss.api.CompuWssRequest;

@Path("/")
public class RestService {

	@POST
	@Path("/restserv")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crunchifyREST(InputStream incomingData) {
		CompuWssRequest requete = null;
		CompuWssRequest reponse = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			StringBuilder jsonString = new StringBuilder();
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				jsonString.append(line);
			}

			System.out.println(jsonString.toString());

			requete = mapper.readValue(jsonString.toString(), CompuWssRequest.class);
			reponse = requete;
			CompuDoc docReponse = new CompuDoc();
			docReponse.setId(CompuDocId.RESPONSE.toString());
			docReponse.setDocuments(new ArrayList<>());
			List<CompuFieldValue> fields = new ArrayList();
			CompuFieldValue field = new CompuFieldValue();
			fields.add(field);
			docReponse.setFields(fields);

			if (requete.getDocument().getId().equals(CompuDocId.LOGIN.toString())) {
				reponse.setPasswordToken("SUPERTOKENDELESPACE");
				field.setId("");
				field.setFieldType(CompuFieldType.SUCCESS);
				field.setFieldStringValue("");
			}
			else {
				field.setId("");
				field.setFieldType(CompuFieldType.ERROR);
				field.setFieldStringValue("");
			}
			String reponseString = mapper.writeValueAsString(requete);
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
