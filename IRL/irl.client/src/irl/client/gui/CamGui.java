package irl.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

//import javax.media.CaptureDeviceInfo;
//import javax.media.CaptureDeviceManager;
//import javax.media.Format;
//import javax.media.format.AudioFormat;
//import javax.media.format.VideoFormat;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;


public class CamGui extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final Logger		logger = Logger.getLogger(CamGui.class);
	private static CamGui instance;

//	private CaptureDeviceInfo	captureVideoDevice = null;
//	private CaptureDeviceInfo	captureAudioDevice = null;
//	private VideoFormat			captureVideoFormat = null;
//	private AudioFormat			captureAudioFormat = null;

	private Vector<?> deviceListVector = null;
	private String[] deviceNames=null;
	private String[] captureVideoDevices=null;
	private String[] captureAudioDevices=null;
	private String[] captureVideoFormats=null;
	private String[] captureAudioFormats=null;

	private int currentDeviceVectorIndex=0;
//	private CaptureDeviceInfo currentDeviceInfo=null;

	private JPanel camPanel;
	private JLabel devicesLbl;
	private JComboBox devicesCombo;
	private JLabel captureVideoDeviceLbl;
	private JLabel captureAudioDeviceLbl;
	private JLabel captureVideoFormatLbl;
	private JLabel captureAudioFormatLbl;
	private JComboBox captureVideoDeviceCombo;
	private JComboBox captureAudioDeviceCombo;
	private JComboBox captureVideoFormatCombo;
	private JComboBox captureAudioFormatCombo;

	private boolean quit=false;

	private void buildCamList() {
//		deviceListVector = CaptureDeviceManager.getDeviceList(null);
		deviceNames = new String[deviceListVector.size()];
		for (int x = 0; x < deviceListVector.size(); x++) {
//			CaptureDeviceInfo deviceInfo = (CaptureDeviceInfo) deviceListVector.elementAt(x);
//			deviceNames[x] = deviceInfo.getName();
		}
	}

	private void buildCamCapabilities() {
//		if(currentDeviceInfo!=null) {
//			Format[] deviceFormats = currentDeviceInfo.getFormats();
//			captureVideoDevices=new String[deviceFormats.length];
//			captureAudioDevices=new String[deviceFormats.length];
//			captureVideoFormats=new String[deviceFormats.length];
//			captureAudioFormats=new String[deviceFormats.length];
//			for (int y = 0; y < deviceFormats.length; y++) {
//				Format deviceFormat=deviceFormats[y];
//				captureVideoDevices[y]="..COMPUTING..";
//				captureAudioDevices[y]="..COMPUTING..";
//				captureVideoFormats[y]="..COMPUTING..";
//				captureAudioFormats[y]="..COMPUTING..";
//				if(deviceFormat instanceof VideoFormat) {
//					captureVideoDevices[y]=currentDeviceInfo.getName();
//					VideoFormat captureFormat = (VideoFormat) deviceFormats[y];
//					captureVideoFormats[y]=captureFormat.toString();
//				}
//				if(deviceFormat instanceof AudioFormat) {
//					captureAudioDevices[y]=currentDeviceInfo.getName();
//					AudioFormat captureFormat = (AudioFormat) deviceFormats[y];
//					captureAudioFormats[y]=captureFormat.toString();
//				}
//			}
//		}
//		else {
//			captureVideoDevices=new String[1];
//			captureAudioDevices=new String[1];
//			captureVideoFormats=new String[1];
//			captureAudioFormats=new String[1];
//			captureVideoDevices[0]="No Device Selected";
//			captureAudioDevices[0]="No Device Selected";
//			captureVideoFormats[0]="No Device Selected";
//			captureAudioFormats[0]="No Device Selected";
//		}
		
	}

	// Constructor
	private CamGui() {

		buildCamList();
		buildCamCapabilities();
		
		camPanel = new JPanel();
		camPanel.setLayout(new GridLayout(5, 2));
		
		// Add the panel to the frame.
		getContentPane().add(camPanel, BorderLayout.CENTER);

		addWidgets();


		// Exit when the window is closed.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Show the converter.
		pack();
		setVisible(true);
	}

	public static synchronized CamGui getInstance() {
		if(instance==null) {
			instance = new CamGui();
		}
		return instance;
	}

	private void addWidgets() {

		devicesCombo = new JComboBox(deviceNames);
		captureVideoDeviceCombo = new JComboBox(captureVideoDevices);
		captureAudioDeviceCombo = new JComboBox(captureAudioDevices);
		captureVideoFormatCombo = new JComboBox(captureVideoFormats);
		captureAudioFormatCombo = new JComboBox(captureAudioFormats);
		
		devicesLbl=new JLabel("devicesLbl");
		captureVideoDeviceLbl=new JLabel("captureVideoDeviceLbl");
		captureAudioDeviceLbl=new JLabel("captureAudioDeviceLbl");
		captureVideoFormatLbl=new JLabel("captureVideoFormatLbl");
		captureAudioFormatLbl=new JLabel("captureAudioFormatLbl");

		// Listen to events from Convert button.
		devicesCombo.addActionListener(this);

		// Add widgets to container.
		camPanel.add(devicesLbl);
		camPanel.add(devicesCombo);
		camPanel.add(captureVideoDeviceLbl);
		camPanel.add(captureVideoDeviceCombo);
		camPanel.add(captureVideoFormatLbl);
		camPanel.add(captureVideoFormatCombo);
		camPanel.add(captureAudioDeviceLbl);
		camPanel.add(captureAudioDeviceCombo);
		camPanel.add(captureAudioFormatLbl);
		camPanel.add(captureAudioFormatCombo);

	}
	
	private void resetCombo() {
		captureVideoDeviceCombo.removeAllItems();
		captureAudioDeviceCombo.removeAllItems();
		captureVideoFormatCombo.removeAllItems();
		captureAudioFormatCombo.removeAllItems();
		for(int y = 0; y < captureVideoDevices.length; y++) {
			captureVideoDeviceCombo.addItem(captureVideoDevices[y]);
		}
		for(int y = 0; y < captureAudioDevices.length; y++) {
			captureAudioDeviceCombo.addItem(captureAudioDevices[y]);
		}
		for(int y = 0; y < captureVideoFormats.length; y++) {
			captureVideoFormatCombo.addItem(captureVideoFormats[y]);
		}
		for(int y = 0; y < captureAudioFormats.length; y++) {
			captureAudioFormatCombo.addItem(captureAudioFormats[y]);
		}
	}
	
	

	@Override
	public void actionPerformed(ActionEvent event) {
		logger.debug("Action");
		if(event.getSource() instanceof JComboBox) {
			JComboBox cb = (JComboBox)event.getSource();
//			Object selectedDevice = (Object)cb.getSelectedItem();
			currentDeviceVectorIndex = cb.getSelectedIndex();
//			currentDeviceInfo = (CaptureDeviceInfo)deviceListVector.elementAt(currentDeviceVectorIndex);
			buildCamCapabilities();
			resetCombo();
			//			CaptureDeviceInfo deviceInfo = (CaptureDeviceInfo) deviceListVector.elementAt(currentDeviceVectorIndex);
			//			if (deviceFormat[y] instanceof VideoFormat)
			//				if (deviceInfo.getName().indexOf(defaultVideoDeviceName) >= 0) 	{
			//					captureVideoDevice = deviceInfo;
			//					Stdout.log(">>> capture video device = " + deviceInfo.getName());
			//				}
			//			captureVideoDevice = ;
			//			captureAudioDevice = ;
			//			captureVideoFormat = ;
			//			captureAudioFormat = ;
		}

	}

	@Override
	public void dispose() {
		quit=true;
		super.dispose();
	}

	public boolean isQuit() {
		return quit;
	}

}