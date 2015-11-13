package lucie.object;

import java.util.ArrayList;
import java.util.List;

import lucie.interfaces.INeuronData;

public class BasicNeuronData implements INeuronData {

	private long         neuronId;
	private boolean      activated;
	private long         neuronState;
	private long         neuronTrigger;
	private SpatialCoord inputSpatialPos;
	private SpatialCoord outputSpatialPos;

	private List<INeuronData> connectedNeurons;

	public BasicNeuronData(long _Id) {
		neuronId         = _Id;
		neuronState      = 0;
		neuronTrigger    = 0;
		activated        = false;
		connectedNeurons = new ArrayList<INeuronData>();
		inputSpatialPos    = new SpatialCoord();
		outputSpatialPos   = new SpatialCoord();
	}

	// Getters

	@Override
	public long getNeuronId() {
		return neuronId;
	}

	@Override
	public long getNeuronState() {
		return neuronState;
	}

	@Override
	public long getNeuronTrigger() {
		return neuronTrigger;
	}

	@Override
	public boolean isActivated() {
		return activated;
	}

	@Override
	public SpatialCoord getInputSpatialPos() {
		return inputSpatialPos;
	}

	@Override
	public SpatialCoord getOutputSpatialPos() {
		return outputSpatialPos;
	}

	// Setters

	@Override
	public void setNeuroneId(long _id) {
		neuronId=_id;
	}

	@Override
	public void setNeuronState(long _neuronState) {
		neuronState = _neuronState;
	}

	@Override
	public void setNeuronTrigger(long _neuronTrigger) {
		neuronTrigger = _neuronTrigger;
	}

	@Override
	public void setActivated(boolean _activated) {
		activated = _activated;
	}

	@Override
	public void setInputSpatialPos(SpatialCoord _inputSpatialPos) {
		inputSpatialPos = _inputSpatialPos;
	}

	@Override
	public void setOutputSpatialPos(SpatialCoord _outputSpatialPos) {
		outputSpatialPos = _outputSpatialPos;
	}

	// Other

	@Override
	public void inform(long _value) {
		//TODO
	}

	@Override
	public void connectMe(INeuronData _neuron) {
		connectedNeurons.add(_neuron);
	}

	@Override
	public void disconnectMe(INeuronData _neuron) {
		connectedNeurons.remove(_neuron);
	}

	@Override
	public String toString     () {
		String info="";
		info+="        neuronId:"+neuronId     +"\n";
		info+="       activated:"+activated    +"\n";
		info+="     neuronState:"+neuronState  +"\n";
		info+="   neuronTrigger:"+neuronTrigger+"\n";
		info+="      connecteds:"+connectedNeurons.size()+"\n";
		info+=" inputSpatialPos:"+inputSpatialPos.toString()+"\n";
		info+="outputSpatialPos:"+outputSpatialPos.toString()+"\n";
		return info;
	}

}
