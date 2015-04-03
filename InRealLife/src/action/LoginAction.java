package action;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

import communication.IrlSession;

import view.LoginView;

public class LoginAction extends Action {
	private static final Logger		logger = Logger.getLogger(LoginAction.class);

	private LoginView loginView;
	
	public LoginAction(LoginView _loginView) {
		loginView=_loginView;
	}
	
	@Override
	public void run() {
		if(!IrlSession.getInstance().isSocketOpen())
			IrlSession.getInstance().login(loginView.getTxtEmailLogin().getText(), loginView.getTxtPassword().getText());
		logger.debug("Login in progress");
	}

}
