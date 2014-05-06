package chess.moves;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.pieces.Pawn;

public class PawnMoveCheckerTest {
	
	@Test
	public void TestBoardEdge() {
		GameState gameState = new GameState();
		
		PawnMoveChecker checker = new PawnMoveChecker(gameState);
		List<Move> moves = checker.getAllMoves(new Pawn(Player.White), new Position('a',8),true);
		Assert.assertTrue("Pawn should not move past edge of row.", moves.isEmpty());
	}
	
	@Test
	public void TestDiagonalCapture() {
		GameState gameState = new GameState("+---+---+---+---+---+---+---+---+" +
											"|   | R | B | Q | K | B | N | R |" +
											"+---+---+---+---+---+---+---+---+" +
											"| p | P | P | P | P | P | P | P |" +
											"+---+---+---+---+---+---+---+---+" +
											"|   |   |   |   |   |   |   |   |" +
											"+---+---+---+---+---+---+---+---+" +
											"|   |   |   |   |   |   |   |   |" +
											"+---+---+---+---+---+---+---+---+" +
											"|   |   |   |   |   |   |   |   |" +
											"+---+---+---+---+---+---+---+---+" +
											"|   |   |   |   |   |   |   |   |" +
											"+---+---+---+---+---+---+---+---+" +
											"| p | p | p | p | p | p | p | p |" +
											"+---+---+---+---+---+---+---+---+" +
											"| r | n | b | q | k | b | n | r |" +
											"+---+---+---+---+---+---+---+---+");
		
		PawnMoveChecker checker = new PawnMoveChecker(gameState);

		List<Move> moves = checker.getAllMoves(new Pawn(Player.White), new Position('a',7),true);
		Assert.assertTrue("Pawn should take rook or move forward - 2 moves.",moves.size()==2 && moves.contains(new Move(new Position('a',7), new Position('b',8))));
	}
	
	@Test
	public void TestDoesNotCaptureOwnPiece() {
		GameState gameState = new GameState("+---+---+---+---+---+---+---+---+" +
											"| R | p | B | Q | K | B | N | R |" +
											"+---+---+---+---+---+---+---+---+" +
											"| p | P | P | P | P | P | P | P |" +
											"+---+---+---+---+---+---+---+---+" +
											"|   |   |   |   |   |   |   |   |" +
											"+---+---+---+---+---+---+---+---+" +
											"|   |   |   |   |   |   |   |   |" +
											"+---+---+---+---+---+---+---+---+" +
											"|   |   |   |   |   |   |   |   |" +
											"+---+---+---+---+---+---+---+---+" +
											"|   |   |   |   |   |   |   |   |" +
											"+---+---+---+---+---+---+---+---+" +
											"| p | p | p | p | p | p | p | p |" +
											"+---+---+---+---+---+---+---+---+" +
											"| r | n | b | q | k | b | n | r |" +
											"+---+---+---+---+---+---+---+---+");
		
		PawnMoveChecker checker = new PawnMoveChecker(gameState);

		List<Move> moves = checker.getAllMoves(new Pawn(Player.White), new Position('a',7),true);
		Assert.assertTrue("Pawn should not move take own piece.", moves.isEmpty());
	}

}
