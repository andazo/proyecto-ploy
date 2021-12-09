
/**
 * Concrete class for representing a board square, contains all
 * the information about the piece placed on it.
 */
public class PloyBoardSquare extends BoardSquare {
	int direction;
	
	/**
	 * Instantiates a new ploy board square.
	 *
	 * @param type the type of piece in the square
	 * @param direction the direction the piece is facing
	 * @param owner the owner of the piece
	 * @param color the color of the piece
	 */
	public PloyBoardSquare(int type, int direction, int owner, String color) {
        this.type = type;
        this.direction = direction;
        this.owner = owner;
        this.color = color;
    }
	
    public void setDirection(int direction) {
    	this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
