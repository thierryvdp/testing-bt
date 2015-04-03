package client;

import iocontrol.ComThread;

import java.applet.Applet;
import java.awt.GridLayout;
import java.awt.Label;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.URL;

public class SocketClient{

	private static Socket echoSocket;
	private static PrintWriter out;
	private static BufferedReader in;
	static ComThread newServer;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			echoSocket = new Socket("localhost",5555);
			newServer = new ComThread(echoSocket);
			newServer.start();
			
		} catch (Exception e) {
			System.out.println("Exeption "+e);
			return;
		}
		
		try {
			
			newServer.send(".....1");
			newServer.send(".....2");
			newServer.send(".....3");
			newServer.send(".....4");
			newServer.send(".....5");
			
		} catch (Exception e) {
			return;
		}



		try {
			out.close();
			in.close();
			echoSocket.close();
		} catch (Exception e) {
			System.out.println("Exeption "+e);
		}


	}


}


