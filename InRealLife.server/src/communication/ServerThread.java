package communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;


public class ServerThread extends Thread implements IMessageDispatcher {

	private static final Logger		logger = Logger.getLogger(ServerThread.class);

	private boolean allowRunning;
	private boolean finish;
	private int runningPort;
	private ServerSocket srvSocket;
	private Socket clientSocket;

	public ServerThread(int _port) {
		runningPort=_port;
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
					ComThread newPlayer = new ComThread(clientSocket,this);
					//TODO enregistrer la liste des clients
					newPlayer.start();
				} catch (IOException e) {
					allowRunning=false;
					logger.error("",e);
				}
				try {
					this.sleep(10000);
				} catch (InterruptedException e) {
					logger.error("",e);
				}
			}
		}
	}

	@Override
	public void dispatch(Object _messageObject,ComThread comThread) {
		// TODO Sortir le dispatcher et le mettre dans un  Thread indépendant
		
		if(_messageObject instanceof LoginRequest) {
			((LoginRequest)_messageObject).setAcceptedConnection(true);
			comThread.send(_messageObject);
		}

	}

}
