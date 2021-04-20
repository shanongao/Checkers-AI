package test.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import utils.CheckersAIReturn;
import utils.Vector2i;

public class CheckersAIReturnTest {
	@Test
	public void testInitialization() {
		CheckersAIReturn test = new CheckersAIReturn(new Vector2i(1, 1), new Vector2i(2, 2));
				
		assertTrue(test.getFrom().equals(new Vector2i(1, 1)));
		assertTrue(test.getTo().equals(new Vector2i(2, 2)));
	}
}
