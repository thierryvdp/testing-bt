package test.url.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.sun.xml.internal.messaging.saaj.util.Base64;



public class UrlCon {

	public static void main(String[] args) {
		try {

			String protocol= "http:";
			String address = "eu.eclipsemc.com";
			String port    = "3333";
			String usr     = args[0];
			String pwd     = args[1];

			connecte2(protocol, address, port, usr, pwd);
//			connecte2(protocol, address, port, usr, pwd);

		} catch (Exception e) {
			System.out.println("Grosse erreur "+e.toString()+" on recommence.");
			for(StackTraceElement stack:e.getStackTrace()) {
				System.out.println(stack.toString());
			}
			System.exit(3);
		}
		System.exit(0);
	}

	private static void connecte1(String protocol, String address, String port, String usr, String pwd) throws MalformedURLException, IOException {
		
		String fullConnection = protocol+"//"+usr+":"+pwd+"@"+address+":"+port;
		System.out.println("Trying ... "+fullConnection);


		URL url = new URL(fullConnection);
		InputStream stream = url.openStream();

		printOutStream(stream);
	}

	private static void printOutStream(InputStream stream) throws IOException {
		byte buf[]=new byte[1024];
		int len;
		while((len=stream.read(buf))>0) {
			System.out.print(buf);
		}
	}

	private static void connecte2(String protocol, String address, String port, String usr, String pwd) throws MalformedURLException, IOException {
		
		String urlConnection = protocol+"//"+address+":"+port;
		String userpass = usr+":"+pwd;
		System.out.println("Trying ... "+userpass+" @ "+urlConnection);

		/** ouverture de la connection http pour post de la requete **/
		URL _url = new URL(urlConnection);

		URLConnection conn = _url.openConnection();
		String basicAuth = "Basic " + new String(Base64.encode(userpass.getBytes()));
		conn.setRequestProperty ("Authorization", basicAuth);
		conn.setDoOutput(true);

		/** recuperation de la reponse au post **/
//		OutputStreamWriter postStreamWriter = new OutputStreamWriter(conn.getOutputStream());
//		postStreamWriter.write(postDocument.asXML());
//		postStreamWriter.flush();
		InputStream responseStream=conn.getInputStream();
		printOutStream(responseStream);
		
		
//		SAXReader saxReader = new SAXReader();
//		responseDocument = saxReader.read(responseStream);
	}

}
