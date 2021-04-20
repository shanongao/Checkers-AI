package scene;

import subscene.CheckersSubScene;
import subscene.GameBoardSubScene;
import subscene.InGameMenuSubScene;
import subscene.SubScenes;
import subscene.WinConditionSubScene;

/**
 * Scene handler for the game play itself. The checker board, in-game menu should
 * all be handled through this scene.
 * 
 * This file is currently a template, but should be built out for the game.
 * 
 * @author Andrew Johnston
 *
 */

public class GameScene extends CheckersScene {
	private CheckersSubScene activeScene;
	private CheckersSubScene gameBoard;
	private CheckersSubScene inGameMenu;
	private CheckersSubScene winCondition;
	
	/**
	 * Initialize the scene with a reference to the game's SceneManager so we
	 * can perform segue's between different scene's.
	 * @param sceneManager SceneManager to manage scene changes.
	 */
	public GameScene (SceneManager sceneManager) {
		super(sceneManager);
		setup();
	}
	
	/**
	 * Segue to the GameBoard and start the game.
	 */
	public void startGame() {
		// Ensure we're on the right subscene
		try {
			segueToSubScene(SubScenes.GAME_BOARD);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Start the game
		((GameBoardSubScene) gameBoard).start();
	}
	
	/**
	 * Set up Game Scene specific variables.
	 */
	private void setup () {
		super.setBackground("file:resources/background.png");
		super.getStylesheets().add("file:resources/styles/button.css");
		super.getStylesheets().add("file:resources/styles/label.css");
		
		gameBoard = new GameBoardSubScene(this);
		add(gameBoard);
		
		inGameMenu = new InGameMenuSubScene(this);
		add(inGameMenu);
		
		winCondition = new WinConditionSubScene(this);
		add(winCondition);
		
		activeScene = gameBoard;
	}

	@Override
	public void segueToSubScene(SubScenes subScene) throws Exception {
		CheckersSubScene segueTo = null;

		switch (subScene) {
		case GAME_BOARD:
			segueTo = gameBoard;
			break;
			
		case IN_GAME_MENU:
			segueTo = inGameMenu;
			break;
			
		case WIN_CONDITION:
			segueTo = winCondition;
			break;
			
		default:
			throw new Exception("Invalid Segue: " + subScene.toString());
		}
		
		if (segueTo != null && activeScene != segueTo) {
			activeScene.transitionScene(false);
			segueTo.transitionScene(true);
			activeScene = segueTo;
		}
	}

	@Override
	public void segueToScene(Scenes scene) {
		try {
			sceneManager.segueTo(scene);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
