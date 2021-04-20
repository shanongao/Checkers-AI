package subscene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.Configs;
import gui.CheckersButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Duration;
import scene.CheckersScene;

/**
 * Subscene for the How To Play screen in the Main Menu.
 * 
 * @author Andrew Johnston
 *
 */
public class HowToPlaySubScene extends CheckersSubScene {

	/**
	 * Initialize the sub-scene.
	 * @param scene CheckersScene
	 */
	public HowToPlaySubScene(CheckersScene scene) {
		super(scene);
		setLayoutX(Configs.WINDOW_WIDTH);
		createBackButton();
		setHeader();
		setCopy();
		setImages();
	}
	
	/**
	 * Set the subscene's header.
	 */
	private void setHeader () {
		Label header = new Label("H O W  T O  P L A Y");
		header.getStyleClass().add("header");

		header.setLayoutX(300);
		header.setLayoutY(34);
		try {
			header.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_SEMIBOLD), 52));
		} catch (FileNotFoundException e) {
			header.setFont(Font.font("Verdana", 52));
		}
		
		add(header);
	}
	
	/**
	 * Set the text copy for the sub-scene.
	 */
	private void setCopy () {
		setText("Checkers is played on an 8x8 grid, where opposing players control\n\n" + 
				"12 pieces of a specific color.", 150);
		
		setText("Players may move one piece on their given turn; either diagonally by 1 space,\n\n" + 
				"or to jump over and eliminate an opposing piece.\n\n" + 
				"Successive jumps are allowed, meaning if another jump is possible after jumping\n\n" + 
				"over a piece, players may do so.\n", 300);
		
		setText("In order to win, you must eliminate all of the opposing player's pieces.", 650);
	}
	
	/**
	 * Add a text element to the sub-scene.
	 * @param text String for the what the label should say.
	 * @param posY Integer Y-Position for where the element should be vertically aligned. Note that the
	 * X position is statically defined as all elements are left-aligned the same for this sub-scene.
	 */
	private void setText (String text, int posY) {
		Label label = new Label(text);
		label.getStyleClass().add("paragraph");
		label.setLayoutX(175);
		label.setLayoutY(posY);
		
		try {
			label.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_REGULAR), 20));
		} catch (FileNotFoundException e) {
			label.setFont(Font.font("Verdana", 20));
		}
		
		add(label);
	}
	
	/**
	 * Add the images to the sub-scene as described in the design.
	 */
	private void setImages () {
		ImageView checkersPieces = new ImageView("file:resources/how-to-play-pieces.png");
		checkersPieces.setLayoutX(500);
		checkersPieces.setLayoutY(200);
		add(checkersPieces);
		
		ImageView move = new ImageView("file:resources/how-to-play-move.png");
		move.setLayoutX(700);
		move.setLayoutY(450);
		add(move);
	}
	
	/**
	 * Initialize a back button to transition back to the main-menu
	 * subscene.
	 */
	
	private void createBackButton () {
		CheckersButton button = new CheckersButton(" BACK", CheckersButton.ButtonSizes.MEDIUM);
		button.setGraphic(new ImageView("file:resources/chevron-left.png"));
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					segueToSubScene(SubScenes.MAIN_MENU);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		button.setLayoutX(30);
		button.setLayoutY(30);
		add(button);
	}

	@Override
	public void transitionScene(boolean isSubSceneActive) {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.2));
		transition.setNode(this);
		transition.setToX(isSubSceneActive ? -Configs.WINDOW_WIDTH : Configs.WINDOW_WIDTH);
		
		transition.play();
	}

}
