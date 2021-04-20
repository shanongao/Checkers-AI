package test.utils;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import utils.CheckersLogic;
import utils.Vector2i;

public class CheckersLogicTest {
	@Test
	public void intitialize () {
		CheckersLogic logic = new CheckersLogic();
		int[][] gameBoard = logic.getGameBoard();
		
		assertTrue(gameBoard[0][0] == 1);
	}
	
	@Test
	public void getAllMoves () {
		CheckersLogic logic = new CheckersLogic();
		ArrayList<Vector2i> moves = logic.getAllMoves(new Vector2i(0, 2));
		
		assertTrue(moves.size() == 1);
		assertTrue(moves.get(0).x == 1 && moves.get(0).y == 3);
	} 
	
	@Test
	public void isSelectable () {
		CheckersLogic logic = new CheckersLogic();
		assertTrue(logic.isSelectable(1, new Vector2i(0, 0)));
		assertTrue(!logic.isSelectable(1, new Vector2i(6, 6)));
		
		assertTrue(!logic.isSelectable(2, new Vector2i(0, 0)));
		assertTrue(logic.isSelectable(2, new Vector2i(6, 6)));
	}
	
	@Test
	public void canMove () {
		CheckersLogic logic = new CheckersLogic();
		try {
			Vector2i from = new Vector2i(0, 2);
			Vector2i move = logic.canMove(from, new Vector2i(1, 3));
			
			assertTrue(move.equals(from));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void move () {
		CheckersLogic logic = new CheckersLogic();
		ArrayList<Vector2i> moves = new ArrayList<Vector2i>();
		moves.add(new Vector2i(1, 3));
		
		ArrayList<Vector2i> mv = logic.move(new Vector2i(0, 2), moves);
		assertTrue(mv.size() == 1);
	}
	
	@Test
	public void checkIfDraw () {
		CheckersLogic logic = new CheckersLogic();
		assertTrue(!logic.checkIfDraw(1));
		assertTrue(!logic.checkIfDraw(2));
	}
	
	@Test
	public void hasWonGame () {
		CheckersLogic logic = new CheckersLogic();
		assertTrue(logic.hasWonGame() == -1);
	}
}
