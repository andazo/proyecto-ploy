public class Player {
    private String name;
    private String color;
    private int numPieces;
    private boolean lost;
    private int friend;

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
     * @param n
     */
    public void setNumPieces(int n) {
    		numPieces = n;
    }
    
    /**
     * @return
     */
    public int getNumPieces() {
    		return numPieces;
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
    
    /**
     * @param f
     */
    public void setFriend(int f) {
    	friend = f;
    }
    
    /**
     * @return
     */
    public int getFriend() {
    	return friend;
    }
}
