
public class PloyPiece extends Piece {
	private int type, direction;
	
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
