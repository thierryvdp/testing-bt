package lucie.interfaces;

public interface IBrainModel {
	
	
	/**
	 * 
	 */
	public  void addNeurone(INeuronData  neurone);
	
	public void buildCoreBrain();
	
	public boolean isBrainFullyBuilt();
	
	public void brainSleep();
	
	public void brainWakeUp();
	
	public void brainSave();
	
	public void brainRestore();


}