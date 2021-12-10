
/**
 * Abstract class that represents a player
 */
abstract class Player {
	protected int id;
	protected String name, color;
	protected boolean lost;
	
	public int getID() {
		return id;
	}
	
	/**
     * @param playerName
     */
    public void setName(String playerName) {
        name = playerName;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param playerColor
     */
    public void setColor(String playerColor) {
        color = playerColor;
    }

    /**
     * @return
     */
    public String getColor() {
        return color;
    }
	
	/**
     * @param cond
     */
    public void setLost(boolean cond) {
    	lost = cond;
    }
    
    /**
     * @return
     */
    public boolean getLost() {
    	return lost;
    }
	
}
