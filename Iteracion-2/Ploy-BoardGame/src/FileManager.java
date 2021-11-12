import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
		    		fileContent = fileContent + "\n" + i + " " + j
		    				+ " " + boardInfo.boardSquares[i][j].getType()
		    				+ " " + boardInfo.boardSquares[i][j].getDirection()
		    				+ " " + boardInfo.boardSquares[i][j].getOwner()
		    				+ " " + boardInfo.boardSquares[i][j].getColor();
		    	}
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
			}
		}
		
		String[] lineData = data[pos].split(" ");
		
		return Integer.parseInt(lineData[1]);
	}
	
	public String getBoardData() {
		String token = "board";
		int pos = 0;
		
		for (int i = 0; i < data.length; i++) {
			if (data[i].contains(token)) {
				pos = i + 1;
			}
		}
		
		int lastPos = 9 * 9;
		for (int i = 0; i < lastPos; i++) {
			String[] lineData = data[pos].split(" ");
		}
		
		// Trabajando...
		
		return null;
	}
}
