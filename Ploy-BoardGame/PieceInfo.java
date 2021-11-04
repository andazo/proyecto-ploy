
public class PieceInfo {
    int type;
    int direction;
    int owner;

    public PieceInfo(int type, int direction, int owner) {
        this.type = type;
        this.direction = direction;
        this.owner = owner;
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
}