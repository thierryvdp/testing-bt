package test.url.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
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
		conn.setRequestProperty ("ContentType", "application/json-rpc");
		
//		webRequest.ContentType = "application/json-rpc";
//		webRequest.Method = "POST";

		String method="getwork";
		String paramString=null;
        String jsonParam = (paramString != null) ? "\"" + paramString + "\"" : "";
        String request = "{\"id\": 0, \"method\": \"" + method + "\", \"params\": [" + jsonParam + "]}";

        // serialize json for the request
//        byte[] byteArray = Encoding.UTF8.GetBytes(request);
//        webRequest.ContentLength = byteArray.Length;
//        using (Stream dataStream = webRequest.GetRequestStream())
//            dataStream.Write(byteArray, 0, byteArray.Length);
//
//        string reply = "";
//        using (WebResponse webResponse = webRequest.GetResponse())
//        using (Stream str = webResponse.GetResponseStream())
//        using (StreamReader reader = new StreamReader(str))
//        reply = reader.ReadToEnd();


		/** recuperation de la reponse au post **/
		OutputStreamWriter postStreamWriter = new OutputStreamWriter(conn.getOutputStream());
		postStreamWriter.write(request);
//		postStreamWriter.flush();
		InputStream responseStream=conn.getInputStream();
		printOutStream(responseStream);
		
		
//		SAXReader saxReader = new SAXReader();
//		responseDocument = saxReader.read(responseStream);
	}

}
