package iocontrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class ComThread extends Thread {
	
	private final Logger logger = Logger.getLogger(ComThread.class);
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private boolean linked;
	private MessageBreaker messageBreaker;
	
	public ComThread(Socket _clientSocket) {
		this.clientSocket=_clientSocket;
		linked=false;
		try {
			out = new PrintWriter(clientSocket.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			messageBreaker=new MessageBreaker(this);
			linked=true;
		} catch (IOException e) {
			logger.error("in/out open channels exception:  "+e);
		}
	}

	public void run() {
		while(linked) {
			try {
				String inputLine=in.readLine();
				messageBreaker.dispatch(inputLine);
			} catch (IOException e) {
				logger.error("IO Exception reading input: "+e);
			}
		}
	}
	
	public void send(String outputLine) {
		if(linked) {
			System.out.println("Sending:["+outputLine+"]");
			out.println(outputLine);
		}
		else
			logger.error("Can't send message while not Linked");
	}
	
	
}
