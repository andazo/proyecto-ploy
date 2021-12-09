
public class PloyBoardSquare extends BoardSquare {
	int direction;
	
	/**
     * @param type
     * @param direction
     * @param owner
     * @param color
     */
	public PloyBoardSquare(int type, int direction, int owner, String color) {
        this.type = type;
        this.direction = direction;
        this.owner = owner;
        this.color = color;
    }
	
	/**
     * @param direction
     */
    public void setDirection(int direction) {
    	this.direction = direction;
    }

    /**
     * @return
     */
    public int getDirection() {
        return direction;
    }
}
