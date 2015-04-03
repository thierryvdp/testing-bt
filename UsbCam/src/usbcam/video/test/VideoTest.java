package usbcam.video.test;

import java.awt.Component;
import java.awt.Frame;
import java.io.IOException;

import javax.media.CannotRealizeException;
import javax.media.DataSink;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoProcessorException;
import javax.media.Processor;
import javax.media.ProcessorModel;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;

import tools.Stdout;
import usbcam.video.tools.DeviceInfo;
import usbcam.video.tools.DeviceManager;
import usbcam.video.tools.MyDataSinkListener;


public class VideoTest  {
	
	public VideoTest() throws NoDataSourceException, IOException, NoProcessorException, CannotRealizeException, NoDataSinkException {
		DeviceManager deviceManager = new DeviceManager();

		if(deviceManager.getDeviceListVector()==null||deviceManager.getDeviceListVector().size()==0) {
			Stdout.logAndAbortException("No device to use");
		}

		// setup video data source
		// -----------------------
		MediaLocator videoMediaLocator = deviceManager.getVideoDevice().getLocator();
		DataSource videoDataSource = javax.media.Manager.createDataSource(videoMediaLocator);
		DeviceInfo.setFormat(videoDataSource, deviceManager.getVideoFormat());

		// create a new processor
		// ----------------------

		// setup output video and audio data format
		Format outputFormat[] = new Format[1];
		outputFormat[0] = new VideoFormat(VideoFormat.YUV);

		// create processor
		ProcessorModel processorModel = new ProcessorModel(videoDataSource, outputFormat, new FileTypeDescriptor(FileTypeDescriptor.MSVIDEO));
		Processor processor =  Manager.createRealizedProcessor(processorModel);

		// get the output of the processor
		DataSource source = processor.getDataOutput();
		
		/**
		 * ATTENTION c'est du plugin eclipse:
			java.awt.Component comp = player.getVisualComponent();
			if (comp != null) {
				video = new Composite(comp, SWT.BORDER | SWT.EMBEDDED);
				frame = SWT_AWT.new_Frame(video);
  				frame.add("Center", comp);
			}
		 */

		// create a File protocol MediaLocator with the location
		// of the file to which bits are to be written
		MediaLocator dest = new MediaLocator("file:testcam.avi");

		// create a datasink to do the file
		DataSink dataSink = Manager.createDataSink(source, dest);
		MyDataSinkListener dataSinkListener = new MyDataSinkListener();;
		dataSink.addDataSinkListener(dataSinkListener);
		dataSink.open();
		dataSink.start();
		processor.start();

		Stdout.log("starting capturing ...");

		try { 
			Thread.sleep(10000); 
		} catch (InterruptedException ie) {}	// capture for 10 seconds
		Stdout.log("... capturing done");
		
		//essaie d'affichage dans 1 frame
		Component comp = processor.getVisualComponent();
		if(comp!=null) {
			Frame videoFrame = new Frame();
			videoFrame.add(comp);
		}

		// stop and close the processor when done capturing...
		// close the datasink when EndOfStream event is received...
		processor.stop();
		processor.close();

		dataSinkListener.waitEndOfStream(10);
		dataSink.close();

		Stdout.log("[all done]");
	}

}
