package lucie.object;

import java.util.ArrayList;
import java.util.List;

import lucie.interfaces.INeuronData;

public class BasicNeuron implements INeuronData {

	private long    neuronId;
	private long    x;
	private long    y;
	private long    z;
	private boolean activated;
	private long    neuronState;
	private long    neuronTrigger;

	private List<INeuronData> connectedNeurons;

	public BasicNeuron(long _Id) {
		neuronId         = _Id;
		x                = 0;
		y                = 0;
		z                = 0;
		neuronState      = 0;
		neuronTrigger    = 0;
		activated        = false;
		connectedNeurons = new ArrayList<INeuronData>();
	}

	// Getters

	@Override
	public long getNeuronId() {
		return neuronId;
	}

	@Override
	public long getX() {
		return x;
	}

	@Override
	public long getY() {
		return y;
	}

	@Override
	public long getZ() {
		return z;
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

	// Setters

	@Override
	public void setNeuroneId(long _id) {
		neuronId=_id;
	}

	@Override
	public void setX(long _x) {
		x=_x;
	}

	@Override
	public void setY(long _y) {
		y=_y;
	}

	@Override
	public void setZ(long _z) {
		z=_z;
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
		info+="     neuronId:"+neuronId     +"\n";
		info+="            x:"+x            +"\n";
		info+="            y:"+y            +"\n";
		info+="            z:"+z            +"\n";
		info+="    activated:"+activated    +"\n";
		info+="  neuronState:"+neuronState  +"\n";
		info+="neuronTrigger:"+neuronTrigger+"\n";
		return info;
	}

}
