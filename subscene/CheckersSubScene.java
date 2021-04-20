package subscene;

import application.Configs;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import scene.CheckersScene;

/**
 * Parent class for each sub-scene, creating a layout for all
 * subscenes in the game.
 * 
 * @author Andrew Johnston
 *
 */

public abstract class CheckersSubScene extends SubScene {
	private AnchorPane pane;
	private CheckersScene scene;

	/**
	 * Initialize the SubScene
	 * @param scene The scene this sub-scene is attached to. Utilized for handling segue's
	 */
	public CheckersSubScene(CheckersScene scene) {
		// Generate the SubScene with the width/height of the actual window
		// so it'll fill the entire screen.
		super(new AnchorPane(), Configs.WINDOW_WIDTH, Configs.WINDOW_HEIGHT);
		this.pane = (AnchorPane) this.getRoot();
		this.pane.setBackground(null);
		this.scene = scene;
	}

	/**
	 * Add a JavaFX Node to the scene.
	 * @param e JavaFX Node
	 */
	public void add (Node e) {
		pane.getChildren().add(e);
	}
	
	/**
	 * Remove a JavaFX Node from the scene.
	 * @param e JavaFX Node
	 */
	public void remove (Node e) {
		pane.getChildren().remove(e);
	}
	
	/**
	 * Transition from one sub-scene to a target sub-scene
	 * @param subScene SubScenes enum: The sub-scene to transition to
	 */
	public void segueToSubScene (SubScenes subScene) {
		try {
			scene.segueToSubScene(subScene);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Animate the transition of sub-scene's.
	 * 
	 * @param isSubSceneActive Should the scene become active? This allows for the subscene to define how, or where it animates to.
	 */
	public abstract void transitionScene (boolean isSubSceneActive);
}
