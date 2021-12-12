package PloyGame;
import javax.imageio.ImageIO;
import javax.swing.*;

import MARDA.GUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Concrete class representing a GUI for Ploy
 */
public class PloyGUI extends GUI {
	JPanel rotateButtons;
	JButton rotateLeftBut, rotateRightBut;
	JLabel squaresPanels[][];
	Color boardColorPurple = new Color(65, 11, 153);
	Color boardColorThistle = new Color(200, 184, 219);
	Color boardColorSnow = new Color(249, 244, 245);
	Color boardColorHighlight = new Color(129, 92, 173);
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

	/**
	 * Instantiates a new Ploy GUI.
	 *
	 * @param boardSize the size of the board 
	 */
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

	/**
	 * Draws all of the board's components.
	 */
	@Override
	public void drawBoard(Object[] players, int gameMode) {		
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

		if (gameMode != 0) {
			JMenuItem hitP3 = new JMenuItem("Jugador 3");
			lostPieces.add(hitP3);
			JMenuItem hitP4 = new JMenuItem("Jugador 4");
			lostPieces.add(hitP4);
		}

		boardPanel.setLayout(new GridLayout(9,9,5,5));
		boardPanel.setBackground(boardColorThistle);
		boardPanel.setBorder(BorderFactory.createMatteBorder(30, 30, 30, 30, boardColorThistle));

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
		guiPrintLine("Modos de juego: 1v1, 1v1v1v1, 2v2" + "\n");

		String modeString = "";
		if (gameMode == 0) {
			modeString = "1v1";
		} else if (gameMode == 1) {
			modeString = "1v1v1v1";
		} else {
			modeString = "2v2";
		}

		guiPrintLine("Modo elegido: " + modeString);
		for (int i = 0; i < players.length; i++) {
			guiPrintLine(((PloyPlayer) players[i]).getName() + ": " + ((PloyPlayer) players[i]).getColor());
		}

		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
		rotateButtons.paintImmediately(new Rectangle(new Point(0,0),rotateButtons.getSize()));
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
		rotateButtons.paintImmediately(new Rectangle(new Point(0,0),rotateButtons.getSize()));

		frame.setVisible(true);
	}

	/**
	 * Shows each player's lost pieces.
	 *
	 * @param hitPiecesData the hit pieces data
	 * @param hitPiecesIndex the current amount of pieces that were lost
	 */
	@Override
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

	/**
	 * Draws all the players' pieces on the board.
	 *
	 * @param players the players in the game
	 * @param gameMode the game mode being played
	 */
	@Override
	public void populateBoard(Object[] players, int gameMode) {
		switch (gameMode) {
		case 0: // 1v1
			populateBoard1v1(((PloyPlayer) players[0]).getColor(), 1);
			populateBoard1v1(((PloyPlayer) players[1]).getColor(), 2);
			break;
		case 1: // 1v1v1v1
			populateBoard1v1v1v1(((PloyPlayer) players[0]).getColor(), 1);
			populateBoard1v1v1v1(((PloyPlayer) players[1]).getColor(), 2);
			populateBoard1v1v1v1(((PloyPlayer) players[2]).getColor(), 3);
			populateBoard1v1v1v1(((PloyPlayer) players[3]).getColor(), 4);
			break;
		case 2: // 2v2
			populateBoard2v2(((PloyPlayer) players[0]).getColor(), 1);
			populateBoard2v2(((PloyPlayer) players[1]).getColor(), 2);
			populateBoard2v2(((PloyPlayer) players[2]).getColor(), 3);
			populateBoard2v2(((PloyPlayer) players[3]).getColor(), 4);
			break;
		}
	}

