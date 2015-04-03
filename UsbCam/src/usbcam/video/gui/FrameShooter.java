package usbcam.video.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.PixelGrabber;

import javax.media.Buffer;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FrameShooter extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player player;
	private JLabel shootedImage;
	private FrameGrabbingControl frameGrabingControl;

	public FrameShooter(Player _player) {
		player=_player;
		setLayout(new BorderLayout());

		frameGrabingControl = (FrameGrabbingControl) player.getControl("javax.media.control.FrameGrabbingControl");

		shootedImage = new JLabel();
		getContentPane().add("Center",shootedImage);
		setVisible(true);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				player.stop();
				System.exit(0);
			}
		});

		shootedImage.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				shoot();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});

	}

	private void shoot() {
		Buffer frame = frameGrabingControl.grabFrame();
		BufferToImage b2i = new BufferToImage((VideoFormat)frame.getFormat());
		Image image = b2i.createImage(frame);
		ImageIcon icon = new ImageIcon(image);
		shootedImage.setIcon(icon);
		pack();

		//		Graphics2D graphik = (Graphics2D)getRootPane().getGraphics();

		int height = image.getHeight(null);
		int width  = image.getWidth(null);
		int[] pixels = new int[width*height];
		PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);

		for (int i = 0; i < pixels.length; i++) {
			int c       = pixels[i];
			int opacity = (c & 0xff000000) >> 24;
		int red     = (c & 0x00ff0000) >> 16;
		int green   = (c & 0x0000ff00) >> 8;
		int blue    = (c & 0x000000ff);
		Color color = new Color(red,blue,green);
		}
		/**

//Get the color of a specific pixel. We assume that we have an Image called picture
pixels = new int[width*height];
PixelGrabber pg = new PixelGrabber(picture, 0, 0, width, height, pixels, 0, width);
try {
	pg.grabPixels();
} catch (InterruptedException e) { }

//From here, individual pixel can be accessed via the pixels array.

int c = pixels[index]; // or pixels[x * width + y]
int opacity = (c & 0xff000000) >> 24;
int red     = (c & 0x00ff0000) >> 16;
int green   = (c & 0x0000ff00) >> 8;
int blue    = (c & 0x000000ff);

// and the Java Color is ...
Color c = new Color(red,blue,green); 

		 */

	}

}
