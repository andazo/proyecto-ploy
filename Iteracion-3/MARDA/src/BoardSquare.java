
abstract class BoardSquare {
	protected int type;
	protected int owner;
	protected String color;

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
