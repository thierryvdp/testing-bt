package irl.client.interfaces;

import java.util.List;

public interface ICamParamGui {
	
	public void setVideoDevices(List<String> _videoDeviceNames);
	public void setVideoFormats(List<String> _videoDeviceFormats);
	public String getVideoDeviceSelected();
	public String getVideoFormatSelected();
	public void openGui();
	public void closeGui();

}
