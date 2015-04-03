package irl.client.main;

import irl.client.gui.CamGui;

import org.apache.log4j.Logger;


public class CamTest {

	private static final Logger		logger = Logger.getLogger(CamTest.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		logger.debug("Starting Client");
		
		CamGui camGui = CamGui.getInstance();
		
		while(!camGui.isQuit()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("",e);
			}
		}
	}

}
