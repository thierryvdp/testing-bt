package lucie.object;

import java.util.ArrayList;
import java.util.List;

import lucie.interfaces.INeuronConnection;
import lucie.interfaces.INeuronLocation;
import lucie.interfaces.INeuronModel;
import lucie.interfaces.INeuronProcessor;
import lucie.interfaces.INeuronTester;

public class BasicNeuron implements INeuronModel {

	private INeuronLocation neuronLocation;
	private INeuronTester neuronTester;
	private INeuronProcessor neuronProcessor;

	private long neuronId;
	private boolean activated;
	private long neuronState;
	private long neuronTrigger;

	private List<INeuronConnection> connecteds;

	public BasicNeuron(long _Id) {
		neuronLocation=null;
		neuronTester=null;
		neuronProcessor=null;
		neuronId=_Id;
		neuronState=0;
		neuronTrigger=0;
		activated=false;
		connecteds=new ArrayList<INeuronConnection>();
	}

	@Override
	public INeuronLocation getNeuronLocation() {
		return neuronLocation;
	}

	@Override
	public void setNeuronLocation(INeuronLocation _neuronLocation) {
		neuronLocation = _neuronLocation;
	}

	@Override
	public long getNeuronState() {
		return neuronState;
	}

	@Override
	public void setNeuronState(long _neuronState) {
		neuronState = _neuronState;
	}

	@Override
	public long getNeuronTrigger() {
		return neuronTrigger;
	}

	@Override
	public void setNeuronTrigger(long _neuronTrigger) {
		neuronTrigger = _neuronTrigger;
	}

	@Override
	public boolean isActivated() {
		return activated;
	}

	@Override
	public void setActivated(boolean _activated) {
		activated = _activated;
	}

	@Override
	public long getNeuronId() {
		return neuronId;
	}

	@Override
	public void setNeuroneId(long _id) {
		neuronId=_id;
	}

	@Override
	public void inform(long _value) {
		if(neuronProcessor==null) return;
		neuronProcessor.processSignal(this, _value);
		if(neuronTester==null) return;
		if(neuronTester.propagateSignal(this)) {
			for(INeuronConnection neuronConnection:connecteds) {
				neuronConnection.getNeuronTarget().inform(_value);
			}
		}
	}

	@Override
	public void connectMe(INeuronConnection _connection) {
		connecteds.add(_connection);
	}

	@Override
	public void disconnectMe(INeuronConnection _connection) {
		connecteds.remove(_connection);
	}

	@Override
	public void setNeuronProcessor(INeuronProcessor _neuronProcessor) {
		neuronProcessor=_neuronProcessor;

	}

	@Override
	public INeuronProcessor getNeuronProcessor() {
		return neuronProcessor;
	}

	@Override
	public void setNeuronTester(INeuronTester _neuronTester) {
		neuronTester=_neuronTester;
	}

	@Override
	public INeuronTester getNeuroneTester() {
		return neuronTester;
	}

}
