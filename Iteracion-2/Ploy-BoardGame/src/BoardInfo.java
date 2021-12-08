
public class BoardInfo {
	public BoardSquareInfo[][] boardSquares;
	
	private boolean pieceActive;
	private boolean gameOver;
	private int currentPlayer;
	private int lastI;
	private int lastJ;
	private int originalDirection;
	int activePlayers;
	
	private int p1HitPiecesIndex;
	private int p2HitPiecesIndex;
	private int p3HitPiecesIndex;
	private int p4HitPiecesIndex;
	
	public String[][] p1HitPieces;
	public String[][] p2HitPieces;
	public String[][] p3HitPieces;
	public String[][] p4HitPieces;

    /**
     * 
     */
    public BoardInfo() {
    	boardSquares = new BoardSquareInfo[9][9];
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					boardSquares[i][j] = new BoardSquareInfo(-1, 0, 0, "-");
				}
			}
    	pieceActive = false;
    	gameOver = false;
    	currentPlayer = 1;
    	lastI = 0;
    	lastJ = 0;
    	originalDirection = 0;
    	p1HitPiecesIndex = 0;
    	p2HitPiecesIndex = 0;
    	p3HitPiecesIndex = 0;
    	p4HitPiecesIndex = 0;
    }
    
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
     * @param originalDirection
     */
    public void setOriginalDirection(int originalDirection) {
        this.originalDirection = originalDirection;
    }

    /**
     * @return
     */
    public int getOriginalDirection() {
        return originalDirection;
    }
    
    /**
     * @param p1HitPiecesIndex
     */
    public void setP1HitPiecesIndex(int p1HitPiecesIndex) {
        this.p1HitPiecesIndex = p1HitPiecesIndex;
    }

    /**
     * @return
     */
    public int getP1HitPiecesIndex() {
        return p1HitPiecesIndex;
    }
    
    /**
     * @param p2HitPiecesIndex
     */
    public void setP2HitPiecesIndex(int p2HitPiecesIndex) {
        this.p2HitPiecesIndex = p2HitPiecesIndex;
    }

    /**
     * @return
     */
    public int getP2HitPiecesIndex() {
        return p2HitPiecesIndex;
    }
    
    /**
     * @param p3HitPiecesIndex
     */
    public void setP3HitPiecesIndex(int p3HitPiecesIndex) {
        this.p3HitPiecesIndex = p3HitPiecesIndex;
    }

    /**
     * @return
     */
    public int getP3HitPiecesIndex() {
        return p3HitPiecesIndex;
    }
    
    /**
     * @param p4HitPiecesIndex
     */
    public void setP4HitPiecesIndex(int p4HitPiecesIndex) {
        this.p4HitPiecesIndex = p4HitPiecesIndex;
    }

    /**
     * @return
     */
    public int getP4HitPiecesIndex() {
        return p4HitPiecesIndex;
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
    
    /**
     * @param currentOwner
     * @param newOwner
     */
    public void updateOwner(int currentOwner, int newOwner) {
    	for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if(boardSquares[i][j].getOwner() == currentOwner) {
						boardSquares[i][j].setOwner(newOwner);
					}
				}
			}
    }

    // direction = -45 rota hacia la izquierda, direction = 45 rota hacia la derecha
 	/**
 	 * @param x
 	 * @param y
 	 * @param direction
 	 */
 	public void rotatePiece(int x, int y, int direction) {
 		int newDirection = boardSquares[x][y].getDirection() + direction;
 		int type = boardSquares[x][y].getType();
 		if (type == 0) {
 			if (newDirection < 0) {
	 			newDirection = newDirection + 90;
	 		} else if (newDirection >= 90) {
	 			newDirection = newDirection - 90;
	 		}
 		} else if (type == 6) {
 			if (newDirection < 0) {
	 			newDirection = newDirection + 180;
	 		} else if (newDirection >= 180) {
	 			newDirection = newDirection - 180;
	 		}
 		} else {
	 		if (newDirection < 0) {
	 			newDirection = newDirection + 360;
	 		} else if (newDirection >= 360) {
	 			newDirection = newDirection - 360;
	 		}
 		}
 		boardSquares[x][y].setDirection(newDirection);
 	}
}
