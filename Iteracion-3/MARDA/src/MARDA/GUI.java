package MARDA;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * Abstract class representing the GUI for a boardgame
 */
public abstract class GUI {
	
	/** The window that will contain all the boardgame's elements. */
	public JFrame frame;
	
	/** The panel that will contain the game. */
	protected JPanel boardPanel;
	
	/** The menu bar that will contain options to see the rules, save games and see lost pieces . */
	public JMenuBar menuBar;
	
	/** Text window used to display messages about the state of the game. */
	protected JTextArea textOutput;
	protected JScrollPane textScroll;
	
	/** The board dimensions. */
	protected int boardSize;
  
  /**
   * Draw board.
   */
	protected abstract void drawBoard(Object[] players, int gameMode);
  
  /**
   * Shows a player's lost pieces.
   *
   * @param hitPiecesData a player's lost pieces
   * @param hitPiecesIndex the amount of pieces lost
   */
	protected abstract void showLostPieces(String[][] hitPiecesData, int hitPiecesIndex);
  
  /**
   * Draws all the pieces on the board
   *
   * @param players array of players in the game
   * @param gameMode the game mode being played
   */
	protected abstract void populateBoard(Object[] players, int gameMode);
  
  /**
   * Prints a message.
   *
   * @param message the message
   */
	public void printMessage(Object message) {
		JOptionPane.showMessageDialog(null, message);
	}

  /**
   * Prints a simple message.
   *
   * @param message the message
   */
	public void printSimpleMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

  /**
   * Prints a message in a window with a title.
   *
   * @param message the message
   * @param title the title of the window
   */
	public void printMessageWithTitle(Object message, String title) {
		JOptionPane.showMessageDialog(null, message, title, -1, null);
	}

  /**
   * Brings up a dialogue used to obtain input from the user.
   *
   * @param showMessage the query being shown on the window
   * @return the user's input
   */
	public String inputMessage(String showMessage) {
		return JOptionPane.showInputDialog(showMessage);
	}

  /**
   * Brings up a dialogue with a question to obtain input from the user.
   *
   * @param showMessage the message being shown on the window
   * @param title the title of the window
   * @param object the object
   * @param obj the object
   * @return the user's input
   */
	public String inputQuestionMessage(String showMessage, String title, Object[] object, Object obj) {
		return (String) JOptionPane.showInputDialog(null, showMessage, title, JOptionPane.QUESTION_MESSAGE, null, object, obj);
	}

  /**
   * Displays a dialogue with several options for the user to choose
   *
   * @param showMessage the message being shown on the window
   * @param title the title of the window
   * @param options the options being given to the user
   * @return the user's choice
   */
	public int inputMessageWithOptions(String showMessage, String title, String[] options) {
		return JOptionPane.showOptionDialog(null, showMessage, title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	}
  
  /**
   * Prints a line on the text box next to the game board
   *
   * @param str the message to print
   */
	public void guiPrintLine(String str) {
		System.out.println(str);
		textOutput.append(str+"\n");
		textOutput.setCaretPosition(textOutput.getDocument().getLength());
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
	}
  
  /**
   * Show save load message.
   *
   * @param message the message that will appear in the window
   * @param title the title of the window
   */
	public void showSaveLoadMessage(String message, String title) {
		JLabel label = new JLabel(message);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		JOptionPane.showMessageDialog(null, label, title, JOptionPane.PLAIN_MESSAGE, null);
	}
  
  /**
   * Closes the window.
   */
	public void closeWindow() {
		frame.dispose();
	}
}
