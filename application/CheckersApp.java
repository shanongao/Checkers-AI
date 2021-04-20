package application;

import javafx.application.Application;
import javafx.stage.Stage;
import scene.SceneManager;
import utils.GameTimer;

/**
 * The main application class for the Checkers game. This file is required and cannot
 * be combined with the Main class due to a bug with JavaFX and Maven where the Main
 * class cannot extend Application. Separating the files is a known workaround.
 * 
 * @author Andrew Johnston
 *
 */

public class CheckersApp extends Application {
	/**
	 * The game window is spawned using a stage. We should only handle one stage for the
	 * duration of the application, therefore we should manage multiple Scene's and
	 * SubScene's. The SceneManager class manages each of the scene's.
	 */
	private SceneManager sceneManager;
	
	@Override
	public void start(Stage stage) {
		try {
			sceneManager = new SceneManager();
			sceneManager.run();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main function to launch a JavaFX application.
	 * @param args Application arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Clean up the Java timer when closing. Omitting this will leave an open
	 * thread.
	 */
	@Override
	public void stop() {
		GameTimer.cleanUp();
	}
}