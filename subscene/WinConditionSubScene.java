package subscene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.Configs;
import gui.CheckersButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;
import scene.CheckersScene;
import scene.GameScene;
import scene.Scenes;
import utils.JsonFileIO;

/**
 * 
 * @author Shaion Habibvand
 *
 */

public class WinConditionSubScene extends CheckersSubScene{
	private CheckersScene scene;
	private long timeElapsedInSeconds;
	private Label headerLabel;
	private Label timerLabel;

	/**
	 * Initialize the sub-scene.
	 * @param scene CheckersScene
	 */
	public WinConditionSubScene(CheckersScene scene) {
		super(scene);
		setLayoutX(Configs.WINDOW_WIDTH);
		createButtons();
		setHeader();
		setSubHeader();
		
		this.scene = scene;
	}
	
	/**
	 * Set the subscene's header.
	 */
	private void setHeader () {
		headerLabel = new Label("");
		headerLabel.getStyleClass().add("header");

		headerLabel.setLayoutX(0);
		headerLabel.setLayoutY(75);
		headerLabel.setMinWidth(Configs.WINDOW_WIDTH);
		try {
			headerLabel.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_SEMIBOLD), 52));
		} catch (FileNotFoundException e) {
			headerLabel.setFont(Font.font("Verdana", 52));
		}
		
		add(headerLabel);
	}
	
	/**
	 * Set the sub header
	 */
	private void setSubHeader () {
		timerLabel = new Label("Time: 10:23");
		timerLabel.getStyleClass().add("header");

		timerLabel.setLayoutX(0);
		timerLabel.setLayoutY(200);
		timerLabel.setMinWidth(Configs.WINDOW_WIDTH);
		
		try {
			timerLabel.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_SEMIBOLD), 45));
		} catch (FileNotFoundException e) {
			timerLabel.setFont(Font.font("Verdana", 45));
		}
		
		add(timerLabel);
	}
	
	/**
	 * Initialize a back button to transition back to the main-menu
	 * subscene.
	 */
	private void createButtons() {
		createReturnHomeButton();
		createPlayAgainButton();
	}
	
	/**
	 * Create a button to return home
	 */
	private void createReturnHomeButton () {
		CheckersButton button = new CheckersButton("RETURN HOME", CheckersButton.ButtonSizes.LARGE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					segueToSubScene(SubScenes.GAME_BOARD);
					scene.segueToScene(Scenes.MENU);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		button.setLayoutX(353);
		button.setLayoutY(500);
		button.setAlignment(Pos.BASELINE_CENTER);
		add(button);
	}
	
	/**
	 * Create a button that says play again
	 */
	private void createPlayAgainButton () {
		CheckersButton button = new CheckersButton("PLAY AGAIN", CheckersButton.ButtonSizes.LARGE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					segueToSubScene(SubScenes.GAME_BOARD);
					((GameScene) scene).startGame();
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		button.setLayoutX(353);
		button.setLayoutY(400);
		button.setAlignment(Pos.BASELINE_CENTER);
		add(button);
	}
	
	/**
	 * Set the winner once the game is over
	 */
	private void setWinner () {
		timeElapsedInSeconds = scene.getTimeElapsed();
		Integer minutes = (int) Math.floor(timeElapsedInSeconds / 60);
		Integer seconds = (int) (timeElapsedInSeconds % 60);
		
		String secString = seconds < 10 ? "0" + seconds : seconds.toString();
		timerLabel.setText("Time: " + minutes + ":" + secString);
		
		if (scene.getPlayerWhoWonName() == null) {
			if (scene.getWhoWon() == WinConditions.DRAW) {
				headerLabel.setText("It was a draw!");
			}
			else {
				headerLabel.setText("Computer won!");
			}
		} else {
			String winner = scene.getPlayerWhoWonName();
			headerLabel.setText(winner + " won!");
			
			JsonFileIO.save(timeElapsedInSeconds, winner);
		}
	}

	@Override
	public void transitionScene(boolean isSubSceneActive) {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.2));
		transition.setNode(this);
		transition.setToX(isSubSceneActive ? -Configs.WINDOW_WIDTH : Configs.WINDOW_WIDTH);
		
		if (isSubSceneActive) {
			setWinner();
		}
		
		transition.play();
	}

}
