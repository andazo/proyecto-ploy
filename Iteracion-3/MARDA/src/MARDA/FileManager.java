package MARDA;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * Abstract class that manages the saving and loading of game files.
 */
public abstract class FileManager {
	
	/** The data contained in a save file. */
	protected String[] data;
	
	/** Window for choosing a file. */
	protected JFileChooser fileChooser;
	
	/**
	 * Instantiates a new file manager.
	 */
	protected FileManager() {
		fileChooser = new JFileChooser();
		File directory = new File("Saves");
		if (!directory.exists()) {
	        directory.mkdirs();
		}
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/Saves"));
	}
	
	/**
	 * Loads a file.
	 *
	 * @return error code, 0 on success
	 */
	public int loadFile() {
		// Read the content from file
		File selectedFile = chooseFile();
		if (selectedFile != null) {
			int loadSuccess = 0;
			try (FileReader fileReader = new FileReader(selectedFile)) {
			    int data = fileReader.read();
			    String buffer = "";
			    while(data != -1) {
			    	buffer = buffer + (char) data;
			        data = fileReader.read();
			    }
			    this.data = buffer.lines().toArray(String[]::new);
			    fileReader.close();
			} catch (FileNotFoundException e) {
				loadSuccess = 1;
			} catch (IOException e) {
				// Exception handling
			}
			return loadSuccess;
		} else {
			return 2;
		}
	}
	
	/**
	 * Brings up a prompt allowing the user to choose a file.
	 *
	 * @return the file chosen 
	 */
	private File chooseFile() {
		File selectedFile = null;
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		    selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
		return selectedFile;
	}
	
	/**
	 * Saves a file with the current board's status.
	 *
	 * @param players array of players in the game
	 * @param gameMode the game mode being played
	 * @param board the board object with all its information
	 * @param fileName the name the save file will have
	 */
	protected abstract void saveFile(Object[] players, int gameMode, Object board, String fileName);
	
	/**
	 * Gets the players from a saved file.
	 *
	 * @return the array of players
	 */
	protected abstract Object[] getPlayers();
	
	/**
	 * Gets the game mode from a saved file.
	 *
	 * @return the game mode 
	 */
	protected abstract int getGameMode();
	
	/**
	 * Gets the current player from a saved file.
	 *
	 * @return the current player
	 */
	protected abstract int getCurrentPlayer();
	
	/**
	 * Gets the board data from a saved file.
	 *
	 * @return the board data
	 */
	protected abstract String[][][] getBoardData();
}
