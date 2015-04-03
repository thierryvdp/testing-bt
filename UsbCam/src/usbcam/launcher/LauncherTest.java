package usbcam.launcher;
/******************************************************
 * File: TestQuickCamPro.java
 * created 24.07.2001 21:40:13 by David Fischer, fischer@d-fischer.com
 *
 * Description: this test program will capture the video and audio stream
 * from a Logitech QuickCam© Pro 3000 USB camera for 10 seconds and stores
 * it on a file, named "testcam.avi". You can use the microsoft windows
 * media player to display this file.
 *
 * operating system: Windows 2000
 * required hardware:  Logitech QuickCam© Pro 3000
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
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoPlayerException;
import javax.media.NoProcessorException;
import javax.media.NotConfiguredError;

import tools.Stdout;
import usbcam.video.test.VideoPlayerTest;


public class LauncherTest
{

	public static void main(String args[]) 	{
		try {
			new VideoPlayerTest();
		} catch (NoProcessorException e) {
			Stdout.log(e.toString());
		} catch (NoDataSourceException e) {
			Stdout.log(e.toString());
		} catch (CannotRealizeException e) {
			Stdout.log(e.toString());
		} catch (NoDataSinkException e) {
			Stdout.log(e.toString());
		} catch (IOException e) {
			Stdout.log(e.toString());
		} catch (NoPlayerException e) {
			Stdout.log(e.toString());
		} catch (NotConfiguredError e) {
			Stdout.log(e.toString());
		}
	}

}
