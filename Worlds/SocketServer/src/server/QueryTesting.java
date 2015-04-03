package server;

import org.apache.log4j.Logger;

import data.Email;

import requete.QueryFactory;

public class QueryTesting {
	
	private final static Logger logger = Logger.getLogger(QueryTesting.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			Email email=QueryFactory.getInstance().loadEmail("thierry.vdp@gmail.com");
			if(email!=null) {
				logger.debug("Test->"+email.getEmail()+" Found");
			}
			else {
				logger.debug("Test->"+email.getEmail()+" Not Found");
			}
		} catch (Exception e) {
			logger.error("Exception loading email:"+"thierry.vdp@gmail.com",e);
		}
		

	}

}
