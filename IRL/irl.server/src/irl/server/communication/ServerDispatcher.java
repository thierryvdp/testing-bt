package irl.server.communication;

import org.apache.log4j.Logger;

import irl.common.communication.ComThread;
import irl.common.communication.IMessageDispatcher;
import irl.common.messages.CreateAccountRequest;
import irl.common.messages.LoginRequest;
import irl.common.tool.RecordNotFoundException;
import irl.server.data.Email;
import irl.server.data.User;
import irl.server.query.QueryFactory;

public class ServerDispatcher implements IMessageDispatcher {
	
	private static final Logger		logger = Logger.getLogger(ServerDispatcher.class);

	@Override
	public void dispatch(Object _messageObject, ComThread comThread) {
		logger.debug("Received["+_messageObject+"]");

		if(_messageObject instanceof LoginRequest) {
			LoginRequest login=(LoginRequest)_messageObject;
			login.setAcceptedRequest(false);				
			try {
				QueryFactory.getInstance().loadEmail(login.getEmail(),login.getPassword());
				login.setAcceptedRequest(true);
			} catch (RecordNotFoundException e) {
			} catch (Exception e) {
				logger.error("",e);
			}
			comThread.send(login);
			return;
		}
		
		if(_messageObject instanceof CreateAccountRequest) {
			CreateAccountRequest createAccount=(CreateAccountRequest)_messageObject;
			createAccount.setAcceptedRequest(false);
			Email email=null;
			try {
				email=QueryFactory.getInstance().loadEmail(createAccount.getEmail(),createAccount.getPassword());
			} catch (RecordNotFoundException e) {
				createAccount.setAcceptedRequest(true);
			} catch (Exception e) {
				logger.error("",e);
			}
			if(createAccount.isAcceptedRequest()) {
				try {
					User user=new User("first","last","ad1","ad2","pcod","town","country","phon","mob",null);
					email=new Email(user,createAccount.getEmail(),createAccount.getPassword());
					QueryFactory.getInstance().createEmail(email);
				} catch (Exception e) {
					logger.error("",e);
					createAccount.setAcceptedRequest(false);
				}
			}
			
			comThread.send(createAccount);
			return;
		}

	}

}
