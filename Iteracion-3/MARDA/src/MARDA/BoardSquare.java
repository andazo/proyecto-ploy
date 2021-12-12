package MARDA;

/**
 * Abstract class for representing the information of the pieces placed on squares.
 */
public abstract class BoardSquare {
	/**
	 * Sets the piece on the board square.
	 * 
	 * @param piece The piece to set on the board square.
	 */
	protected abstract void setPiece(Object piece);
	
	/**
	 * Gets the piece from the board square.
	 *
	 * @return the piece on the board square
	 */
	protected abstract Object getPiece();
}
