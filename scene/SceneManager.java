package scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import subscene.WinConditions;

/**
 * The Scene Manager class manages each of the scenes in the game. As
 * we're only utilizing one game window (e.g. Stage), any transitions
 * between major scenes should be handled by this class. SubScene's
 * within a scene (e.g. main-menu selections), should be a SubScene
 * class from the JavaFX library.
 * 
 * @author Andrew Johnston
 *
 */

public class SceneManager {
	private MenuScene menuScene;
	private GameScene gameScene;
	private Stage stage;
	
	// Some global variables passed around scene's
	private String playerOneName;
	private String playerTwoName;
	private boolean isSinglePlayer;
	private GameDifficulty gameDifficulty;
	private long timeElapsed;
	private String playerNameWhoWon;
	private WinConditions whoWon;
	
	/**
	 * Create a stage (window) and initialize the scene manager.
	 * 
	 * TODO: Change application title bar color.
	 */
	public SceneManager () {
		menuScene = new MenuScene(this);
		gameScene = new GameScene(this);
		stage = new Stage();
		stage.setResizable(false); // dont let resizing of the window happen
		stage.setTitle("Checkers"); // application title
		stage.getIcons().add(new Image("file:resources/logo.png")); // application icon
		stage.setScene(menuScene);
	}
	
	/**
	 * Perform a scene transition from the current scene to a target scene.
	 * 
	 * @param scene Target Scene to transition to.
	 * @throws Exception If the scene isn't defined in the Scenes ENUM class, we'll throw an exception.
	 */
	public void segueTo (Scenes scene) throws Exception {
		stage.setScene(gameScene);
		
		switch (scene) {
		case GAME:
			gameScene.startGame();
			stage.setScene(gameScene);
			break;
			
		case MENU:
			stage.setScene(menuScene);
			break;
			
		default:
			throw new Exception("Invalid segue: " + scene);
		}
	}
	
	/**
	 * Save the global settings for a new game.
	 * @param playerOneName String
	 * @param playerTwoName String
	 * @param isSinglePlayer Boolean
	 * @param gameDifficulty GameDifficulty Enum
	 */
	public void setSettings (String playerOneName, String playerTwoName, boolean isSinglePlayer, GameDifficulty gameDifficulty) {
		this.playerOneName = playerOneName;
		this.playerTwoName = playerTwoName;
		this.isSinglePlayer = isSinglePlayer;
		this.gameDifficulty = gameDifficulty;
	}
	
	/**
	 * Set the name and time of the winner upon a game completion
	 * @param playerNameWhoWon String
	 * @param timeElapsed Long
	 */
	public void setWinner (String playerNameWhoWon, long timeElapsed, WinConditions whoWon) {
		this.playerNameWhoWon = playerNameWhoWon;
		this.timeElapsed = timeElapsed;
		this.whoWon = whoWon;
	}
	
	/**
	 * Get the player one name
	 * @return String
	 */
	public String getPlayerOneName () {
		return this.playerOneName;
	}
	
	public WinConditions getWhoWon () {
		return this.whoWon;
	}
	
	/**
	 * Get the player two name
	 * @return String
	 */
	public String getPlayerTwoName () {
		return this.playerTwoName;
	}
	
	/**
	 * Get the player name who won
	 * @return String
	 */
	public String getPlayerWhoWonName () {
		return this.playerNameWhoWon;
	}
	
	/**
	 * Get the timer elapsed
	 * @return Long
	 */
	public long getTimeElapsed () {
		return this.timeElapsed;
	}
	
	/**
	 * Get whether or not the game is set up for single player vs PvP
	 * @return Boolean
	 */
	public boolean getIsSinglePlayer () {
		return this.isSinglePlayer;
	}
	
	/**
	 * Get the game difficulty
	 * @return GameDifficulty Enum
	 */
	public GameDifficulty getGameDifficulty () {
		return this.gameDifficulty;
	}

	/**
	 * Start the application with show().
	 */
	public void run() {
		stage.show();
	}
}
