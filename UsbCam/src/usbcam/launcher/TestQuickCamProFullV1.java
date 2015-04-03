package usbcam.launcher;
/******************************************************
 * File: TestQuickCamPro.java
 * created 24.07.2001 21:40:13 by David Fischer, fischer@d-fischer.com
 *
 * Description: this test program will capture the video and audio stream
 * from a Logitech QuickCam� Pro 3000 USB camera for 10 seconds and stores
 * it on a file, named "testcam.avi". You can use the microsoft windows
 * media player to display this file.
 *
 * operating system: Windows 2000
 * required hardware:  Logitech QuickCam� Pro 3000
 * required software: jdk 1.3 or jdk1.4 plus jmf2.1.1 (www.javasoft.com)
 *
 * source files: DeviceInfo.java, MyDataSinkListener.java,
 *               Stdout.java, TestQuickCamPro.java
 *
 * You can just start this program with "java TestQuickCamPro"
 *
 * hint: please make shure that you setup first the logitech camerea drives
 * and jmf2.1.1 correctly. "jmf.jar" must be part of your CLASSPATH.
 *
 * useful links:
 * - http://java.sun.com/products/java-media/jmf/2.1.1/index.html
 * - http://java.sun.com/products/java-media/jmf/2.1.1/solutions/index.html
 *
 * with some small modifications, this program will work with any USB camera.
 */

import java.io.IOException;

import javax.media.CannotRealizeException;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.DataSink;
import javax.media.Format;
import javax.media.IncompatibleSourceException;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoProcessorException;
import javax.media.Processor;
import javax.media.ProcessorModel;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;

import tools.Stdout;
import usbcam.video.tools.DeviceInfo;
import usbcam.video.tools.MyDataSinkListener;


public class TestQuickCamProFullV1
{

	private static boolean				debugDeviceList = false;

	private static String				defaultVideoDeviceName = "Microsoft WDM Image Capture";
	private static String				defaultAudioDeviceName = "DirectSoundCapture";
	private static String				defaultVideoFormatString = "size=176x144, encoding=yuv, maxdatalength=50688";
	private static String				defaultAudioFormatString = "linear, 16000.0 hz, 8-bit, mono, unsigned";

	private static CaptureDeviceInfo	captureVideoDevice = null;
	private static CaptureDeviceInfo	captureAudioDevice = null;
	private static VideoFormat			captureVideoFormat = null;
	private static AudioFormat			captureAudioFormat = null;


