package subscene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import application.Configs;
import gui.CheckersButton;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.util.Duration;
import scene.CheckersScene;
import scene.GameDifficulty;
import utils.CheckersAIReturn;
import utils.CheckersLogic;
import utils.GameTimer;
import utils.Vector2i;

/**
 * Subscene for the Game Board.
 * 
 * TODO
 * Bug: If AI wins, it shows a screen saying "It was a draw!"
 * 
 * @author Andrew Johnston
 *
 */
public class GameBoardSubScene extends CheckersSubScene {
	private CheckersScene scene;
	private int[][] gameBoard;
	
	private Label title;
	private Label timerLabel;
	
	private boolean isSinglePlayer;
	private String playerOneName;
	private String playerTwoName;
	
	private int playerTurn;
	
	private Vector2i gamePieceCoordSelected;
	private ImageView gamePieceImageSelected;
	private ImageView highlightedSpace;
	
	private Map<Vector2i, ImageView> gameBoardImages;
	private Map<String, ImageView> gameBoardPieces;
	
	private CheckersLogic checkersLogic;
	
	private GameDifficulty gameDifficulty;
	
	/**
	 * Initialize the sub-scene.
	 * @param scene CheckersScene
	 */
	public GameBoardSubScene(CheckersScene scene) {
		super(scene);
		
		this.scene = scene;
		
		checkersLogic = new CheckersLogic();
		gameBoardImages = new HashMap<Vector2i, ImageView>();
		gameBoardPieces = new HashMap<String, ImageView>();
		setup();
	}
	
	/**
	 * Start the game. Should be called when segue'd to
	 */
	public void start () {
		if (highlightedSpace != null) {
			remove(highlightedSpace);
			highlightedSpace = null;
		}
		
		GameTimer.reset();
		checkersLogic.reset();
		resetGameboard();
		
		isSinglePlayer = scene.getIsSinglePlayer();
		playerOneName = scene.getPlayerOneName();
		playerTwoName = scene.getPlayerTwoName();
		gameDifficulty = scene.getGameDifficulty();
		
		changeToPlayersTurn(1);
		startTimer();
	}
	
	/**
	 * Reset the gameboard to its original state. Clean up all images and re-initialize them.
	 */
	private void resetGameboard () {
		for (Map.Entry<Vector2i, ImageView> entry : gameBoardImages.entrySet()) {
			remove(entry.getValue());
		}
		
		for (Map.Entry<String, ImageView> entry : gameBoardPieces.entrySet()) {
			remove(entry.getValue());
		}
		
		initializeGameBoard();
	}
	
	/**
	 * Initially set up the game board.
	 */
	private void setup () {
		initializeImages();
		initializeText();
		createMenuButton();
	}
	
	/**
	 * Initialize the static images to the game board (e.g. timer).
	 */
	private void initializeImages () {
		ImageView timerImage = new ImageView("file:resources/timer.png");
		timerImage.setLayoutX(50); 
		timerImage.setLayoutY(15);
		add(timerImage);
	}
	
	/**
	 * Initialize the static text to the game board.
	 */
	private void initializeText () {
		title = createLabel("", 0, 20, true, "header", 52);
		add(title);
		
		add(createLabel("P1", 95, 98, false, "header", 52));
		add(createLabel("P2", 925, 98, false, "header", 52));
		
		timerLabel = createLabel("5:03", 100, 20, false, "paragraph", 30);
		add(timerLabel);
	}
	
	/**
	 * Change the player turn from the current player to the specified one.
	 * @param player Integer (1 or 2)
	 */
	private void changeToPlayersTurn (Integer player) {
		playerTurn = player;
		gamePieceCoordSelected = null;
		gamePieceImageSelected = null;
		highlightedSpace = null;
		
		if (player == 1) {
			title.setText(playerOneName + "'s Turn");
		} else if (player == 2 && isSinglePlayer) {
			title.setText("Opponent's Turn");
			
			Platform.runLater(() -> {
				CheckersAIReturn aiMoveReturn = checkersLogic.moveAI(gameDifficulty);
				gamePieceImageSelected = gameBoardPieces.get(aiMoveReturn.getFrom().toString());
				gamePieceCoordSelected = aiMoveReturn.getFrom();
				
				ArrayList<Vector2i> aiMoves = new ArrayList<Vector2i>();
				aiMoves.add(aiMoveReturn.getTo());
				ArrayList<Vector2i> movePiece = checkersLogic.move(aiMoveReturn.getFrom(), aiMoves);
				
				if (movePiece.size() > 0) {
					updatePieceImage(gamePieceCoordSelected, aiMoveReturn.getTo());
					updateGameBoard(aiMoveReturn.getTo(), movePiece, checkersLogic.getGameBoard()[aiMoveReturn.getTo().x][aiMoveReturn.getTo().y] != checkersLogic.getGameBoard()[aiMoveReturn.getFrom().x][aiMoveReturn.getFrom().y]);
						
					int checkForWinningPlayer = checkersLogic.hasWonGame();
					
					if (checkForWinningPlayer == 1) {
						endGame(checkForWinningPlayer);
					} else if (checkForWinningPlayer == 2) {
						endGame(-2);
					} else {
						changeToPlayersTurn(playerTurn == 1 ? 2 : 1);
							
						if (checkersLogic.checkIfDraw(playerTurn) && checkForWinningPlayer < 0) {
							endGame(-1);
						}
					}
				}
				
				changeToPlayersTurn(1);
			});
			
			

			
		} else {
			title.setText(playerTwoName + "'s Turn");
		}
	}
	
