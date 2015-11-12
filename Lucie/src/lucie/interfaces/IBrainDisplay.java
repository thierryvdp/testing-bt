package lucie.interfaces;

public interface IBrainDisplay {
	
	public void addNeuron(INeuronData _neuron);
	public void addNeuron(INeuronData _neuron,boolean show);
	public void deleteNeuron(INeuronData _neuron);
	
	public void showNeuron(INeuronData _neuron);
	public void hideNeuron(INeuronData _neuron);

}
