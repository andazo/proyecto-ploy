package MARDA;

/**
 * Abstract class that represents the logic aspects of the game board.
 */
public abstract class Board {	
	
	/** True if a piece is selected and ready to move. */
	protected boolean pieceActive;
	
	/** True if the game is currently over. */
	protected boolean gameOver;
	
	/** The player whose turn it is. */
	protected int currentPlayer;
	
	/** The location on the x axis of the piece previously selected. */
	protected int lastI;
	
	/** The location on the y axis of the piece previously selected. */
	protected int lastJ;
	
	/** The number of players still in the game */
	protected int activePlayers;

	/**
	 * Populates board according to the amount of players and the mode being played.
	 *
	 * @param players array of players in the game
	 * @param gameMode the game mode being played
	 */
	protected abstract void populateBoard(Object[] players, int gameMode);

	public void setPieceActive(boolean pieceActive) {
		this.pieceActive = pieceActive;
	}

	public boolean getPieceActive() {
		return pieceActive;
	}

	protected void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	protected boolean getGameOver() {
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

	public void setActivePlayers(int players) {
		activePlayers = players;
	}

	public int getActivePlayers() {
		return activePlayers;
	}
	
	/**
	 * Set the player whose turn it is
	 *
	 * @param currentPlayer the current player
	 */
	public void loadCurrentPlayer(int currentPlayer) {
		setCurrentPlayer(currentPlayer);
	}
}
