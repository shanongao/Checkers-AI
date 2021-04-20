package subscene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import application.Configs;
import gui.CheckersButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import scene.CheckersScene;

/**
 * The main-menu sub-scene in the MenuScene.
 * 
 * @author Andrew Johnston
 * 
 */

public class MainMenuSubScene extends CheckersSubScene {
	private static final int MENU_BUTTON_STARTING_POS_X = 100;
	private static final int MENU_BUTTON_STARTING_POS_Y = 150;
	
	/**
	 * A list of all the buttons on this sub-scene.
	 */
	private List<CheckersButton> buttons;
	
	/**
	 * Initialize the sub-scene.
	 * @param scene CheckersScene
	 */
	public MainMenuSubScene (CheckersScene scene) {
		super(scene);
		buttons = new ArrayList<>();
		createButtons();
		setHeader();
		setFooter();
		setLogo();
	}
	
	/**
	 * Add the header to the scene.
	 */
	private void setHeader () {
		Label header = new Label("C H E C K E R S");
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
	 * Add the footer to the scene.
	 */
	private void setFooter () {
		Label footer = new Label("CURATED BY: ANDREW JOHNSTON, SHANON GAO, SHAION HABIBVAND, VISHNU ADDA");
		footer.getStyleClass().add("footer");

		footer.setLayoutX(20);
		footer.setLayoutY(Configs.WINDOW_HEIGHT - 30);
		try {
			footer.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_MEDIUM), 14));
		} catch (FileNotFoundException e) {
			footer.setFont(Font.font("Verdana", 14));
		}
		
		add(footer);
	}
	
	/**
	 * Add the logo to the scene.
	 */
	private void setLogo () {
		ImageView logo = new ImageView("file:resources/logo.png");
		logo.setLayoutX(Configs.WINDOW_WIDTH - 375);
		logo.setLayoutY(Configs.WINDOW_HEIGHT - 300);
		add(logo);
	}
	
	/**
	 * Create the menu buttons.
	 */
	private void createButtons() {
		createNewGameButton();
		createHighScoresButton();
		createHowToPlayButton();
		createExitButton();
	}
	
	/**
	 * Add a menu button and calculate it's position.
	 * @param button Node button to be added.
	 */
	private void addMenuButton (CheckersButton button) {
		button.setLayoutX(MENU_BUTTON_STARTING_POS_X);
		button.setLayoutY(MENU_BUTTON_STARTING_POS_Y + buttons.size() * 100);
		buttons.add(button);
		add(button);
	}
	
	/**
	 * Create the New Game button.
	 */
	private void createNewGameButton () {
		CheckersButton button = new CheckersButton("NEW GAME");
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					segueToSubScene(SubScenes.NEW_GAME);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		addMenuButton(button);
	}
	
	/**
	 * Create the High Scores button.
	 */
	private void createHighScoresButton () {
		CheckersButton button = new CheckersButton("HIGH SCORES");
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					segueToSubScene(SubScenes.HIGH_SCORES);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		addMenuButton(button);
	}
	
	/**
	 * Create the How to Play button.
	 */
	private void createHowToPlayButton () {
		CheckersButton button = new CheckersButton("HOW TO PLAY");
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					segueToSubScene(SubScenes.HOW_TO_PLAY);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		addMenuButton(button);
	}
	
	/**
	 * Create the Exit button.
	 */
	private void createExitButton () {
		CheckersButton button = new CheckersButton("EXIT");
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Node source = (Node) event.getSource();
					Stage stage = (Stage) source.getScene().getWindow();
					stage.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});

		addMenuButton(button);
	}

	@Override
	public void transitionScene(boolean isSubSceneActive) {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.2));
		transition.setNode(this);
		transition.setToX(isSubSceneActive ? 0 : -Configs.WINDOW_WIDTH);
		
		transition.play();
	}
}