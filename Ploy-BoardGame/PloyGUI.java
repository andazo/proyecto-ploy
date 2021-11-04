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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Graphics;

public class PloyGUI {
	JFrame ployInterface;
	JPanel boardPanel;
	JLabel squaresPanels[][];
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
	ImageIcon greenIcons []= new ImageIcon[9];
	int currentPlayer;
	boolean pieceActive = false;
	int lastI;
	int lastJ;
	
	int[][] board;
	int[][] pieceOwner;
	boolean gameOver;
	
	public PloyGUI(mainClass controller, int numPlayers, player[] players, int mode) {
		makeGUI(controller, numPlayers, players, mode);
		loadImages();
		populateBoard(players, mode);
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
		JMenu menu = new JMenu("Opciones");
        menuBar.add(menu);
		JMenuItem option = new JMenuItem("Reglas");
        option.addActionListener(controller);
        menu.add(option);
		
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
						clickedOn(myI,myJ);
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
		ployInterface.add(boardPanel,c);
		
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

		JPanel rotateButtons = new JPanel();
		rotateButtons.setLayout(new GridLayout(1,5,0,0));
    
		JButton rotateLeftBut = new JButton("Girar izq");
		rotateLeftBut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				rotatePieceLeft(lastI, lastJ);
			}
		});
		rotateButtons.add(rotateLeftBut);
    
    JButton rotateRightBut = new JButton("Girar der");
		rotateRightBut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				rotatePieceRight(lastI, lastJ);
			}
		});
		rotateButtons.add(rotateRightBut);
		
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

		board = new int[9][9];
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        board[i][j] = -1;
      }
      System.out.print("\n");
    }
    
    pieceOwner = new int[9][9];
	
		guiPrintLine("Bienvenidos a Ploy");
		guiPrintLine("Para ganar, capture el comandante del oponente o todas las piezas excepto el comandante.");
		guiPrintLine("Cada pieza se puede mover por el tablero la cantidad de espacios de acuerdo con la cantidad de rayas que posee.");
		guiPrintLine("En un turno se puede mover una pieza o cambiar su direcci�n.");
		guiPrintLine("Los escudos se pueden mover y cambiar de direcci�n en un mismo turno.");
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
				populateBoard1v1(players[0].color, 1);
			  	populateBoard1v1(players[1].color, 2);
			    break;
			case 1: // 1v1v1v1
				populateBoard1v1v1v1(players[0].color, 1);
				populateBoard1v1v1v1(players[1].color, 2);
				populateBoard1v1v1v1(players[2].color, 3);
				populateBoard1v1v1v1(players[3].color, 4);
			    break;
			case 2: // 2v2
				populateBoard2v2(players[0].color, 1);
			  	populateBoard2v2(players[1].color, 2);
			  	populateBoard2v2(players[2].color, 3);
			  	populateBoard2v2(players[3].color, 4);
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
				board[8][i] = pieceOrder1v1P1[orderArrayIndex];
				pieceOwner[8][i] = 1;
				orderArrayIndex++;
			}
			for (int i = 2; i < 7; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1P1[orderArrayIndex]], 180.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				board[7][i] = pieceOrder1v1P1[orderArrayIndex];
				pieceOwner[7][i] = 1;
				orderArrayIndex++;
			}
			for (int i = 3; i < 6; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[8], 180.0, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
				board[6][i] = 8;
				pieceOwner[6][i] = 1;
			}
		} else {
			for (int i = 1; i < 8; i++) {
				squaresPanels[0][i].setIcon(getIconArray(color)[pieceOrder1v1P2[orderArrayIndex]]);
				squaresPanels[0][i].setName(color);
				board[0][i] = pieceOrder1v1P2[orderArrayIndex];
				pieceOwner[0][i] = 2;
				orderArrayIndex++;
			}
			for (int i = 2; i < 7; i++) {
				squaresPanels[1][i].setIcon(getIconArray(color)[pieceOrder1v1P2[orderArrayIndex]]);
				squaresPanels[1][i].setName(color);
				board[1][i] = pieceOrder1v1P2[orderArrayIndex];
				pieceOwner[1][i] = 2;
				orderArrayIndex++;
			}
			for (int i = 3; i < 6; i++) {
				squaresPanels[2][i].setIcon(getIconArray(color)[8]);
				squaresPanels[2][i].setName(color);
				board[2][i] = 8;
				pieceOwner[2][i] = 2;
			}
		}
	}
	
	private void populateBoard1v1v1v1(String color, int playerNum) {
		int orderArrayIndex = 0;
		if (playerNum == 1) {
			for (int i = 0; i < 3; i++) {
				RotatedIcon ri;
				if (i != 2) {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 225.0, true);
				} else {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 180.0, true);
				}
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				board[8][i] = pieceOrder1v1v1v1[orderArrayIndex];
				pieceOwner[8][i] = 1;
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 225.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				board[7][i] = pieceOrder1v1v1v1[orderArrayIndex];
				pieceOwner[7][i] = 1;
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				RotatedIcon ri;
				if (i == 0) {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 270.0, true);
				} else {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 225.0, true);
				}
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
				board[6][i] = pieceOrder1v1v1v1[orderArrayIndex];
				pieceOwner[6][i] = 1;
				orderArrayIndex++;
			}
		} else if (playerNum == 2) {
			for (int i = 0; i < 3; i++) {
				RotatedIcon ri;
				if (i != 2) {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 315.0, true);
				} else {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 270.0, true);
				}
				squaresPanels[i][0].setIcon(ri);
				squaresPanels[i][0].setName(color);
				board[i][0] = pieceOrder1v1v1v1[orderArrayIndex];
				pieceOwner[i][0] = 2;
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 315.0, true);
				squaresPanels[i][1].setIcon(ri);
				squaresPanels[i][1].setName(color);
				board[i][1] = pieceOrder1v1v1v1[orderArrayIndex];
				pieceOwner[i][1] = 2;
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				RotatedIcon ri;
				if (i == 0) {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 0.0, true);
				} else {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 315.0, true);
				}
				squaresPanels[i][2].setIcon(ri);
				squaresPanels[i][2].setName(color);
				board[i][2] = pieceOrder1v1v1v1[orderArrayIndex];
				pieceOwner[i][2] = 2;
				orderArrayIndex++;
			}
		} else if (playerNum == 3) {
			for (int i = 8; i > 5; i--) {
				RotatedIcon ri;
				if (i != 6) {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 45.0, true);
				} else {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 0.0, true);
				}
				squaresPanels[0][i].setIcon(ri);
				squaresPanels[0][i].setName(color);
				board[0][i] = pieceOrder1v1v1v1[orderArrayIndex];
				pieceOwner[0][i] = 3;
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 45.0, true);
				squaresPanels[1][i].setIcon(ri);
				squaresPanels[1][i].setName(color);
				board[1][i] = pieceOrder1v1v1v1[orderArrayIndex];
				pieceOwner[1][i] = 3;
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				RotatedIcon ri;
				if (i == 8) {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 90.0, true);
				} else {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 45.0, true);
				}
				squaresPanels[2][i].setIcon(ri);
				squaresPanels[2][i].setName(color);
				board[2][i] = pieceOrder1v1v1v1[orderArrayIndex];
				pieceOwner[2][i] = 3;
				orderArrayIndex++;
			}
		} else {
			for (int i = 8; i > 5; i--) {
				RotatedIcon ri;
				if (i != 6) {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 135.0, true);
				} else {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 90.0, true);
				}
				squaresPanels[i][8].setIcon(ri);
				squaresPanels[i][8].setName(color);
				board[i][8] = pieceOrder1v1v1v1[orderArrayIndex];
				pieceOwner[i][8] = 4;
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 135.0, true);
				squaresPanels[i][7].setIcon(ri);
				squaresPanels[i][7].setName(color);
				board[i][7] = pieceOrder1v1v1v1[orderArrayIndex];
				pieceOwner[i][7] = 4;
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				RotatedIcon ri;
				if (i == 8) {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 180.0, true);
				} else {
					ri = new RotatedIcon(getIconArray(color)[pieceOrder1v1v1v1[orderArrayIndex]], 135.0, true);
				}
				squaresPanels[i][6].setIcon(ri);
				squaresPanels[i][6].setName(color);
				board[i][6] = pieceOrder1v1v1v1[orderArrayIndex];
				pieceOwner[i][6] = 4;
				orderArrayIndex++;
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
				board[8][i] = pieceOrder2v2[orderArrayIndex];
				pieceOwner[8][i] = 1;
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				board[7][i] = pieceOrder2v2[orderArrayIndex];
				pieceOwner[7][i] = 1;
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[8], 180.0, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
				board[6][i] = 8;
				pieceOwner[6][i] = 1;
			}
		} else if (playerNum == 2) {
			for(int i = 1; i < 4; i++) {
				squaresPanels[0][i].setIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]]);
				squaresPanels[0][i].setName(color);
				board[0][i] = pieceOrder2v2[orderArrayIndex];
				pieceOwner[0][i] = 2;
				orderArrayIndex++;
			}
			for(int i = 3; i > 0; i--) {
				squaresPanels[1][i].setIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]]);
				squaresPanels[1][i].setName(color);
				board[1][i] = pieceOrder2v2[orderArrayIndex];
				pieceOwner[1][i] = 2;
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				squaresPanels[2][i].setIcon(getIconArray(color)[8]);
				squaresPanels[2][i].setName(color);
				board[2][i] = 8;
				pieceOwner[2][i] = 2;
			}
		} else if (playerNum == 3) {
			for(int i = 7; i > 4; i--) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				board[8][i] = pieceOrder2v2[orderArrayIndex];
				pieceOwner[8][i] = 3;
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]], 180.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				board[7][i] = pieceOrder2v2[orderArrayIndex];
				pieceOwner[7][i] = 3;
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[8], 180.0, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
				board[6][i] = 8;
				pieceOwner[6][i] = 3;
			}
		} else {
			for(int i = 7; i > 4; i--) {
				squaresPanels[0][i].setIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]]);
				squaresPanels[0][i].setName(color);
				board[0][i] = pieceOrder2v2[orderArrayIndex];
				pieceOwner[0][i] = 4;
				orderArrayIndex++;
			}
			for(int i = 7; i > 4; i--) {
				squaresPanels[1][i].setIcon(getIconArray(color)[pieceOrder2v2[orderArrayIndex]]);
				squaresPanels[1][i].setName(color);
				board[1][i] = pieceOrder2v2[orderArrayIndex];
				pieceOwner[1][i] = 4;
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				squaresPanels[2][i].setIcon(getIconArray(color)[8]);
				squaresPanels[2][i].setName(color);
				board[2][i] = 8;
				pieceOwner[2][i] = 4;
			}
		}
	}
	
  private void rotatePieceLeft(int x, int y) {
    RotatedIcon ri = new RotatedIcon(squaresPanels[x][y].getIcon(), 315.0, true);
    squaresPanels[x][y].setIcon(ri);
  }
  
  private void rotatePieceRight(int x, int y) {
    RotatedIcon ri = new RotatedIcon(squaresPanels[x][y].getIcon(), 45.0, true);
    squaresPanels[x][y].setIcon(ri);
  }
  
	//TODO: logica de turnos
	//TODO: logica de movidas legales (Etapa 2)
	//TODO: desplegar las piezas que han salido del juego
	private void clickedOn(int i, int j) {
		if (!pieceActive) {
		//TODO: activar opcion para escoger si quiere girar la pieza
			if (squaresPanels[i][j].getIcon() != null) {
		  		pieceActive = true;
		  		squaresPanels[i][j].setBackground(boardColorHighlight);
		  		lastI = i;
		  		lastJ = j;
		  		guiPrintLine("pieza activa");
			}
		} else {
			if (!(lastI == i && lastJ == j)) {
				if (pieceOwner[i][j] != pieceOwner[lastI][lastJ]) {
					squaresPanels[i][j].setIcon(squaresPanels[lastI][lastJ].getIcon());
					squaresPanels[lastI][lastJ].setIcon(null);
					squaresPanels[lastI][lastJ].setBackground(boardColorPurple);
					board[i][j] = board[lastI][lastJ];
					board[lastI][lastJ] = -1;
					pieceOwner[i][j] = pieceOwner[lastI][lastJ];
					pieceOwner[lastI][lastJ] = 0;
					pieceActive = false;
					guiPrintLine("pieza movida");
				} else {
					squaresPanels[lastI][lastJ].setBackground(boardColorPurple);
					pieceActive = false;
					guiPrintLine("pieza pertenece al jugador");
				}
			} else {
				squaresPanels[lastI][lastJ].setBackground(boardColorPurple);
				pieceActive = false;
				guiPrintLine("pieza no movida");
			}
		}
	}

	private void highlightMoves() {
		
	}
	
	private void guiPrintLine(String str) {
		System.out.println(str);
		textOutput.append(str+"\n");
		textOutput.setCaretPosition(textOutput.getDocument().getLength());
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
	}
}
