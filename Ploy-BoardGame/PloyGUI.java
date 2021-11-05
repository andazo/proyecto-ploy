/*
 * @(#)PloyGUI.java
 *
 *
 * @author 
 * @version 
 */

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class PloyGUI {
	JFrame ployInterface;
	JPanel boardPanel;
	JPanel rotateButtons;
	JButton rotateLeftBut;
	JButton rotateRightBut;
	JLabel squaresPanels[][];
	JLabel P1HitPieces[];
	JLabel P2HitPieces[];
	JLabel P3HitPieces[];
	JLabel P4HitPieces[];
	JTextArea textOutput;
	JScrollPane textScroll;
	JSpinner depthSpinner;
	int boardSize = 600;
	JMenuBar menuBar;
	
	Color boardColorPurple = new Color(65, 11, 153);
	Color boardColorThistle = new Color(200, 184, 219);
	Color boardColorPurpleHighlight = new Color(213,198,70);
	Color boardColorBlackHighlight = new Color(184,164,35);
	Color boardColorSnow = new Color(249, 244, 245);
	Color boardColorHighlight = new Color(129, 92, 173);
	
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
	int currentPlayer;
	boolean pieceActive = false;
	int lastI;
	int lastJ;
	int originalDirection;
	int P1HitPiecesIndex = 0;
	int P2HitPiecesIndex = 0;
	int P3HitPiecesIndex = 0;
	int P4HitPiecesIndex = 0;
	
	PieceInfo[][] board;
	boolean gameOver;
	
	public PloyGUI(mainClass controller, int numPlayers, player[] players, int mode) {
		makeGUI(controller, numPlayers, players, mode);
		loadImages();
		populateBoard(players, mode);
	}
	
	public void showHitPieces(String player) {
		int hitPiecesIndex = 0;
		JLabel[] hitPieces = null;
		if (player == "p1") {
			hitPiecesIndex = P1HitPiecesIndex;
			hitPieces = P1HitPieces;
		} else if (player == "p2") {
			hitPiecesIndex = P2HitPiecesIndex;
			hitPieces = P2HitPieces;
		} else if (player == "p3") {
			hitPiecesIndex = P3HitPiecesIndex;
			hitPieces = P3HitPieces;
		} else if (player == "p4") {
			hitPiecesIndex = P4HitPiecesIndex;
			hitPieces = P4HitPieces;
		}
		
		JPanel hitPiecesPanel = new JPanel(new GridLayout(1,0));
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
	
	private void makeGUI(mainClass controller, int numPlayers, player[] players, int mode) {
		ployInterface = new JFrame();
		ployInterface.setTitle("Ploy");
		ployInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ployInterface.setFocusable(true);
		ployInterface.requestFocus();
		ployInterface.setBackground(Color.black);
		
		menuBar = new JMenuBar();
        ployInterface.setJMenuBar(menuBar);
        
		JMenu options = new JMenu("Opciones");
        menuBar.add(options);
		JMenuItem reglas = new JMenuItem("Reglas");
		reglas.addActionListener(controller);
		options.add(reglas);
        
        JMenu hitPieces = new JMenu("Piezas Eliminadas");
        menuBar.add(hitPieces);
        
        JMenuItem hitP1 = new JMenuItem("Jugador 1");
        hitP1.addActionListener(controller);
        hitPieces.add(hitP1);
		
        JMenuItem hitP2 = new JMenuItem("Jugador 2");
        hitP2.addActionListener(controller);
        hitPieces.add(hitP2);
        
        JMenuItem hitP3 = new JMenuItem("Jugador 3");
        hitP3.addActionListener(controller);
        hitPieces.add(hitP3);
        
        JMenuItem hitP4 = new JMenuItem("Jugador 4");
        hitP4.addActionListener(controller);
        hitPieces.add(hitP4);
        
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(9,9,5,5));
		boardPanel.setBackground(boardColorThistle);
		boardPanel.setBorder(BorderFactory.createMatteBorder(30, 30, 30, 30, boardColorThistle));
		int smallSquareSize = boardSize / 10;
		squaresPanels = new JLabel[9][9];
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
				final int tempI = i;
				final int tempJ = j;
				squaresPanels[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
					final int myI = tempI;
					final int myJ = tempJ;
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						clickedOn(myI, myJ, numPlayers, players);
					}
				});	
				boardPanel.add(squaresPanels[i][j]);
			}
		}
		
		ployInterface.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		ployInterface.add(boardPanel, c);
		
		ployInterface.add(boardPanel);
		
		textOutput = new JTextArea(1,1);
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
		ployInterface.add(textScroll,c);

		rotateButtons = new JPanel();
		rotateButtons.setLayout(new GridLayout(1,5,0,0));
    
		rotateLeftBut = new JButton("Girar izq");
		rotateLeftBut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				rotatePieceLeft(lastI, lastJ);
			}
		});
		rotateButtons.add(rotateLeftBut);
    
		rotateRightBut = new JButton("Girar der");
		rotateRightBut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				rotatePieceRight(lastI, lastJ);
			}
		});
		rotateButtons.add(rotateRightBut);
		
		rotateLeftBut.setEnabled(false);
		rotateRightBut.setEnabled(false);
		
		c.gridx = 0;
		c.gridy = 1;
		ployInterface.add(rotateButtons,c);

		ployInterface.setResizable(false);

		try {
			ployInterface.setIconImage(ImageIO.read(this.getClass().getResource("img/icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ployInterface.pack();

		board = new PieceInfo[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board[i][j] = new PieceInfo(-1, 0, 0, "");
			}
		}
	
		guiPrintLine("Bienvenidos a Ploy");
		guiPrintLine("Para ganar, capture el comandante del oponente o todas las piezas\nexcepto el comandante.");
		guiPrintLine("Cada pieza se puede mover por el\ntablero la cantidad de espacios de\nacuerdo con la cantidad de rayas\nque posee.");
		guiPrintLine("En un turno se puede mover una\npieza o cambiar su direccion.");
		guiPrintLine("Los escudos se pueden mover y\ncambiar de direccion en un mismo\nturno.");
		guiPrintLine("Modos de juego: 1v1, 1v1v1v1, 2v2" + "\n");
		
		String modeString = "";
		if (mode == 0) {
			modeString = "1v1";
		} else if (mode == 1) {
			modeString = "1v1v1v1";
		} else {
			modeString = "2v2";
		}
		
		guiPrintLine("Modo elegido: " + modeString);
		if (numPlayers == 2) {
			guiPrintLine(players[0].name + ": " + players[0].color);
			guiPrintLine(players[1].name + ": " + players[1].color);
		}
		else {
			guiPrintLine(players[0].name + ": " + players[0].color);
			guiPrintLine(players[1].name + ": " + players[1].color);
			guiPrintLine(players[2].name + ": " + players[2].color);
			guiPrintLine(players[3].name + ": " + players[3].color);
		}
		
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
		rotateButtons.paintImmediately(new Rectangle(new Point(0,0),rotateButtons.getSize()));
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
		rotateButtons.paintImmediately(new Rectangle(new Point(0,0),rotateButtons.getSize()));

		ployInterface.setVisible(true);
	}
	
	//Carga todas las imagenes de las piezas, los convierte a iconos y los guarda en arreglos.
	private void loadImages() {
		String path = "img/";
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 9; j++) {
				path += chipFolderNames[i];
				path += chipNames[j];
				path += ".png";
				ImageIcon icon = new ImageIcon(path);
				System.out.println(path);
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
	
	private void populateBoard(player[] players, int mode) {
		switch (mode) {
			case 0: // 1v1
				populateBoard1v1(players[0].getColor(), 1);
			  	populateBoard1v1(players[1].getColor(), 2);
			    break;
			case 1: // 1v1v1v1
				populateBoard1v1v1v1(players[0].getColor(), 1);
				populateBoard1v1v1v1(players[1].getColor(), 2);
				populateBoard1v1v1v1(players[2].getColor(), 3);
				populateBoard1v1v1v1(players[3].getColor(), 4);
			    break;
			case 2: // 2v2
				populateBoard2v2(players[0].getColor(), 1);
			  	populateBoard2v2(players[1].getColor(), 2);
			  	populateBoard2v2(players[2].getColor(), 3);
			  	populateBoard2v2(players[3].getColor(), 4);
			    break;
		}
	}
	
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
	
	private void populateBoard1v1(String color, int playerNum) {
		int orderArrayIndex = 0;
		if (playerNum == 1) {
			for (int i = 1; i < 8; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1P1[orderArrayIndex]], 180.0, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				board[8][i].setType(pieceOrder1v1P1[orderArrayIndex]);
				board[8][i].setOwner(1);
				board[8][i].setDirection(180);
				board[8][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 2; i < 7; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1P1[orderArrayIndex]], 180.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				board[7][i].setType(pieceOrder1v1P1[orderArrayIndex]);
				board[7][i].setOwner(1);
				board[7][i].setDirection(180);
				board[7][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 3; i < 6; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[8], 180.0, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
				board[6][i].setType(8);
				board[6][i].setOwner(1);
				board[6][i].setDirection(180);
				board[6][i].setColor(color);
			}
			P1HitPieces = new JLabel[15];
			for (int i = 0; i < 15; i++) {
				P1HitPieces[i] = new JLabel();
			}
		} else {
			for (int i = 1; i < 8; i++) {
				squaresPanels[0][i].setIcon(getIconArray(color)[pieceOrder1v1P2[orderArrayIndex]]);
				squaresPanels[0][i].setName(color);
				board[0][i].setType(pieceOrder1v1P2[orderArrayIndex]);
				board[0][i].setOwner(2);
				board[0][i].setDirection(0);
				board[0][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 2; i < 7; i++) {
				squaresPanels[1][i].setIcon(getIconArray(color)[pieceOrder1v1P2[orderArrayIndex]]);
				squaresPanels[1][i].setName(color);
				board[1][i].setType(pieceOrder1v1P2[orderArrayIndex]);
				board[1][i].setOwner(2);
				board[1][i].setDirection(0);
				board[1][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 3; i < 6; i++) {
				squaresPanels[2][i].setIcon(getIconArray(color)[8]);
				squaresPanels[2][i].setName(color);
				board[2][i].setType(8);
				board[2][i].setOwner(2);
				board[2][i].setDirection(0);
				board[2][i].setColor(color);
			}
			P2HitPieces = new JLabel[15];
			for (int i = 0; i < 15; i++) {
				P2HitPieces[i] = new JLabel();
			}
		}
	}
	
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
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				board[8][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				board[8][i].setOwner(1);
				board[8][i].setDirection(direction);
				board[8][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 225.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				board[7][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				board[7][i].setOwner(1);
				board[7][i].setDirection(225);
				board[7][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i == 0) {
					direction = 270;
				} else {
					direction = 225;
				}
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
				board[6][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				board[6][i].setOwner(1);
				board[6][i].setDirection(direction);
				board[6][i].setColor(color);
				orderArrayIndex++;
			}
			P1HitPieces = new JLabel[9];
			for (int i = 0; i < 9; i++) {
				P1HitPieces[i] = new JLabel();
			}
		} else if (playerNum == 2) {
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i != 2) {
					direction = 315;
				} else {
					direction = 270;
				}
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[i][0].setIcon(ri);
				squaresPanels[i][0].setName(color);
				board[i][0].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				board[i][0].setOwner(2);
				board[i][0].setDirection(direction);
				board[i][0].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 315.0, true);
				squaresPanels[i][1].setIcon(ri);
				squaresPanels[i][1].setName(color);
				board[i][1].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				board[i][1].setOwner(2);
				board[i][1].setDirection(315);
				board[i][1].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i == 0) {
					direction = 0;
				} else {
					direction = 315;
				}
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[i][2].setIcon(ri);
				squaresPanels[i][2].setName(color);
				board[i][2].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				board[i][2].setOwner(2);
				board[i][2].setDirection(direction);
				board[i][2].setColor(color);
				orderArrayIndex++;
			}
			P2HitPieces = new JLabel[9];
			for (int i = 0; i < 9; i++) {
				P2HitPieces[i] = new JLabel();
			}
		} else if (playerNum == 3) {
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i != 6) {
					direction = 45;
				} else {
					direction = 0;
				}
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[0][i].setIcon(ri);
				squaresPanels[0][i].setName(color);
				board[0][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				board[0][i].setOwner(3);
				board[0][i].setDirection(direction);
				board[0][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 45.0, true);
				squaresPanels[1][i].setIcon(ri);
				squaresPanels[1][i].setName(color);
				board[1][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				board[1][i].setOwner(3);
				board[1][i].setDirection(45);
				board[1][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i == 8) {
					direction = 90;
				} else {
					direction = 45;
				}
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[2][i].setIcon(ri);
				squaresPanels[2][i].setName(color);
				board[2][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				board[2][i].setOwner(3);
				board[2][i].setDirection(direction);
				board[2][i].setColor(color);
				orderArrayIndex++;
			}
			P3HitPieces = new JLabel[9];
			for (int i = 0; i < 9; i++) {
				P3HitPieces[i] = new JLabel();
			}
		} else {
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i != 6) {
					direction = 135;
				} else {
					direction = 90;
				}
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[i][8].setIcon(ri);
				squaresPanels[i][8].setName(color);
				board[i][8].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				board[i][8].setOwner(4);
				board[i][8].setDirection(direction);
				board[i][8].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 135.0, true);
				squaresPanels[i][7].setIcon(ri);
				squaresPanels[i][7].setName(color);
				board[i][7].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				board[i][7].setOwner(4);
				board[i][7].setDirection(135);
				board[i][7].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i == 8) {
					direction = 180;
				} else {
					direction = 135;
				}
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[i][6].setIcon(ri);
				squaresPanels[i][6].setName(color);
				board[i][6].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				board[i][6].setOwner(4);
				board[i][6].setDirection(direction);
				board[i][6].setColor(color);
				orderArrayIndex++;
			}
			P4HitPieces = new JLabel[9];
			for (int i = 0; i < 9; i++) {
				P4HitPieces[i] = new JLabel();
			}
		}
	}
	
	private void populateBoard2v2(String color, int playerNum) {
		int orderArrayIndex = 0;
		if (playerNum == 1) {
			for(int i = 1; i < 4; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				board[8][i].setType(pieceOrder2v2[orderArrayIndex]);
				board[8][i].setOwner(1);
				board[8][i].setDirection(180);
				board[8][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				board[7][i].setType(pieceOrder2v2[orderArrayIndex]);
				board[7][i].setOwner(1);
				board[7][i].setDirection(180);
				board[7][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[8], 180.0, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
				board[6][i].setType(8);
				board[6][i].setOwner(1);
				board[6][i].setDirection(180);
				board[6][i].setColor(color);
			}
			P1HitPieces = new JLabel[9];
			for (int i = 0; i < 9; i++) {
				P1HitPieces[i] = new JLabel();
			}
		} else if (playerNum == 2) {
			for(int i = 1; i < 4; i++) {
				squaresPanels[0][i].setIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]]);
				squaresPanels[0][i].setName(color);
				board[0][i].setType(pieceOrder2v2[orderArrayIndex]);
				board[0][i].setOwner(2);
				board[0][i].setDirection(0);
				board[0][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 3; i > 0; i--) {
				squaresPanels[1][i].setIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]]);
				squaresPanels[1][i].setName(color);
				board[1][i].setType(pieceOrder2v2[orderArrayIndex]);
				board[1][i].setOwner(2);
				board[1][i].setDirection(0);
				board[1][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				squaresPanels[2][i].setIcon(getIconArray(color)[8]);
				squaresPanels[2][i].setName(color);
				board[2][i].setType(8);
				board[2][i].setOwner(2);
				board[2][i].setDirection(0);
				board[2][i].setColor(color);
			}
			P2HitPieces = new JLabel[9];
			for (int i = 0; i < 9; i++) {
				P2HitPieces[i] = new JLabel();
			}
		} else if (playerNum == 3) {
			for(int i = 7; i > 4; i--) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				board[8][i].setType(pieceOrder2v2[orderArrayIndex]);
				board[8][i].setOwner(3);
				board[8][i].setDirection(180);
				board[8][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				board[7][i].setType(pieceOrder2v2[orderArrayIndex]);
				board[7][i].setOwner(3);
				board[7][i].setDirection(180);
				board[7][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[8], 180.0, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
				board[6][i].setType(8);
				board[6][i].setOwner(3);
				board[6][i].setDirection(180);
				board[6][i].setColor(color);
			}
			P3HitPieces = new JLabel[9];
			for (int i = 0; i < 9; i++) {
				P3HitPieces[i] = new JLabel();
			}
		} else {
			for(int i = 7; i > 4; i--) {
				squaresPanels[0][i].setIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]]);
				squaresPanels[0][i].setName(color);
				board[0][i].setType(pieceOrder2v2[orderArrayIndex]);
				board[0][i].setOwner(4);
				board[0][i].setDirection(0);
				board[0][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 7; i > 4; i--) {
				squaresPanels[1][i].setIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]]);
				squaresPanels[1][i].setName(color);
				board[1][i].setType(pieceOrder2v2[orderArrayIndex]);
				board[1][i].setOwner(4);
				board[1][i].setDirection(0);
				board[1][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				squaresPanels[2][i].setIcon(getIconArray(color)[8]);
				squaresPanels[2][i].setName(color);
				board[2][i].setType(8);
				board[2][i].setOwner(4);
				board[2][i].setDirection(0);
				board[2][i].setColor(color);
			}
			P4HitPieces = new JLabel[9];
			for (int i = 0; i < 9; i++) {
				P4HitPieces[i] = new JLabel();
			}
		}
	}
	
	private void rotatePieceLeft(int x, int y) {
		RotatedIcon ri = new RotatedIcon(squaresPanels[x][y].getIcon(), 315.0, true);
		squaresPanels[x][y].setIcon(ri);
		int direction = board[x][y].getDirection() - 45;
		if (direction < 0) {
			direction = direction + 360;
		}
		board[x][y].setDirection(direction);
	}
  
	private void rotatePieceRight(int x, int y) {
		RotatedIcon ri = new RotatedIcon(squaresPanels[x][y].getIcon(), 45.0, true);
		squaresPanels[x][y].setIcon(ri);
		int direction = board[x][y].getDirection() + 45;
		if (direction >= 360) {
			direction = direction - 360;
		}
		board[x][y].setDirection(direction);
	}
  
	//TODO: logica de turnos
	//TODO: logica de movidas legales (Etapa 2)
	//TODO: desplegar las piezas que han salido del juego
	private void clickedOn(int i, int j, int numPlayers, player[] players) {
		if (!pieceActive) {
			if (squaresPanels[i][j].getIcon() != null) {
		  		pieceActive = true;
		  		squaresPanels[i][j].setBackground(boardColorHighlight);
		  		lastI = i;
		  		lastJ = j;
		  		originalDirection = board[i][j].getDirection();
		  		rotateLeftBut.setEnabled(true);
				rotateRightBut.setEnabled(true);
		  		guiPrintLine("pieza activa");
			}
		} else {
			if (!(lastI == i && lastJ == j)) {
				if (board[lastI][lastJ].getDirection() == originalDirection || board[lastI][lastJ].getType() == 8) {
					if (board[i][j].getOwner() != board[lastI][lastJ].getOwner()) {
						if (numPlayers == 2) {
							if (board[i][j].getColor() == players[0].getColor()) {
								P1HitPieces[P1HitPiecesIndex].setIcon(squaresPanels[i][j].getIcon());
								P1HitPiecesIndex++;
							} else if (board[i][j].getColor() == players[1].getColor()) {
								P2HitPieces[P2HitPiecesIndex].setIcon(squaresPanels[i][j].getIcon());
								P2HitPiecesIndex++;
							}
						} else {
							if (board[i][j].getColor() == players[0].getColor()) {
								P1HitPieces[P1HitPiecesIndex].setIcon(squaresPanels[i][j].getIcon());
								P1HitPiecesIndex++;
							} else if (board[i][j].getColor() == players[1].getColor()) {
								P2HitPieces[P2HitPiecesIndex].setIcon(squaresPanels[i][j].getIcon());
								P2HitPiecesIndex++;
							} else if (board[i][j].getColor() == players[2].getColor()) {
								P3HitPieces[P3HitPiecesIndex].setIcon(squaresPanels[i][j].getIcon());
								P3HitPiecesIndex++;
							} else if (board[i][j].getColor() == players[3].getColor()) {
								P4HitPieces[P4HitPiecesIndex].setIcon(squaresPanels[i][j].getIcon());
								P4HitPiecesIndex++;
							}
						}
						squaresPanels[i][j].setIcon(squaresPanels[lastI][lastJ].getIcon());
						squaresPanels[lastI][lastJ].setIcon(null);
						squaresPanels[lastI][lastJ].setBackground(boardColorPurple);
						board[i][j].setType(board[lastI][lastJ].getType());
						board[lastI][lastJ].setType(-1);
						board[i][j].setOwner(board[lastI][lastJ].getOwner());
						board[lastI][lastJ].setOwner(0);
						board[i][j].setDirection(board[lastI][lastJ].getDirection());
						board[lastI][lastJ].setDirection(0);
						board[i][j].setColor(board[lastI][lastJ].getColor());
						board[lastI][lastJ].setColor("");
						pieceActive = false;
						rotateLeftBut.setEnabled(false);
						rotateRightBut.setEnabled(false);
						guiPrintLine("pieza movida");
					} else {
						guiPrintLine("pieza pertenece al jugador");
					}
				} else {
					guiPrintLine("pieza rotada, imposible mover");
				}
			} else {
				squaresPanels[lastI][lastJ].setBackground(boardColorPurple);
				pieceActive = false;
				rotateLeftBut.setEnabled(false);
				rotateRightBut.setEnabled(false);
				if (board[i][j].getDirection() != originalDirection) {
					guiPrintLine("pieza rotada");
				} else {
					guiPrintLine("pieza no movida");
				}
			}
		}
	}

	/*
	private void highlightMoves() {
		
	}
	*/
	
	private void guiPrintLine(String str) {
		System.out.println(str);
		textOutput.append(str+"\n");
		textOutput.setCaretPosition(textOutput.getDocument().getLength());
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
	}
}
