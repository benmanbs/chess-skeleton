package chess.moves;

import java.util.List;

import chess.GameState;
import chess.Position;
import chess.pieces.Piece;

public abstract class MoveChecker {
	private GameState gameState;
	
	public MoveChecker(GameState gameState){
		this.gameState = gameState;
	}
	
	protected GameState getGameState() {
		return gameState;
	}
	
	public abstract List<Move> getAllMoves(Piece piece, Position position, boolean checkCheck);
}
