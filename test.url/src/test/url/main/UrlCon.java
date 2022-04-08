package test.url.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.messaging.saaj.util.Base64;

public class UrlCon {

	public static void main(String[] args) {
		try {
			// bitseed.xf2.org : reseau p2p reel			
			// sinon https://en.bitcoin.it/wiki/Testnet reseau de test

			/** nslookup peers **/
			List<String> peers = lookup("bitseed.xf2.org");
			for (String peer : peers) {
				System.out.println(peer);
			}

			/**
			 * fake modif
			 */

			/**
			 * Talking to peers
			Once I had the address of a working peer, the next step was to send my transaction into the peer-to-peer network.[8] Using the peer-to-peer protocol is pretty straightforward. I opened a TCP connection to an arbitrary peer on port 8333, started sending messages, and received messages in turn. The Bitcoin peer-to-peer protocol is pretty forgiving; peers would keep communicating even if I totally messed up requests.
			Important note: as a few people pointed out, if you want to experiment you should use the Bitcoin Testnet, which lets you experiment with "fake" bitcoins, since it's easy to lose your valuable bitcoins if you mess up on the real network. (For example, if you forget the change address in a transaction, excess bitcoins will go to the miners as a fee.) But I figured I would use the real Bitcoin network and risk my $1.00 worth of bitcoins.
			The protocol consists of about 24 different message types. Each message is a fairly straightforward binary blob containing an ASCII command name and a binary payload appropriate to the command. The protocol is well-documented on the Bitcoin wiki.
			The first step when connecting to a peer is to establish the connection by exchanging version messages. First I send a version message with my protocol version number[21], address, and a few other things. The peer sends its version message back. After this, nodes are supposed to acknowledge the version message with averack message. (As I mentioned, the protocol is forgiving - everything works fine even if I skip the verack.)
			Generating the version message isn't totally trivial since it has a bunch of fields, but it can be created with a few lines of Python. makeMessage below builds an arbitrary peer-to-peer message from the magic number, command name, and payload. getVersionMessage creates the payload for a version message by packing together the various fields.
			
			 * 
			 */

			String protocol = "http:";
			String address = "eu.eclipsemc.com";
			String port = "3333";
			String usr = args[0];
			String pwd = args[1];

			//			connecte2(protocol, address, port, usr, pwd);
			//			connecte2(protocol, address, port, usr, pwd);

		} catch (Exception e) {
			System.out.println("Grosse erreur " + e.toString() + " on recommence.");
			for (StackTraceElement stack : e.getStackTrace()) {
				System.out.println(stack.toString());
			}
			System.exit(3);
		}
		System.exit(0);
	}

	private static void connecte1(String protocol, String address, String port, String usr, String pwd) throws MalformedURLException, IOException {

		String fullConnection = protocol + "//" + usr + ":" + pwd + "@" + address + ":" + port;
		System.out.println("Trying ... " + fullConnection);

		URL url = new URL(fullConnection);
		InputStream stream = url.openStream();

		printOutStream(stream);
	}

	private static void printOutStream(InputStream stream) throws IOException {
		byte buf[] = new byte[1024];
		int len;
		while ((len = stream.read(buf)) > 0) {
			System.out.print(buf);
		}
	}

	private static void connecte2(String protocol, String address, String port, String usr, String pwd) throws MalformedURLException, IOException {

		String urlConnection = protocol + "//" + address + ":" + port;
		String userpass = usr + ":" + pwd;
		System.out.println("Trying ... " + userpass + " @ " + urlConnection);

		/** ouverture de la connection http pour post de la requete **/
		URL _url = new URL(urlConnection);

		URLConnection conn = _url.openConnection();
		String basicAuth = "Basic " + new String(Base64.encode(userpass.getBytes()));
		conn.setRequestProperty("Authorization", basicAuth);
		conn.setDoOutput(true);
		conn.setRequestProperty("ContentType", "application/json-rpc");

		//		webRequest.ContentType = "application/json-rpc";
		//		webRequest.Method = "POST";

		String method = "getwork";
		String paramString = null;
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
		InputStream responseStream = conn.getInputStream();
		printOutStream(responseStream);

		//		SAXReader saxReader = new SAXReader();
		//		responseDocument = saxReader.read(responseStream);
	}

	private static List<String> lookup(String s) {

		InetAddress[] ipAds;
		List<String> gatheredIps = new ArrayList<String>();

		// get the bytes of the IP address
		try {
			ipAds = InetAddress.getAllByName(s);
			for (InetAddress ad : ipAds) {
				byte[] address = ad.getAddress();
				// Print the IP address
				String ip = "";
				for (int i = 0; i < address.length; i++) {
					int unsignedByte = address[i] < 0 ? address[i] + 256 : address[i];
					ip += unsignedByte + ".";
				}
				gatheredIps.add(ip.substring(0, ip.length() - 1));
			}
		} catch (UnknownHostException ue) {
			System.out.println("Cannot find host " + s);
		}
		return gatheredIps;
	}

}
