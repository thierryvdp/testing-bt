package lucie.interfaces;

public interface IBrainDisplay {
	
	public void addNeuron(INeuronModel _neuron);
	public void addNeuron(INeuronModel _neuron,boolean show);
	public void deleteNeuron(INeuronModel _neuron);
	
	public void showNeuron(INeuronModel _neuron);
	public void hideNeuron(INeuronModel _neuron);

	public void addConnection(INeuronConnection _connection);
	public void addConnection(INeuronConnection _connection,boolean show);
	public void deleteConnection(INeuronConnection _connection);

	public void showConnection(INeuronConnection _connection);
	public void hideConnection(INeuronConnection _connection);

}
