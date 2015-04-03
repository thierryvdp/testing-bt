package usbcam.video.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;

public class DeviceManager {

	private Vector<?>               deviceListVector   = null;
	
	private List<CaptureDeviceInfo> videoDevices       = new ArrayList<CaptureDeviceInfo>();
	private CaptureDeviceInfo    	videoDevice = null;
	private VideoFormat		    	videoFormat = null;

	private List<CaptureDeviceInfo> audioDevices       = new ArrayList<CaptureDeviceInfo>();
	private CaptureDeviceInfo    	audioDevice = null;
	private AudioFormat			    audioFormat = null;

	public DeviceManager() {
		deviceListVector = CaptureDeviceManager.getDeviceList(null);
		if(deviceListVector!=null&&deviceListVector.size()!=0) {
			for (int x = 0; x < deviceListVector.size(); x++) {

				CaptureDeviceInfo deviceInfo = (CaptureDeviceInfo) deviceListVector.elementAt(x);
				Format deviceFormat[] = deviceInfo.getFormats();
				for (int y = 0; y < deviceFormat.length; y++) {
					if(deviceFormat[y] instanceof VideoFormat) {
						if(!videoDevices.contains(deviceInfo))
							videoDevices.add(deviceInfo);
					}
					if(deviceFormat[y] instanceof AudioFormat) {
						if(!audioDevices.contains(deviceInfo))
							audioDevices.add(deviceInfo);
					}	
				}
			}
			if(audioDevices.size()>0) {
				audioDevice=audioDevices.get(0);
				audioFormat=(AudioFormat) audioDevice.getFormats()[0];
			}
			if(videoDevices.size()>0) {
				videoDevice=videoDevices.get(0);
				videoFormat=(VideoFormat) videoDevice.getFormats()[0];
			}
		}
	}
	
	public Vector<?> getDeviceListVector() {
		return deviceListVector;
	}

	public CaptureDeviceInfo getVideoDevice() {
		return videoDevice;
	}

	public CaptureDeviceInfo getAudioDevice() {
		return audioDevice;
	}

	public VideoFormat getVideoFormat() {
		return videoFormat;
	}

	public AudioFormat getAudioFormat() {
		return audioFormat;
	}

}
