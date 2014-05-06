package chess.moves;

import java.util.LinkedList;
import java.util.List;

import chess.GameState;
import chess.Position;
import chess.pieces.Piece;

public class KnightMoveChecker extends MoveChecker {

	public KnightMoveChecker(GameState gameState) {
		super(gameState);
	}

	@Override
	public List<Move> getAllMoves(Piece piece, Position position, boolean checkCheck) {
		List<Move> moves = new LinkedList<Move>();
		int[][] coords = {{1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2}};
		for(int[] i : coords){
			int x = position.getRow() + i[0];
			char y = (char)(position.getColumn() + i[1]);
			if(x<=Position.MAX_ROW && x>=Position.MIN_ROW && y<=Position.MAX_COLUMN && y>=Position.MIN_COLUMN){
				Piece p = getGameState().getPieceAt(new Position(y,x));
				CheckChecker checker = new CheckChecker(super.getGameState());
				if(p==null || p.getOwner()!=piece.getOwner())
					if(!checkCheck || !checker.checkIfMovePutsInCheck(piece.getOwner(), super.getGameState(),new Move(position,new Position(y,x)) ))
						moves.add(new Move(position, new Position(y,x)));
			}
		}
		return moves;
	}

}
