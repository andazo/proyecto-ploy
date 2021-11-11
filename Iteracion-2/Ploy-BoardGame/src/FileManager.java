import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	String fileName;
	
	public FileManager() {
		fileName = "SavedData.txt";
	}
	
	public void saveFile() {	
		// Write content to a file
		try (FileWriter fileWriter = new FileWriter(fileName, false)) {
		    String fileContent = "This is a sample text.";
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
		    System.out.print(buffer);
		    fileReader.close();
		} catch (FileNotFoundException e) {
		    // Exception handling
		} catch (IOException e) {
		    // Exception handling
		}
	}
}
