package lucie.interfaces;

/**
 * This interface describe what method are needed to handle a neuron datas
 * @author thierry
 *
 */
public interface INeuronData {

	// Getters
	
	/** @return long               unique id of the neuron */
	public long                    getNeuronId();
	/** @return long               x spatial coordinate (right/left) */
	public long                    getX();
	/** @return long               y spatial coordinate (up/down) */
	public long                    getY();
	/** @return long               z spatial coordinate (forward/backward) */
	public long                    getZ();
	/** @return long               neuron state is a combined value result of values received from other neurons */
	public long                    getNeuronState();
	/** @return long               neuron trigger value : this value compared to neuron state to know if neuron have to transmit its information */
	public long                    getNeuronTrigger();
	/** @return true               neuron is activ */
	public boolean                 isActivated();
	
	// Setters : usualy used when buiding a neuron or restoring from a backup
	public void setNeuroneId       (long _id);
	public void setX               (long _x);
	public void setY               (long _y);
	public void setZ               (long _z);
	public void setNeuronState     (long _neuronState);
	public void setNeuronTrigger   (long _neuronTrigger);
	public void setActivated       (boolean _activate);


	// Other methods
	
	/** @param _value          information to the neuron */
	public void   inform       (long _value);
	/** @param _neuron         Connect this INeuronData to me */
	public void   connectMe    (INeuronData _neuron);
	/** @param _neuron         Disconnect this INeuronData to me */
	public void   disconnectMe (INeuronData _neuron);
	/** @return String         all neuron informations in a string */
	public String toString     ();
}
