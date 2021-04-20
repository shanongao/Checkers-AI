package subscene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.Configs;
import gui.CheckersButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Duration;
import scene.CheckersScene;
import scene.GameDifficulty;
import scene.Scenes;

/**
 * The sub-scene for the New Game screen to select difficulty, Player vs Player, etc.
 * 
 * @author Andrew Johnston, Vishnu Adda
 *
 */

public class NewGameSubScene extends CheckersSubScene {
	private CheckersScene scene;
	private String playerName;
	private GameDifficulty gameDifficulty;
	private TextField yourNameField;
	
	/**
	 * Initialize new game sub-scene.
	 * @param scene CheckersScene
	 */
	public NewGameSubScene(CheckersScene scene) {
		super(scene);
		
		// Will generate all UI components
		setLayoutX(Configs.WINDOW_WIDTH);
		createBackButton();
		createPVPButton();
		createYourNameText();
		createYourNameField();
		createDifficultyText();
		createDifficultyButtons();
		createStartButton();
		createImages();
		
		// Sets the difficulty to MEDIUM by default
		gameDifficulty = GameDifficulty.MEDIUM;
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
		
		// Position to top left
		button.setLayoutX(30);
		button.setLayoutY(30);
		add(button);
	}
	
	private void createImages () {
		// Generates the colored piece next to the name field
		ImageView playerOnePiece = new ImageView("file:resources/player-1-piece.png");
		playerOnePiece.setLayoutX(850);
		playerOnePiece.setLayoutY(182);
		add(playerOnePiece);
	}
	
	/**
	 * Create a button to switch to a PVP creation scene
	 */
	private void createPVPButton () {
		CheckersButton button = new CheckersButton("PVP", CheckersButton.ButtonSizes.MEDIUM);
		button.setStyle("-fx-border-color: white; -fx-alignment: center; -fx-min-width: 150;");
		
		// Set action to change to light green when hovered
		button.setOnMouseEntered(event -> {
			button.setStyle("-fx-border-color: lightgreen;"
					+ " -fx-text-fill: lightgreen;"
					+ " -fx-alignment: center;"
					+ " -fx-min-width: 150;");
		});
		
		// Changes color back when not hovered anymore
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
					// Should go to the PVP creation scene
					segueToSubScene(SubScenes.NEW_GAME_PVP);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		// Position to top right
		button.setLayoutX(880);
		button.setLayoutY(20);
		add(button);
	}
	
