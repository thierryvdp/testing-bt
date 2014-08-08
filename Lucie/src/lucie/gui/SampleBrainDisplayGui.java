package lucie.gui;

import java.awt.Frame;
import java.awt.TextArea;

import lucie.interfaces.IBrainControler;
import lucie.interfaces.IBrainDisplay;
import lucie.interfaces.INeuronConnection;
import lucie.interfaces.INeuronModel;

public class SampleBrainDisplayGui implements IBrainDisplay {
	
	private IBrainControler brainControler;
	private Frame frame;
	private TextArea outText;

	public SampleBrainDisplayGui(IBrainControler _brainControler) {
		brainControler=_brainControler;
		frame = new Frame("Brain");
		outText = new TextArea("Init...\n");
		frame.add(outText);
	}
	
	private void message(Object _object,String _message) {
		String display="================================================================================\n";
		display+=_message+"\n";
		display+="--------------------------------------------------------------------------------\n";
		display+=_object.toString();
		display+="================================================================================\n";
		outText.setText(outText.getText()+display);
	}

	@Override
	public void addNeuron(INeuronModel _neuron) {
		addNeuron(_neuron, true);
	}

	@Override
	public void addNeuron(INeuronModel _neuron, boolean show) {
		message(_neuron, "Adding show:"+show);
	}


	@Override
	public void deleteNeuron(INeuronModel _neuron) {
		message(_neuron, "Deleting");
	}


	@Override
	public void showNeuron(INeuronModel _neuron) {
		message(_neuron, "Showing");
	}


	@Override
	public void hideNeuron(INeuronModel _neuron) {
		message(_neuron, "Hiding");
	}


	@Override
	public void addConnection(INeuronConnection _connection) {
		addConnection(_connection,true);
	}


	@Override
	public void addConnection(INeuronConnection _connection, boolean show) {
		message(_connection, "Adding");
	}


	@Override
	public void deleteConnection(INeuronConnection _connection) {
		message(_connection, "Deleting");
	}


	@Override
	public void showConnection(INeuronConnection _connection) {
		message(_connection, "Showing");
	}

	@Override
	public void hideConnection(INeuronConnection _connection) {
		message(_connection, "Hiding");
	}

}