	public static void main(String args[])
	{
		// get command line arguments
		for (int x = 0; x < args.length; x++)
		{
			// -dd = debug devices list -> display list of all media devices - and exit
			if (args[x].toLowerCase().compareTo("-dd") == 0)
				debugDeviceList = true;
		}

		// get a list of all media devices, search default devices and formats, and print it out if args[x] = "-dd"
		// --------------------------------------------------------------------------------------------------------

		Stdout.log("get list of all media devices ...");
		java.util.Vector<?> deviceListVector = CaptureDeviceManager.getDeviceList(null);
		if (deviceListVector == null) {
			Stdout.logAndAbortException("... error: media device list vector is null, program aborted");
		}
		if (deviceListVector.size() == 0) {
			Stdout.logAndAbortException("... error: media device list vector size is 0, program aborted");
		}

		for (int x = 0; x < deviceListVector.size(); x++) {
			// display device name
			CaptureDeviceInfo deviceInfo = (CaptureDeviceInfo) deviceListVector.elementAt(x);
			String deviceInfoText = deviceInfo.getName();
			if (debugDeviceList)
				Stdout.log("device " + x + ": " + deviceInfoText);

			// display device formats
			Format deviceFormat[] = deviceInfo.getFormats();
			for (int y = 0; y < deviceFormat.length; y++) {
				// serach for default video device
				if (captureVideoDevice == null)
					if (deviceFormat[y] instanceof VideoFormat)
						if (deviceInfo.getName().indexOf(defaultVideoDeviceName) >= 0) 	{
							captureVideoDevice = deviceInfo;
							Stdout.log(">>> capture video device = " + deviceInfo.getName());
						}

				// search for default video format
				if (captureVideoDevice == deviceInfo)
					if (captureVideoFormat == null)
						if (DeviceInfo.formatToString(deviceFormat[y]).indexOf(defaultVideoFormatString) >= 0) {
							captureVideoFormat = (VideoFormat) deviceFormat[y];
							Stdout.log(">>> capture video format = " + DeviceInfo.formatToString(deviceFormat[y]));
						}

				// search for default audio device
				if (captureAudioDevice == null)
					if (deviceFormat[y] instanceof AudioFormat)
						if (deviceInfo.getName().indexOf(defaultAudioDeviceName) >= 0)
						{
							captureAudioDevice = deviceInfo;
							Stdout.log(">>> capture audio device = " + deviceInfo.getName());
						}

				// search for default audio format
				if (captureAudioDevice == deviceInfo)
					if (captureAudioFormat == null)
						if (DeviceInfo.formatToString(deviceFormat[y]).indexOf(defaultAudioFormatString) >= 0)
						{
							captureAudioFormat = (AudioFormat) deviceFormat[y];
							Stdout.log(">>> capture audio format = " + DeviceInfo.formatToString(deviceFormat[y]));
						}

				if (debugDeviceList)
					Stdout.log(" - format: " +  DeviceInfo.formatToString(deviceFormat[y]));
			}
		}
		Stdout.log("... list completed.");

		// if args[x] = "-dd" terminate now
		// --------------------------------
		if (debugDeviceList)
			System.exit(0);


		// setup video data source
		// -----------------------
		MediaLocator videoMediaLocator = captureVideoDevice.getLocator();
		DataSource videoDataSource = null;
		try {
			videoDataSource = javax.media.Manager.createDataSource(videoMediaLocator);
		} catch (IOException ie) { 
			Stdout.logAndAbortException(ie);
		} catch (NoDataSourceException nse) { 
			Stdout.logAndAbortException(nse); 
		}

		if (! DeviceInfo.setFormat(videoDataSource, captureVideoFormat)) {
			Stdout.logAndAbortException("Error: unable to set video format - program aborted");
		}


		// setup audio data source
		// -----------------------
		MediaLocator audioMediaLocator = captureAudioDevice.getLocator();
		DataSource audioDataSource = null;
		try {
			audioDataSource = javax.media.Manager.createDataSource(audioMediaLocator);
		} catch (IOException ie) { 
			Stdout.logAndAbortException(ie); 
		} catch (NoDataSourceException nse) { 
			Stdout.logAndAbortException(nse); 
		}

		if (! DeviceInfo.setFormat(audioDataSource, captureAudioFormat)) {
			Stdout.logAndAbortException("Error: unable to set audio format - program aborted");
		}


		// merge the two data sources
		// --------------------------
		DataSource mixedDataSource = null;
		try {
			DataSource dArray[] = new DataSource[2];
			dArray[0] = videoDataSource;
			dArray[1] = audioDataSource;
			mixedDataSource = javax.media.Manager.createMergingDataSource(dArray);
		} catch (IncompatibleSourceException ise) { 
			Stdout.logAndAbortException(ise); 
		}


		// create a new processor
		// ----------------------

		// setup output file format  ->> msvideo
		FileTypeDescriptor outputType = new FileTypeDescriptor(FileTypeDescriptor.MSVIDEO);

		// setup output video and audio data format
		Format outputFormat[] = new Format[2];
		/**
		 * le format video doit �tre dispo
		 */
		outputFormat[0] = new VideoFormat(VideoFormat.YUV);
		outputFormat[1] = new AudioFormat(AudioFormat.GSM_MS /* LINEAR */);

		// create processor
		ProcessorModel processorModel = new ProcessorModel(mixedDataSource, outputFormat, outputType);
		Processor processor = null;
		try {
			processor = Manager.createRealizedProcessor(processorModel);
		} catch (IOException e) { 
			Stdout.logAndAbortException(e); 
		} catch (NoProcessorException e) { 
			Stdout.logAndAbortException(e); 
		} catch (CannotRealizeException e) { 
			Stdout.logAndAbortException(e); 
		}

		// get the output of the processor
		DataSource source = processor.getDataOutput();

		// create a File protocol MediaLocator with the location
		// of the file to which bits are to be written
		MediaLocator dest = new MediaLocator("file:testcam.avi");

		// create a datasink to do the file
		DataSink dataSink = null;
		MyDataSinkListener dataSinkListener = null;
		try {
			dataSink = Manager.createDataSink(source, dest);
			dataSinkListener = new MyDataSinkListener();
			dataSink.addDataSinkListener(dataSinkListener);
			dataSink.open();
		} catch (IOException e) { 
			Stdout.logAndAbortException(e); 
		} catch (NoDataSinkException e) { 
			Stdout.logAndAbortException(e); 
		} catch (SecurityException e) { 
			Stdout.logAndAbortException(e); 
		}

		// now start the datasink and processor
		try {
			dataSink.start();
		} catch (IOException e) { 
			Stdout.logAndAbortException(e); 
		}
		processor.start();

		Stdout.log("starting capturing ...");
		try { 
			Thread.sleep(10000); 
		} catch (InterruptedException ie) {}	// capture for 10 seconds
		Stdout.log("... capturing done");

		// stop and close the processor when done capturing...
		// close the datasink when EndOfStream event is received...
		processor.stop();
		processor.close();

		dataSinkListener.waitEndOfStream(10);
		dataSink.close();

		Stdout.log("[all done]");
	}

}
