package chess.pieces;

import chess.Player;


public class PieceFactory {
	
	public Piece getPiece(char c) {
		boolean black = false;
		if (c<96){
			c+=32;
			black=true;
		}
		return getPiece(black?Player.Black:Player.White,c);
	}

	public Piece getPiece(Player p, char c) {
		switch (c) {
    	case 'r':
    		return new Rook(p);
    	case 'n':
    		return new Knight(p);
    	case 'b':
    		return new Bishop(p);
    	case 'q':
    		return new Queen(p);
    	case 'k':
    		return new King(p);
    	case 'p':
    		return new Pawn(p);	
    	}
		return null;
	}
}
