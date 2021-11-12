/*
 * @(#)PloyGUI.java
 *
 *
 * @author 
 * @version 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class PloyGUI {
	JFrame ployInterface;
	JPanel boardPanel;
	JPanel rotateButtons;
	JButton rotateLeftBut;
	JButton rotateRightBut;
	JMenuBar menuBar;
	JLabel squaresPanels[][];
	JLabel P1HitPieces[];
	JLabel P2HitPieces[];
	JLabel P3HitPieces[];
	JLabel P4HitPieces[];
	JTextArea textOutput;
	JScrollPane textScroll;
	JSpinner depthSpinner;
	int boardSize = 600;
	
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
	
	public PloyGUI() {
		loadImages();
	}
	
	public void showHitPieces(String player, int hitPiecesIndex) {
		JLabel[] hitPieces = null;
		if (player == "p1") {
			hitPieces = P1HitPieces;
		} else if (player == "p2") {
			hitPieces = P2HitPieces;
		} else if (player == "p3") {
			hitPieces = P3HitPieces;
		} else if (player == "p4") {
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
	
	public void makeGUI(Player[] players, int gameMode) {
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
		JMenuItem rules = new JMenuItem("Reglas");
		options.add(rules);
		
		JMenuItem save = new JMenuItem("Guardar Partida");
		options.add(save);
		JMenuItem load = new JMenuItem("Cargar Partida");
		options.add(load);
        
        JMenu hitPieces = new JMenu("Piezas Eliminadas");
        menuBar.add(hitPieces);
        JMenuItem hitP1 = new JMenuItem("Jugador 1");
        hitPieces.add(hitP1);
        JMenuItem hitP2 = new JMenuItem("Jugador 2");
        hitPieces.add(hitP2);
        
        if (gameMode != 0) {
        	JMenuItem hitP3 = new JMenuItem("Jugador 3");
            hitPieces.add(hitP3);
            JMenuItem hitP4 = new JMenuItem("Jugador 4");
            hitPieces.add(hitP4);
        }
        
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
		rotateButtons.add(rotateLeftBut);
    
		rotateRightBut = new JButton("Girar der");
		rotateButtons.add(rotateRightBut);
		
		rotateLeftBut.setEnabled(false);
		rotateRightBut.setEnabled(false);
		
		c.gridx = 0;
		c.gridy = 1;
		ployInterface.add(rotateButtons,c);

		ployInterface.setResizable(false);

		try {
			ployInterface.setIconImage(ImageIO.read(this.getClass().getResource("icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ployInterface.pack();
	
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
			guiPrintLine(players[i].getName() + ": " + players[i].getColor());
		}
		
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
		rotateButtons.paintImmediately(new Rectangle(new Point(0,0),rotateButtons.getSize()));
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
		rotateButtons.paintImmediately(new Rectangle(new Point(0,0),rotateButtons.getSize()));

		ployInterface.setVisible(true);
	}
	
	//Carga todas las imagenes de las piezas, los convierte a iconos y los guarda en arreglos.
	private void loadImages() {
		String path = "";
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 9; j++) {
				path += chipFolderNames[i];
				path += chipNames[j];
				path += ".png";
				
				BufferedImage img = null;
				try {
					img = ImageIO.read(this.getClass().getResource(path));
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
				path = "";
			}
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
	
	public void populateBoard(Player[] players, int gameMode) {
		switch (gameMode) {
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
	
	private void populateBoard1v1(String color, int playerNum) {
		int orderArrayIndex = 0;
		if (playerNum == 1) {
			for (int i = 1; i < 8; i++) {
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1P1[orderArrayIndex]], 180.0, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				orderArrayIndex++;
			}
			for (int i = 2; i < 7; i++) {
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1P1[orderArrayIndex]], 180.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				orderArrayIndex++;
			}
			for (int i = 3; i < 6; i++) {
				RotateIcon ri = new RotateIcon(getIconArray(color)[8], 180.0, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
			}
			P1HitPieces = new JLabel[15];
			for (int i = 0; i < 15; i++) {
				P1HitPieces[i] = new JLabel();
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
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 225.0, true);
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
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
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
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[i][0].setIcon(ri);
				squaresPanels[i][0].setName(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 315.0, true);
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
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[i][2].setIcon(ri);
				squaresPanels[i][2].setName(color);
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
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[0][i].setIcon(ri);
				squaresPanels[0][i].setName(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 45.0, true);
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
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[2][i].setIcon(ri);
				squaresPanels[2][i].setName(color);
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
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[i][8].setIcon(ri);
				squaresPanels[i][8].setName(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 135.0, true);
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
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], direction, true);
				squaresPanels[i][6].setIcon(ri);
				squaresPanels[i][6].setName(color);
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
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				RotateIcon ri = new RotateIcon(getIconArray(color)[8], 180.0, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
			}
			P1HitPieces = new JLabel[9];
			for (int i = 0; i < 9; i++) {
				P1HitPieces[i] = new JLabel();
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
			P2HitPieces = new JLabel[9];
			for (int i = 0; i < 9; i++) {
				P2HitPieces[i] = new JLabel();
			}
		} else if (playerNum == 3) {
			for(int i = 7; i > 4; i--) {
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				RotateIcon ri = new RotateIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				RotateIcon ri = new RotateIcon(getIconArray(color)[8], 180.0, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
			}
			P3HitPieces = new JLabel[9];
			for (int i = 0; i < 9; i++) {
				P3HitPieces[i] = new JLabel();
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
			P4HitPieces = new JLabel[9];
			for (int i = 0; i < 9; i++) {
				P4HitPieces[i] = new JLabel();
			}
		}
	}
	
	public void loadBoard(Player[] players, int gameMode, String boardData) {
		
	}
	
	// direction = 315 rota hacia la izquierda, direction = 45 rota hacia la derecha
	public void rotatePiece(int x, int y, int direction) {
		RotateIcon ri = new RotateIcon(squaresPanels[x][y].getIcon(), direction, true);
		squaresPanels[x][y].setIcon(ri);
	}

	/*
	private void highlightMoves() {
		
	}
	*/
	
	public void guiPrintLine(String str) {
		System.out.println(str);
		textOutput.append(str+"\n");
		textOutput.setCaretPosition(textOutput.getDocument().getLength());
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
	}
	
	public void closeWindow() {
		ployInterface.dispose();
	}
}
