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
	final int pieceOrder[] = {1,2,3,0,3,2,1,7,5,6,5,4};
	final int pieceOrder2[] = {1,2,3,0,3,2,1,4,5,6,5,7};
	
	ImageIcon redIcons[] = new ImageIcon[9];
	ImageIcon blueIcons[] = new ImageIcon[9];
	ImageIcon yellowIcons[] = new ImageIcon[9];
	ImageIcon greenIcons []= new ImageIcon[9];
	int currentPlayer;
	boolean pieceActive = false;
	int lastI;
	int lastJ;
	
	int[][] board;
	boolean gameOver;

	//public static void main(String[] args) {
		//PloyGUI gui = new PloyGUI();
	//}
	
	public PloyGUI(mainClass controller, int numPlayers, player[] players, int mode) {
		/*
		if (numPlayers == 2) {
			System.out.println(players[0].name);
			System.out.println(players[0].color);
			System.out.println(players[1].name);
			System.out.println(players[1].color);
		}
		else {
			System.out.println(players[0].name);
			System.out.println(players[0].color);
			System.out.println(players[1].name);
			System.out.println(players[1].color);
			System.out.println(players[2].name);
			System.out.println(players[2].color);
			System.out.println(players[3].name);
			System.out.println(players[3].color);
		}
		*/
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
		boardPanel.setBorder(BorderFactory.createMatteBorder(40, 40, 40, 40, boardColorThistle));
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

		JPanel newGameButtons = new JPanel();
		newGameButtons.setLayout(new GridLayout(1,5,0,0));

		/*
		JButton humanDuel = new JButton("Human VS Human");
		humanDuel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				ployInterface.dispose();
				ChessGUI gui=new ChessGUI(false,false);
			}
		});
		newGameButtons.add(humanDuel);
		*/
		c.gridx = 0;
		c.gridy = 1;
		ployInterface.add(newGameButtons,c);

		ployInterface.setResizable(false);

		try{
			ployInterface.setIconImage(ImageIO.read(this.getClass().getResource("img/icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ployInterface.pack();

		board=new int[8][8];
		//board = ArrayOps.copyArr8(reset);
	
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
		
		updatePieceDisplay();
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
		newGameButtons.paintImmediately(new Rectangle(new Point(0,0),newGameButtons.getSize()));
		updatePieceDisplay();
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
		newGameButtons.paintImmediately(new Rectangle(new Point(0,0),newGameButtons.getSize()));

		ployInterface.setVisible(true);
	}
	
	//Carga todas las imagenes de las piezas, los convierte a iconos y los guarda en arreglos.
	private void loadImages() {
		String path = "img/";
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 9; j++) {
				path += chipFolderNames[i];
				path += chipNames[j];
				path += ".png";
				ImageIcon icon = new ImageIcon(path);
				System.out.println(path);
				switch(i) {
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
	
	//TODO: funcionalidad de varios jugadores y de escoger colores
	private void populateBoard(player[] players, int mode) {
		switch(mode) {
			case 0: // 1v1
				populateBoard1v1(players[0].color, 1);
			  	populateBoard1v1(players[1].color, 2);
			    break;
			case 1: // 1v1v1v1
			  	
			    break;
			case 2: // 2v2
			  	
			    break;
		}
	}
	
	private ImageIcon[] getIconArray(String color) {
		switch(color) {
			case "Red":
				return this.redIcons;
			case "Blue":
				return this.blueIcons;
			case "Green":
				return this.greenIcons;
			case "Yellow":
				return this.yellowIcons;
			default:
				return this.redIcons;
		}
	}
	
	private void populateBoard1v1(String color, int playerNum) {
		int orderArrayIndex = 0;
		if(playerNum == 1) {
			for(int i = 1; i < 8; i++) {
				squaresPanels[0][i].setIcon(getIconArray(color)[pieceOrder[orderArrayIndex]]);
				squaresPanels[0][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 2; i < 7; i++) {
				squaresPanels[1][i].setIcon(getIconArray(color)[pieceOrder[orderArrayIndex]]);
				squaresPanels[1][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 3; i < 6; i++) {
				squaresPanels[2][i].setIcon(getIconArray(color)[8]);
				squaresPanels[2][i].setName(color);
			}
		} else {
			for(int i = 1; i < 8; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder2[orderArrayIndex]], 180.0, true);
				squaresPanels[8][i].setIcon(ri);
				squaresPanels[8][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 2; i < 7; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[pieceOrder2[orderArrayIndex]], 180.0, true);
				squaresPanels[7][i].setIcon(ri);
				squaresPanels[7][i].setName(color);
				orderArrayIndex++;
			}
			for(int i = 3; i < 6; i++) {
				RotatedIcon ri = new RotatedIcon(getIconArray(color)[8], 180.0, true);
				squaresPanels[6][i].setIcon(ri);
				squaresPanels[6][i].setName(color);
			}
		}
	}

	//TODO: logica de turnos
	//TODO: logica de movidas legales (Etapa 2)
	//TODO: desplegar las piezas que han salido del juego
	private void clickedOn(int i, int j) {
		  if(!pieceActive) {
		  	//TODO: activar opcion para escoger si quiere girar la pieza
		  	if(squaresPanels[i][j].getIcon() != null) {
		  		pieceActive = true;
		  		squaresPanels[i][j].setBackground(boardColorHighlight);
		  		lastI = i;
		  		lastJ = j;
		  		guiPrintLine("pieza activa");
		  	}
		  } else {
		    squaresPanels[i][j].setIcon(squaresPanels[lastI][lastJ].getIcon());
		    squaresPanels[lastI][lastJ].setIcon(null);
		    squaresPanels[lastI][lastJ].setBackground(boardColorPurple);
		    pieceActive = false;
		    guiPrintLine("pieza movida");
		  }
		//}
		/*if((moving||(board[i][j]/10==currentSide||board[i][j]/10==0))&&!checkmate) {
			if(!moving) {
				lastI=i;
				lastJ=j;
				moving = true;
			} else {
				moving = false;
				if(legalMoves(lastI,lastJ,board)[i][j]!=0){
					highlightMoves(new int[8][8]);
					updatePieceDisplay();
					currentSide=currentSide%2+1;
				}
			}
		 */
	}

	private void highlightMoves(int[][] legals){
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if((i%2+j%2)%2==0){
					if(legals[i][j]>=1){
						squaresPanels[i][j].setBackground(boardColorPurpleHighlight);
					} else {
						squaresPanels[i][j].setBackground(boardColorPurple);
					}
				} else {
					if(legals[i][j]>=1){
						squaresPanels[i][j].setBackground(boardColorBlackHighlight);
					} else {
						//squaresPanels[i][j].setBackground(boardColorBlack);
					}
				}
			}
		}
	}
	
	private void updatePieceDisplay(){
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(board[i][j]/10==1){
					//squaresPanels[i][j].setIcon(pieceSprites[0][board[i][j]%10]);
					//squaresPanels[i][j].setForeground(Color.white);
				} else if(board[i][j]/10==2){
					//squaresPanels[i][j].setIcon(pieceSprites[1][board[i][j]%10]);
					//squaresPanels[i][j].setForeground(Color.black);
				} else {
					squaresPanels[i][j].setIcon(null);
				}
				squaresPanels[i][j].paintImmediately(0,0,boardSize/8+1,boardSize/8+1);
			}
		}
	}

	/*private String makeMove(int i1 ,int j1 ,int i2 ,int j2 , int[][] boardArr){
		
		boolean captureBool = boardArr[i2][j2]!=0;
		String pieceStr = pieceStrArr[boardArr[i1][j1]%10];
		    	if (captureBool&&boardArr[i1][j1]%10==1){
		    		pieceStr = colStr[j1];
		    	}
		String squareStr = colStr[j2]+(8-i2);
		String moveStr = pieceStr+colStr[j1];
		if (captureBool){
			moveStr+="x";
		}
		moveStr+=squareStr;

		boardArr[i2][j2] = boardArr[i1][j1];
		boardArr[i1][j1] = 0;
		if(boardArr[i2][j2]==11&&i2==0){
			boardArr[i2][j2]=15;
			moveStr+="=Q";
		}else if(boardArr[i2][j2]==21&&i2==7){
			boardArr[i2][j2]=25;
			moveStr+="=Q";
		}
		if(boardArr[i2][j2]==19){
			boardArr[i2][j2]=16;
			if(j2==6){
				boardArr[7][5]=14;
				boardArr[7][7]=0;
				moveStr="O-O";
			} else if(j2==2){
				boardArr[7][3]=14;
				boardArr[7][0]=0;
				moveStr="O-O-O";
			}
		}else if(boardArr[i2][j2]==29){
			boardArr[i2][j2]=26;
			if(j2==6){
				boardArr[0][5]=24;
				boardArr[0][7]=0;
				moveStr="O-O";
			} else if(j2==2){
				boardArr[0][3]=24;
				boardArr[0][0]=0;
				moveStr="O-O-O";
			}
		}
		if(boardArr[i2][j2]==18){
			boardArr[i2][j2]=14;
		}else if(boardArr[i2][j2]==28){
			boardArr[i2][j2]=24;
		}

		int side=boardArr[i2][j2]/10;
		for(int a=0; a<8; a++){
			for(int b=0; b<8; b++){
				if(boardArr[a][b]/10==(side%2+1)&&boardArr[a][b]%10==7){
					boardArr[a][b]=(side%2+1)*10+1;
				}
			}
		}

		if(boardArr[i2][j2]==11&&i1==6&&i2==4){
			boardArr[i2][j2]=17;
		} else if(boardArr[i2][j2]==21&&i1==1&&i2==3){
			boardArr[i2][j2]=27;
		}
		if(boardArr[i2][j2]==11&&j1-j2!=0&&!captureBool){//boardArr[i2+1][j2]%10==7){
			boardArr[i2+1][j2]=0;
			//moveStr+="e.p.";
		}
		if(boardArr[i2][j2]==21&&j1-j2!=0&&!captureBool){//boardArr[i2+1][j2]%10==7){
			boardArr[i2-1][j2]=0;
			//moveStr+="e.p.";
		}
		return moveStr;
	}
	*/
	
	private void guiPrintLine(String str){
		System.out.println(str);
		textOutput.append(str+"\n");
		textOutput.setCaretPosition(textOutput.getDocument().getLength());
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
	}
}
