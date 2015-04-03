package server;

import iocontrol.ComThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer {

	private static PrintWriter out;
	private static BufferedReader in;
	private static Socket clientSocket;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket srvSocket ;
		try {
			srvSocket = new ServerSocket(5555) ;
			System.out.println("Serveur Ok") ;
			while ( true ) {
				System.out.println("i");

				clientSocket = srvSocket.accept();
				
				System.out.println("Accepting, starting ComThread");
				ComThread newPlayer = new ComThread(clientSocket);
				newPlayer.start();
				
			}
		}
		catch(IOException e) {
			System.out.println("Exception Server"+e);
		}
	}
	
}

