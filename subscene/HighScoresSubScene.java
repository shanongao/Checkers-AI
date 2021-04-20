package subscene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import application.Configs;
import gui.CheckersButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import scene.CheckersScene;
import utils.JsonFileIO;

/**
 * Subscene to handle the view for the high scores menu.
 * 
 * @author Andrew Johnston
 *
 */

public class HighScoresSubScene extends CheckersSubScene {
	private Map<Long, String> highScores;
	private VBox content;

	/**
	 * Initialize the high score subscene
	 * 
	 * @param scene The parent scene so we can handle segue's
	 */
	public HighScoresSubScene(CheckersScene scene) {
		super(scene);
		this.highScores = JsonFileIO.load();
		setLayoutX(Configs.WINDOW_WIDTH);		
		createBackButton();
		setHeader();
		setTableHeader();
		setTableContent();
	}
	
	/**
	 * Add a header to the sub-scene.
	 */
	private void setHeader () {
		Label header = new Label("H I G H S C O R E S");
		header.getStyleClass().add("header");

		header.setLayoutX(305);
		header.setLayoutY(34);

		setFont(header, Configs.Font.MONTSERRAT_SEMIBOLD, 52);
		
		add(header);
	}
	
	/**
	 * Set the font type and size for a given label.
	 * @param label The JavaFX Label to apply the font to.
	 * @param fontType String FontType
	 * @param fontSize Integer size of the font.
	 */
	private void setFont (Label label, String fontType, int fontSize) {
		try {
			label.setFont(Font.loadFont(new FileInputStream(fontType), fontSize));
		} catch (FileNotFoundException e) {
			label.setFont(Font.font("Verdana", fontSize));
		}
	}
	
	/**
	 * Create the header - or top row - of the table.
	 */
	private void setTableHeader () {
		HBox header = createTableRow("RANK", "NAME", "TIME");
		header.setLayoutX(275);
		header.setLayoutY(140);
				
		add(header);
	}
	
	/**
	 * Set the content of the table. If there is no highscores saved, then
	 * load a message describing no highscores available.
	 */
	private void setTableContent () {
		// if no content available, display no content, otherwise loop through and set each row up to 10
		content = new VBox();
		content.setLayoutX(275);
		content.setLayoutY(180);
		
		if (this.highScores == null || this.highScores.size() <= 0) {
			HBox noContent = new HBox();
			Label noContentLabel = new Label("No highscores available.");
			setFont(noContentLabel, Configs.Font.MONTSERRAT_REGULAR, 22);
			noContentLabel.getStyleClass().add("empty-content");
			noContent.getChildren().add(noContentLabel);
			content.getChildren().add(noContent);
		} else {
			// get an array of times
			List<Long> times = new ArrayList<Long>();

			this.highScores.forEach((time, name) -> {
				times.add(time);
			});
			
			// sort the array by least to greatest
			Collections.sort(times);
			
			// create each row from the array
			times.forEach(time -> {
				HBox row = createTableRow(Integer.toString(times.indexOf(time) + 1), this.highScores.get(time), convertToTimeStamp(time));
				row.getStyleClass().add("table-row");
				content.getChildren().add(row);
			});
		}
		
		add(content);
	}
	
	/**
	 * Convert the given seconds into minutes and seconds.
	 * @param seconds Seconds to be converted
	 * @return String of MM:SS
	 */
	private String convertToTimeStamp (long seconds) {		
		Integer min = (int) Math.floor(seconds / 60);
		Integer sec = (int) (seconds % 60);
		
		String secString = sec < 10 ? "0" + sec : sec.toString();
		
		return min + ":" + secString;
	}
	
	/**
	 * Create the data for one row in the table.
	 * @param rank String rank
	 * @param name String name
	 * @param time String time
	 * @return HBox row
	 */
	private HBox createTableRow (String rank, String name, String time) {
		HBox hbox = new HBox();
		Label rankLabel = new Label(rank);
		Label nameLabel = new Label(name);
		Label timeLabel = new Label(time);
		
		rankLabel.getStyleClass().add("col1");
		nameLabel.getStyleClass().add("col2");
		timeLabel.getStyleClass().add("col3");
		
		setFont(rankLabel, Configs.Font.MONTSERRAT_REGULAR, 22);
		setFont(nameLabel, Configs.Font.MONTSERRAT_REGULAR, 22);
		setFont(timeLabel, Configs.Font.MONTSERRAT_REGULAR, 22);
		
		hbox.getChildren().add(rankLabel);
		hbox.getChildren().add(nameLabel);
		hbox.getChildren().add(timeLabel);
		
		return hbox;
	}
	
	/**
	 * Create a back-button to transition from the high scores subscene
	 * back to the main-menu.
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
		
		if (isSubSceneActive) {
			remove(content);
			this.highScores = JsonFileIO.load();
			setTableContent();
		}
		
		transition.play();
	}

}