	/**
	 * Populates the board for the 1v1 game mode.
	 *
	 * @param color the color of the pieces being placed
	 * @param playerNum the number of the player whose pieces are being placed
	 */
	private void populateBoard1v1(String color, int playerNum) {
		int orderArrayIndex = 0;
		if (playerNum == 1) {
			for (int i = 1; i < 8; i++) {
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1P1[orderArrayIndex]], 180.0, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				orderArrayIndex++;
			}
			for (int i = 2; i < 7; i++) {
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1P1[orderArrayIndex]], 180.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				orderArrayIndex++;
			}
			for (int i = 3; i < 6; i++) {
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[8], 180.0, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
			}
		} else {
			for (int i = 1; i < 8; i++) {
				squaresPanels[0][i].setIcon(getIconArray(color)[pieceOrder1v1P2[orderArrayIndex]]);
				squaresPanels[0][i].setName(color);
				orderArrayIndex++;
			}
			for (int i = 2; i < 7; i++) {
				squaresPanels[1][i].setIcon(getIconArray(color)[pieceOrder1v1P2[orderArrayIndex]]);
				squaresPanels[1][i].setName(color);
				orderArrayIndex++;
			}
			for (int i = 3; i < 6; i++) {
				squaresPanels[2][i].setIcon(getIconArray(color)[8]);
				squaresPanels[2][i].setName(color);
			}
		}
	}

	/**
	 * Populates the board for the 1v1v1v1 game mode.
	 *
	 * @param color the color of the pieces being placed
	 * @param playerNum the number of the player whose pieces are being placed
	 */
	private void populateBoard1v1v1v1(String color, int playerNum) {
		int orderArrayIndex = 0;
		if (playerNum == 1) {
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i != 2) {
					direction = 225;
				} else {
					direction = 180;
				}
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 225.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i == 0) {
					direction = 270;
				} else {
					direction = 225;
				}
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
				orderArrayIndex++;
			}
		} else if (playerNum == 2) {
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i != 2) {
					direction = 315;
				} else {
					direction = 270;
				}
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[i][0].setIcon(ri);
				squaresPanels[i][0].setName(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 315.0, true);
				squaresPanels[i][1].setIcon(ri);
				squaresPanels[i][1].setName(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i == 0) {
					direction = 0;
				} else {
					direction = 315;
				}
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[i][2].setIcon(ri);
				squaresPanels[i][2].setName(color);
				orderArrayIndex++;
			}
		} else if (playerNum == 3) {
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i != 6) {
					direction = 45;
				} else {
					direction = 0;
				}
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[0][i].setIcon(ri);
				squaresPanels[0][i].setName(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 45.0, true);
				squaresPanels[1][i].setIcon(ri);
				squaresPanels[1][i].setName(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i == 8) {
					direction = 90;
				} else {
					direction = 45;
				}
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[2][i].setIcon(ri);
				squaresPanels[2][i].setName(color);
				orderArrayIndex++;
			}
		} else {
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i != 6) {
					direction = 135;
				} else {
					direction = 90;
				}
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[i][8].setIcon(ri);
				squaresPanels[i][8].setName(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 135.0, true);
				squaresPanels[i][7].setIcon(ri);
				squaresPanels[i][7].setName(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i == 8) {
					direction = 180;
				} else {
					direction = 135;
				}
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[i][6].setIcon(ri);
				squaresPanels[i][6].setName(color);
				orderArrayIndex++;
			}
		}
	}

	/**
	 * Populates the board for the 2v2.
	 *
	 * @param color the color of the pieces being placed
	 * @param playerNum the number of the player whose pieces are being placed
	 */
	private void populateBoard2v2(String color, int playerNum) {
		int orderArrayIndex = 0;
		if (playerNum == 1) {
			for(int i = 1; i < 4; i++) {
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[8], 180.0, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
			}
		} else if (playerNum == 2) {
			for(int i = 1; i < 4; i++) {
				squaresPanels[0][i].setIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]]);
				squaresPanels[0][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 3; i > 0; i--) {
				squaresPanels[1][i].setIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]]);
				squaresPanels[1][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				squaresPanels[2][i].setIcon(getIconArray(color)[8]);
				squaresPanels[2][i].setName(color);
			}
		} else if (playerNum == 3) {
			for(int i = 7; i > 4; i--) {
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				PloyRotateIcon ri = new PloyRotateIcon(getIconArray(color)[8], 180.0, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
			}
		} else {
			for(int i = 7; i > 4; i--) {
				squaresPanels[0][i].setIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]]);
				squaresPanels[0][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 7; i > 4; i--) {
				squaresPanels[1][i].setIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]]);
				squaresPanels[1][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				squaresPanels[2][i].setIcon(getIconArray(color)[8]);
				squaresPanels[2][i].setName(color);
			}
		}
	}

	/**
	 * Rotates a piece.
	 *
	 * @param x location of the piece on the x axis of the board
	 * @param y location of the piece on the y axis of the board
	 * @param direction the new direction the piece will face
	 */
	public void rotatePiece(int x, int y, int direction) {
		PloyRotateIcon ri = new PloyRotateIcon(squaresPanels[x][y].getIcon(), direction, true);
		squaresPanels[x][y].setIcon(ri);
	}

	/**
	 * Loads all the pieces' images on an icon array so they can be drawn on the board.
	 */
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
	 * Gets the array of icons of a given color.
	 *
	 * @param color the color of the pieces wanted 
	 * @return the array of icons for that color
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

	/**
	 * Draws the board from a save file.
	 *
	 * @param players array of players in the game
	 * @param gameMode the game mode being played
	 * @param board the board's information
	 */
	public void loadBoard(PloyPlayer[] players, int gameMode, String[][][] board) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!(board[i][j][0].equals("null"))) {
					PloyRotateIcon ri = new PloyRotateIcon(getIconArray(board[i][j][2])[Integer.parseInt(board[i][j][0])], Integer.parseInt(board[i][j][3]), true);
					squaresPanels[i][j].setIcon(ri);
					squaresPanels[i][j].setName(board[i][j][2]);
				}
			}
		}
	}
}
