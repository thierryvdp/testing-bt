package server;

import iocontrol.ComThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


import org.apache.log4j.Logger;


public class SocketServer {

	private static PrintWriter out;
	private static BufferedReader in;
	private static Socket clientSocket;
	private static ServerDispatcher dispatcher;
	private final static Logger logger = Logger.getLogger(SocketServer.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket srvSocket ;
		try {
			srvSocket = new ServerSocket(5555) ;
			logger.info("Serveur Ok") ;
			while ( true ) {
				logger.info("i");

				clientSocket = srvSocket.accept();
				
				logger.info("Accepting, starting ComThread");
				dispatcher=new ServerDispatcher();
				ComThread newPlayer = new ComThread(clientSocket,dispatcher);
				newPlayer.start();
				
			}
		}
		catch(IOException e) {
			logger.error("Exception Server",e);
		}
	}
	
}

