package lucie.interfaces;


public interface INeuronModel {

	/**
	 * method to return the unique id of neuron inside a brain
	 * 
	 * @return long the id of the neuron
	 */
	public long getNeuronId();
	
	/**
	 * method to set the unique id of neuron inside a brain
	 * 
	 * @param _id unique id of neuron inside a brain
	 */
	public void setNeuroneId(long _id);

	/**
	 * method to return the neuron location
	 * 
	 * @return INeuronLocation the location of the neuron
	 */
	public INeuronLocation getNeuronLocation();
	
	/**
	 * method to set the neuron location
	 * 
	 * @param _neuronLocation
	 */
	public void setNeuronLocation(INeuronLocation _neuronLocation);

	/**
	 * method to get the neuron state
	 * the neuron state is the values it received from other neurons
	 * 
	 * @return
	 */
	public long getNeuronState();
	
	/**
	 * method to set the neuron state
	 * the neuron state is the values it received from other neurons
	 * 
	 * @param _neuronState
	 */
	public void setNeuronState(long _neuronState);

	/**
	 * method to get the trigger value
	 * this value will be compared to the neuron state
	 * to know if the neuron have to transmit the information
	 * 
	 * @return long neuron trigger value
	 */
	public long getNeuronTrigger();
	
	/**
	 * method to set the neurone trigger.
	 * this value will be compared to the neuron state
	 * to know if the neuron have to transmit the information
	 * 
	 * @param _neuronTrigger
	 */
	public void setNeuronTrigger(long _neuronTrigger);

	/**
	 * method to know if the neuron is active
	 * 
	 * @return true if the neuron is activated
	 */
	public boolean isActivated();
	
	/**
	 * method to activate of desactivate the neuron
	 * set true to activate
	 * 
	 * @param _activate
	 */
	public void setActivated(boolean _activate);

	/**
	 * method to set the processor that will handle the neuron
	 * @param _neuronProcessor
	 */
	public void setNeuronProcessor(INeuronProcessor _neuronProcessor);
	
	/**
	 * get the processor handling the neuron
	 * @return INeuronProcessor
	 */
	public INeuronProcessor getNeuronProcessor();

	/**
	 * method to set a tester to the neuron. 
	 * the tester will be used to know if the neuron state should be propagated.
	 * @param _neuronTester
	 */
	public void setNeuronTester(INeuronTester _neuronTester);
	
	/**
	 * get the tester used by the neuron
	 * @return INeuronTester
	 */
	public INeuronTester getNeuroneTester();
	
	/**
	 * method to give information to the neuron
	 * 
	 * @param _value
	 */
	public void inform(long _value);
	
	/**
	 * when calling this method, the INeuroneModel will inform _connection when changing state  
	 * @param _neuron
	 */
	public void connectMe(INeuronConnection _connection);
	
	/**
	 * when calling this method, the INeuroneModel will inform _connection when changing state  
	 * @param _neuron
	 */
	public void disconnectMe(INeuronConnection _connection);

	/**
	 * conviennient method to get all neuron informations in a string
	 * @return String
	 */
	public String toString();
}
