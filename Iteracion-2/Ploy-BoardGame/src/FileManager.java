import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;

public class FileManager {
	String fileName;
	String[] data;
	
	public FileManager() {
		fileName = "SavedData.txt";
	}
	
	public void saveFile(Player[] players, int gameMode, BoardInfo boardInfo) {	
		// Write content to a file
		try (FileWriter fileWriter = new FileWriter(fileName, false)) {
		    String fileContent = "players";
		    for (int i = 0; i < players.length; i++) {
		    	fileContent = fileContent + " " + players[i].getName() + " " + players[i].getColor();
		    }
		    fileContent = fileContent + "\n" + "gameMode " + gameMode;
		    fileContent = fileContent + "\n" + "board";
		    for (int i = 0; i < 9; i++) {
		    	for (int j = 0; j < 9; j++) {
		    		fileContent = fileContent + "\n" + boardInfo.boardSquares[i][j].getType()
		    				+ " " + boardInfo.boardSquares[i][j].getDirection()
		    				+ " " + boardInfo.boardSquares[i][j].getOwner()
		    				+ " " + boardInfo.boardSquares[i][j].getColor();
		    	}
		    }
		    fileContent = fileContent + "\n" + "p1HitPiecesIndex " + boardInfo.getP1HitPiecesIndex();
		    fileContent = fileContent + "\n" + "p1HitPiecesData";
		    for (int i = 0; i < boardInfo.getP1HitPiecesIndex(); i++) {
		    	fileContent = fileContent + "\n" + boardInfo.p1HitPieces[i][0] + " " + boardInfo.p1HitPieces[i][1];
		    }
		    fileContent = fileContent + "\n" + "p2HitPiecesIndex " + boardInfo.getP2HitPiecesIndex();
		    fileContent = fileContent + "\n" + "p2HitPiecesData";
		    for (int i = 0; i < boardInfo.getP2HitPiecesIndex(); i++) {
		    	fileContent = fileContent + "\n" + boardInfo.p2HitPieces[i][0] + " " + boardInfo.p2HitPieces[i][1];
		    }
		    fileContent = fileContent + "\n" + "p3HitPiecesIndex " + boardInfo.getP3HitPiecesIndex();
		    fileContent = fileContent + "\n" + "p3HitPiecesData";
		    for (int i = 0; i < boardInfo.getP3HitPiecesIndex(); i++) {
		    	fileContent = fileContent + "\n" + boardInfo.p3HitPieces[i][0] + " " + boardInfo.p3HitPieces[i][1];
		    }
		    fileContent = fileContent + "\n" + "p4HitPiecesIndex " + boardInfo.getP4HitPiecesIndex();
		    fileContent = fileContent + "\n" + "p4HitPiecesData";
		    for (int i = 0; i < boardInfo.getP4HitPiecesIndex(); i++) {
		    	fileContent = fileContent + "\n" + boardInfo.p4HitPieces[i][0] + " " + boardInfo.p4HitPieces[i][1];
		    }
		    
		    fileWriter.write(fileContent);
		    fileWriter.close();
		} catch (IOException e) {
		    // Exception handling
		}
	}
	
	public void loadFile() {
		// Read the content from file
		try (FileReader fileReader = new FileReader(fileName)) {
		    int data = fileReader.read();
		    String buffer = "";
		    while(data != -1) {
		    	buffer = buffer + (char) data;
		        data = fileReader.read();
		    }
		    this.data = buffer.lines().toArray(String[]::new);
		    fileReader.close();
		} catch (FileNotFoundException e) {
		    // Exception handling
		} catch (IOException e) {
		    // Exception handling
		}
	}
	
	public Player[] getPlayers() {
		String token = "players";
		int pos = 0;
		
		for (int i = 0; i < data.length; i++) {
			if (data[i].contains(token)) {
				pos = i;
				break;
			}
		}
		
		String[] lineData = data[pos].split(" ");
		
		Player[] players = new Player[lineData.length / 2];
		for (int i = 0; i < lineData.length / 2; i++) {
	    	players[i] = new Player();
		}
		pos = 0;
		
		for (int i = 1; i < lineData.length; i++) {
			if (i % 2 != 0) {
				players[pos].setName(lineData[i]);
			} else if (i % 2 == 0) {
				players[pos].setColor(lineData[i]);
				pos++;
			}
		}

		return players;
	}
	
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