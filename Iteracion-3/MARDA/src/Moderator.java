
abstract class Moderator {
	abstract void clickedOn(int i, int j, int numPlayers, int gameMode, Object[] players, Object board, Object gui);
	abstract void checkGameOver(Object hitInfo, Object attackerInfo, int gameMode, Object[] players, Object board, Object gui);
	abstract char finished(Object gui);
	abstract void playerLost(Object hitInfo, Object attackerInfo, int gameMode, Object[] players, Object board, Object gui);
	abstract void removeActions(Object gui);
	abstract String[][] getValidMoves(int i, int j, Object board);
	abstract void highlightMoves(String[][] moves, int i, int j, Object gui);
	abstract void cancelHighlightMoves(String[][] moves, int i, int j, Object gui);
}
