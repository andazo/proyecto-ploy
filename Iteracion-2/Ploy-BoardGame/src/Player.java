
public class Player {
    private String name;
    private String color;
    private int numPieces;

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
}
