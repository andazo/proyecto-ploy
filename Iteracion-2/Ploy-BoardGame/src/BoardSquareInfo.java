
public class BoardSquareInfo {
	private int type;
	private int direction;
	private int owner;
	private String color;

    /**
     * @param type
     * @param direction
     * @param owner
     * @param color
     */
    public BoardSquareInfo(int type, int direction, int owner, String color) {
        this.type = type;
        this.direction = direction;
        this.owner = owner;
        this.color = color;
    }

    /**
     * @param type
     */
    public void setType(int type) {
    	this.type = type;
    }

    /**
     * @return
     */
    public int getType() {
        return type;
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
    
    /**
     * @param owner
     */
    public void setOwner(int owner) {
    	this.owner = owner;
    }

    /**
     * @return
     */
    public int getOwner() {
        return owner;
    }
    
    /**
     * @param color
     */
    public void setColor(String color) {
    	this.color = color;
    }

    /**
     * @return
     */
    public String getColor() {
        return color;
    }
}
