/**
 * Abstract class that represents a piece
 */
abstract class Piece {
	// The type of piece
	protected int type;
	// The owner of the piece
	protected int owner;
	// The color of the piece
	protected String color;

	public void setType(int type) {
	  	this.type = type;
	}

	public int getType() {
	    return type;
	}

	public void setOwner(int owner) {
	  	this.owner = owner;
	}

	public int getOwner() {
	    return owner;
	}
	  
	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
	    return color;
	}
}
