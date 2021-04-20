package utils;

/**
 * Utility class to represent a x, y coordinate on the game board.
 * 
 * @author Andrew Johnston
 *
 */

public class Vector2i {
	public Integer x;
	public Integer y;

	/**
	 * Initialize with a x,y coordinate
	 * @param x Integer
	 * @param y Integer
	 */
	public Vector2i (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Override the toString method to print out a string of the value.
	 */
	public String toString () {
		return "(" + this.x + ", " + this.y + ")";
	}
	
	/**
	 * Check of two vectors equal each other
	 * @param vec2 Vector to compare it with
	 * @return boolean
	 */
	public boolean equals(Vector2i vec2) {
        return this.x == vec2.x && this.y == vec2.y;
	}
}