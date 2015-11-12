package lucie.interfaces;

/**
 * This interface describe what method are needed to handle a neuron interactions between user-data-display
 * @author thierry
 *
 */
public interface INeuronController {

	// Getters
	public INeuronData getNeuroneData();
	public INeuronGui  getNeuroneGui();
	
	// Setters : usualy used when buiding a neuron or restoring from a backup
	public void setNeuroneData(INeuronData _NeuronData);
	public void setNeuroneGui(INeuronGui _NeuroneGui);

	// Other methods
	
}
