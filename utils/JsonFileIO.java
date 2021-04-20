package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Handle the saving and retrieving of high scores utilizing a JSON file format.
 * This would preferably be handled via using a bin format so the data is
 * encrypted, but the JSON is an easy format to work with for a non-critical function.
 * 
 * @author Andrew Johnston
 *
 */

public class JsonFileIO {
	private static Map<Long, String> highscores;
	
	/**
	 * Load the high scores from a JSON file.
	 * @return HashMap of the milliseconds (time) and the name.
	 */
	public static Map<Long, String> load () {
		loadFile();
		return highscores;
	}
	
	/**
	 * Save one entry into the json file. If there are more than 10 entries,
	 * then the longest entry is removed.
	 * @param milliseconds Long time
	 * @param name String name
	 */
	public static void save (Long milliseconds, String name) {
		loadFile();
		// add new entry
		highscores.put(milliseconds, name);
		
		// check if highScores is longer than 10 entries, if so, trim down to 10
		if (highscores.size() > 10) {
			Map.Entry<Long, String> maxEntry = null;
			
			for (Map.Entry<Long, String> entry : highscores.entrySet()) {
				if (maxEntry == null || entry.getKey() > maxEntry.getKey()) maxEntry = entry;
			}
			
			if (maxEntry != null) highscores.remove(maxEntry.getKey());
		}
		
		// save
		writeFile();
	}
	
	/**
	 * Load the file using a JSON parser from the Gson library.
	 */
	private static void loadFile () {
		Gson gson = new Gson();
		
		try (Reader reader = new FileReader("highscores.json")) {
			Type type = new TypeToken<HashMap<Long, String>>(){}.getType();
			highscores = gson.fromJson(reader, type);
		} catch (FileNotFoundException exception) {
			System.out.println("highscores.json not found, generating new file.");
			highscores = new HashMap<Long, String>();
			writeFile();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Write to the JSON file using the Gson library.
	 */
	private static void writeFile () {
		Gson gson = new Gson();
		
		try (FileWriter writer = new FileWriter("highscores.json")) {
			gson.toJson(new HashMap<Long, String>(highscores), writer);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
