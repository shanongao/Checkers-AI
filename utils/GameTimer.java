package utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/**
 * Utility class to handle the game's timer capabilities, utilizing
 * the Java timer. The timer should be handled on a separate thread
 * other than the JavaFX thread so it's not interrupted.
 * 
 * @author Andrew Johnston
 *
 */
public class GameTimer {
	private static Timer timer;
	private static long timeElapsedInSeconds = 0;
	
	/**
	 * Start the timer.
	 * @param callback Callback function so we can update the game time every seconds
	 */
	public static void start(Consumer<Long> callback) {
		if (timer != null) cleanUp();

		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
            public void run() {
            	timeElapsedInSeconds += 1;
            	if (callback != null) callback.accept(timeElapsedInSeconds);
            }
          }, 0, 1000);
	}
	
	/**
	 * Reset the timer (e.g. on a new game)
	 */
	public static void reset() {
		timeElapsedInSeconds = 0;
		if (timer != null) cleanUp();
	}
	
	/**
	 * Pause the timer.
	 */
	public static void pause() {
		if (timer != null) timer.cancel();
	}
	
	/**
	 * Stop and clear the timer from memory.
	 */
	public static void cleanUp() {
		if (timer != null) {
			timer.cancel();
			timer.purge();
		}
	}
	
	/**
	 * Get the amount of time elapsed in seconds.
	 * @return Long
	 */
	public static long getTimeElapsedInSeconds () {
		return timeElapsedInSeconds;
	}
}
