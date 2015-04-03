package irl.common.messages;


public interface IRequest {
	
	public boolean isAcceptedRequest();
	public void setAcceptedRequest(boolean isAcceptedRequest);
	public boolean isWaitingResponse();
	public void setWaitingResponse(boolean waitingResponse);
	public boolean isResponseReceived();
	public void setResponseReceived(boolean responseReceived);

}
