package test.utils;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Map;
import utils.JsonFileIO;

public class JsonFileIOTest {
	@Test
	public void loadEmptyFile() {
		Map<Long, String> highScores = JsonFileIO.load();
		
		assertTrue(highScores != null);
	}
	
	@Test
	public void saveLine () {
		long time = 5;
		String name = "Tester";
		
		Map<Long, String> highScores = JsonFileIO.load();
		int size = highScores.size();
		
		JsonFileIO.save(time, name);
		
		assertTrue(size + 1 == JsonFileIO.load().size());
	}
}
