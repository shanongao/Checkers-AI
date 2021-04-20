package test.utils;

import static org.junit.Assert.*;
import org.junit.Test;

import utils.GameTimer;
import java.util.concurrent.TimeUnit;

public class GameTimerTest {
	@Test
	public void timerNotRunning() {
		GameTimer.reset();
		long timeElapsed = GameTimer.getTimeElapsedInSeconds();
		try {
			TimeUnit.SECONDS.sleep(1);
			
			assertEquals(timeElapsed, GameTimer.getTimeElapsedInSeconds());
			GameTimer.reset();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runTimerFiveSeconds () {
		GameTimer.reset();
		GameTimer.start(null);
		
		try {
			TimeUnit.SECONDS.sleep(5);
			
			assertEquals(5, GameTimer.getTimeElapsedInSeconds());
			GameTimer.reset();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCleanUp () {
		GameTimer.reset();
		GameTimer.start(null);
		
		try {
			TimeUnit.SECONDS.sleep(3);
			
			assertTrue(GameTimer.getTimeElapsedInSeconds() < 5);
			GameTimer.cleanUp();
			
			TimeUnit.SECONDS.sleep(5);
			
			assertTrue(GameTimer.getTimeElapsedInSeconds() < 10);
			GameTimer.reset();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReset () {
		GameTimer.reset();
		GameTimer.start(null);
		try {
			TimeUnit.SECONDS.sleep(2);
			
			GameTimer.reset();
			assertEquals(0, GameTimer.getTimeElapsedInSeconds());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
