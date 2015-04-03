package usbcam.video.tools;

import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.PrefetchCompleteEvent;
import javax.media.Processor;
import javax.media.RealizeCompleteEvent;
import javax.media.ResourceUnavailableEvent;
import javax.media.TransitionEvent;

import tools.Stdout;

public class MyProcessorControllerListener implements ControllerListener {

	private Object waitSync = new Object();
	private boolean stateTransitionOK = true;
	private Processor processor;

	public MyProcessorControllerListener(Processor _processor) {
		processor=_processor;
		processor.addControllerListener(this);
	}

	/**
	 * Block until the processor has transitioned to the given state.
	 * Return false if the transition failed.
	 */
	public boolean waitForState(int state) {
		synchronized (waitSync) {
			try {
				while (processor.getState() != state && stateTransitionOK)
					waitSync.wait();
			} catch (Exception e) {}
		}
		return stateTransitionOK;
	}

	/**
	 * Controller Listener.
	 */
	public void controllerUpdate(ControllerEvent evt) {

		if (evt instanceof ConfigureCompleteEvent ||
				evt instanceof RealizeCompleteEvent ||
				evt instanceof PrefetchCompleteEvent) {
			synchronized (waitSync) {
				stateTransitionOK = true;
				waitSync.notifyAll();
			}
		} else if (evt instanceof ResourceUnavailableEvent) {
			synchronized (waitSync) {
				stateTransitionOK = false;
				waitSync.notifyAll();
			}
		} else if (evt instanceof EndOfMediaEvent) {
			processor.close();
			System.exit(0);
		}
		else if(evt instanceof TransitionEvent) {
			Stdout.log(evt.toString());
		}
		else {
			Stdout.log("unkown event:"+evt);
		}
	}
}
