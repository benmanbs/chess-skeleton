package chess.moves;

import java.util.LinkedList;
import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.pieces.Piece;

public class PawnMoveChecker extends MoveChecker {
	Position checkSpace;

	public PawnMoveChecker(GameState gameState) {
		super(gameState);
	}

	@Override
	public List<Move> getAllMoves(Piece piece, Position position, boolean checkCheck) {
		List<Move> moves = new LinkedList<Move>();
		Player player = piece.getOwner();
		int playerMult = 1;
		if(player==Player.Black)
			playerMult=-1;
		
		CheckChecker checker = new CheckChecker(super.getGameState());
		//Check Pawn spot one ahead
		if(canMoveOneAhead(position, playerMult))
			if(!checkCheck || !checker.checkIfMovePutsInCheck(piece.getOwner(), super.getGameState(),new Move(position,checkSpace) ))
				moves.add(new Move(position,checkSpace));
		//Check Homerow Pawn spot two ahead
		if(canMoveTwoAhead(position, playerMult))
			if(!checkCheck || !checker.checkIfMovePutsInCheck(piece.getOwner(), super.getGameState(),new Move(position,checkSpace) ))
				moves.add(new Move(position,checkSpace));
		//Check Diagonal right
		if(canMoveDiagonal(position,player,playerMult,1))
			if(!checkCheck || !checker.checkIfMovePutsInCheck(piece.getOwner(), super.getGameState(),new Move(position,checkSpace) ))
				moves.add(new Move(position,checkSpace));
		//Check Diagonal left
		if(canMoveDiagonal(position,player,playerMult,-1))
			if(!checkCheck || !checker.checkIfMovePutsInCheck(piece.getOwner(), super.getGameState(),new Move(position,checkSpace) ))
				moves.add(new Move(position,checkSpace));
		return moves;
	}
	
	private boolean canMoveOneAhead(Position position, int playerMult) {
		if(playerMult>0 && position.getRow()==Position.MAX_ROW)
			return false;
		if(playerMult<0 && position.getRow()==Position.MIN_ROW)
			return false;
		return getGameState().getPieceAt(checkSpace=new Position(position.getColumn(),position.getRow()+1*playerMult))==null;
	}
	
	private boolean canMoveTwoAhead(Position position, int playerMult) {
		if(!isHomeRow(playerMult,position))
			return false;
		return getGameState().getPieceAt(checkSpace)==null
			&& getGameState().getPieceAt(checkSpace = new Position(position.getColumn(),position.getRow()+2*playerMult))==null;
	}
	
	private boolean canMoveDiagonal(Position position, Player player, int playerMult, int diagDirection) {
		Piece p = getGameState().getPieceAt(checkSpace = new Position((char)(position.getColumn()+diagDirection),position.getRow()+1*playerMult));
		return p!=null&&p.getOwner()!=player;
	}
	
    private boolean isHomeRow(int p, Position pos){
    	return (p<0&&pos.getRow()==7)||(p>0&&pos.getRow()==2);
    }

}
