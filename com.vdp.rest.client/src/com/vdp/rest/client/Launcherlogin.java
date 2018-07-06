package com.vdp.rest.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdp.rest.wss.api.Methodes;
import com.vdp.rest.wss.api.WssRequest;

public class Launcherlogin {

	public static void main(String[] args) {

		WssRequest requete = null;
		WssRequest reponse = null;
		ObjectMapper mapper = new ObjectMapper();

		try {

			requete = new WssRequest();
			requete.setMethodName(Methodes.LOGIN.toString());
			requete.setLogin("thierry");
			requete.setPasswordToken("rgjlvdp");

			// Now pass JSONString Data to REST Service
			try {
				String reqString = mapper.writeValueAsString(requete);

				URL url = new URL("http://192.168.2.171:8080/com.vdp.rest.wss/api/restserv");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(reqString);
				out.close();

				// Read JSONStringResponse from REST Service
				StringBuilder jsonString = new StringBuilder();
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					jsonString.append(line);
				}

				System.out.println(jsonString);
				reponse = mapper.readValue(jsonString.toString(), WssRequest.class);
				System.out.println(reponse.getPasswordToken());

				in.close();
			} catch (Exception e) {
				System.out.println("\nError while calling Crunchify REST Service");
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
