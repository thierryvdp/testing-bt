package server;

import message.server.processing.LoginRequest;
import messages.Message;

import org.apache.log4j.Logger;

import tools.EnumMessageName;
import iocontrol.ComThread;
import iocontrol.IMessageDispatcher;
import iocontrol.MessageBreaker;

public class ServerDispatcher implements IMessageDispatcher {
	private final Logger logger = Logger.getLogger(MessageBreaker.class);

	@Override
	public void dispatch(String _message,ComThread ioThread) {
			logger.debug("Received---------------->["+_message+"]");
			Message message = new Message();
			message.setMessage(_message);
			if(message.getField(EnumMessageName.MESSAGE.getName()).equals(EnumMessageName.LOGIN.getName())) {
				LoginRequest logRequest=new LoginRequest(message.getField("EMAIL"),message.getField("PWD"));
				ioThread.send(logRequest.getLoginResponse());
				return;
		}
	}

}
