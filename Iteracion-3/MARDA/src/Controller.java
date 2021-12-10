import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

abstract class Controller implements ActionListener {

	// metodo plantilla
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
	
	// métodos abstractos 
	abstract void startGame();
	abstract void loadGame();
	abstract Object initGUI();
	abstract Object initBoard();
	abstract int getNumPlayers();
	abstract Object[] getPlayers(int numPlayers);
	abstract int getMode(int numPlayers);
	abstract void setActions();
}
