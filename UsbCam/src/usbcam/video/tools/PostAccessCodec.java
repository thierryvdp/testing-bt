package usbcam.video.tools;

import javax.media.Buffer;
import javax.media.Format;
import javax.media.format.YUVFormat;


import tools.Stdout;

public class PostAccessCodec extends MyCodec {
	
	public PostAccessCodec() {
		supportedIns = new Format [] {
				new YUVFormat()
		};
	}

	/**
	 * Callback to access individual video frames.
	 */
	public void accessFrame(Buffer frame) {

		// For demo, we'll just print out the frame #, time &
		// data length.

		long t = (long)(frame.getTimeStamp()/10000000f);

		Stdout.log("Post: frame #: " + frame.getSequenceNumber() + 
				", time: " + ((float)t)/100f + 
				", len: " + frame.getLength());
	}

	public String getName() {
		return "Post-Access Codec";
	}

}