	/**
	 * Utility function to condense the creation of labels.
	 * @param text String
	 * @param x Integer x-coordinate
	 * @param y Integer y-coordinate
	 * @param maxWidth Boolean if it should fill the max-width of the screen
	 * @param className String
	 * @param fontSize Integer
	 * @return Label
	 */
	private Label createLabel (String text, Integer x, Integer y, boolean maxWidth, String className, Integer fontSize) {
		Label label = new Label(text);
		label.getStyleClass().add(className);
		
		label.setLayoutX(x);
		label.setLayoutY(y);
		
		if (maxWidth) label.setMinWidth(Configs.WINDOW_WIDTH);
		
		try {
			label.setFont(Font.loadFont(new FileInputStream(Configs.Font.MONTSERRAT_REGULAR), fontSize));
		} catch (FileNotFoundException e) {
			label.setFont(Font.font("Verdana", fontSize));
		}
		
		return label;
	}
	
	/**
	 * Create an empty gameboard with the images and event handlers.
	 */
	private void initializeGameBoard () {
		int[][] gameBoard = checkersLogic.getGameBoard();
	
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int x = i;
				int y = j; 

				// Place a background on the playable spaces
				if (gameBoard[i][j] > -1) {
					ImageView space = new ImageView("file:resources/playable-space.png");
					
					space.setLayoutX(240 + i * 75); // 240 = (Configs.WINDOW_WIDTH / 2) - (total game board width / 2). 75 = width of PNG used
					space.setLayoutY(100 + j * 75); // 100 constant value to push the game board down 100px from the top, preserving 20px padding on bottom
					
					space.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
						onClick(new Vector2i(x, y), null);
						event.consume();
					});
					
