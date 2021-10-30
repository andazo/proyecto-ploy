/*
 * @(#)ChessGUI.java
 *
 *
 * @author 
 * @version 1.00 2016/11/2
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
	int boardSize=600;
	
	Color boardColorPurple = new Color(65, 11, 153);
	Color boardColorThistle = new Color(200, 184, 219);
	Color boardColorPurpleHighlight = new Color(213,198,70);
	Color boardColorBlackHighlight = new Color(184,164,35);
	Color boardColorSnow = new Color(249, 244, 245);
	
	final String chipFolderNames [] = {"chips_red/", "chips_blue/", "chips_green/", "chips_yellow/"};
	final String chipNames [] = {"comm", "lance_1", "lance_2", "lance_3", "probe_1","probe_2","probe_3","probe_4", "shield"}; 
	
	ImageIcon redIcons[] = new ImageIcon[9];
	ImageIcon blueIcons[] = new ImageIcon[9];;
	ImageIcon yellowIcons[] = new ImageIcon[9];;
	ImageIcon greenIcons []= new ImageIcon[9];;
	
	int[][] board;
	boolean moving;
	int lastI;
	int lastJ;
	boolean checkmate;
	boolean gameOver;
	int currentSide;
	int[] coords = new int[4];
	int turnCount=1;
	static final int StandardBoard[][] = {
			{28,22,23,25,29,23,22,28},
			{21,21,21,21,21,21,21,21},
			{00,00,00,00,00,00,00,00},
			{00,00,00,00,00,00,00,00},
			{00,00,00,00,00,00,00,00},
			{00,00,00,00,00,00,00,00},
			{11,11,11,11,11,11,11,11},
			{18,12,13,15,19,13,12,18}
	};
	static final int reset[][]=StandardBoard;

	public static void main(String[] args) {
		PloyGUI gui = new PloyGUI(false,true);
	}
	
	public PloyGUI(boolean whiteIsAI, boolean blackIsAI) {
		currentSide=3;
		makeGUI();
		loadImages();
		populateBoard(0);
	}
	
	private void makeGUI() {
		ployInterface = new JFrame();
		ployInterface.setTitle("Ploy");
		ployInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ployInterface.setFocusable(true);
		ployInterface.requestFocus();
		ployInterface.setBackground(Color.black);
		
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
				//TODO: Event listeners para todas las labels
				/*
				final int tempI = i;
				final int tempJ = j;
				squaresPanels[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
					final int myI = tempI;
					final int myJ = tempJ;
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						clickedOn(myI,myJ);
					}
				});
				*/
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
			ployInterface.setIconImage(ImageIO.read(this.getClass().getResource("icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ployInterface.pack();

		board=new int[8][8];
		//board = ArrayOps.copyArr8(reset);
	
		guiPrintLine("Bienvenidos a Ploy");
		guiPrintLine("Para ganar, capture el comandante del oponente o todas las piezas excepto el comandante.");
		guiPrintLine("Cada pieza se puede mover por el tablero la cantidad de espacios de acuerdo con la cantidad de rayas que posee.");
		guiPrintLine("En un turno se puede mover una pieza o cambiar su dirección.");
		guiPrintLine("Los escudos se pueden mover y cambiar de dirección en un mismo turno.");
		guiPrintLine("Modos de juego: 1v1, 1v1v1v1, 2v2");

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
	private void populateBoard(int mode) {
		switch(mode) {
	  case 0:
	  	populateBoard1v1("red", 1);
	  	populateBoard1v1("blue", 2);
	    break;
	  case 1: // 1v1v1v1
	  	
	    break;
	  case 2: // 2v2
	  	
	    break;
		}
	}
	private ImageIcon[] getIconArray(String color) {
		switch(color) {
	  case "red":
	  	return this.redIcons;
	  case "blue":
	  	return this.blueIcons;
	  case "green":
	  	return this.greenIcons;
		case "yellow":
			return this.yellowIcons;
		default:
			return this.redIcons;
		}
	} 
	
	private void populateBoard1v1(String color, int playerNum) {
		if(playerNum == 1) {
			//TODO: meter los numeros de icono en un arreglo y recorrerlo con un for
			//TODO: rotar las piezas segun el jugador
			squaresPanels[0][1].setIcon(getIconArray(color)[1]);
			squaresPanels[0][2].setIcon(getIconArray(color)[2]);
			squaresPanels[0][3].setIcon(getIconArray(color)[3]);
			squaresPanels[0][4].setIcon(getIconArray(color)[0]);
			squaresPanels[0][5].setIcon(getIconArray(color)[3]);
			squaresPanels[0][6].setIcon(getIconArray(color)[2]);
			squaresPanels[0][7].setIcon(getIconArray(color)[1]);
			
			squaresPanels[1][2].setIcon(getIconArray(color)[7]);
			squaresPanels[1][3].setIcon(getIconArray(color)[5]);
			squaresPanels[1][4].setIcon(getIconArray(color)[6]);
			squaresPanels[1][5].setIcon(getIconArray(color)[5]);
			squaresPanels[1][6].setIcon(getIconArray(color)[4]);
			for(int i = 3; i < 6; i++){
				squaresPanels[2][i].setIcon(getIconArray(color)[8]);
			}
		} else {
			squaresPanels[8][1].setIcon(getIconArray(color)[1]);
			squaresPanels[8][2].setIcon(getIconArray(color)[2]);
			squaresPanels[8][3].setIcon(getIconArray(color)[3]);
			squaresPanels[8][4].setIcon(getIconArray(color)[0]);
			squaresPanels[8][5].setIcon(getIconArray(color)[3]);
			squaresPanels[8][6].setIcon(getIconArray(color)[2]);
			squaresPanels[8][7].setIcon(getIconArray(color)[1]);
			
			squaresPanels[7][2].setIcon(getIconArray(color)[7]);
			squaresPanels[7][3].setIcon(getIconArray(color)[5]);
			squaresPanels[7][4].setIcon(getIconArray(color)[6]);
			squaresPanels[7][5].setIcon(getIconArray(color)[5]);
			squaresPanels[7][6].setIcon(getIconArray(color)[4]);
			for(int i = 3; i < 6; i++){
				squaresPanels[6][i].setIcon(getIconArray(color)[8]);
			}
		}
	}

	private void clickedOn(int i, int j) {
		if((moving||(board[i][j]/10==currentSide||board[i][j]/10==0))&&!checkmate) {
			if(!moving) {
				lastI=i;
				lastJ=j;
				moving = true;
			} else {
				moving = false;
				//if(legalMoves(lastI,lastJ,board)[i][j]!=0){
					highlightMoves(new int[8][8]);
					updatePieceDisplay();
					currentSide=currentSide%2+1;

					/*
					if(WHITE_AI||BLACK_AI){
						//int[] coords = ChessAI.aiMiniMax(board,currentSide,depth);

						if(coords[3]==-1){
							if(WHITE_AI){
								guiPrintLine("Black wins!");
							}
							if(BLACK_AI){
								guiPrintLine("White wins!");
							}
							checkmate=true;
						} else if(coords[3]==-2){
							guiPrintLine("Stalemate!");
							checkmate=true;
						} else if(coords[3]>=0){
							printMove(makeMove(coords[0],coords[1],coords[2],coords[3],board));
						}
						updatePieceDisplay();
						currentSide=currentSide%2+1;
					}
				//} else {
					highlightMoves(new int[8][8]);
				//}
				 * 
				 */
			}
		}
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