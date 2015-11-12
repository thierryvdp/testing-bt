package lucie.interfaces;

/**
 * This interface describe what method are needed to display a neuron on a graphic interface
 * @author thierry
 *
 */
public interface INeuronGui {

	// Getters
	
	
	// Setters : usualy used when buiding a neuron or restoring from a backup


	// Other methods
	/**
	 * this methode is called to update the display of the neuron
	 * depending on its Datas
	 * @param INeuronData _NeuronData
	 */
	public void updateDisplay(INeuronData _NeuronData);
	
}
