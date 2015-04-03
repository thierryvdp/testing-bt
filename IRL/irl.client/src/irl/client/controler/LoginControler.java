package irl.client.controler;

import org.apache.log4j.Logger;

import irl.client.communication.IrlSession;
import irl.client.gui.LoginGui;
import irl.client.interfaces.IAction;
import irl.client.interfaces.ILoginControler;
import irl.common.messages.LoginRequest;


public class LoginControler implements ILoginControler, IAction {
	
	private LoginRequest login;
	private boolean running;
	private LoginGui loginGui;
	private boolean loggued;
	
	private static final Logger		logger = Logger.getLogger(LoginControler.class);
	
	public LoginControler() {
		running=true;
		login = new LoginRequest("", "");
		loginGui=new LoginGui();
		loginGui.setLoginAction(this);
		loggued=false;
	}
	
	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public boolean isLogged() {
		return loggued;
	}

	@Override
	public void run() {
		loginGui.disableLoginButton();
		login.setEmail(loginGui.getLogin());
		login.setPassword(loginGui.getPassword());
		IrlSession.getInstance().requestLogin(login);
		while(!IrlSession.getInstance().getRequest().isResponseReceived()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("",e);
			}
		}
		if(!IrlSession.getInstance().getRequest().isAcceptedRequest()) {
			loggued=true;
			loginGui.closeGui();
		}
		else {
			loginGui.setMessageLabel("Connection Failed");
			loginGui.enableLoginButton();
		}
	}

}
