import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PloyGUI extends GUI {
	JPanel rotateButtons;
	JButton rotateLeftBut, rotateRightBut;
	JLabel squaresPanels[][];
	Color boardColorPurple = new Color(65, 11, 153);
	Color boardColorThistle = new Color(200, 184, 219);
	Color boardColorSnow = new Color(249, 244, 245);
	int smallSquareSize;
	
	final String chipFolderNames [] = {"chips_red/", "chips_blue/", "chips_green/", "chips_yellow/"};
	final String chipNames [] = {"comm", "lance_1", "lance_2", "lance_3", "probe_1", "probe_2", "probe_3", "probe_4", "shield"}; 
	final int pieceOrder1v1P1[] = {1,2,3,0,3,2,1,4,5,6,5,7};
	final int pieceOrder1v1P2[] = {1,2,3,0,3,2,1,7,5,6,5,4};
	final int pieceOrder1v1v1v1[] = {0,1,7,3,5,8,4,8,8};
	final int pieceOrder2v2[] = {1,0,3,4,5,7};
	
	ImageIcon redIcons[] = new ImageIcon[9];
	ImageIcon blueIcons[] = new ImageIcon[9];
	ImageIcon yellowIcons[] = new ImageIcon[9];
	ImageIcon greenIcons [] = new ImageIcon[9];
	
	public PloyGUI(int boardSize) {
		this.boardSize = boardSize;
		frame = new JFrame();
		menuBar = new JMenuBar();
		boardPanel = new JPanel();
		squaresPanels = new JLabel[9][9];
		textOutput = new JTextArea(1,1);
		smallSquareSize = boardSize / 10;
		rotateButtons = new JPanel();
		rotateLeftBut = new JButton("Girar izquierda");
		rotateRightBut = new JButton("Girar derecha");
		loadImages();
	}
	
	public void drawBoard() {
		frame.setTitle("Ploy");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.requestFocus();
		frame.setBackground(Color.black);
		
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
	    JMenuItem hitP3 = new JMenuItem("Jugador 3");
	    lostPieces.add(hitP3);
        JMenuItem hitP4 = new JMenuItem("Jugador 4");
        lostPieces.add(hitP4);
    
		boardPanel.setLayout(new GridLayout(9,9,5,5));
		boardPanel.setBackground(boardColorThistle);
		boardPanel.setBorder(BorderFactory.createMatteBorder(30, 30, 30, 30, boardColorThistle));
		
		/*
		JLabel squarePanel = new JLabel();
		squarePanel.setOpaque(true);
		squarePanel.setSize(new Dimension(60,60));
		squarePanel.setMinimumSize(new Dimension(60,60));
		squarePanel.setMaximumSize(new Dimension(60,60));
		squarePanel.setPreferredSize(new Dimension(60,60));
		squarePanel.setBackground(boardColorPurple);
		boardPanel.add(squarePanel);
		*/
		
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				squaresPanels[i][j] = new JLabel();
				squaresPanels[i][j].setOpaque(true);
				squaresPanels[i][j].setSize(new Dimension(smallSquareSize,smallSquareSize));
				squaresPanels[i][j].setMinimumSize(new Dimension(smallSquareSize,smallSquareSize));
				squaresPanels[i][j].setMaximumSize(new Dimension(smallSquareSize,smallSquareSize));
				squaresPanels[i][j].setPreferredSize(new Dimension(smallSquareSize,smallSquareSize));
				squaresPanels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				squaresPanels[i][j].setVerticalAlignment(SwingConstants.CENTER);
				squaresPanels[i][j].setFont(new Font("Segoe UI Symbol", squaresPanels[i][j].getFont().getStyle(), 70));
				squaresPanels[i][j].setForeground(Color.BLACK);
				squaresPanels[i][j].setBackground(boardColorPurple);
				boardPanel.add(squaresPanels[i][j]);
			}
		}
		
		
		
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		frame.add(boardPanel, c);
		frame.add(boardPanel);

		textOutput.setBackground(boardColorSnow);
		textOutput.setLineWrap(true);
		textOutput.setEditable(false);
		textOutput.setFont(new Font("monospaced", Font.PLAIN, 14));
		textOutput.setColumns(35);
		textScroll = new JScrollPane(textOutput);
		textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridx = 1;
		c.gridy = 0;
		frame.add(textScroll,c);

		rotateButtons.setLayout(new GridLayout(1,5,0,0));
		rotateButtons.add(rotateLeftBut);
		rotateButtons.add(rotateRightBut);
		rotateLeftBut.setEnabled(false);
		rotateRightBut.setEnabled(false);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(rotateButtons,c);
		
		frame.setResizable(false);		
		
		try {
			frame.setIconImage(ImageIO.read(new File("img/icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		frame.pack();
		
		guiPrintLine("Bienvenidos a Ploy");
		guiPrintLine("Para ganar, capture el comandante del oponente o todas las piezas\nexcepto el comandante.");
		guiPrintLine("Cada pieza se puede mover por el\ntablero la cantidad de espacios de\nacuerdo con la cantidad de rayas\nque posee.");
		guiPrintLine("En un turno se puede mover una\npieza o cambiar su direccion.");
		guiPrintLine("Los escudos se pueden mover y\ncambiar de direccion en un mismo\nturno.");
		
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
		rotateButtons.paintImmediately(new Rectangle(new Point(0,0),rotateButtons.getSize()));
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
		rotateButtons.paintImmediately(new Rectangle(new Point(0,0),rotateButtons.getSize()));
		
		frame.setVisible(true);
	}
	
	public void populateBoard() {
		
	}
	
	public void showLostPieces(String[][] hitPiecesData, int hitPiecesIndex) {
		JLabel[] hitPieces = new JLabel[hitPiecesIndex];	
		for (int i = 0; i < hitPiecesIndex; i++) {
			hitPieces[i] = new JLabel();
		}
		JPanel hitPiecesPanel = new JPanel(new GridLayout(1,0));
		for (int i = 0; i < hitPiecesIndex; i++) {
			hitPieces[i].setIcon(getIconArray(hitPiecesData[i][1])[Integer.parseInt(hitPiecesData[i][0])]);
		}
		for (int i = 0; i < hitPiecesIndex; i++) {
			hitPiecesPanel.add(new JLabel(hitPieces[i].getIcon()));
		}
		if (hitPiecesIndex == 0) {
			JLabel label = new JLabel("No hay piezas");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			JOptionPane.showMessageDialog(null, label, "Piezas eliminadas", JOptionPane.PLAIN_MESSAGE, null);
		} else {
			JOptionPane.showMessageDialog(null, hitPiecesPanel, "Piezas eliminadas", JOptionPane.PLAIN_MESSAGE, null);
		}
	}
	
	public void rotatePiece(int x, int y, int direction) {
		RotateIcon ri = new RotateIcon(squaresPanels[x][y].getIcon(), direction, true);
		squaresPanels[x][y].setIcon(ri);
	}
	
	public void loadBoard(Player[] players, int gameMode, String[][][] board) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Integer.parseInt(board[i][j][0]) != -1) {
					RotateIcon ri = new RotateIcon(getIconArray(board[i][j][3])[Integer.parseInt(board[i][j][0])], Integer.parseInt(board[i][j][1]), true);
					squaresPanels[i][j].setIcon(ri);
					squaresPanels[i][j].setName(board[i][j][3]);
				}
			}
		}
	}
	
	private void loadImages() {
		String path = "img/";
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 9; j++) {
				path += chipFolderNames[i];
				path += chipNames[j];
				path += ".png";
				
				BufferedImage img = null;
				try {
					img = ImageIO.read(new File(path));
				} catch (IOException e) {
					e.printStackTrace();
				}
				ImageIcon icon = new ImageIcon(img);
				
				switch (i) {
					case 0:
						redIcons[j] = icon;
						break;
					case 1:
						blueIcons[j] = icon;
						break;
					case 2:
						greenIcons[j] = icon;
						break;
					case 3:
						yellowIcons[j] = icon;
						break;
				}
				path = "img/";
			}
		}
	}
	
	/**
	 * @param color
	 * @return
	 */
	private ImageIcon[] getIconArray(String color) {
		switch(color) {
			case "Rojo":
				return this.redIcons;
			case "Azul":
				return this.blueIcons;
			case "Verde":
				return this.greenIcons;
			case "Amarillo":
				return this.yellowIcons;
			default:
				return this.redIcons;
		}
	}
	
	public void setMouseActions() {
		
	}
	
	public void removeMouseActions() {
		
	}
}
