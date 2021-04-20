package subscene;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import application.Configs;
import gui.CheckersButton;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Duration;
import scene.CheckersScene;
import scene.Scenes;
import utils.GameTimer;

/**
 * 
 * Subscene for in game "Game Pause" menu.
 *
 */

public class InGameMenuSubScene extends CheckersSubScene {
	private CheckersScene scene;
	
	private List<CheckersButton> buttons;
	
	private static final int MENU_BUTTON_STARTING_POS_X = 100;
	private static final int MENU_BUTTON_STARTING_POS_Y = 150;
	
	private long timeElapsedInSeconds;
	private Label timerLabel;

	/**
	 * In game menu constructor. Adds buttons, header, logo and timer.
	 */
	public InGameMenuSubScene(CheckersScene scene) {
		super(scene);
		buttons = new ArrayList<>();
		setLayoutX(Configs.WINDOW_WIDTH);
		createButtons();
		setHeader();
		setLogo();
		setTimer();
		
		timeElapsedInSeconds = 0;
		this.scene = scene;
	}
	
	/**
	 * Adds header to scene
	 */
	private void setHeader () {
		Label header = new Label("GAME PAUSED");
		header.getStyleClass().add("header");

		header.setLayoutX(100);
		header.setLayoutY(50);
		try {
			header.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_SEMIBOLD), 52));
		} catch (FileNotFoundException e) {
			header.setFont(Font.font("Verdana", 52));
		}
		
		add(header);
	}
	
	/**
	 * Adds logo to scene
	 */
	private void setLogo () {
		ImageView logo = new ImageView("file:resources/logo.png");
		logo.setLayoutX(Configs.WINDOW_WIDTH - 375);
		logo.setLayoutY(Configs.WINDOW_HEIGHT - 300);
		add(logo);
	}
	
	/**
	 * Adds timer to the scene
	 */
	private void setTimer () {
		timerLabel = new Label("5:03");
		timerLabel.getStyleClass().add("paragraph");
		
		timerLabel.setLayoutX(950);
		timerLabel.setLayoutY(55);
		
		try {
			timerLabel.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_REGULAR), 30));
		} catch (FileNotFoundException e) {
			timerLabel.setFont(Font.font("Verdana", 30));
		}
		
		add(timerLabel);
		
		ImageView timerImage = new ImageView("file:resources/timer.png");
		timerImage.setLayoutX(900); 
		timerImage.setLayoutY(50);
		add(timerImage);
	}
	
	/**
	 * Updates timer with the current time elapsed in minutes and seconds
	 */
	private void updateTimer () {
		timeElapsedInSeconds = GameTimer.getTimeElapsedInSeconds();
		Integer minutes = (int) Math.floor(timeElapsedInSeconds / 60);
		Integer seconds = (int) (timeElapsedInSeconds % 60);
		
		String secString = seconds < 10 ? "0" + seconds : seconds.toString();
		
		Platform.runLater(() -> timerLabel.setText(minutes + ":" + secString));
	}

	/**
	 * Adds resume and forfeit buttons
	 */
	private void createButtons() {
		ResumeButton();
		ForfeitButton();
	}
	
	/**
	 * The resume option transitions the current scene to Game Board subscene
	 */
	private void ResumeButton() {
		CheckersButton button = new CheckersButton("RESUME");
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					segueToSubScene(SubScenes.GAME_BOARD);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		addMenuButton(button);
	}
	
	/**
	 * The forfeit option transitions the current scene to win condition scene then back to main menu scene
	 * 
	 */
	private void ForfeitButton() {
		CheckersButton button = new CheckersButton("FORFEIT GAME");
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					// Return the state back to the game board
					segueToSubScene(SubScenes.GAME_BOARD);
					
					// And segue to the menu scene
					scene.segueToScene(Scenes.MENU);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		addMenuButton(button);
	}
	
	/**
	 * adds the menu button options
	 */
	private void addMenuButton (CheckersButton button) {
		button.setLayoutX(MENU_BUTTON_STARTING_POS_X);
		button.setLayoutY(MENU_BUTTON_STARTING_POS_Y + buttons.size() * 100);
		buttons.add(button);
		add(button);
	}

	@Override
	public void transitionScene(boolean isSubSceneActive) {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.2));
		transition.setNode(this);
		transition.setToX(isSubSceneActive ? -Configs.WINDOW_WIDTH : Configs.WINDOW_WIDTH);
		
		if (isSubSceneActive) updateTimer();
		
		transition.play();
	}

}