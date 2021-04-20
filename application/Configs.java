package application;

/**
 * A global configuration file. Any configurations / constants shared between
 * classes should be configured here.
 * 
 * @author Andrew Johnston
 *
 */

public class Configs {
	/**
	 * Window dimensions in pixels.
	 */
	public static final int WINDOW_WIDTH = 1080; // pixels
	public static final int WINDOW_HEIGHT = 720; // pixels
	
	/**
	 * Custom fonts available for use.
	 * 
	 * @author Andrew Johnston
	 *
	 */
	public class Font {
		public static final String MONTSERRAT_REGULAR = "resources/font/Montserrat-Regular.ttf";
		public static final String MONTSERRAT_MEDIUM = "resources/font/Montserrat-Medium.ttf";
		public static final String MONTSERRAT_SEMIBOLD = "resources/font/Montserrat-SemiBold.ttf";
	}
}
