
public class Board {	
	protected boolean pieceActive;
	protected boolean gameOver;
	protected int currentPlayer;
	protected int lastI;
	protected int lastJ;
	protected int activePlayers;
    
    /**
     * @param pieceActive
     */
    public void setPieceActive(boolean pieceActive) {
        this.pieceActive = pieceActive;
    }

    /**
     * @return
     */
    public boolean getPieceActive() {
        return pieceActive;
    }
    
    /**
     * @param gameOver
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * @return
     */
    public boolean getGameOver() {
        return gameOver;
    }
    
    /**
     * @param currentPlayer
     */
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * @return
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * @param lastI
     */
    public void setLastI(int lastI) {
        this.lastI = lastI;
    }

    /**
     * @return
     */
    public int getLastI() {
        return lastI;
    }
    
    /**
     * @param lastJ
     */
    public void setLastJ(int lastJ) {
        this.lastJ = lastJ;
    }

    /**
     * @return
     */
    public int getLastJ() {
        return lastJ;
    }
    
    /**
     * @param players
     */
    public void setActivePlayers(int players) {
    	activePlayers = players;
    }
    
    /**
     * @return
     */
    public int getActivePlayers() {
    	return activePlayers;
    }
}
