package utils;

/**
 * Utility class to help with the return variables from the AI algorithm.
 * 
 * @author Andrew Johnston
 *
 */

public class CheckersAIReturn {
	private Vector2i from;
	private Vector2i to;
	
	/**
	 * Constructor to initialize the class
	 * @param from Vector2i
	 * @param to Vector2i
	 */
	public CheckersAIReturn (Vector2i from, Vector2i to) {
		this.from = from;
		this.to = to;
	}
	
	/**
	 * Get the from Vector2i
	 * @return Vector2i
	 */
	public Vector2i getFrom () {
		return this.from;
	}
	
	/**
	 * Get the to Vector2i
	 * @return Vector2i
	 */
	public Vector2i getTo () {
		return this.to;
	}
}
