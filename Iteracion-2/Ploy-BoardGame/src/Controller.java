import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

	private Message msg;
	private FileManager fm;
	private Player[] players;
	private PloyGUI gui;
	private PloyBoard board;
	private int gameMode;
	private final String GAME_RULES = "Para 2 jugadores:\nEl objetivo es capturar al "
			+ "Comandante del oponente o todas sus piezas excepto el Comandante.\n"
			+ "En el juego de dos jugadores solo se utilizan los conjuntos de color "
			+ "rojo y verde, ya que los conjuntos amarillo y azul\ntienen un numero "
			+ "diferente de piezas para adaptarse a la menor cantidad de piezas "
			+ "utilizadas en el juego de cuatro jugadores.\nEl jugador verde va "
			+ "primero. En cada turno un jugador, puede realizar un movimiento o "
			+ "un cambio de direccion.\nEl Comandante se puede desplazar 1 espacio. "
			+ "Las Lanzas se pueden desplazar 1, 2 o 3 espacios. Las Probes 1 o 2 "
			+ "espacios.\nLos Escudos 1 espacio.\n\nCon 4 jugadores 1v1v1v1:\nEl "
			+ "objetivo es ser el ultimo jugador en pie despues de que los demas hayan "
			+ "sido eliminados.\nEn el turno de un jugador, puede realizar un "
			+ "movimiento o un cambio de direccion. Si el Comandante de un jugador es "
			+ "capturado,\nlas piezas restantes quedan bajo el mando del jugador que lo "
			+ "captura. Si todas las piezas de un jugador, excepto el Comandante,\nhan "
			+ "sido capturadas, el Comandante se retira del juego y el jugador queda "
			+ "fuera del juego. El juego continua en el sentido de las\nagujas del reloj "
			+ "hasta que quede un jugador.\n\nCon 4 jugadores 2v2:\nUna vez que el "
			+ "comandante de un jugador es absorbido, su companero de equipo se hace "
			+ "cargo de todas sus piezas restantes.\nEl companero tambien toma el turno "
			+ "de su companero de equipo y puede usar todas las piezas del equipo para "
			+ "sus movimientos.";

	public Controller(Message msg, FileManager fm, Player[] players, PloyGUI gui, PloyBoard board, int gameMode) {
		this.msg = msg;
		this.fm = fm;
		this.players = players;
		this.gui = gui;
		this.board = board;
		this.gameMode = gameMode;
	}
	
	public void startGame(char newGame) {
		if (newGame == 'Y') {
			gui.makeGUI(players, gameMode);
			gui.populateBoard(players, gameMode);
			board.populateBoard(players, gameMode);
			setMenuActions(gameMode);
			setMouseActions();
			setButtonActions();
		} else if (newGame == 'N') {
			int loadStatus = fm.loadFile();
			if (loadStatus == 0) {
				if (gui.ployInterface != null) {
					gui.closeWindow();
				}
				players = fm.getPlayers();
				gameMode = fm.getGameMode();
				gui.makeGUI(players, gameMode);
				board.loadBoard(players, gameMode, fm.getBoardData());
				gui.loadBoard(players, gameMode, fm.getBoardData());
				board.loadHitPiecesIndexes(fm.getHitPiecesIndexes());
				board.loadHitPieces(gameMode, fm.getHitPieces());
				setMenuActions(gameMode);
				setMouseActions();
				setButtonActions();
				gui.showSaveLoadMessage("La partida fue cargada satisfactoriamente", "Partida Cargada");
			} else if (loadStatus == 1) {
				gui.showSaveLoadMessage("Error al cargar la partda, puede que el archivo no exista", "Error");
				if (gui.ployInterface == null) {
					System.exit(0);
				}
			} else {
				gui.showSaveLoadMessage("El proceso de cargado fue cancelado", "Cargado cancelado");
				if (gui.ployInterface == null) {
					System.exit(0);
				}
			}
		}
	}
	
	private void setMenuActions(int gameMode) {
		gui.menuBar.getMenu(0).getItem(0).addActionListener(this);  // Opciones / Reglas
		gui.menuBar.getMenu(0).getItem(1).addActionListener(this);  // Opciones / Guardar Partida
		gui.menuBar.getMenu(0).getItem(2).addActionListener(this);  // Opciones / Cargar Partida
		gui.menuBar.getMenu(1).getItem(0).addActionListener(this);  // Piezas Eliminadas / Jugador 1
		gui.menuBar.getMenu(1).getItem(1).addActionListener(this);  // Piezas Eliminadas / Jugador 2
		if (gameMode != 0) {
			gui.menuBar.getMenu(1).getItem(2).addActionListener(this);  // Piezas Eliminadas / Jugador 3
			gui.menuBar.getMenu(1).getItem(3).addActionListener(this);  // Piezas Eliminadas / Jugador 4
		}
	}
    
	private void setMouseActions() {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				final int tempI = i;
				final int tempJ = j;
				gui.squaresPanels[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
					final int myI = tempI;
					final int myJ = tempJ;
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						clickedOn(myI, myJ, players.length, players);
					}
				});
			}
		}
	}
	
	private void removeMouseActions() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				try {
				gui.squaresPanels[i][j].removeMouseListener(gui.squaresPanels[i][j].getMouseListeners()[0]);
				} catch (Exception e) { }
			}
		}
	}
	
	private void setButtonActions() {
		gui.rotateLeftBut.addActionListener(this);
		gui.rotateRightBut.addActionListener(this);
	}
	
    /**
     * Ejecuta las acciones de los botones.
     */
    public void actionPerformed(ActionEvent evento) {
        if (evento.getActionCommand().equals("Reglas")) {
            msg.printMessageWithTitle(GAME_RULES, "Reglas del juego");
        } else if (evento.getActionCommand().equals("Guardar Partida")) {
        	String fileName = msg.inputMessage("Ingrese el nombre del archivo a guardar");
        	if (fileName != null) {
        		fm.saveFile(players, gameMode, board.getBoardInfo(), fileName);
        		gui.showSaveLoadMessage("La partida fue guardada satisfactoriamente", "Partida guardada");
        	} else {
        		gui.showSaveLoadMessage("El proceso de guardado fue cancelado", "Guardado cancelado");
        	}
        } else if (evento.getActionCommand().equals("Cargar Partida")) {
        	startGame('N');
        } else if (evento.getActionCommand().equals("Jugador 1")) {
        	gui.showHitPieces(board.getBoardInfo().p1HitPieces, board.getBoardInfo().getP1HitPiecesIndex());
        } else if (evento.getActionCommand().equals("Jugador 2")) {
        	gui.showHitPieces(board.getBoardInfo().p2HitPieces, board.getBoardInfo().getP2HitPiecesIndex());
        } else if (evento.getActionCommand().equals("Jugador 3")) {
        	gui.showHitPieces(board.getBoardInfo().p3HitPieces, board.getBoardInfo().getP3HitPiecesIndex());
        } else if (evento.getActionCommand().equals("Jugador 4")) {
        	gui.showHitPieces(board.getBoardInfo().p4HitPieces, board.getBoardInfo().getP4HitPiecesIndex());
        } else if (evento.getActionCommand().equals("Girar izq")) {
        	String[][] moves = getValidMoves(board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ());
			cancelHighlightMoves(moves, board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ());
        	gui.rotatePiece(board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ(), 315);
        	board.getBoardInfo().rotatePiece(board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ(), -45);
        	int lastI = board.getBoardInfo().getLastI();
			int lastJ = board.getBoardInfo().getLastJ();
        	int originalDirection = board.getBoardInfo().getOriginalDirection();
			if (board.getBoardInfo().boardSquares[lastI][lastJ].getDirection() == originalDirection || board.getBoardInfo().boardSquares[lastI][lastJ].getType() == 8) {
				moves = getValidMoves(board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ());
				highlightMoves(moves, board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ());
			}
        } else if (evento.getActionCommand().equals("Girar der")) {
        	String[][] moves = getValidMoves(board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ());
			cancelHighlightMoves(moves, board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ());
        	gui.rotatePiece(board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ(), 45);
        	board.getBoardInfo().rotatePiece(board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ(), 45);
        	int lastI = board.getBoardInfo().getLastI();
			int lastJ = board.getBoardInfo().getLastJ();
        	int originalDirection = board.getBoardInfo().getOriginalDirection();
			if (board.getBoardInfo().boardSquares[lastI][lastJ].getDirection() == originalDirection || board.getBoardInfo().boardSquares[lastI][lastJ].getType() == 8) {
				moves = getValidMoves(board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ());
				highlightMoves(moves, board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ());
			}
        }
    }
    
    public void clickedOn(int i, int j, int numPlayers, Player[] players) {
    	String[][] moves = null;
    	//if (board.getBoardInfo().boardSquares[i][j].getColor().equals(players[board.getBoardInfo().getCurrentPlayer() - 1].getColor()) && !board.getBoardInfo().getPieceActive()) {
  		if (board.getBoardInfo().boardSquares[i][j].getOwner() == board.getBoardInfo().getCurrentPlayer() && !board.getBoardInfo().getPieceActive()) {	
				if (board.getBoardInfo().boardSquares[i][j].getType() != -1) {
					board.getBoardInfo().setPieceActive(true);
		  		gui.squaresPanels[i][j].setBackground(gui.boardColorHighlight);
			  	board.getBoardInfo().setLastI(i);
			  	board.getBoardInfo().setLastJ(j);
				 	int direction = board.getBoardInfo().boardSquares[i][j].getDirection();
				 	board.getBoardInfo().setOriginalDirection(direction);
			 		gui.rotateLeftBut.setEnabled(true);
					gui.rotateRightBut.setEnabled(true);
		  		gui.guiPrintLine("pieza activa");
		  		moves = getValidMoves(i, j);
		  		highlightMoves(moves, i, j);
				}
		} else if (board.getBoardInfo().getPieceActive()) {
			int lastI = board.getBoardInfo().getLastI();
			int lastJ = board.getBoardInfo().getLastJ();
			if (!(lastI == i && lastJ == j)) {
				int originalDirection = board.getBoardInfo().getOriginalDirection();
				if (board.getBoardInfo().boardSquares[lastI][lastJ].getDirection() == originalDirection || board.getBoardInfo().boardSquares[lastI][lastJ].getType() == 8) {
					int targetPieceOwner = board.getBoardInfo().boardSquares[i][j].getOwner();
					if (gui.squaresPanels[i][j].getBackground() == gui.boardColorHighlight) {
						
						moves = getValidMoves(lastI, lastJ);
						cancelHighlightMoves(moves, lastI, lastJ);
						
						if (targetPieceOwner != 0) {
							players[targetPieceOwner - 1].setNumPieces(players[targetPieceOwner - 1].getNumPieces() - 1);
							checkGameOver(board.getBoardInfo().boardSquares[i][j], board.getBoardInfo().boardSquares[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()]);
						}
						
						String targetPieceColor = board.getBoardInfo().boardSquares[i][j].getColor();
						String targetPieceType = Integer.toString(board.getBoardInfo().boardSquares[i][j].getType());
						
						if (numPlayers == 2) {
							if (targetPieceColor.equals(players[0].getColor())) {
								board.getBoardInfo().p1HitPieces[board.getBoardInfo().getP1HitPiecesIndex()][0] = targetPieceType;
								board.getBoardInfo().p1HitPieces[board.getBoardInfo().getP1HitPiecesIndex()][1] = targetPieceColor;
								board.getBoardInfo().setP1HitPiecesIndex(board.getBoardInfo().getP1HitPiecesIndex() + 1);
							} else if (targetPieceColor.equals(players[1].getColor())) {
								board.getBoardInfo().p2HitPieces[board.getBoardInfo().getP2HitPiecesIndex()][0] = targetPieceType;
								board.getBoardInfo().p2HitPieces[board.getBoardInfo().getP2HitPiecesIndex()][1] = targetPieceColor;
								board.getBoardInfo().setP2HitPiecesIndex(board.getBoardInfo().getP2HitPiecesIndex() + 1);
							}
						} else {
							if (targetPieceColor.equals(players[0].getColor())) {
								board.getBoardInfo().p1HitPieces[board.getBoardInfo().getP1HitPiecesIndex()][0] = targetPieceType;
								board.getBoardInfo().p1HitPieces[board.getBoardInfo().getP1HitPiecesIndex()][1] = targetPieceColor;
								board.getBoardInfo().setP1HitPiecesIndex(board.getBoardInfo().getP1HitPiecesIndex() + 1);
							} else if (targetPieceColor.equals(players[1].getColor())) {
								board.getBoardInfo().p2HitPieces[board.getBoardInfo().getP2HitPiecesIndex()][0] = targetPieceType;
								board.getBoardInfo().p2HitPieces[board.getBoardInfo().getP2HitPiecesIndex()][1] = targetPieceColor;
								board.getBoardInfo().setP2HitPiecesIndex(board.getBoardInfo().getP2HitPiecesIndex() + 1);
							} else if (targetPieceColor.equals(players[2].getColor())) {
								board.getBoardInfo().p3HitPieces[board.getBoardInfo().getP3HitPiecesIndex()][0] = targetPieceType;
								board.getBoardInfo().p3HitPieces[board.getBoardInfo().getP3HitPiecesIndex()][1] = targetPieceColor;
								board.getBoardInfo().setP3HitPiecesIndex(board.getBoardInfo().getP3HitPiecesIndex() + 1);
							} else if (targetPieceColor.equals(players[3].getColor())) {
								board.getBoardInfo().p4HitPieces[board.getBoardInfo().getP4HitPiecesIndex()][0] = targetPieceType;
								board.getBoardInfo().p4HitPieces[board.getBoardInfo().getP4HitPiecesIndex()][1] = targetPieceColor;
								board.getBoardInfo().setP4HitPiecesIndex(board.getBoardInfo().getP4HitPiecesIndex() + 1);
							}
						}
							
						gui.squaresPanels[i][j].setIcon(gui.squaresPanels[lastI][lastJ].getIcon());
						gui.squaresPanels[lastI][lastJ].setIcon(null);
						gui.squaresPanels[lastI][lastJ].setBackground(gui.boardColorPurple);
						board.getBoardInfo().boardSquares[i][j].setType(board.getBoardInfo().boardSquares[lastI][lastJ].getType());
						board.getBoardInfo().boardSquares[lastI][lastJ].setType(-1);
						board.getBoardInfo().boardSquares[i][j].setOwner(board.getBoardInfo().boardSquares[lastI][lastJ].getOwner());
						board.getBoardInfo().boardSquares[lastI][lastJ].setOwner(0);
						board.getBoardInfo().boardSquares[i][j].setDirection(board.getBoardInfo().boardSquares[lastI][lastJ].getDirection());
						board.getBoardInfo().boardSquares[lastI][lastJ].setDirection(0);
						board.getBoardInfo().boardSquares[i][j].setColor(board.getBoardInfo().boardSquares[lastI][lastJ].getColor());
						board.getBoardInfo().boardSquares[lastI][lastJ].setColor("-");
						board.getBoardInfo().setPieceActive(false);
						gui.rotateLeftBut.setEnabled(false);
						gui.rotateRightBut.setEnabled(false);
						gui.guiPrintLine("Pieza movida");
						board.getBoardInfo().setCurrentPlayer(board.getBoardInfo().getCurrentPlayer() + 1);
						if (numPlayers == 2) {
							if (board.getBoardInfo().getCurrentPlayer() == 3) {
								board.getBoardInfo().setCurrentPlayer(board.getBoardInfo().getCurrentPlayer() - 2);
							}
						} else {
							if (board.getBoardInfo().getCurrentPlayer() == 5) {
								board.getBoardInfo().setCurrentPlayer(board.getBoardInfo().getCurrentPlayer() - 4);
							}
							while (players[board.getBoardInfo().getCurrentPlayer() - 1].getLost() == true) {
								board.getBoardInfo().setCurrentPlayer(board.getBoardInfo().getCurrentPlayer() + 1);
								if (board.getBoardInfo().getCurrentPlayer() == 5) {
									board.getBoardInfo().setCurrentPlayer(board.getBoardInfo().getCurrentPlayer() - 4);
								}
							}
						}
					} else {
						gui.guiPrintLine("Movimiento no permitido");
					}
				} else {
					gui.guiPrintLine("Pieza rotada, imposible mover");
				}
			} else {
				gui.squaresPanels[lastI][lastJ].setBackground(gui.boardColorPurple);
				board.getBoardInfo().setPieceActive(false);
				gui.rotateLeftBut.setEnabled(false);
				gui.rotateRightBut.setEnabled(false);
				if (board.getBoardInfo().boardSquares[i][j].getDirection() != board.getBoardInfo().getOriginalDirection()) {
					gui.guiPrintLine("Pieza rotada");
					board.getBoardInfo().setCurrentPlayer(board.getBoardInfo().getCurrentPlayer() + 1);
					if (numPlayers == 2) {
						if (board.getBoardInfo().getCurrentPlayer() == 3) {
							board.getBoardInfo().setCurrentPlayer(board.getBoardInfo().getCurrentPlayer() - 2);
						}
					} else {
						if (board.getBoardInfo().getCurrentPlayer() == 5) {
							board.getBoardInfo().setCurrentPlayer(board.getBoardInfo().getCurrentPlayer() - 4);
						}
						while (players[board.getBoardInfo().getCurrentPlayer() - 1].getLost() == true) {
							board.getBoardInfo().setCurrentPlayer(board.getBoardInfo().getCurrentPlayer() + 1);
							if (board.getBoardInfo().getCurrentPlayer() == 5) {
								board.getBoardInfo().setCurrentPlayer(board.getBoardInfo().getCurrentPlayer() - 4);
							}
						}
					}
				} else {
					gui.guiPrintLine("Pieza no movida");
				}
				moves = getValidMoves(board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ());
				cancelHighlightMoves(moves, board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ());
			}
    	} else {
    		gui.guiPrintLine("Pieza no pertenece al jugador");
    	}
	}
    
    private void checkGameOver(BoardSquareInfo hitInfo, BoardSquareInfo attackerInfo) {
    	if(hitInfo.getType() == 0 || players[hitInfo.getOwner()-1].getNumPieces() == 1) {
    		playerLost(hitInfo, attackerInfo, this.gameMode);
    	}
    }
    
    private static char finished(Message msg) {
		String[] options = {"Mirar Tablero", "Terminar"};
		char newGame = ' ';
    	int input = 0;
	    input = msg.inputMessageWithOptions("Seleccione lo que desea hacer", "Menu Gamer Over", options);
	    if (input == 0) {
	    	newGame = 'Y';
	    } else if (input == 1) {
	    	System.exit(0);
	    }
	    
	    return newGame;
	}
    
    private void playerLost(BoardSquareInfo hitInfo, BoardSquareInfo attackerInfo, int gameMode) {
        switch(gameMode) {
        case 0: //1v1
        	players[hitInfo.getOwner()-1].setLost(true);
        	gui.guiPrintLine("Game Over, tablero bloqueado"); 
        	msg.printSimpleMessage("Game Over \n" + players[attackerInfo.getOwner()-1].getName() + " ha ganado");
        	char finishedGame = finished(msg);
        	if (finishedGame == 'Y' ) {
        		removeMouseActions();
        	} else {
        		System.exit(0);
        	}
        	break;
        case 1: //1v1v1v1
            players[hitInfo.getOwner()-1].setLost(true);
            board.getBoardInfo().setActivePlayers(board.getBoardInfo().getActivePlayers()-1);
            if (board.getBoardInfo().getActivePlayers() == 1) {
	            removeMouseActions();
	            msg.printSimpleMessage("Game Over \n" + players[attackerInfo.getOwner()-1].getName() + " ha ganado");
	            gui.guiPrintLine("Game Over 1v1v1v1, tablero bloqueado");
	            char finishedGame1 = finished(msg);
	            if (finishedGame1 == 'Y' ) {
	            	removeMouseActions();
	            } else {
	            	System.exit(0);
	            }
            } else {
            	board.getBoardInfo().updateOwner(hitInfo.getOwner(), attackerInfo.getOwner()); //jugador controla las piezas ahora
                msg.printSimpleMessage(players[hitInfo.getOwner()-1].getName() + " ha perdido, ahora "
            	+ players[hitInfo.getOwner()-1].getName() + " controla sus piezas.");
            }
        	break;
        case 2: //2v2
        	players[hitInfo.getOwner()-1].setLost(true);
            int friend = players[(hitInfo.getOwner()-1)].getFriend()-1;
            if (players[friend].getLost()) { //si los dos ya perdieron
              removeMouseActions();
              msg.printSimpleMessage("Game Over \n" + players[attackerInfo.getOwner()-1].getName()
            		+ " y " + players[players[attackerInfo.getOwner()-1].getFriend()-1].getName() + " han ganado");
              gui.guiPrintLine("Game Over 2v2, tablero bloqueado");
              char finishedGame3 = finished(msg);
              if (finishedGame3 == 'Y' ) {
               	removeMouseActions();
              } else {
            	System.exit(0);
              }
	        } else {
	        	board.getBoardInfo().updateOwner(hitInfo.getOwner(), players[hitInfo.getOwner()-1].getFriend()); //amigo controla las piezas ahora
	        	board.getBoardInfo().setActivePlayers(board.getBoardInfo().getActivePlayers()-1);
            msg.printSimpleMessage(players[hitInfo.getOwner()-1].getName() + " ha perdido");
	      }
          break;
        }
    }
    
    /*
     * Valid moves
     * 
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|O|-|-|-|
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|O|-|-|-|
     * |-|-|O|-|O|-|-|				|-|-|-|-|-|-|-|				|-|-|-|O|-|-|-|
     * |-|-|-|P|-|-|-| commander	|O|O|O|P|O|O|O| lance 1		|-|-|-|P|-|-|-| lance 2
     * |-|-|O|-|O|-|-|				|-|-|-|O|-|-|-|				|-|-|O|-|O|-|-|
     * |-|-|-|-|-|-|-|				|-|-|-|O|-|-|-|				|-|O|-|-|-|O|-|
     * |-|-|-|-|-|-|-|				|-|-|-|O|-|-|-|				|O|-|-|-|-|-|O|
     * 
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * |-|-|-|P|-|-|-| lance 3		|-|-|-|P|-|-|-| probe 1		|-|-|-|P|-|-|-| probe 2
     * |-|-|O|O|O|-|-|				|-|-|O|O|-|-|-|				|-|-|0|-|0|-|-|
     * |-|O|-|O|-|O|-|				|-|O|-|O|-|-|-|				|-|0|-|-|-|0|-|
     * |O|-|-|O|-|-|O|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * 
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * |-|-|-|O|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * |-|-|-|O|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * |-|-|-|P|-|-|-| probe 3		|-|-|-|P|-|-|-| probe 4		|-|-|-|P|-|-|-| shield
     * |-|-|-|O|-|-|-|				|-|-|-|0|0|-|-|				|-|-|-|0|-|-|-|
     * |-|-|-|O|-|-|-|				|-|-|-|0|-|0|-|				|-|-|-|-|-|-|-|
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     */
    public String[][] getValidMoves(int i, int j) {
		String[][] moves = new String[7][7];
		moves[3][3] = "O";
		int type = board.getBoardInfo().boardSquares[i][j].getType();
		if (type == 0) {
			moves[2][2] = "O";
			moves[2][4] = "O";
			moves[4][2] = "O";
			moves[4][4] = "O";			
		} else if (type == 1) {
			moves[3][2] = "O";
			moves[3][1] = "O";
			moves[3][0] = "O";
			moves[4][3] = "O";
			moves[5][3] = "O";
			moves[6][3] = "O";
			moves[3][4] = "O";
			moves[3][5] = "O";
			moves[3][6] = "O";
		} else if (type == 2) {
			moves[2][3] = "O";
			moves[1][3] = "O";
			moves[0][3] = "O";
			moves[4][2] = "O";
			moves[5][1] = "O";
			moves[6][0] = "O";
			moves[4][4] = "O";
			moves[5][5] = "O";
			moves[6][6] = "O";
		} else if (type == 3) {
			moves[4][2] = "O";
			moves[5][1] = "O";
			moves[6][0] = "O";
			moves[4][3] = "O";
			moves[5][3] = "O";
			moves[6][3] = "O";
			moves[4][4] = "O";
			moves[5][5] = "O";
			moves[6][6] = "O";
		} else if (type == 4) {
			moves[4][2] = "O";
			moves[5][1] = "O";
			moves[4][3] = "O";
			moves[5][3] = "O";
		} else if (type == 5) {
			moves[4][2] = "O";
			moves[5][1] = "O";
			moves[4][4] = "O";
			moves[5][5] = "O";
		} else if (type == 6) {
			moves[2][3] = "O";
			moves[1][3] = "O";
			moves[4][3] = "O";
			moves[5][3] = "O";
		} else if (type == 7) {
			moves[4][3] = "O";
			moves[5][3] = "O";
			moves[4][4] = "O";
			moves[5][5] = "O";
		} else if (type == 8) {
			moves[4][3] = "O";
		}
		moves = rotateMoves(moves, i, j);
		
		// Exclude other pieces
		// Allies
		
		// -1 | +1
		if (i - 1 >= 0 && j - 1 >= 0) {
			if (board.getBoardInfo().boardSquares[i - 1][j - 1].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[2][2] = "-";
				moves[1][1] = "-";
				moves[0][0] = "-";
			}
		}
		if (i - 1 >= 0) {
			if (board.getBoardInfo().boardSquares[i - 1][j].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[2][3] = "-";
				moves[1][3] = "-";
				moves[0][3] = "-";
			}
		}
		if (i - 1 >= 0 && j + 1 <= 8) {
			if (board.getBoardInfo().boardSquares[i - 1][j + 1].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[2][4] = "-";
				moves[1][5] = "-";
				moves[0][6] = "-";
			}
		}
		if (j - 1 >= 0) {
			if (board.getBoardInfo().boardSquares[i][j - 1].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[3][2] = "-";
				moves[3][1] = "-";
				moves[3][0] = "-";
			}
		}
		if (j + 1 <= 8) {
			if (board.getBoardInfo().boardSquares[i][j + 1].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[3][4] = "-";
				moves[3][5] = "-";
				moves[3][6] = "-";
			}
		}
		if (i + 1 <= 8 && j - 1 >= 0) {
			if (board.getBoardInfo().boardSquares[i + 1][j - 1].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[4][2] = "-";
				moves[5][1] = "-";
				moves[6][0] = "-";
			}
		}
		if (i + 1 <= 8) {
			if (board.getBoardInfo().boardSquares[i + 1][j].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[4][3] = "-";
				moves[5][3] = "-";
				moves[6][3] = "-";
			}
		}
		if (i + 1 <= 8 && j + 1 <= 8) {
			if (board.getBoardInfo().boardSquares[i + 1][j + 1].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[4][4] = "-";
				moves[5][5] = "-";
				moves[6][6] = "-";
			}
		}
		
		// -2 | +2
		if (i - 2 >= 0 && j - 2 >= 0) {
			if (board.getBoardInfo().boardSquares[i - 2][j - 2].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[1][1] = "-";
				moves[0][0] = "-";
			}
		}
		if (i - 2 >= 0) {
			if (board.getBoardInfo().boardSquares[i - 2][j].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[1][3] = "-";
				moves[0][3] = "-";
			}
		}
		if (i - 2 >= 0 && j + 2 <= 8) {
			if (board.getBoardInfo().boardSquares[i - 2][j + 2].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[1][5] = "-";
				moves[0][6] = "-";
			}
		}
		if (j - 2 >= 0) {
			if (board.getBoardInfo().boardSquares[i][j - 2].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[3][1] = "-";
				moves[3][0] = "-";
			}
		}
		if (j + 2 <= 8) {
			if (board.getBoardInfo().boardSquares[i][j + 2].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[3][5] = "-";
				moves[3][6] = "-";
			}
		}
		if (i + 2 <= 8 && j - 2 >= 0) {
			if (board.getBoardInfo().boardSquares[i + 2][j - 2].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[5][1] = "-";
				moves[6][0] = "-";
			}
		}
		if (i + 2 <= 8) {
			if (board.getBoardInfo().boardSquares[i + 2][j].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[5][3] = "-";
				moves[6][3] = "-";
			}
		}
		if (i + 2 <= 8 && j + 2 <= 8) {
			if (board.getBoardInfo().boardSquares[i + 2][j + 2].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[5][5] = "-";
				moves[6][6] = "-";
			}
		}
		
		// -3 | +3
		if (i - 3 >= 0 && j - 3 >= 0) {
			if (board.getBoardInfo().boardSquares[i - 3][j - 3].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[0][0] = "-";
			}
		}
		if (i - 3 >= 0) {
			if (board.getBoardInfo().boardSquares[i - 3][j].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[0][3] = "-";
			}
		}
		if (i - 3 >= 0 && j + 3 <= 8) {
			if (board.getBoardInfo().boardSquares[i - 3][j + 3].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[0][6] = "-";
			}
		}
		if (j - 3 >= 0) {
			if (board.getBoardInfo().boardSquares[i][j - 3].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[3][0] = "-";
			}
		}
		if (j + 3 <= 8) {
			if (board.getBoardInfo().boardSquares[i][j + 3].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[3][6] = "-";
			}
		}
		if (i + 3 <= 8 && j - 3 >= 0) {
			if (board.getBoardInfo().boardSquares[i + 3][j - 3].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[6][0] = "-";
			}
		}
		if (i + 3 <= 8) {
			if (board.getBoardInfo().boardSquares[i + 3][j].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[6][3] = "-";
			}
		}
		if (i + 3 <= 8 && j + 3 <= 8) {
			if (board.getBoardInfo().boardSquares[i + 3][j + 3].getOwner() == board.getBoardInfo().boardSquares[i][j].getOwner()) {
				moves[6][6] = "-";
			}
		}
		
		// Enemies
		
		// -2 | +2
		if (i - 2 >= 0 && j - 2 >= 0) {
			if (board.getBoardInfo().boardSquares[i - 2][j - 2].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i - 2][j - 2].getOwner() != 0) {
				if (moves[1][1] == "O") {
					moves[0][0] = "-";
				}
			}
		}
		if (i - 2 >= 0) {
			if (board.getBoardInfo().boardSquares[i - 2][j].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i - 2][j].getOwner() != 0) {
				if (moves[1][3] == "O") {
					moves[0][3] = "-";
				}
			}
		}
		if (i - 2 >= 0 && j + 2 <= 8) {
			if (board.getBoardInfo().boardSquares[i - 2][j + 2].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i - 2][j + 2].getOwner() != 0) {
				if (moves[1][5] == "O") {
					moves[0][6] = "-";
				}
			}
		}
		if (j - 2 >= 0) {
			if (board.getBoardInfo().boardSquares[i][j - 2].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i][j - 2].getOwner() != 0) {
				if (moves[3][1] == "O") {
					moves[3][0] = "-";
				}
			}
		}
		if (j + 2 <= 8) {
			if (board.getBoardInfo().boardSquares[i][j + 2].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i][j + 2].getOwner() != 0) {
				if (moves[3][5] == "O") {
					moves[3][6] = "-";
				}
			}
		}
		if (i + 2 <= 8 && j - 2 >= 0) {
			if (board.getBoardInfo().boardSquares[i + 2][j - 2].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i + 2][j - 2].getOwner() != 0) {
				if (moves[5][1] == "O") {
					moves[6][0] = "-";
				}
			}
		}
		if (i + 2 <= 8) {
			if (board.getBoardInfo().boardSquares[i + 2][j].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i + 2][j].getOwner() != 0) {
				if (moves[5][3] == "O") {
					moves[6][3] = "-";
				}
			}
		}
		if (i + 2 <= 8 && j + 2 <= 8) {
			if (board.getBoardInfo().boardSquares[i + 2][j + 2].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i + 2][j + 2].getOwner() != 0) {
				if (moves[5][5] == "O") {
					moves[6][6] = "-";
				}
			}
		}
		
		// -1 | +1
		if (i - 1 >= 0 && j - 1 >= 0) {
			if (board.getBoardInfo().boardSquares[i - 1][j - 1].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i - 1][j - 1].getOwner() != 0) {
				if (moves[2][2] == "O") {
					moves[1][1] = "-";
					moves[0][0] = "-";
				}
			}
		}
		if (i - 1 >= 0) {
			if (board.getBoardInfo().boardSquares[i - 1][j].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i - 1][j].getOwner() != 0) {
				if (moves[2][3] == "O") {
					moves[1][3] = "-";
					moves[0][3] = "-";
				}
			}
		}
		if (i - 1 >= 0 && j + 1 <= 8) {
			if (board.getBoardInfo().boardSquares[i - 1][j + 1].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i - 1][j + 1].getOwner() != 0) {
				if (moves[2][4] == "O") {
					moves[1][5] = "-";
					moves[0][6] = "-";
				}
			}
		}
		if (j - 1 >= 0) {
			if (board.getBoardInfo().boardSquares[i][j - 1].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i][j - 1].getOwner() != 0) {
				if (moves[3][2] == "O") {
					moves[3][1] = "-";
					moves[3][0] = "-";
				}
			}
		}
		if (j + 1 <= 8) {
			if (board.getBoardInfo().boardSquares[i][j + 1].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i][j + 1].getOwner() != 0) {
				if (moves[3][4] == "O") {
					moves[3][5] = "-";
					moves[3][6] = "-";
				}
			}
		}
		if (i + 1 <= 8 && j - 1 >= 0) {
			if (board.getBoardInfo().boardSquares[i + 1][j - 1].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i + 1][j - 1].getOwner() != 0) {
				if (moves[4][2] == "O") {
					moves[5][1] = "-";
					moves[6][0] = "-";
				}
			}
		}
		if (i + 1 <= 8) {
			if (board.getBoardInfo().boardSquares[i + 1][j].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i + 1][j].getOwner() != 0) {
				if (moves[4][3] == "O") {
					moves[5][3] = "-";
					moves[6][3] = "-";
				}
			}
		}
		if (i + 1 <= 8 && j + 1 <= 8) {
			if (board.getBoardInfo().boardSquares[i + 1][j + 1].getOwner() != board.getBoardInfo().boardSquares[i][j].getOwner() && board.getBoardInfo().boardSquares[i + 1][j + 1].getOwner() != 0) {
				if (moves[4][4] == "O") {
					moves[5][5] = "-";
					moves[6][6] = "-";
				}
			}
		}
		
		return moves;
    }
    
    public String[][] rotateMoves(String[][] moves, int i, int j) {
    	String[][] rotatedMoves = new String[7][7];
		int direction = board.getBoardInfo().boardSquares[i][j].getDirection();
		while (direction > 0) {
			for (int x = 0; x < 7; x++) {
				for (int y = 0; y < 7; y++) {
					if (moves[x][y] != null) {
						if (x == 0) {
							if (y < 4) {
								rotatedMoves[x][y + 3] = moves[x][y];
							} else if (y == 4) {
								rotatedMoves[x + 1][y + 2] = moves[x][y];
							} else if (y == 5) {
								rotatedMoves[x + 2][y + 1] = moves[x][y];
							} else if (y == 6) {
								rotatedMoves[x + 3][y] = moves[x][y];
							}
						} else if (x == 1) {
							if (y < 4) {
								rotatedMoves[x][y + 2] = moves[x][y];
							} else if (y == 4) {
								rotatedMoves[x + 1][y + 1] = moves[x][y];
							} else if (y == 5) {
								rotatedMoves[x + 2][y] = moves[x][y];
							}
						} else if (x == 2) {
							if (y < 4) {
								rotatedMoves[x][y + 1] = moves[x][y];
							} else if (y == 4) {
								rotatedMoves[x + 1][y] = moves[x][y];
							}
						} else if (x == 3) {
							if (y == 4) {
								rotatedMoves[x + 1][y] = moves[x][y];
							} else if (y == 5) {
								rotatedMoves[x + 2][y] = moves[x][y];
							} else if (y == 6) {
								rotatedMoves[x + 3][y] = moves[x][y];
							} else if (y == 2) {
								rotatedMoves[x - 1][y] = moves[x][y];
							} else if (y == 1) {
								rotatedMoves[x - 2][y] = moves[x][y];
							} else if (y == 0) {
								rotatedMoves[x - 3][y] = moves[x][y];
							} else {
								rotatedMoves[x][y] = moves[x][y];
							}
						} else if (x == 4) {
							if (y > 2) {
								rotatedMoves[x][y - 1] = moves[x][y];
							} else if (y == 2) {
								rotatedMoves[x - 1][y] = moves[x][y];
							}
						} else if (x == 5) {
							if (y > 2) {
								rotatedMoves[x][y - 2] = moves[x][y];
							} else if (y == 2) {
								rotatedMoves[x - 1][y - 1] = moves[x][y];
							} else if (y == 1) {
								rotatedMoves[x - 2][y] = moves[x][y];
							}
						} else if (x == 6) {
							if (y > 2) {
								rotatedMoves[x][y - 3] = moves[x][y];
							} else if (y == 2) {
								rotatedMoves[x - 1][y - 2] = moves[x][y];
							} else if (y == 1) {
								rotatedMoves[x - 2][y - 1] = moves[x][y];
							} else if (y == 0) {
								rotatedMoves[x - 3][y] = moves[x][y];
							}
						}
					}
				}
			}
			moves = rotatedMoves;			
			rotatedMoves = new String[7][7];
			direction = direction - 45;
		}
    	return moves;
    }
    
    private void highlightMoves(String[][] moves, int i, int j) {
    	
    	for (int x = 0; x < 7; x++) {
			for (int y = 0; y < 7; y++) {
				try {
					if (moves[x][y] == "O") {
						gui.squaresPanels[(i - 3) + x][(j - 3) + y].setBackground(gui.boardColorHighlight);
					}
				} catch (Exception e) { }
			}
		}
    }
    
    private void cancelHighlightMoves(String[][] moves, int i, int j) {
    	for (int x = 0; x < 7; x++) {
			for (int y = 0; y < 7; y++) {
				if (!(x == 3 && y == 3)) {
					try {
						gui.squaresPanels[(i - 3) + x][(j - 3) + y].setBackground(gui.boardColorPurple);
					} catch (Exception e) { }
				}
			}
		}
    }
}
