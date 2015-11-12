package lucie.gui;

import java.awt.Frame;
import java.awt.TextArea;

import lucie.interfaces.IBrainControler;
import lucie.interfaces.IBrainDisplay;
import lucie.interfaces.INeuronData;

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
	public void addNeuron(INeuronData _neuron) {
		addNeuron(_neuron, true);
	}

	@Override
	public void addNeuron(INeuronData _neuron, boolean show) {
		message(_neuron, "Adding show:"+show);
	}


	@Override
	public void deleteNeuron(INeuronData _neuron) {
		message(_neuron, "Deleting");
	}


	@Override
	public void showNeuron(INeuronData _neuron) {
		message(_neuron, "Showing");
	}


	@Override
	public void hideNeuron(INeuronData _neuron) {
		message(_neuron, "Hiding");
	}


}
