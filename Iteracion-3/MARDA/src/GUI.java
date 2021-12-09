import java.awt.GridLayout;
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

abstract class GUI {
	JFrame frame;
	JPanel boardPanel;
	JMenuBar menuBar;
	JTextArea textOutput;
	JScrollPane textScroll;
	int boardSize;
  
  abstract void drawBoard();
  abstract void showLostPieces(String[][] hitPiecesData, int hitPiecesIndex);
  
  public void printMessage(Object message) {
  	JOptionPane.showMessageDialog(null, message);
  }

  public void printSimpleMessage(String message) {
  	JOptionPane.showMessageDialog(null, message);
  }

  public void printMessageWithTitle(Object message, String title) {
  	JOptionPane.showMessageDialog(null, message, title, -1, null);
  }

  public String inputMessage(String showMessage) {
  	return JOptionPane.showInputDialog(showMessage);
  }

  public String inputQuestionMessage(String showMessage, String title, Object[] object, Object obj) {
  	return (String) JOptionPane.showInputDialog(null, showMessage, title, JOptionPane.QUESTION_MESSAGE, null, object, obj);
  }

  public int inputMessageWithOptions(String showMessage, String title, String[] options) {
  	return JOptionPane.showOptionDialog(null, showMessage, title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
  }
  
  public void guiPrintLine(String str) {
		System.out.println(str);
		textOutput.append(str+"\n");
		textOutput.setCaretPosition(textOutput.getDocument().getLength());
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
	}
  
  public void showSaveLoadMessage(String message, String title) {
		JLabel label = new JLabel(message);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		JOptionPane.showMessageDialog(null, label, title, JOptionPane.PLAIN_MESSAGE, null);
	}
  
  public void closeWindow() {
		frame.dispose();
	}
}
