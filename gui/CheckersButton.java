package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.Configs;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

/**
 * This class handles the logic for a button on the main menu.
 * 
 * @author Andrew Johnston
 *
 */

public class CheckersButton extends Button {
	private ButtonSizes buttonSize;
	/**
	 * Initialize a menu button.
	 * @param label String text for what the button should have labeled on it.
	 */
	public CheckersButton (String label) {
		setText(label);
		super.getStyleClass().add("menu-button");
		this.buttonSize = ButtonSizes.LARGE;
		
		initializeButtonListeners();
		setStyles();
	}
	
	/**
	 * Initialize a button with an image view, rather than text
	 * @param image ImageView
	 */
	public CheckersButton (ImageView image) {
		setGraphic(image);
		super.getStyleClass().add("menu-button");
		this.buttonSize = ButtonSizes.SMALL;
		
		initializeButtonListeners();
		setStyles();
	}
	
	/**
	 * Initialize a checkers button with a specified size.
	 * @param label String text for what the button should have labeled on it.
	 * @param size The size of the button.
	 */
	public CheckersButton (String label, ButtonSizes size) {
		setText(label);
		super.getStyleClass().add("menu-button");
		this.buttonSize = size;
		
		initializeButtonListeners();
		setStyles();
	}
	
	/**
	 * Set the button size. Styling is defined in the button.css stylesheet.
	 * @param size ButtonSizes enum
	 */
	public void setButtonSize (ButtonSizes size) {
		this.buttonSize = size;
	}
	
	/**
	 * Setup the styles for the button.
	 */
	private void setStyles () {
		setAlignment(Pos.BASELINE_LEFT);
		
		String fontType = Configs.Font.MONTSERRAT_SEMIBOLD;
		int fontSize = 30;
		
		switch (buttonSize) {
		case SMALL:
			super.getStyleClass().add("small");
			fontType = Configs.Font.MONTSERRAT_REGULAR;
			fontSize = 18;
			break;
			
		case MEDIUM:
			super.getStyleClass().add("medium");
			fontType = Configs.Font.MONTSERRAT_REGULAR;
			fontSize = 23;
			break;
			
		default:
			super.getStyleClass().add("large");
			fontType = Configs.Font.MONTSERRAT_SEMIBOLD;
			fontSize = 30;
		}
		
		setFont(fontType, fontSize);
	}
	
	/**
	 * Set the font type for buttons. Default is SemiBold.
	 * @param fontSize Int size of the font.
	 */
	private void setFont (String fontType, int fontSize) {
		try {
			setFont(Font.loadFont(new FileInputStream(fontType), fontSize));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", fontSize));
		}
	}
	
	/**
	 * Initialize any event listeners (e.g. OnMouseEnter, Click, etc.) for global
	 * effects, such as drop shadow, etc.
	 */
	private void initializeButtonListeners () {
		setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// If the left button is pressed
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					// System.out.println("Button pressed!");
				}
			}
		});
	}
	
	/**
	 * Enum class for the available sizes of the button
	 * 
	 * @author Andrew Johnston
	 *
	 */
	public enum ButtonSizes {
		LARGE,
		MEDIUM,
		SMALL
	}
}