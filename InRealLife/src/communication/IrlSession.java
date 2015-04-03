package communication;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import tools.EnumMessageName;

import communication.ComThread;
import communication.IMessageDispatcher;
import communication.LoginRequest;
import communication.Message;

public class IrlSession implements IMessageDispatcher {

	private static final Logger		logger = Logger.getLogger(IrlSession.class);
	private static IrlSession instance=null;
	private static Socket socket;
	private static ComThread comThread;

	private boolean socketOpen;
	private boolean loginAcccepted;

	private IrlSession() {
		socketOpen=false;
		loginAcccepted=false;
		socket=null;
		comThread=null;
	}

	public synchronized static IrlSession getInstance() {
		if(instance==null) instance=new IrlSession();
		return instance;
	}

	public boolean login(String _email,String _password) {
		if(!socketOpen) {
			try {
				socket = new Socket("localhost",5555);
				comThread = new ComThread(socket,this);
				comThread.start();
				
				LoginRequest login = new LoginRequest(_email,_password);
				comThread.send(login);
				
			} catch (UnknownHostException e) {
				logger.error("",e);
			} catch (IOException e) {
				logger.error("", e);
			}
		}
		return socketOpen;
	}

	public void logout() {
		if(socketOpen) {
			comThread.stopRunning();
		}
		socketOpen=false;
		loginAcccepted=false;
	}

	public boolean isSocketOpen() {
		return socketOpen;
	}

	public boolean isLoginAccepted() {
		return loginAcccepted;
	}

	@Override
	public void dispatch(Object _messageObject,ComThread comThread) {
		//TODO sortir le dispatcher
		logger.debug("Received["+_messageObject+"]");
		if(_messageObject instanceof LoginRequest) {
			loginAcccepted=((LoginRequest)_messageObject).isAcceptedConnection();
			logger.debug("Login Accepted="+loginAcccepted);
		}
	}

}
