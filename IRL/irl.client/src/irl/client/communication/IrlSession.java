package irl.client.communication;

import irl.common.communication.ComThread;
import irl.common.communication.IMessageDispatcher;
import irl.common.messages.CreateAccountRequest;
import irl.common.messages.IRequest;
import irl.common.messages.LoginRequest;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public class IrlSession implements IMessageDispatcher {

	private static final Logger		logger = Logger.getLogger(IrlSession.class);
	private static IrlSession instance=null;
	private static Socket socket;
	private static ComThread comThread;
	private boolean connectionEstablished;
	private IRequest request;

	private IrlSession() {
		socket=null;
		comThread=null;
		connectionEstablished=false;
	}

	public synchronized static IrlSession getInstance() {
		if(instance==null) instance=new IrlSession();
		return instance;
	}

	public void requestLogin(LoginRequest _loginRequest) {
		request=_loginRequest;
		establishConnection();
		if(isConnectionEstablished()) {
			request.setWaitingResponse(true);
			request.setResponseReceived(false);
			comThread.send(request);
		}
	}
	
	public void requestCreateAccount(CreateAccountRequest _createAccountRequest) {
		request=_createAccountRequest;
		establishConnection();
		if(isConnectionEstablished())
		{
			request.setWaitingResponse(true);
			request.setResponseReceived(false);
			comThread.send(request);
		}
	}
	
	private void establishConnection() {
		if(!isConnectionEstablished()) {
			try {
				socket = new Socket("localhost",5555);
				comThread = new ComThread(socket,this);
				comThread.start();
				connectionEstablished=true;				
			} catch (UnknownHostException e) {
				logger.error("",e);
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}
	
	public void logout() {
		if(isConnectionEstablished()) {
			comThread.stopRunning();
		}
		connectionEstablished=false;
	}

	@Override
	public void dispatch(Object _messageObject,ComThread comThread) {
		logger.debug("Received["+_messageObject+"]");
		if(_messageObject instanceof LoginRequest) {
			request=((LoginRequest)_messageObject);
			request.setResponseReceived(true);
			return;
		}
		if(_messageObject instanceof CreateAccountRequest) {
			request=((CreateAccountRequest)_messageObject);
			request.setResponseReceived(true);
			return;
		}
		
	}

	public boolean isConnectionEstablished() {
		return connectionEstablished;
	}

	public IRequest getRequest() {
		return request;
	}

}
