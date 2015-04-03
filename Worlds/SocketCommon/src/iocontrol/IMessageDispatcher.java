package iocontrol;

public interface IMessageDispatcher {
	
	public void dispatch(String message,ComThread ioThread);

}
