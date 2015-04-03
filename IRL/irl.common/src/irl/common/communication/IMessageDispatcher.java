package irl.common.communication;

public interface IMessageDispatcher {
	
	public void dispatch(Object _messageObject,ComThread comThread);

}
