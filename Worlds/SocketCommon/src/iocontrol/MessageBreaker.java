package iocontrol;

import org.apache.log4j.Logger;

public class MessageBreaker {
	
	private final Logger logger = Logger.getLogger(MessageBreaker.class);
	private ComThread ioThread;
	private IMessageDispatcher dispatcher;
	
	public MessageBreaker(ComThread _ioThread,IMessageDispatcher _dispatcher) {
		ioThread=_ioThread;
		dispatcher=_dispatcher;
	}
	
	public void dispatch(String message) {
		dispatcher.dispatch(message, ioThread);		
	}
	
}
