
import java.io.FileWriter;
import java.io.IOException;

/**
 * Concrete class of a file manager for Ploy
 */
public class PloyFileManager extends FileManager {

	/**
	 * Saves a file with the current board's status.
	 *
	 * @param players the players in the game
	 * @param gameMode the game mode being played
	 * @param board the current board and all its information
	 * @param fileName name for the new save file
	 */
	@Override
	public void saveFile(Object[] players, int gameMode, Object board, String fileName) {	
		// Write content to a file
		try (FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "/Saves/" + fileName, false)) {
			String fileContent = "players";
			for (int i = 0; i < players.length; i++) {
				fileContent = fileContent + " " + ((PloyPlayer) players[i]).getName() + " " + ((PloyPlayer) players[i]).getColor() + " " + ((PloyPlayer) players[i]).getNumPieces() + " " + ((PloyPlayer) players[i]).getLost() + " " + ((PloyPlayer) players[i]).getFriend();
			}
			fileContent = fileContent + "\n" + "gameMode " + gameMode;
			fileContent = fileContent + "\n" + "currentPlayer " + ((PloyBoard) board).getCurrentPlayer();
			fileContent = fileContent + "\n" + "board";
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					fileContent = fileContent + "\n" + ((PloyBoard) board).boardSquares[i][j].getType()
							+ " " + ((PloyBoard) board).boardSquares[i][j].getDirection()
							+ " " + ((PloyBoard) board).boardSquares[i][j].getOwner()
							+ " " + ((PloyBoard) board).boardSquares[i][j].getColor();
				}
			}
			fileContent = fileContent + "\n" + "p1HitPiecesIndex " + ((PloyBoard) board).getP1HitPiecesIndex();
			fileContent = fileContent + "\n" + "p1HitPiecesData";
			for (int i = 0; i < ((PloyBoard) board).getP1HitPiecesIndex(); i++) {
				fileContent = fileContent + "\n" + ((PloyBoard) board).p1HitPieces[i][0] + " " + ((PloyBoard) board).p1HitPieces[i][1];
			}
			fileContent = fileContent + "\n" + "p2HitPiecesIndex " + ((PloyBoard) board).getP2HitPiecesIndex();
			fileContent = fileContent + "\n" + "p2HitPiecesData";
			for (int i = 0; i < ((PloyBoard) board).getP2HitPiecesIndex(); i++) {
				fileContent = fileContent + "\n" + ((PloyBoard) board).p2HitPieces[i][0] + " " + ((PloyBoard) board).p2HitPieces[i][1];
			}
			fileContent = fileContent + "\n" + "p3HitPiecesIndex " + ((PloyBoard) board).getP3HitPiecesIndex();
			fileContent = fileContent + "\n" + "p3HitPiecesData";
			for (int i = 0; i < ((PloyBoard) board).getP3HitPiecesIndex(); i++) {
				fileContent = fileContent + "\n" + ((PloyBoard) board).p3HitPieces[i][0] + " " + ((PloyBoard) board).p3HitPieces[i][1];
			}
			fileContent = fileContent + "\n" + "p4HitPiecesIndex " + ((PloyBoard) board).getP4HitPiecesIndex();
			fileContent = fileContent + "\n" + "p4HitPiecesData";
			for (int i = 0; i < ((PloyBoard) board).getP4HitPiecesIndex(); i++) {
				fileContent = fileContent + "\n" + ((PloyBoard) board).p4HitPieces[i][0] + " " + ((PloyBoard) board).p4HitPieces[i][1];
			}

			fileWriter.write(fileContent);
			fileWriter.close();
		} catch (IOException e) {
			// Exception handling
		}
	}

	/**
	 * Gets the players from a saved file and creates an array containing them.
	 *
	 * @return the array of players created from the file
	 */
	@Override
	public Object[] getPlayers() {
		String token = "players";
		int pos = 0;

		for (int i = 0; i < data.length; i++) {
			if (data[i].contains(token)) {
				pos = i;
				break;
			}
		}

		String[] lineData = data[pos].split(" ");

		PloyPlayer[] players = new PloyPlayer[lineData.length / 5];
		for (int i = 0; i < lineData.length / 5; i++) {
			players[i] = new PloyPlayer(null, 0, null);
		}
		pos = 0;

		for (int i = 1; i < lineData.length; i++) {
			if (i % 5 == 1) {
				players[pos].setName(lineData[i]);
			} else if (i % 5 == 2) {
				players[pos].setColor(lineData[i]);
			} else if (i % 5 == 3) {
				players[pos].setNumPieces(Integer.parseInt(lineData[i]));
			} else if (i % 5 == 4) {
				players[pos].setLost(Boolean.parseBoolean(lineData[i]));
			} else if (i % 5 == 0) {
				players[pos].setFriend(Integer.parseInt(lineData[i]));
				pos++;
			}
		}

		return players;
	}


	/**
	 * Gets the game mode from a saved file.
	 *
	 * @return the game mode 
	 */
	@Override
	public int getGameMode() {
		String token = "gameMode";
		int pos = 0;

		for (int i = 0; i < data.length; i++) {
			if (data[i].contains(token)) {
				pos = i;
				break;
			}
		}

		String[] lineData = data[pos].split(" ");

		return Integer.parseInt(lineData[1]);
	}

	/**
	 * Gets the current player from a saved file.
	 *
	 * @return the current player
	 */
	@Override
	public int getCurrentPlayer() {
		String token = "currentPlayer";
		int pos = 0;

		for (int i = 0; i < data.length; i++) {
			if (data[i].contains(token)) {
				pos = i;
				break;
			}
		}

		String[] lineData = data[pos].split(" ");

		return Integer.parseInt(lineData[1]);
	}

	/**
	 * Gets the board data from a saved file.
	 *
	 * @return the board data from the file
	 */
	@Override
	public String[][][] getBoardData() {
		String token = "board";
		int pos = 0;

		for (int i = 0; i < data.length; i++) {
			if (data[i].contains(token)) {
				pos = i + 1;
				break;
			}
		}

		int lastPos = 9 * 9;
		String[][][] board = new String[9][9][4];
		for (int i = 0; i < lastPos; i++) {
			String[] lineData = data[pos].split(" ");
			board[i / 9][i % 9] = lineData;
			pos = pos + 1;
		}

		return board;
	}

	/**
	 * Gets the hit pieces indexes from a save file
	 * 
	 * @return array of indexes for every hit piece array
	 */
	public int[] getHitPiecesIndexes() {
		int[] hitPiecesIndexes = new int[4];
		String token = "";
		int pos = 0;

		for (int i = 0; i < 4; i++) {
			token = "p" + (i + 1) + "HitPiecesIndex";

			for (int j = pos; j < data.length; j++) {
				if (data[j].contains(token)) {
					pos = j;
				}
			}

			String[] lineData = data[pos].split(" ");
			hitPiecesIndexes[i] = Integer.parseInt(lineData[1]);
		}

		return hitPiecesIndexes;
	}

	/**
	 * Gets all the captured pieces for every player in a save file.
	 * 
	 * @return collection of captured pieces for every player in the file 
	 */
	public String[][][] getHitPieces() {
		String[][][] hitPieces = new String[4][15][2];
		String tokenIni = "";
		String tokenFin = "";
		int posIni = 0;
		int posFin = 0;

		for (int i = 0; i < 4; i++) {
			tokenIni = "p" + (i + 1) + "HitPiecesData";

			for (int j = posIni; j < data.length; j++) {
				if (data[j].contains(tokenIni)) {
					posIni = j + 1;
				}
			}

			tokenFin = "p" + (i + 2) + "HitPiecesIndex";

			boolean found = false;
			for (int j = posIni; j < data.length; j++) {
				if (data[j].contains(tokenFin)) {
					posFin = j;
					found = true;
				} else {
					if (found == false) {
						posFin = data.length;
					}
				}
			}

			if (posIni == data.length) {
				posFin = posIni;
			}

			int pos = posIni;

			for (int j = 0; j < posFin - posIni; j++) {
				String[] lineData = data[pos].split(" ");
				hitPieces[i][j] = lineData;
				pos = pos + 1;
			}
		}

		return hitPieces;
	}
}
