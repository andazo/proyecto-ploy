package MARDA;

/**
 * Abstract class for representing the information of the pieces placed on squares.
 */
public abstract class BoardSquare {
	protected abstract void setPiece(Object piece);
	protected abstract Object getPiece();
}
