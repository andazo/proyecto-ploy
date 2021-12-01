
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

abstract class GUI {
	JFrame frame;
	JPanel boardPanel;
	JMenuBar menuBar;
	int boardSize;
  
  abstract void drawBoard();
  abstract void populateBoard();
  abstract void showLostPieces();
}
