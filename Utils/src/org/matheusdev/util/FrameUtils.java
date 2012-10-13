package org.matheusdev.util;

import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

/**
 * @author matheusdev
 *
 */
public final class FrameUtils {

	private FrameUtils() {}

	/**
	 * <p>Positions the given <tt>Frame</tt> in the middle of the
	 * default screen device.</p>
	 * <p>Remember to call this method AFTER setting the <tt>width</tt>
	 * and <tt>height</tt> of the <tt>frame</tt>.</p>
	 * @param frame the {@link Frame} to position in the middle of the
	 * default screen device.
	 */
	public static void setPositionToMidScreen(Frame frame) {
		GraphicsDevice defaultScreen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		Rectangle screenSize = defaultScreen.getDefaultConfiguration().getBounds();

		int midx = screenSize.width/2;
		int midy = screenSize.height/2;

		int posx = midx-(frame.getWidth()/2);
		int posy = midy-(frame.getHeight()/2);

		frame.setLocation(posx, posy);
	}

}
