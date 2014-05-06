package chess.moves;

import chess.Position;

public class Move {
	private Position origin;
	private Position destination;
	
	public Move(Position origin, Position destination) {
		this.origin = origin;
		this.destination = destination;
	}

	public Position getOrigin() {
		return origin;
	}

	public Position getDestination() {
		return destination;
	}

	@Override
	public String toString() {
		return origin + " " + destination;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Move)){
			return false;
		}
		Move move = (Move) o;
		if(move.getOrigin().equals(this.origin) && move.getDestination().equals(this.destination)){
			return true;
		}
		return false;
	}

}
