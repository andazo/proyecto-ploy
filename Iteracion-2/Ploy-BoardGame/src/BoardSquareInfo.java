
public class BoardSquareInfo {
	private int type;
	private int direction;
	private int owner;
	private String color;

    public BoardSquareInfo(int type, int direction, int owner, String color) {
        this.type = type;
        this.direction = direction;
        this.owner = owner;
        this.color = color;
    }

    public void setType(int type) {
    	this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setDirection(int direction) {
    	this.direction = direction;
    }

    public int getDirection() {
        return direction;
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
