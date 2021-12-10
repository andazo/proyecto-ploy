/**
 * Concrete class representing a piece of Ploy.
 */
public class PloyPiece extends Piece {
	private int type, direction;
	
	/**
	 * Instantiates a new Ploy piece.
	 *
	 * @param type is the type of a piece
	 * @param direction indicates where the piece can be moved
	 * @param owner indicates to which player the piece belongs
	 * @param color indicates the color of the piece
	 */
	PloyPiece(int type, int direction, int owner, String color) {
		this.type = type;
		this.direction = direction;
		this.owner = owner;
		this.color = color;
	}
	
	public int getType() {
		return type;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
}
