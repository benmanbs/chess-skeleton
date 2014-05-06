package chess.moves;

import java.util.List;
import java.util.Map;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.pieces.King;
import chess.pieces.Piece;

public class CheckChecker {
	private GameState gameState;

	public CheckChecker(GameState gameState) {
		this.gameState = gameState;
	}
	
	public boolean checkIfMovePutsInCheck(Player p, GameState g, Move m) {
		Map<Position, Piece> board = g.getGameBoardImmutable();
		gameState = new GameState(board);
		gameState.doMove(m,g.getPieceAt(m.getOrigin()));
		return checkCheck(p);
	}
	
	public boolean checkCheck(Player p){
		//Find King of player
		Position position = null;
		for (int i = Position.MAX_ROW; i >= Position.MIN_ROW; i--) {
			for (char c = Position.MIN_COLUMN; c <= Position.MAX_COLUMN; c++) {
	            Piece piece = gameState.getPieceAt(String.valueOf(c) + i);
	            if(piece!=null &&piece.getClass().equals(King.class) && piece.getOwner()==p){
	            	position = new Position(c,i);
	            	break;
	            }
			}
		}
		//Find out if a move of other player contains position of king
		List<Move> moves = gameState.listAllMoves(p==Player.Black?Player.White:Player.Black,false);
		for(Move move : moves){
			if(move.getDestination().equals(position)){
				return true;
			}
		}
		return false;
		
	}
}
