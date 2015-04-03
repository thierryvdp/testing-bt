package usbcam.brain;

import java.awt.Image;

public class Retine {
	
	private static Retine instance;
	private Image imageIn;
	private Image imageOut;
	private int width;
	private int height;
	private int[] horizontalCells;
	private int[] bipolarCells;
	private int[] amacrineCels;
	private int[] ganglionCells;

	private Retine() {
		/** definir width & height **/
		/** fabriquer une image par defaut **/
		/** fabriquer les couches neuronales **/
	}

	public synchronized static Retine getInstance() {
		if (instance == null)
			instance = new Retine();
		return instance;
	}
	
	public Image getImageOut() {
		return imageOut;
	}

	public Image getImageIn() {
		return imageIn;
	}

	public void setImageIn(Image _image) {
		/** resizer et verser dans image **/
		imageIn = _image;
	}

}
