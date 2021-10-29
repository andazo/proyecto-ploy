/*
 * @(#)ChessGUI.java
 *
 *
 * @author 
 * @version 1.00 2016/11/2
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Graphics;

public class ChessGUI {
	int depth = 1;
	JFrame ployInterface;
	//JPanel boardPanel;
	JLayeredPane boardPanel;
	JPanel legalBoardPanel;
	JLabel squaresPanels[][];
	JLabel squaresPanels2[][];
	JTextArea textOutput;
	JScrollPane textScroll;
	JSpinner depthSpinner;
	int boardSize=600;
	
	Color boardColorPurple = new Color(65, 11, 153);
	Color boardColorThistle = new Color(200, 184, 219);
	Color boardColorPurpleHighlight = new Color(213,198,70);
	Color boardColorBlackHighlight = new Color(184,164,35);
	Color snow = new Color(249, 244, 245);
	
	BufferedImage sprites;
	ImageIcon pieceSprites[][] = new ImageIcon[2][10];
	static boolean WHITE_AI;
	static boolean BLACK_AI;
	int[][] board;
	boolean moving;
	int lastI;
	int lastJ;
	boolean checkmate;
	boolean gameOver;
	int currentSide;
	int[] coords = new int[4];
	String[] pieceStrArr = {"","","N","B","R","Q","K","","R","K"};
	String[] colStr = {"a","b","c","d","e","f","g","h"};
	String turnStr="";
	int turnCount=1;
	boolean printTurn = true;
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
		ChessGUI gui = new ChessGUI(false,true);
	}
	
	public ChessGUI(boolean whiteIsAI, boolean blackIsAI) {
		currentSide=3;
		makeGUI();
		loadImages();
		
		/*if (WHITE_AI){
			currentSide = 2;
			gameOver = false;
			highlightMoves(new int[8][8]);
			moving = false;
			lastI = 0;
			lastJ = 0;
			checkmate = false;
			if(coords[3] == -1){
				System.out.println("Black wins!");
				checkmate=true;
			} else if(coords[3]==-2){
				checkmate=true;
				System.out.println("Stalemate! White cannot move!");
			} else {
				guiPrintLine(makeMove(coords[0],coords[1],coords[2],coords[3],board));
				updatePieceDisplay();
			}
		} else {
			*/
		
			gameOver=false;
			updatePieceDisplay();
			highlightMoves(new int[8][8]);
			moving = false;
			lastI=0;
			lastJ=0;
			checkmate=false;
			currentSide=1;
		//}
	}
	
	private void makeGUI() {
		ployInterface = new JFrame();
		ployInterface.setTitle("Ploy");

		ployInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ployInterface.setFocusable(true);
		ployInterface.requestFocus();
		ployInterface.setBackground(Color.black);
		
		//boardPanel = new JPanel();
		boardPanel = new JLayeredPane();
		boardPanel.setBorder(BorderFactory.createMatteBorder(40, 40, 40, 40, new Color(200, 184, 219)));
		
		//**********************
		boardPanel.setLayout(new GridLayout(8,8,2,2));
		int squareSize = boardSize / 8;
		squaresPanels = new JLabel[8][8];
		boolean squareColor = true;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				squaresPanels[i][j] = new JLabel();
				squaresPanels[i][j].setOpaque(true);
				squaresPanels[i][j].setSize(new Dimension(squareSize,squareSize));
				squaresPanels[i][j].setMinimumSize(new Dimension(squareSize,squareSize));
				squaresPanels[i][j].setMaximumSize(new Dimension(squareSize,squareSize));
				squaresPanels[i][j].setPreferredSize(new Dimension(squareSize,squareSize));
				squaresPanels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				squaresPanels[i][j].setVerticalAlignment(SwingConstants.CENTER);
				squaresPanels[i][j].setFont(new Font("Segoe UI Symbol", squaresPanels[i][j].getFont().getStyle(), 70));
				squaresPanels[i][j].setForeground(Color.BLACK);
				squaresPanels[i][j].setBackground(boardColorPurple);
				
				JLabel label1= new JLabel();
			  label1.setOpaque(true);
			  label1.setBackground(Color.RED);
			  label1.setBounds(50,50,200,200);
			  
			  //boardPanel.add(label1, Integer.valueOf(1));

				squareColor=!squareColor;
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
			squareColor=!squareColor;
		}
		//*****************************************************
		legalBoardPanel = new JPanel();
		legalBoardPanel.setLayout(new GridLayout(9,9,25,25));
		legalBoardPanel.setBackground(boardColorThistle);
		legalBoardPanel.setBorder(BorderFactory.createMatteBorder(40, 40, 40, 40, new Color(200, 184, 219)));
		int smallSquareSize = boardSize / 16;
		squaresPanels2 = new JLabel[9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				squaresPanels2[i][j] = new JLabel();
				squaresPanels2[i][j].setOpaque(true);
				squaresPanels2[i][j].setSize(new Dimension(smallSquareSize,smallSquareSize));
				squaresPanels2[i][j].setMinimumSize(new Dimension(smallSquareSize,smallSquareSize));
				squaresPanels2[i][j].setMaximumSize(new Dimension(smallSquareSize,smallSquareSize));
				squaresPanels2[i][j].setPreferredSize(new Dimension(smallSquareSize,smallSquareSize));
				squaresPanels2[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				squaresPanels2[i][j].setVerticalAlignment(SwingConstants.CENTER);
				squaresPanels2[i][j].setFont(new Font("Segoe UI Symbol", squaresPanels2[i][j].getFont().getStyle(), 70));
				squaresPanels2[i][j].setForeground(Color.BLACK);
				squaresPanels2[i][j].setBackground(boardColorPurple);
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
				legalBoardPanel.add(squaresPanels2[i][j]);
			}
		}
		
		
		ployInterface.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		//ployInterface.add(boardPanel,c);
		ployInterface.add(legalBoardPanel);
		
		textOutput = new JTextArea(1,1);
		textOutput.setBackground(snow);
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
			ployInterface.setIconImage(ImageIO.read(this.getClass().getResource("icon2.png")));
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

		updatePieceDisplay();
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
		newGameButtons.paintImmediately(new Rectangle(new Point(0,0),newGameButtons.getSize()));
		updatePieceDisplay();
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
		newGameButtons.paintImmediately(new Rectangle(new Point(0,0),newGameButtons.getSize()));

		ployInterface.setVisible(true);
	}
	
	private void loadImages(){
		/*
		try{
			sprites=ImageIO.read(this.getClass().getResource("sprites.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i=0;i<2;i++){
			for(int j=0;j<7;j++){
				pieceSprites[i][j] = new ImageIcon(sprites.getSubimage(i*100,j*100,100,100).getScaledInstance(boardSize/8, boardSize/8,Image.SCALE_SMOOTH));
			}
		}
		pieceSprites[0][7]=pieceSprites[0][1];
		pieceSprites[1][7]=pieceSprites[1][1];
		pieceSprites[0][8]=pieceSprites[0][4];
		pieceSprites[1][8]=pieceSprites[1][4];
		pieceSprites[0][9]=pieceSprites[0][6];
		pieceSprites[1][9]=pieceSprites[1][6];
		
		
		
		try{
			sprites=ImageIO.read(this.getClass().getResource("commander.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		ImageIcon icon = new ImageIcon("icon2.png");
		for(int i = 1; i < 8; i++){
				squaresPanels2[0][i].setIcon(icon);
		}
		for(int i = 2; i < 7; i++){
			squaresPanels2[1][i].setIcon(icon);
		}
		for(int i = 3; i < 6; i++){
			squaresPanels2[2][i].setIcon(icon);
		}
		
		for(int i = 1; i < 8; i++){
			squaresPanels2[8][i].setIcon(icon);
		}
		for(int i = 2; i < 7; i++){
			squaresPanels2[7][i].setIcon(icon);
		}
		for(int i = 3; i < 6; i++){
			squaresPanels2[6][i].setIcon(icon);
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
					printMove(makeMove(lastI,lastJ,i,j,board));
					highlightMoves(new int[8][8]);
					updatePieceDisplay();
					currentSide=currentSide%2+1;

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
					squaresPanels[i][j].setIcon(pieceSprites[0][board[i][j]%10]);
					//squaresPanels[i][j].setForeground(Color.white);
				} else if(board[i][j]/10==2){
					squaresPanels[i][j].setIcon(pieceSprites[1][board[i][j]%10]);
					//squaresPanels[i][j].setForeground(Color.black);
				} else {
					squaresPanels[i][j].setIcon(null);
				}
				squaresPanels[i][j].paintImmediately(0,0,boardSize/8+1,boardSize/8+1);
			}
		}
	}

	private String makeMove(int i1 ,int j1 ,int i2 ,int j2 , int[][] boardArr){
		boolean captureBool = boardArr[i2][j2]!=0;
		String pieceStr = pieceStrArr[boardArr[i1][j1]%10];
		//    	if (captureBool&&boardArr[i1][j1]%10==1){
		//    		pieceStr = colStr[j1];
		//    	}
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
	
	private void guiPrintLine(String str){
		System.out.println(str);
		textOutput.append(str+"\n");
		textOutput.setCaretPosition(textOutput.getDocument().getLength());
		textScroll.paintImmediately(new Rectangle(new Point(0,0),textScroll.getSize()));
	}
	private void printMove(String str){
		printTurn=!printTurn;
		turnStr+=str+" ";
		if(printTurn){
			guiPrintLine(turnCount+"."+turnStr);
			turnCount++;
			turnStr="";
		}
		//ArrayOps.print8x8(board);
	}
}