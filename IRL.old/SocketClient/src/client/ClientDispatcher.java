package client;

import iocontrol.ComThread;
import iocontrol.IMessageDispatcher;
import iocontrol.MessageBreaker;
import messages.Message;

import org.apache.log4j.Logger;

import tools.EnumMessageName;

public class ClientDispatcher implements IMessageDispatcher {
	private final Logger logger = Logger.getLogger(MessageBreaker.class);

	@Override
	public void dispatch(String _message,ComThread ioThread) {
			logger.debug("Received["+_message+"]");
			Message message = new Message();
			message.setMessage(_message);
			if(message.getField(EnumMessageName.MESSAGE.getName()).equals(EnumMessageName.LOGRESPONSE.getName())) {	
				return;
		}
	}

}
