package scene;

import application.Configs;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import subscene.SubScenes;
import subscene.WinConditions;

/**
 * A utility class to represent a Scene, ensuring we have the
 * same global configurations for all Scene's.
 * 
 * @author Andrew Johnston
 *
 */

public abstract class CheckersScene extends Scene {
	private AnchorPane pane;
	protected SceneManager sceneManager;
	
	/**
	 * Initialize a new JavaFX scene object.
	 */
	public CheckersScene (SceneManager sceneManager) {
		super(new AnchorPane(), Configs.WINDOW_WIDTH, Configs.WINDOW_HEIGHT);
		this.sceneManager = sceneManager;
		this.pane = (AnchorPane) this.getRoot();
	}
	
	/**
	 * Set the background image of the scene.
	 * 
	 * @param resourcePath String absolute path of the resource.
	 */
	public void setBackground(String resourcePath) {
		Image backgroundImage = new Image(resourcePath);
		BackgroundImage background = new BackgroundImage(
				backgroundImage, 
				BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.CENTER, 
				BackgroundSize.DEFAULT
		);

		this.pane.setBackground(new Background(background));
	}
	
	/**
	 * Segue to a subscene as long as it is initialized in the callers scene.
	 * @param subScene The SubScene to transition to
	 * @throws Exception If the SubScene is not initialized, an exception will be thrown.
	 */
	public abstract void segueToSubScene(SubScenes subScene) throws Exception;
	
	/**
	 * Segue to a different scene, defined in the Enum class Scenes.
	 * @param scene Enum Scenes
	 */
	public abstract void segueToScene(Scenes scene);
	
	/**
	 * Set the global settings when configuring a new game.
	 * @param playerOneName String
	 * @param playerTwoName String
	 * @param isSinglePlayer Boolean
	 * @param gameDifficulty GameDifficulty Enum of easy, medium, hard
	 */
	public void setSettings (String playerOneName, String playerTwoName, boolean isSinglePlayer, GameDifficulty gameDifficulty) {
		this.sceneManager.setSettings(playerOneName, playerTwoName, isSinglePlayer, gameDifficulty);
	}
	
	/**
	 * Set the winner of a game upon game completion.
	 * @param playerNameWhoWon String
	 * @param timeElapsed Long time in seconds.
	 */
	public void setWinner(String playerNameWhoWon, long timeElapsed, WinConditions whoWon) {
		this.sceneManager.setWinner(playerNameWhoWon, timeElapsed, whoWon);
		
	}

	/**
	 * Get the player one name from settings.
	 * @return String
	 */
	public String getPlayerOneName() {
		return this.sceneManager.getPlayerOneName();
	}

	/**
	 * Get the player two name from settings.
	 * @return String
	 */
	public String getPlayerTwoName() {
		return this.sceneManager.getPlayerTwoName();
	}
	
	public WinConditions getWhoWon () {
		return this.sceneManager.getWhoWon();
	}

	/**
	 * Get the players name who won the game.
	 * @return String
	 */
	public String getPlayerWhoWonName() {
		return this.sceneManager.getPlayerWhoWonName();
	}

	/**
	 * Check if the game is single player or PvP.
	 * @return Boolean
	 */
	public boolean getIsSinglePlayer() {
		return this.sceneManager.getIsSinglePlayer();
	}

	/**
	 * Get the current amount of time elapsed in seconds.
	 * @return Long
	 */
	public long getTimeElapsed() {
		return this.sceneManager.getTimeElapsed();
	}
	
	/**
	 * Returns the GameDifficulty
	 * @return GameDifficulty enum.
	 */
	public GameDifficulty getGameDifficulty () {
		return this.sceneManager.getGameDifficulty();
	}
	
	/**
	 * Add a JavaFX Node to the scene.
	 * @param e JavaFX Node
	 */
	public void add (Node e) {
		pane.getChildren().add(e);
	}
}
