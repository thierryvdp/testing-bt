package irl.server.main;

import org.apache.log4j.Logger;

import irl.server.communication.ServerThread;

public class MainServer {

	private static final Logger		logger = Logger.getLogger(MainServer.class);

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		logger.debug("Starting Server");
		
		ServerThread serverThread = new ServerThread(5555);
		serverThread.start();
		
		logger.debug("Server Started");

	}

}
