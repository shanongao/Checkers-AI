package test.utils;

import static org.junit.Assert.*;
import org.junit.Test;

import utils.Vector2i;

public class Vector2iTest {
	@Test
	public void testInitialization() {
		Vector2i test = new Vector2i(5, 5);
				
		assertEquals(true, test.x == 5);
		assertEquals(true, test.y == 5);
	}
	
	@Test
	public void testToString () {
		Vector2i test = new Vector2i(5, 5);
		
		assertEquals("(5, 5)", test.toString());
	}
	
	@Test
	public void testEquals () {
		Vector2i test1 = new Vector2i(2, 2);
		Vector2i test2 = new Vector2i(2, 2);
		
		assertTrue(test1.equals(test2));
	}
}
