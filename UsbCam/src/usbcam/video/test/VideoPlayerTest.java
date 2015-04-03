package usbcam.video.test;

import java.io.IOException;

import javax.media.CannotRealizeException;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;

import tools.Stdout;
import usbcam.video.gui.FrameShooter;
import usbcam.video.gui.VideoDisplay;
import usbcam.video.tools.DeviceInfo;
import usbcam.video.tools.DeviceManager;


public class VideoPlayerTest  {
	
	private Player player;

	public VideoPlayerTest() throws NoDataSourceException, IOException, CannotRealizeException, NoDataSinkException, NoPlayerException {
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

		//Create Player
		player = Manager.createRealizedPlayer(videoDataSource);
		VideoDisplay display1=new VideoDisplay(player);
		FrameShooter shooter=new FrameShooter(player);
		
		player.start();
		display1.pack();
		shooter.pack();
		
		Stdout.log("start running ...");

	}
}
