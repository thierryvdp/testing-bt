package usbcam.video.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.Player;
import javax.swing.JFrame;

public class VideoDisplay extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player player;
	private Component videoComp;
	private Component controlComp;

	public VideoDisplay(Player _player) {
		player=_player;
		setLayout(new BorderLayout());
		videoComp = player.getVisualComponent();
		getContentPane().add("Center",videoComp);
		controlComp = player.getControlPanelComponent();
		getContentPane().add("South",controlComp);
		setVisible(true);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				player.stop();
				System.exit(0);
			}
		});
	}

}
