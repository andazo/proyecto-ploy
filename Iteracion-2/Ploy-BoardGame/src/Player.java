public class Player {
    private String name;
    private String color;
    private int numPieces;
    private boolean lost;
    private int friend;

    public void setName(String playerName) {
        name = playerName;
    }

    public String getName() {
        return name;
    }

    public void setColor(String playerColor) {
        color = playerColor;
    }

    public String getColor() {
        return color;
    }
    
    public void setNumPieces(int n) {
    		numPieces = n;
    }
    
    public int getNumPieces() {
    		return numPieces;
    }
    
    public void setLost(boolean cond) {
    	lost = cond;
    }
    
    public boolean getLost() {
    	return lost;
    }
    
    public void setFriend(int f) {
    	friend = f;
    }
    
    public int getFriend() {
    	return friend;
    }
}
