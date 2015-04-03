package usbcam.video.tools;

import javax.media.Buffer;
import javax.media.Format;
import javax.media.format.YUVFormat;

import tools.Stdout;

public class PreAccessCodec extends MyCodec {
	
	public PreAccessCodec() {
		supportedIns = new Format [] {
				new YUVFormat()
		};
	}

	/**
	 * Callback to access individual video frames.
	 */
	public void accessFrame(Buffer frame) {
		
		// le YUV correspondrait au V210 : Raw YUV ou YCrCv

		// For demo, we'll just print out the frame #, time &
		// data length.

		long t = (long)(frame.getTimeStamp()/10000000f);

		Stdout.log("Pre: frame #: " + frame.getSequenceNumber() + 
				", time: " + ((float)t)/100f + 
				", len: " + frame.getLength());
		
//		BufferToImage b2i = new BufferToImage((VideoFormat)frame.getFormat());
//		Image image = b2i.createImage(frame);
		
	}

	public String getName() {
		return "Pre-Access Codec";
	}

}
