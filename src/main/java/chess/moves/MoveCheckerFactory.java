package chess.moves;

import chess.GameState;
import chess.pieces.Piece;

public class MoveCheckerFactory {
	private GameState gameState;
	
	public MoveCheckerFactory(GameState gameState) {
		this.gameState = gameState;
	}
	public MoveChecker getMoveChecker(Piece p) {
		switch (Character.toLowerCase(p.getIdentifier())) {
    	case 'r':
    		return new RookMoveChecker(gameState);
    	case 'n':
    		return new KnightMoveChecker(gameState);
    	case 'b':
    		return new BishopMoveChecker(gameState);
    	case 'q':
    		return new QueenMoveChecker(gameState);
    	case 'k':
    		return new KingMoveChecker(gameState);
    	case 'p':
    		return new PawnMoveChecker(gameState);	
    	}
		return null;
	}

}
