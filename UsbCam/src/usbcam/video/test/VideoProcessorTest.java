package usbcam.video.test;

import java.io.IOException;

import javax.media.CannotRealizeException;
import javax.media.Codec;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoPlayerException;
import javax.media.NotConfiguredError;
import javax.media.Processor;
import javax.media.UnsupportedPlugInException;
import javax.media.control.TrackControl;
import javax.media.format.VideoFormat;

import tools.Stdout;
import usbcam.video.gui.VideoDisplay;
import usbcam.video.tools.DeviceManager;
import usbcam.video.tools.MyProcessorControllerListener;
import usbcam.video.tools.PostAccessCodec;
import usbcam.video.tools.PreAccessCodec;


public class VideoProcessorTest  {
	
	public VideoProcessorTest() throws NoDataSourceException, IOException, CannotRealizeException, NoDataSinkException, NoPlayerException, UnsupportedPlugInException, NotConfiguredError {
		DeviceManager deviceManager = new DeviceManager();

		if(deviceManager.getDeviceListVector()==null||deviceManager.getDeviceListVector().size()==0) {
			Stdout.logAndAbortException("No device to use");
		}
		
		//setup Processor
		MediaLocator videoMediaLocator = deviceManager.getVideoDevice().getLocator();
		Processor processor = Manager.createProcessor(videoMediaLocator);
		MyProcessorControllerListener listener = new MyProcessorControllerListener(processor);
		processor.configure();
		if(!listener.waitForState(Processor.Configured)) Stdout.logAndAbortException("Failed to configure the processor.");
		processor.setContentDescriptor(null); //use as Player

		// Obtain the track controls.
		TrackControl tc[] = processor.getTrackControls();
		if (tc == null)	Stdout.logAndAbortException("Failed to obtain track controls from the processor.");
		TrackControl videoTrack = null;
		for (int i = 0; i < tc.length; i++) {
			if (tc[i].getFormat() instanceof VideoFormat) {
				videoTrack = tc[i];
				break;
			}
		}
		if(videoTrack==null) Stdout.logAndAbortException("The input media does not contain a video track.");
		
		//set codec call backs
		Codec codec[] = { new PreAccessCodec(), new PostAccessCodec()};
		videoTrack.setCodecChain(codec);

		// Realize the processor.
		processor.realize();
		if (!listener.waitForState(Processor.Realized)) Stdout.logAndAbortException("Failed to realize the processor.");
		

		// And display
		VideoDisplay display1=new VideoDisplay(processor);
		processor.start();
		display1.pack();

		Stdout.log("start running ...");

	}
}
