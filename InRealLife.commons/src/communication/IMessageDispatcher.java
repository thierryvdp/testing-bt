package communication;

public interface IMessageDispatcher {
	
	public void dispatch(Object message,ComThread comThread);

}
