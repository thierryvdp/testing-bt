package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

public class ComThread extends Thread {
	
	private final Logger logger = Logger.getLogger(ComThread.class);
	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean linked;
	private boolean running;
	private IMessageDispatcher responseDispatcher;
	
	public ComThread(Socket _clientSocket, IMessageDispatcher _responseDispatcher) {
		clientSocket=_clientSocket;
		_responseDispatcher=responseDispatcher;
		linked=false;
		running=false;
		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			linked=true;
		} catch (IOException e) {
			logger.error("in/out open channels exception:  ",e);
		}
	}

	public void run() {
		running=true;
		while(linked&&running) {
			try {
				Object inputObject=in.readObject();
				responseDispatcher.dispatch(inputObject,this);
			} catch (IOException e) {
				logger.error("IO Exception reading input: ",e);
				linked=false;
			} catch (ClassNotFoundException e) {
				logger.error("ClassNotFoundException Exception reading input: ",e);
				linked=false;
			}
		}
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			logger.error("IO Exception closing",e);
		}
		logger.debug("stop running");
	}
	
	public void stopRunning() {
		running=false;
	}
	
	public void send(Object _outputObject) {
		if(linked) {
			logger.debug("Sending:["+_outputObject+"]");
			try {
				out.writeObject(_outputObject);
			} catch (IOException e) {
				logger.error("",e);
			}
		}
		else
			logger.error("Can't send message while not Linked");
	}
	
	
}
