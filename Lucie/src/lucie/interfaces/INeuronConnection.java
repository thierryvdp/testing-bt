package lucie.interfaces;

public interface INeuronConnection {
	
	public void setNeuronConnectionId(long _id);
	public long getNeuronConnectionId();
	
	public void setNeuronTarget(INeuronModel _neuron);
	public INeuronModel getNeuronTarget();
	
	public void setSignalModifier(long _modifier);
	public long getSignalModifier();
	
	public String toString();
}
