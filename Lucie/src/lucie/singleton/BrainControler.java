package lucie.singleton;

import lucie.object.Brain;

import org.apache.log4j.Logger;

public class BrainControler {
	
	private static BrainControler instance=null;
	private static Logger LOGGER = Logger.getLogger(BrainControler.class);
	private boolean    sleeping=true;
	private boolean    freezed=true;
	private boolean    building=true;
	private Brain brain;
	private long neuralId;
	
	private BrainControler() {
		brain = new Brain();
		neuralId=0;
		sleeping=true;
		freezed=true;
		building=false;
	}
	
	public synchronized static BrainControler getInstance() {
		if(instance==null) instance=new BrainControler();
		return instance;
	}
	
	public synchronized long getUnikNeuralId() {
		neuralId=neuralId+1;
		return neuralId;
	}

	public boolean isSleeping() {
		return sleeping;
	}

	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}

	public boolean isFreezed() {
		return freezed;
	}

	public void setFreezed(boolean freezed) {
		this.freezed = freezed;
	}

	public boolean isBuilding() {
		return building;
	}

	public void setBuilding(boolean building) {
		this.building = building;
	}
	
	public void generateBrain() {
		building=true;
		brain.buildCoreBrain();
		while (!brain.isBrainFullyBuilt()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOGGER.error("",e);
			}
		}
	}

}
