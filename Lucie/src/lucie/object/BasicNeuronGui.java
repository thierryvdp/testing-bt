package lucie.object;

import org.apache.log4j.Logger;

import lucie.interfaces.INeuronData;
import lucie.interfaces.INeuronGui;

public class BasicNeuronGui implements INeuronGui {
	Logger logger=Logger.getLogger(BasicNeuronGui.class);

	@Override
	public void updateDisplay(INeuronData _NeuronData) {
		logger.info(_NeuronData.toString());
	}

}
