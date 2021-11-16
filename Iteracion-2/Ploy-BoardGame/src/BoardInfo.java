
public class BoardInfo {
	public BoardSquareInfo[][] boardSquares;
	
	private boolean pieceActive;
	private boolean gameOver;
	private int currentPlayer;
	private int lastI;
	private int lastJ;
	private int originalDirection;
	
	private int p1HitPiecesIndex;
	private int p2HitPiecesIndex;
	private int p3HitPiecesIndex;
	private int p4HitPiecesIndex;
	
	public String[][] p1HitPieces;
	public String[][] p2HitPieces;
	public String[][] p3HitPieces;
	public String[][] p4HitPieces;

    public BoardInfo() {
    	boardSquares = new BoardSquareInfo[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				boardSquares[i][j] = new BoardSquareInfo(-1, 0, 0, "-");
			}
		}
    	pieceActive = false;
    	gameOver = false;
    	currentPlayer = 0;
    	lastI = 0;
    	lastJ = 0;
    	originalDirection = 0;
    	p1HitPiecesIndex = 0;
    	p2HitPiecesIndex = 0;
    	p3HitPiecesIndex = 0;
    	p4HitPiecesIndex = 0;
    }
    
    public void setPieceActive(boolean pieceActive) {
        this.pieceActive = pieceActive;
    }

    public boolean getPieceActive() {
        return pieceActive;
    }
    
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean getGameOver() {
        return gameOver;
    }
    
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }
    
    public void setLastI(int lastI) {
        this.lastI = lastI;
    }

    public int getLastI() {
        return lastI;
    }
    
    public void setLastJ(int lastJ) {
        this.lastJ = lastJ;
    }

    public int getLastJ() {
        return lastJ;
    }
    
    public void setOriginalDirection(int originalDirection) {
        this.originalDirection = originalDirection;
    }

    public int getOriginalDirection() {
        return originalDirection;
    }
    
    public void setP1HitPiecesIndex(int p1HitPiecesIndex) {
        this.p1HitPiecesIndex = p1HitPiecesIndex;
    }

    public int getP1HitPiecesIndex() {
        return p1HitPiecesIndex;
    }
    
    public void setP2HitPiecesIndex(int p2HitPiecesIndex) {
        this.p2HitPiecesIndex = p2HitPiecesIndex;
    }

    public int getP2HitPiecesIndex() {
        return p2HitPiecesIndex;
    }
    
    public void setP3HitPiecesIndex(int p3HitPiecesIndex) {
        this.p3HitPiecesIndex = p3HitPiecesIndex;
    }

    public int getP3HitPiecesIndex() {
        return p3HitPiecesIndex;
    }
    
    public void setP4HitPiecesIndex(int p4HitPiecesIndex) {
        this.p4HitPiecesIndex = p4HitPiecesIndex;
    }

    public int getP4HitPiecesIndex() {
        return p4HitPiecesIndex;
    }

}
