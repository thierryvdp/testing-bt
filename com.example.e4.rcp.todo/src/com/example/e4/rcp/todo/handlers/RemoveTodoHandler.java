package com.example.e4.rcp.todo.handlers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.eclipse.e4.core.di.annotations.Execute;

public class RemoveTodoHandler {

	@Execute
	public void execute() {
		System.out.println(this.getClass().getSimpleName() + " execute method called.");

		String string = "";
		try {

			// Step1: Let's 1st read file from fileSystem
			// Change CrunchifyJSON.txt path here
			InputStream crunchifyInputStream = new FileInputStream("/home/batchoperator/tmp/json.txt");
			InputStreamReader crunchifyReader = new InputStreamReader(crunchifyInputStream);
			BufferedReader br = new BufferedReader(crunchifyReader);
			String line;
			while ((line = br.readLine()) != null) {
				string += line + "\n";
			}
			// Step2: Now pass JSON File Data to REST Service
			URL url = new URL("http://192.168.2.171:8080/com.vdp.rest.wss/api/restserv");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(string);
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String response = in.readLine();
			while (response != null) {
				System.out.println(response);
				response = in.readLine();
			}
			System.out.println("\nCrunchify REST Service Invoked Successfully..");
			in.close();

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
