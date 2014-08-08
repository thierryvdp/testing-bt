package lucie.launcher;

import lucie.singleton.BrainControler;

public class Launcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BrainControler.getInstance().generateBrain();
	}

}
