package message.server.processing;


import messages.Message;
import object.metier.PlayerClient;
import object.metier.PlayerPool;

import org.apache.log4j.Logger;

import requete.QueryFactory;
import tools.EnumMessageName;
import data.Email;

public class LoginRequest {

	private PlayerClient playerClient;
	private Message response;
	private final static Logger logger = Logger.getLogger(LoginRequest.class);


	public LoginRequest(String email,String password) {
		boolean success=false;
		response=new Message();
		response.setField(EnumMessageName.MESSAGE.getName(), EnumMessageName.LOGRESPONSE.getName());
		response.setField("RESPONSE", "INVALID LOGIN");
		Email emailRec=null;
		try {
			emailRec = QueryFactory.getInstance().loadEmail(email);
		} catch (Exception e) {
			logger.error("Exception querying DB for email:"+email,e);
		}
		if(emailRec!=null&&emailRec.getEmail().equals(email)) {
			success=true;
			playerClient =PlayerPool.getInstance().registerPlayer(email);
			response.setField("RESPONSE", "GOOD");
			response.setField("SECUREKEY", "ACK");
		}
	}

	public String getLoginResponse() {
		return response.getMessage();	
	}

}
