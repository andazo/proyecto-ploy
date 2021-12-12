package PloyGame;
import MARDA.Piece;

/**
 * Concrete class representing a piece of Ploy.
 */
abstract class PloyPiece extends Piece {
	// The direction of the piece
	protected int direction;

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}
