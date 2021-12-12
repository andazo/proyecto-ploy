package MARDA;

/**
 * Abstract class for a moderator in charge of reviewing the game's state to determine 
 * if a player is out, legal moves and game over conditions. 
 */
public abstract class Moderator {

	/**
	 * Handles the clicking of a piece in case it is not currently active. This includes
	 * checking whose turn it is, highlighting legal moves and enabling rotation buttons.
	 * In the case a piece is currently active, it handles legality of moves, capturing 
	 * enemy pieces and checking the state of the game after a move.
	 *
	 * @param i location on the x axis of the board
	 * @param j location on the y axis of the board
	 * @param numPlayers the number of players in the game
	 * @param gameMode the game mode being played
	 * @param players array of players in the game
	 * @param board current instance of the board 
	 * @param gui current instance of the gui
	 */
	protected abstract void clickedOn(int i, int j, int numPlayers, int gameMode, Object[] players, Object board, Object gui);

	/**
	 * Checks if the game is over after a move has been made.
	 *
	 * @param hitInfo the information of the piece that was captured
	 * @param attackerInfo the information of the piece that made the attack
	 * @param gameMode the game mode being played
	 * @param players the players in the game
	 * @param board instance of the board
	 * @param gui instance of the gui
	 */
	protected abstract void checkGameOver(Object hitInfo, Object attackerInfo, int gameMode, Object[] players, Object board, Object gui);

	/**
	 * Shows a prompt allowing the user to decide what to do after a game is finished.
	 *
	 * @param gui instance of the gui
	 * @return variable indicating if the user wants a new game
	 */
	protected abstract char finished(Object gui);

	/**
	 * Removes a player from the match
	 *
	 * @param hitInfo the information of the piece that was captured
	 * @param attackerInfo the information of the piece that made the attack
	 * @param gameMode the game mode being played
	 * @param players the players in the game
	 * @param board instance of the board
	 * @param gui instance of the gui
	 */
	protected abstract void playerLost(Object hitInfo, Object attackerInfo, int gameMode, Object[] players, Object board, Object gui);

	/**
	 * Removes the actions from the board's squares, locking the board.
	 *
	 * @param gui instance of the gui
	 */
	protected abstract void removeActions(Object gui);

	/**
	 * Gets the valid moves for the piece currently in play.
	 *
	 * @param i location on the x axis of the board
	 * @param j location on the y axis of the board
	 * @param board instance of the board
	 * @return matrix of valid moves for the piece
	 */
	protected abstract String[][] getValidMoves(int i, int j, Object board);

	/**
	 * Highlights the legal moves for the piece currently in play.
	 *
	 * @param moves the valid moves for a piece
	 * @param i location on the x axis of the board
	 * @param j location on the y axis of the board
	 * @param gui instance of the gui
	 */
	protected abstract void highlightMoves(String[][] moves, int i, int j, Object gui);

	/**
	 * Removes the highlights of available moves.
	 *
	 * @param moves the valid moves for a piece
	 * @param i location on the x axis of the board
	 * @param j location on the y axis of the board
	 * @param gui instance of the gui
	 */
	protected abstract void cancelHighlightMoves(String[][] moves, int i, int j, Object gui);
}
