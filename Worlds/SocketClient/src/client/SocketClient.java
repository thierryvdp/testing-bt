package client;

import iocontrol.ComThread;

import java.net.Socket;

import messages.Message;

import org.apache.log4j.Logger;

import tools.EnumMessageName;


public class SocketClient{

	private static Socket echoSocket;
	static ComThread newServer;
	static ClientDispatcher dispatcher;
	private final static Logger logger = Logger.getLogger(SocketClient.class);


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			logger.debug("Initialisation de la connection");

			dispatcher = new ClientDispatcher();
			echoSocket = new Socket("localhost",5555);
			newServer = new ComThread(echoSocket,dispatcher);
			newServer.start();

			logger.debug("Connection commencée");

		} catch (Exception e) {
			logger.error("Exeption ",e);
			return;
		}

		try {

			logger.debug("Debut du programme");
			
			Message login = new Message();
			login.setField(EnumMessageName.MESSAGE.getName(), EnumMessageName.LOGIN.getName());
			login.setField("EMAIL", "thierry.vdp@gmail.com");
			login.setField("PWD", "123456");
			logger.debug("Message to send:["+login.getMessage()+"]");
			newServer.send(login.getMessage());

			LoginFrame loginFrame = new LoginFrame();

			logger.debug("Fin du programme");

		} catch (Exception e) {
			return;
		}

		try {
			newServer.join();
		} catch (Exception e) {
			logger.error("Exeption:",e);
		}


	}


}