					add(space);
					gameBoardImages.put(new Vector2i(i, j), space);
				}
				
				if (gameBoard[i][j] == 1) {
					ImageView playerOnePiece = new ImageView("file:resources/player-1-piece.png");
					playerOnePiece.setLayoutX(240 + i * 75); 
					playerOnePiece.setLayoutY(100 + j * 75);
					setPickOnBounds(true); // allows clicking on transparent part of PNG
					playerOnePiece.setViewOrder(-1.0);
					
					playerOnePiece.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
						int playerOnePieceXCoord = (int) ((playerOnePiece.getLayoutX() - 240) / 75);
						int playerOnePieceYCoord = (int) ((playerOnePiece.getLayoutY() - 100) / 75);
						onClick(new Vector2i(playerOnePieceXCoord, playerOnePieceYCoord), playerOnePiece);
						event.consume();
					});
					
					add(playerOnePiece);
					gameBoardPieces.put(new Vector2i(i, j).toString(), playerOnePiece);
				}
				
				// put the game pieces down for player 2
				if (gameBoard[i][j] == 2) {
					ImageView playerTwoPiece = new ImageView("file:resources/player-2-piece.png");
					playerTwoPiece.setLayoutX(240 + i * 75); 
					playerTwoPiece.setLayoutY(100 + j * 75);
					playerTwoPiece.setPickOnBounds(true); // allows clicking on transparent part of PNG
					playerTwoPiece.setViewOrder(-1.0);
					
					playerTwoPiece.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
						int playerTwoPieceXCoord = (int) ((playerTwoPiece.getLayoutX() - 240) / 75);
						int playerTwoPieceYCoord = (int) ((playerTwoPiece.getLayoutY() - 100) / 75);
						onClick(new Vector2i(playerTwoPieceXCoord, playerTwoPieceYCoord), playerTwoPiece);
						event.consume();
					});
					
					add(playerTwoPiece);
					gameBoardPieces.put(new Vector2i(i, j).toString(), playerTwoPiece);
				}
			}
			
			// Add a border to the game board. The asset is 2px wide, so the placement
			// accounts for it needing to be 2px up and left.
			ImageView border = new ImageView("file:resources/gameboard-border.png");
			border.setLayoutX(238);
			border.setLayoutY(98);
			add(border);
			gameBoardImages.put(null, border);
		}
	}
	
	/**
	 * Update the timer each second.
	 * @param timeElapsedInSeconds Long
	 */
	private void updateTimerText (long timeElapsedInSeconds) {
		Integer minutes = (int) Math.floor(timeElapsedInSeconds / 60);
		Integer seconds = (int) (timeElapsedInSeconds % 60);
		
		String secString = seconds < 10 ? "0" + seconds : seconds.toString();
		
		Platform.runLater(() -> timerLabel.setText(minutes + ":" + secString));
	}
	
	private void startTimer() {
		GameTimer.start((timeElapsedInSeconds) -> {
			updateTimerText(timeElapsedInSeconds);
		});
	}
	
	/**
	 * Check to see where we're clicking. If on the game board and a space that's selectable,
	 * then select it (e.g. piece that we're going to move). Then subsequent selections
	 * should be where the piece will move to.
	 */
	private void onClick (Vector2i coord, ImageView playerPiece) {
		if (gamePieceCoordSelected != null && playerPiece == null) {
			// take the game piece at the x,y and move its coords to the new coord
				ArrayList<Vector2i> moves = new ArrayList<Vector2i>();
				moves.add(coord);
				
				int gamePieceSelectedValue = checkersLogic.getGameBoard()[gamePieceCoordSelected.x][gamePieceCoordSelected.y];
				
				ArrayList<Vector2i> movePiece = checkersLogic.move(gamePieceCoordSelected, moves);
				
				if (movePiece.size() > 0) {
					updatePieceImage(gamePieceCoordSelected, coord);
					
					updateGameBoard(coord, movePiece, checkersLogic.getGameBoard()[coord.x][coord.y] != gamePieceSelectedValue);
					
					int checkForWinningPlayer = checkersLogic.hasWonGame();
					
					if (checkForWinningPlayer > 0) {
						endGame(checkForWinningPlayer);
					} else {
						changeToPlayersTurn(playerTurn == 1 ? 2 : 1);
						
						if (checkersLogic.checkIfDraw(playerTurn)) {
							endGame(-1);
						}
					}
					
				} else {
					System.out.println("Not a valid move, try again!");
				}
		}
		
		if (checkersLogic.isSelectable(playerTurn, coord)) {
			// If a player piece is already selected and now we're trying to move
			 if (playerPiece != null) {
				gamePieceCoordSelected = coord;
				gamePieceImageSelected = playerPiece;
				
				if (highlightedSpace != null) {
					remove(highlightedSpace);
					highlightedSpace = null;
				}

				highlightedSpace = new ImageView("file:resources/space-highlight.png");
				highlightedSpace.setLayoutX(playerPiece.getLayoutX()); 
				highlightedSpace.setLayoutY(playerPiece.getLayoutY());
				
				add(highlightedSpace);
			}
		}
	}
	
	private void updatePieceImage (Vector2i from, Vector2i to) {
		gameBoardPieces.put(to.toString(), gameBoardPieces.get(from.toString()));
		gameBoardPieces.remove(from.toString());
	}
	
	private void updateGameBoard (Vector2i to, ArrayList<Vector2i> jumpedPieces, boolean hasUpgradedToKing) {
		this.gameBoard = checkersLogic.getGameBoard();

		gamePieceImageSelected.setLayoutX(240 + to.x * 75);
		gamePieceImageSelected.setLayoutY(100 + to.y * 75);
		
		if (gameBoard[to.x][to.y] == 11 && hasUpgradedToKing) {
			gamePieceImageSelected.setImage(new Image("file:resources/player-1-piece-stacked.png"));
		} else if (gameBoard[to.x][to.y] == 22 && hasUpgradedToKing) {
			gamePieceImageSelected.setImage(new Image("file:resources/player-2-piece-stacked.png"));
		}
		
		if (jumpedPieces.size() > 0) {
			remove(gameBoardPieces.get(jumpedPieces.get(0).toString()));
		}
		
		// remove selection
		gamePieceCoordSelected = null;
		gamePieceImageSelected = null;
		
		// remove the highlight
		if (highlightedSpace != null) {
			remove(highlightedSpace);
			highlightedSpace = null;
		}
	}
	
	/**
	 * End the game, presumably during a win-condition step.
	 */
	private void endGame (int playerWhoWon) {
		GameTimer.pause();
		
		if (playerWhoWon == 1) {
			scene.setWinner(playerOneName, GameTimer.getTimeElapsedInSeconds(), WinConditions.PLAYER_ONE);
		} else if (playerWhoWon == 2 && !isSinglePlayer) {
			scene.setWinner(playerTwoName, GameTimer.getTimeElapsedInSeconds(), WinConditions.PLAYER_TWO);
		} else if (playerWhoWon == -2 && isSinglePlayer) {
			scene.setWinner(null, GameTimer.getTimeElapsedInSeconds(), WinConditions.PLAYER_TWO);
		} else {
			scene.setWinner(null, GameTimer.getTimeElapsedInSeconds(), WinConditions.DRAW);
		}
		
		segueToSubScene(SubScenes.WIN_CONDITION);
	}

	/**
	 * Initialize a placeholder menu button to transition to the in-game menu
	 * subscene.
	 */
	private void createMenuButton () {
		CheckersButton button = new CheckersButton(new ImageView("file:resources/hamburger-menu.png"));
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					GameTimer.pause();
					segueToSubScene(SubScenes.IN_GAME_MENU);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		button.setLayoutX(970);
		button.setLayoutY(5);
		add(button);
	}

	@Override
	public void transitionScene(boolean isSubSceneActive) {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.2));
		transition.setNode(this);
		transition.setToX(isSubSceneActive ? 0 : -Configs.WINDOW_WIDTH);
		
		if (isSubSceneActive) startTimer();
		//else GameTimer.pause();
		
		transition.play();
	}

}
