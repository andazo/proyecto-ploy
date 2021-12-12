package PloyGame;
import MARDA.BoardSquare;

/**
 * Concrete class for representing a board square, contains all
 * the information about the piece placed on it.
 */
public class PloyBoardSquare extends BoardSquare {
	PloyPiece piece;

	/**
	 * Instantiates a new ploy board square.
	 *
	 * @param piece the piece to set in the square
	 */
	public PloyBoardSquare(PloyPiece piece) {
		this.piece = piece;
	}

	@Override
	public void setPiece(Object piece) {
		this.piece = (PloyPiece) piece;
	}

	@Override
	public Object getPiece() {
		return (PloyPiece) piece;
	}
}
