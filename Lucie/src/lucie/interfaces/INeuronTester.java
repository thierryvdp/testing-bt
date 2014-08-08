package lucie.interfaces;
/**
 * 
 * @author thierry
 *
 */
public interface INeuronTester {

	/**
	 * the processor return true if the _neuron information should be propagated
	 * @return
	 */
	public boolean propagateSignal(INeuronModel _neuron);
	
}
