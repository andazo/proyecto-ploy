/**
 * Abstract class that represents a piece
 */
abstract class Piece {
	protected int owner;
	protected String color; 
	
	public int getOwner() {
		return owner;
	}
	
	public void setOwner(int owner) {
		this.owner = owner;
	} 
	
	public String getColor() {
		return color;
	}
	
}
