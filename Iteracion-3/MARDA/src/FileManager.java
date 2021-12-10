import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

abstract class FileManager {
	String[] data;
	JFileChooser fileChooser;
	
	/**
	 * 
	 */
	public FileManager() {
		fileChooser = new JFileChooser();
		File directory = new File("Saves");
		if (!directory.exists()) {
	        directory.mkdirs();
		}
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/Saves"));
	}
	
	/**
	 * @return
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
	
	private File chooseFile() {
		File selectedFile = null;
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		    selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
		return selectedFile;
	}
	
	abstract void saveFile(Object[] players, int gameMode, Object board, String fileName);
	abstract Object[] getPlayers();
	abstract int getGameMode();
	abstract int getCurrentPlayer();
	abstract String[][][] getBoardData();
}