	/**
	 * Creates text for the 'Your Name' label
	 */
	private void createYourNameText () {
		Label yourName = new Label("YOUR NAME");
		yourName.getStyleClass().add("header");
		
		// Set near the top middle area
		yourName.setLayoutX(380);
		yourName.setLayoutY(100);
		
		try {
			yourName.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_REGULAR), 52));
		} catch (FileNotFoundException e) {
			yourName.setFont(Font.font("Verdana", 52));
		}
		
		add(yourName);
	}
	
	/**
	 * Creates text for the 'Difficulty' label
	 */
	private void createDifficultyText () {
		Label difficulty = new Label("DIFFICULTY");
		difficulty.getStyleClass().add("header");
		
		// Set near the middle, below the text field
		difficulty.setLayoutX(400);
		difficulty.setLayoutY(320);
		
		try {
			difficulty.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_REGULAR), 52));
		} catch (FileNotFoundException e) {
			difficulty.setFont(Font.font("Verdana", 52));
		}
		
		add(difficulty);
	}
	
	/**
	 * Creates the name entry field
	 */
	private void createYourNameField () {
		yourNameField = new TextField();
		yourNameField.setPromptText("Shaion");
		
		yourNameField.getStyleClass().add("name-text-field");
		
		// Set near the middle
		yourNameField.setLayoutX(290);
		yourNameField.setLayoutY(180);
		yourNameField.setPrefWidth(500);
		
		try {
			yourNameField.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_REGULAR), 30));
		} catch (FileNotFoundException e) {
			yourNameField.setFont(Font.font("Verdana", 30));
		}
		
		// When a key is entered in the text field, the name should be updated
		yourNameField.setOnKeyTyped(event -> {
			playerName = yourNameField.getText();
		});
		
		add(yourNameField);
	}
	
	/**
	 * Creates 3 separate difficulty buttons.
	 * Only one may be selected at any time.
	 */
	private void createDifficultyButtons () {
		CheckersButton easy = new CheckersButton("EASY", CheckersButton.ButtonSizes.MEDIUM);
		CheckersButton medium = new CheckersButton("MEDIUM", CheckersButton.ButtonSizes.MEDIUM);
		CheckersButton hard = new CheckersButton("HARD", CheckersButton.ButtonSizes.MEDIUM);
		
		CheckersButton[] difficultyButtons = {easy, medium, hard};
		
		for(CheckersButton button : difficultyButtons)
			button.setStyle("-fx-border-color: white; -fx-alignment: center;");
			
		// The following three events should update the colors based on which difficulty is selected
		// Selected should be lightgreen while unselected should be white
		easy.setOnMouseClicked(event -> {
			medium.setStyle("-fx-border-color: white; -fx-alignment: center;");
			hard.setStyle("-fx-border-color: white; -fx-alignment: center;");
			easy.setStyle("-fx-border-color: lightgreen;"
					+ " -fx-text-fill: lightgreen;"
					+ " -fx-alignment: center;");
			gameDifficulty = GameDifficulty.EASY;
		});
		
		medium.setOnMouseClicked(event -> {
			easy.setStyle("-fx-border-color: white; -fx-alignment: center;");
			hard.setStyle("-fx-border-color: white; -fx-alignment: center;");
			medium.setStyle("-fx-border-color: lightgreen;"
					+ " -fx-text-fill: lightgreen;"
					+ " -fx-alignment: center;");
			gameDifficulty = GameDifficulty.MEDIUM;
		});
		
		hard.setOnMouseClicked(event -> {
			easy.setStyle("-fx-border-color: white; -fx-alignment: center;");
			medium.setStyle("-fx-border-color: white; -fx-alignment: center;");
			hard.setStyle("-fx-border-color: lightgreen;"
					+ " -fx-text-fill: lightgreen;"
					+ " -fx-alignment: center;");
			gameDifficulty = GameDifficulty.HARD;
		});
		
		// Sets them near the bottom middle, from left to right
		easy.setLayoutX(230);
		easy.setLayoutY(400);
		
		medium.setLayoutX(450);
		medium.setLayoutY(400);
		
		hard.setLayoutX(670);
		hard.setLayoutY(400);
		
		// Set the medium button as default selection
		medium.setStyle("-fx-border-color: lightgreen;"
				+ " -fx-text-fill: lightgreen;"
				+ " -fx-alignment: center;");
		
		for(CheckersButton button : difficultyButtons) {
			add(button);
		}
			
	}
	
	/**
	 * Create a button that starts the game
	 */
	private void createStartButton () {
		CheckersButton button = new CheckersButton("START", CheckersButton.ButtonSizes.MEDIUM);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					// Verify that a name was entered, then start the game
					if (playerName != null && !playerName.isEmpty()) {
						scene.setSettings(playerName, null, true, gameDifficulty);
						
						scene.segueToScene(Scenes.GAME);
						segueToSubScene(SubScenes.MAIN_MENU);
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		button.setStyle("-fx-border-color: white; -fx-alignment: center;");
		
		// Change to light green when hovered
		button.setOnMouseEntered(event -> {
			button.setStyle("-fx-border-color: lightgreen;"
					+ " -fx-text-fill: lightgreen;"
					+ " -fx-alignment: center;");
		});
		
		// Change back to white when not hovered
		button.setOnMouseExited(event -> {
			button.setStyle("-fx-border-color: white;"
					+ " -fx-text-fill: white;"
					+ " -fx-alignment: center;");
		});
		
		// Place this near the bottom
		button.setLayoutX(450);
		button.setLayoutY(520);
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
			yourNameField.setText("");
		}
		
		transition.play();
	}

}