
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * Abstract class that represents the game controller.
 */
abstract class Controller implements ActionListener {
  /**
	 * Obtains user input to determine whether to start a new game or load an old one.
	 *
	 * @return character representing if a new or saved game will be played 
	 */
	public char getNewGame() {
		String[] options = {"Nueva partida", "Cargar partida","Cancelar"};
		char newGame = ' ';
		int input = -1;
		input = JOptionPane.showOptionDialog(null, "Para empezar, seleccione lo que desea hacer", "Bienvenido a Ploy BoardGame", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		if (input == 0) {
			newGame = 'Y';
		} else if (input == 1) {
			newGame = 'N';
		} else if (input == 2) {
			System.exit(0);
		}
		return newGame;
	}
	
	/**
	 * Instantiates all the classes needed for the game and gets all the user's input
	 * such as user information, game mode and player amount.
	 */ 
	// abstract methods
	abstract void startGame();
	
	/**
	 * Loads a game from a saved file.
	 */
	abstract void loadGame();
	
	/**
	 * Initializes the GUI.
	 *
	 * @return the GUI object
	 */
	abstract Object initGUI();
	
	/**
	 * Initializes the board.
	 *
	 * @return the board object
	 */
	abstract Object initBoard();
	
	
	/**
	 * Brings up a prompt allowing the user to select the amount of players that will be in the game.
	 *
	 * @return the number of players that will play
	 */
	abstract int getNumPlayers();
	
	/**
	 * Generates prompts to get all the players' information such as name and color of the pieces.
	 *
	 * @param numPlayers the number of players in the game
	 * @return the array of initialized players with all their information 
	 */
	abstract Object[] getPlayers(int numPlayers);
	
	/**
	 * Gets the game mode the user wishes to play. 
	 *
	 * @param numPlayers number of players in the game
	 * @return the game mode chosen by the user
	 */
	abstract int getMode(int numPlayers);
	
	/**
	 * Adds mouse listeners for every square on the board.
	 */
	abstract void setActions();
}
