package utils;

import java.util.ArrayList;

import javafx.util.Pair;
import scene.GameDifficulty;

/**
 * A Checkers AI based on the minimax algorithm
 * 
 * Based on the following Python implementation:
 * https://www.youtube.com/watch?v=RjdrFHEgV2o
 * @author Vishnu Adda
 *
 */
public class CheckersAI {
	/**
	 * Move function to call the AI.
	 * @param gameBoard int[][] of the gameboard state
	 * @param difficulty difficulty level from GameDifficulty
	 * @return CheckersAIReturn object with from, to and any jumped enemies.
	 */
	public static CheckersAIReturn move (CheckersLogic logic, GameDifficulty difficulty) {
		
		// Sets the depth based on the given difficulty (more depth = better AI)
		int depth = 0;
		switch(difficulty) {
		case EASY:
			depth = 2;
			break;
		case HARD:
			depth = 4;
			break;
		// MEDIUM case
		default:
			depth = 3;
			break;
		}
		
		// Does a deep copy of the game logic for this class to manipulate
		CheckersLogic newlogic = deepCopyLogic(logic);
		
		// Calls minimax and extracts the new game logic for the game to use
		Pair<Double, CheckersLogic> minimaxReturn = minimax(newlogic, depth, true);
		CheckersLogic minimaxLogic = minimaxReturn.getValue();
		
		// Gets the piece's original location, and its new location
		Pair<Vector2i, Vector2i> fromAndToPair = getFromTo(logic, minimaxLogic);
		Vector2i from = fromAndToPair.getKey();
		Vector2i to = fromAndToPair.getValue();
		
		return new CheckersAIReturn(from, to);
	}
	
	/**
	 * Given an old and new board, this method finds which piece was moved (from and where to)
	 * @param oldBoard
	 * @param newBoard
	 * @return
	 */
	private static Pair<Vector2i, Vector2i> getFromTo(CheckersLogic oldBoard, CheckersLogic newBoard) {
		int[][] oldGameBoard = oldBoard.getGameBoard();
		int[][] newGameBoard = newBoard.getGameBoard();
		
		// Initializes these values
		Vector2i from = new Vector2i(0, 0);
		Vector2i to   = new Vector2i(1,1);
		
		for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	// from: found by finding where there used to be a piece
                if (oldGameBoard[i][j] != 0 && (oldGameBoard[i][j] == 2 || oldGameBoard[i][j] == 22) && newGameBoard[i][j] == 0) from = new Vector2i(i, j);
                
                // to: found by finding where a space used to be empty
                if (newGameBoard[i][j] != 0 && oldGameBoard[i][j] == 0) to = new Vector2i(i, j);
            }
        }
		
		return new Pair<Vector2i, Vector2i>(from, to);
	}
	
	/**
	 * Deep copies the board from the parameter 'gameLogic'
	 * @param gameLogic
	 * @return
	 */
	private static CheckersLogic deepCopyLogic(CheckersLogic gameLogic) {
		// Initializes a new gameboard so we don't have to worry about shared refs
		CheckersLogic newLogic = new CheckersLogic();
		
		newLogic.setGameBoard(gameLogic.getGameBoard());
		return newLogic;
	}
	
	/**
	 * Implementation of the minimax algorithm
	 * The recursion will swap between white and black as each turn is a different player
	 * Should get a new game board and lower depth as the recursion goes deeper
	 * @param gameLogic
	 * @param depth
	 * @param isBlackPlayer
	 * @return
	 */
	private static Pair<Double, CheckersLogic> minimax(CheckersLogic gameLogic,
																  int depth,
																  boolean isBlackPlayer) {
		// Base case of when recursion reaches the lowest level, or if it finds a case where the AI wins
		if (depth == 0 || gameLogic.hasWonGame() == 2) {
			Double evaluation = evaluateBoard(gameLogic);
			return new Pair<Double, CheckersLogic>(evaluation, deepCopyLogic(gameLogic));
		}
		
		// Case for 'white' player
		if (!isBlackPlayer) {
			Double maxEvaluation = Double.MIN_VALUE;
			
			// Key represents original position, Value represents new position
			Pair<Vector2i, Vector2i> bestMove = null;
			
			// Gets all the white pieces, and finds all of their possible moves
			for (Vector2i whitePiece : gameLogic.getAllWhitePieces()) {
				for (Vector2i move : gameLogic.getAllMoves(whitePiece)) {
					CheckersLogic whiteLogic = deepCopyLogic(gameLogic);
					
					// Makes the move for a deepcopied board
					whiteLogic.move(whitePiece, new ArrayList<Vector2i>() {
						private static final long serialVersionUID = 1L;
						{
							add(move);
						}});
					
					Double evaluation = minimax(whiteLogic, depth-1, true).getKey();
					
					// Finds the evaluation that gives a better outcome for the white player
					maxEvaluation = Math.max(maxEvaluation, evaluation);
					if (maxEvaluation.equals(evaluation)) {
						bestMove = new Pair<>(whitePiece, move);
					}
				}
			}
			
			// Prepare the return value for recursion (by best move)
			CheckersLogic newMove = deepCopyLogic(gameLogic);
			ArrayList<Vector2i> newArrayList = new ArrayList<>();
			
			newArrayList.add(bestMove != null ? bestMove.getValue() : null);
			newMove.move(bestMove != null ? bestMove.getKey() : null, newArrayList);
			
			return new Pair<>(maxEvaluation, newMove);
			
		} 
		// Case for 'black' player
		else {
			Double minEvaluation = Double.MAX_VALUE;
			
			// Key represents original position, Value represents new position
			Pair<Vector2i, Vector2i> bestMove = null;
			
			// Gets all the black pieces, and finds all of their possible moves
			for (Vector2i blackPiece : gameLogic.getAllBlackPieces()) {
				for (Vector2i move : gameLogic.getAllMoves(blackPiece)) {
					CheckersLogic blackLogic = deepCopyLogic(gameLogic);
					
					// Makes the move for a deepcopied board
					blackLogic.move(blackPiece, new ArrayList<Vector2i>() {
						private static final long serialVersionUID = 1L;
						{
							add(move);
						}});
					
					Double evaluation = minimax(blackLogic, depth-1, false).getKey();
					
					// Finds the evaluation that gives a better outcome for the black player
					minEvaluation = Math.min(minEvaluation, evaluation);
					if (minEvaluation.equals(evaluation)) {
						bestMove = new Pair<>(blackPiece, move);
					}
				}
			}
			
			// Prepare the return value for recursion (by best move)
			CheckersLogic newMove = deepCopyLogic(gameLogic);
			ArrayList<Vector2i> newArrayList = new ArrayList<>();
			
			newArrayList.add(bestMove != null ? bestMove.getValue() : null);
			newMove.move(bestMove != null ? bestMove.getKey() : null, newArrayList);
			
			return new Pair<>(minEvaluation, newMove);
		}
	}
	
	/**
	 * Evaluates the board based on white piece and black piece count
	 * White player wants to maximize the score, while black player wants to minimize it
	 * 
	 * AI wants to minimize it, as it is the black player
	 * 
	 * Give extra weight to kings
	 * @param logic
	 * @return
	 */
	private static double evaluateBoard(CheckersLogic logic) {
		// Gives extra weight to kings
		double evaluation = logic.getWhitePiecesCount() - logic.getBlackPiecesCount() +
				(logic.getWhiteKingsCount() * 0.5 - logic.getBlackKingsCount() * 0.5);
		return evaluation;
	}
}
