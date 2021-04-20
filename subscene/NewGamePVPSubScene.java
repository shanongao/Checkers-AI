package subscene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import gui.CheckersButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Duration;
import application.Configs;
import scene.CheckersScene;
import scene.Scenes;

/**
 * The sub-scene for the New Game screen to create a Player vs Player match
 * @author Vishnu Adda
 *
 */
public class NewGamePVPSubScene extends CheckersSubScene {
	private CheckersScene scene;
	private String playerOneName;
	private String playerTwoName;
	private TextField p1NameField;
	private TextField p2NameField;

	/**
	 * Initialize the new game PVP sub-scene
	 * @param scene
	 */
	public NewGamePVPSubScene(CheckersScene scene) {
		super(scene);
		setLayoutX(Configs.WINDOW_WIDTH);
		
		// Generates the UI elements
		createBackButton();
		createSoloButton();
		createNameTexts();
		createPlayerFields();
		createStartButton();
		createImages();
		
		this.scene = scene;
	}
	
	/**
	 * Create a back button to the main menu sub-scene.
	 */
	private void createBackButton () {
		CheckersButton button = new CheckersButton(" BACK", CheckersButton.ButtonSizes.MEDIUM);
		button.setGraphic(new ImageView("file:resources/chevron-left.png"));
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					segueToSubScene(SubScenes.MAIN_MENU);
					//sceneManager.segueTo(Scenes.GAME);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		// Places back button near the top left
		button.setLayoutX(30);
		button.setLayoutY(30);
		add(button);
	}
	
	/**
	 * Displays images for the black and white piece next to the respective player names
	 */
	private void createImages () {
		ImageView playerOnePiece = new ImageView("file:resources/player-1-piece.png");
		playerOnePiece.setLayoutX(850);
		playerOnePiece.setLayoutY(182);
		add(playerOnePiece);
		
		ImageView playerTwoPiece = new ImageView("file:resources/player-2-piece.png");
		playerTwoPiece.setLayoutX(850);
		playerTwoPiece.setLayoutY(382);
		add(playerTwoPiece);
	}
	
	/**
	 * Create a button to switch to a Solo game creation scene
	 */
	private void createSoloButton () {
		CheckersButton button = new CheckersButton("SOLO", CheckersButton.ButtonSizes.MEDIUM);
		button.setStyle("-fx-border-color: white; -fx-alignment: center; -fx-min-width: 150;");
		
		// Lambdas related to hover styles, i.e green when hovered, white when not
		button.setOnMouseEntered(event -> {
			button.setStyle("-fx-border-color: lightgreen;"
					+ " -fx-text-fill: lightgreen;"
					+ " -fx-alignment: center;"
					+ " -fx-min-width: 150;");
		});
		
		button.setOnMouseExited(event -> {
			button.setStyle("-fx-border-color: white;"
					+ " -fx-text-fill: white;"
					+ " -fx-alignment: center;"
					+ " -fx-min-width: 150;");
		});
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					// When clicked, the solo game creation scene should be displayed
					segueToSubScene(SubScenes.NEW_GAME);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		// Set this button near the top right
		button.setLayoutX(880);
		button.setLayoutY(20);
		add(button);
	}
	
	/**
	 * Creates text for Player 1 and Player 2
	 */
	private void createNameTexts () {
		Label p1Name = new Label("PLAYER 1 NAME");
		Label p2Name = new Label("PLAYER 2 NAME");
		p1Name.getStyleClass().add("header");
		p2Name.getStyleClass().add("header");
		
		// Sets their names one above the other
		p1Name.setLayoutX(340);
		p1Name.setLayoutY(100);
		
		p2Name.setLayoutX(332);
		p2Name.setLayoutY(300);
		
		try {
			p1Name.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_REGULAR), 52));
			p2Name.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_REGULAR), 52));
		} catch (FileNotFoundException e) {
			p1Name.setFont(Font.font("Verdana", 52));
			p2Name.setFont(Font.font("Verdana", 52));
		}
		
		add(p1Name);
		add(p2Name);
	}
	
	/**
	 * Creates the name entry fields for both players
	 */
	private void createPlayerFields () {
		p1NameField = new TextField();
		p2NameField = new TextField();
		
		// Filler / sample text that is initially displayed
		p1NameField.setPromptText("Andrew");
		p2NameField.setPromptText("Vishnu");
		
		p1NameField.getStyleClass().add("name-text-field");
		p2NameField.getStyleClass().add("name-text-field");
		
		// Placed below their respective texts
		p1NameField.setLayoutX(290);
		p1NameField.setLayoutY(180);
		p1NameField.setPrefWidth(500);
		
		p2NameField.setLayoutX(290);
		p2NameField.setLayoutY(380);
		p2NameField.setPrefWidth(500);
		
		try {
			p1NameField.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_REGULAR), 30));
			p2NameField.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_REGULAR), 30));
		} catch (FileNotFoundException e) {
			p1NameField.setFont(Font.font("Verdana", 30));
			p2NameField.setFont(Font.font("Verdana", 30));
		}
		
		// When a key is entered into any of the fields, their respective logical name should update
		p1NameField.setOnKeyTyped(event -> {
			playerOneName = p1NameField.getText();
		});
		
		p2NameField.setOnKeyTyped(event -> {
			playerTwoName = p2NameField.getText();
		});
		
		add(p1NameField);
		add(p2NameField);
	}
	
	/**
	 * Create a button that starts the game
	 */
	private void createStartButton () {
		CheckersButton button = new CheckersButton("START", CheckersButton.ButtonSizes.MEDIUM);
		try {
			button.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_REGULAR), 30));
		} catch (FileNotFoundException e1) {
			button.setFont(Font.font("Verdana", 30));
			e1.printStackTrace();
		}
		
		button.setStyle("-fx-border-color: white; -fx-alignment: center;");
		
		
		// Hover lambdas; green if hovered, white if not
		button.setOnMouseEntered(event -> {
			button.setStyle("-fx-border-color: lightgreen;"
					+ " -fx-text-fill: lightgreen;"
					+ " -fx-alignment: center;");
		});
		
		button.setOnMouseExited(event -> {
			button.setStyle("-fx-border-color: white;"
					+ " -fx-text-fill: white;"
					+ " -fx-alignment: center;");
		});
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					// Verify that the fields are filled, and then start the game
					if (playerOneName != null && playerTwoName != null &&
						!playerOneName.isEmpty() && !playerTwoName.isEmpty()) {
						scene.setSettings(playerOneName, playerTwoName, false, null);
						
						scene.segueToScene(Scenes.GAME);
						segueToSubScene(SubScenes.MAIN_MENU);
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		// Places the button near the bottom
		button.setLayoutX(440);
		button.setLayoutY(500);
		add(button);
	}

	/**
	 * Method to aid in the transition between two scenes (basically updates their position)
	 */
	@Override
	public void transitionScene(boolean isSubSceneActive) {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.2));
		transition.setNode(this);
		transition.setToX(isSubSceneActive ? -Configs.WINDOW_WIDTH : Configs.WINDOW_WIDTH);
		
		if (isSubSceneActive) {
			p1NameField.setText("");
			p2NameField.setText("");
		}
		
		transition.play();
	}

}
