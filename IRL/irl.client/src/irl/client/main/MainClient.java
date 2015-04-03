package irl.client.main;

import irl.client.communication.IrlSession;
import irl.client.controler.LoginControler;

import org.apache.log4j.Logger;


public class MainClient {

	private static final Logger		logger = Logger.getLogger(MainClient.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		logger.debug("Starting Client");
		
		LoginControler loginGui = new LoginControler();
		
		while(!loginGui.isRunning()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("",e);
			}
		}
				
		if(IrlSession.getInstance().getRequest().isAcceptedRequest()) {
			logger.debug("Envoie ecran thread de jeu");
		}
		
		if(IrlSession.getInstance().isConnectionEstablished())
			IrlSession.getInstance().logout();

	}

}
