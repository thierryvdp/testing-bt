package irl.server.communication;

import irl.common.communication.ComThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class ServerThread extends Thread {

	private static final Logger		logger = Logger.getLogger(ServerThread.class);

	private boolean allowRunning;
	private boolean finish;
	private ServerSocket srvSocket;
	private Socket clientSocket;

	public ServerThread(int _port) {
		allowRunning=false;
		finish=false;
		try {
			srvSocket = new ServerSocket(_port) ;
			allowRunning=true;
			logger.info("Server Ok") ;
		} catch (IOException e) {
			logger.error("Server Not Started",e) ;
		}
	}

	@Override
	public void run() {
		while ( ! finish  ) {

			while (allowRunning) {
				logger.info("i");
				try {
					clientSocket = srvSocket.accept();
					logger.info("Accepting, starting ComThread");
					ComThread newPlayer = new ComThread(clientSocket,new ServerDispatcher());
					//TODO enregistrer la liste des clients
					newPlayer.start();
				} catch (IOException e) {
					allowRunning=false;
					logger.error("",e);
				}
				try {
					sleep(10000);
				} catch (InterruptedException e) {
					logger.error("",e);
				}
			}
		}
	}
}
