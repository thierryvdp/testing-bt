package lucie.interfaces;
/**
 * 
 * @author thierry
 *
 */
public interface INeuronProcessor {

	/**
	 * the processor will handle the signal _value and update accordingly the _neuron
	 * @param _value
	 */
	public void processSignal(INeuronModel _neuron,long _value);
	
}
