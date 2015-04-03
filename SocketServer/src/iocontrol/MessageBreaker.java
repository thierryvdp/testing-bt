package iocontrol;

import message.server.processing.LoginServer;

import org.apache.log4j.Logger;

import tools.Convertion;
import tools.EnumMessage;
import tools.EnumSeparator;

public class MessageBreaker {
	
	private final Logger logger = Logger.getLogger(MessageBreaker.class);
	private ComThread ioThread;
	
	public MessageBreaker(ComThread _ioThread) {
		ioThread=_ioThread;
	}
	
	public void dispatch(String message) {
		String[] fields = message.split(EnumSeparator.FIELD.getSeparatorString());
		String command = fields[0];
		if(command.equals(EnumMessage.LOGIN.getMessageString())) {
			LoginServer login = new LoginServer(fields);
			ioThread.send(login.getLoginResponse());
			return;
		}
		
		ioThread.send(message);
	}
	
}
