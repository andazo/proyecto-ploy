
/**
 * Concrete class for representing a board square, contains all
 * the information about the piece placed on it.
 */
public class PloyBoardSquare extends BoardSquare {
	PloyPiece piece;
	
	/**
	 * Instantiates a new ploy board square.
	 *
	 * @param type the type of piece in the square
	 * @param direction the direction the piece is facing
	 * @param owner the owner of the piece
	 * @param color the color of the piece
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
