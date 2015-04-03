package irl.common.messages;

import java.io.Serializable;

public class AbstractRequest implements IRequest, Serializable {

	private static final long serialVersionUID = 1L;
	private boolean acceptedRequest;
	private boolean waitingResponse;
	private boolean responseReceived;
	
	public AbstractRequest() {
		acceptedRequest=false;
		waitingResponse=false;
		responseReceived=false;
	}

	@Override
	public boolean isAcceptedRequest() {
		return acceptedRequest;
	}
	@Override
	public void setAcceptedRequest(boolean isAcceptedRequest) {
		this.acceptedRequest = isAcceptedRequest;
	}

	@Override
	public boolean isWaitingResponse() {
		return waitingResponse;
	}
	@Override
	public void setWaitingResponse(boolean waitingResponse) {
		this.waitingResponse = waitingResponse;
	}

	@Override
	public boolean isResponseReceived() {
		return responseReceived;
	}
	@Override
	public void setResponseReceived(boolean responseReceived) {
		this.responseReceived = responseReceived;
	}

}
