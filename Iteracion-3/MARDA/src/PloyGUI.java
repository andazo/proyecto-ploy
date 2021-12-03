import javax.swing.*;
import java.awt.*;

public class PloyGUI extends GUI {
	JPanel rotateButtons;
	JButton rotateLeftBut, rotateRightBut;
	Color boardColorPurple = new Color(65, 11, 153);
	Color boardColorThistle = new Color(200, 184, 219);
	
	public PloyGUI(int boardSize) {
		this.boardSize = boardSize;
		frame = new JFrame();
		menuBar = new JMenuBar();
		boardPanel = new JPanel();
		rotateButtons = new JPanel();
		rotateLeftBut = new JButton("Girar izquierda");
		rotateRightBut = new JButton("Girar derecha");
	}
	
	public void drawBoard() {
		frame.setTitle("Ploy");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.requestFocus();
		
		frame.setJMenuBar(menuBar);
		JMenu options = new JMenu("Opciones");
		menuBar.add(options);
		JMenuItem rules = new JMenuItem("Reglas");
		options.add(rules);
		JMenuItem save = new JMenuItem("Guardar Partida");
		options.add(save);
		JMenuItem load = new JMenuItem("Cargar Partida");
		options.add(load);
		JMenu lostPieces = new JMenu("Piezas Eliminadas");
    menuBar.add(lostPieces);
    JMenuItem hitP1 = new JMenuItem("Jugador 1");
    lostPieces.add(hitP1);
    JMenuItem hitP2 = new JMenuItem("Jugador 2");
    lostPieces.add(hitP2);
    
		boardPanel.setLayout(new GridLayout(9,9,5,5));
		boardPanel.setBackground(boardColorPurple);
		boardPanel.setBorder(BorderFactory.createMatteBorder(30, 30, 30, 30, boardColorThistle));
		JLabel squarePanel = new JLabel();
		squarePanel.setOpaque(true);
		squarePanel.setSize(new Dimension(60,60));
		squarePanel.setMinimumSize(new Dimension(60,60));
		squarePanel.setMaximumSize(new Dimension(60,60));
		squarePanel.setPreferredSize(new Dimension(60,60));
		squarePanel.setBackground(boardColorPurple);
		boardPanel.add(squarePanel);
		
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		frame.add(boardPanel, c);
		frame.add(boardPanel);
		
		rotateButtons.setLayout(new GridLayout(1,5,0,0));
		rotateButtons.add(rotateLeftBut);
		rotateButtons.add(rotateRightBut);
		rotateLeftBut.setEnabled(false);
		rotateRightBut.setEnabled(false);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(rotateButtons,c);
		
		frame.setResizable(false);		
		frame.pack();
		frame.setVisible(true);

	}
	
	public void populateBoard() {
		
	}
	
	public void showLostPieces() {
		
	}
	
	public void rotatePiece(int x, int y, int direction) {}
	
	public void setMouseActions() {}
	public void removeMouseActions() {}
}
