import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

	private Message msg;
	private FileManager fm;
	private Player[] players;
	private PloyGUI gui;
	private PloyBoard board;
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

	public Controller(Message msg, FileManager fm, Player[] players, PloyGUI gui, PloyBoard board) {
        this.msg = msg;
        this.fm = fm;
        this.players = players;
		this.gui = gui;
        this.board = board;
    }
	
	public void startGame(char newGame, int gameMode) {
		gui.makeGUI(players, gameMode);
		
		if (newGame == 'Y') {
			gui.populateBoard(players, gameMode);
			board.populateBoard(players, gameMode);
		} else if (newGame == 'N') {
			
		}
		
		setMenuActions(gameMode);
		setMouseActions();
		setButtonActions();
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
        	fm.saveFile();
        } else if (evento.getActionCommand().equals("Cargar Partida")) {
        	fm.loadFile();
        } else if (evento.getActionCommand().equals("Jugador 1")) {
        	gui.showHitPieces("p1", board.getBoardInfo().getP1HitPiecesIndex());
        } else if (evento.getActionCommand().equals("Jugador 2")) {
        	gui.showHitPieces("p2", board.getBoardInfo().getP2HitPiecesIndex());
        } else if (evento.getActionCommand().equals("Jugador 3")) {
        	gui.showHitPieces("p3", board.getBoardInfo().getP3HitPiecesIndex());
        } else if (evento.getActionCommand().equals("Jugador 4")) {
        	gui.showHitPieces("p4", board.getBoardInfo().getP4HitPiecesIndex());
        } else if (evento.getActionCommand().equals("Girar izq")) {
        	gui.rotatePiece(board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ(), 315);
        	board.rotatePiece(board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ(), -45);
        } else if (evento.getActionCommand().equals("Girar der")) {
        	gui.rotatePiece(board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ(), 45);
        	board.rotatePiece(board.getBoardInfo().getLastI(), board.getBoardInfo().getLastJ(), 45);
        }
    }
    
    public void clickedOn(int i, int j, int numPlayers, Player[] players) {
		if (!board.getBoardInfo().getPieceActive()) {
			if (board.getBoardInfo().boardSquares[i][j].getType() != -1) {
				board.getBoardInfo().setPieceActive(true);
		  		gui.squaresPanels[i][j].setBackground(gui.boardColorHighlight);
		  		board.getBoardInfo().setLastI(i);
		  		board.getBoardInfo().setLastJ(j);
		  		board.getBoardInfo().setOriginalDirection(board.getBoardInfo().boardSquares[i][j].getDirection());
		  		gui.rotateLeftBut.setEnabled(true);
				gui.rotateRightBut.setEnabled(true);
		  		gui.guiPrintLine("pieza activa");
			}
		} else {
			if (!(board.getBoardInfo().getLastI() == i && board.getBoardInfo().getLastJ() == j)) {
				if (board.getBoardInfo().boardSquares[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].getDirection() == board.getBoardInfo().getOriginalDirection() || board.getBoardInfo().boardSquares[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].getType() == 8) {
					if (board.getBoardInfo().boardSquares[i][j].getOwner() != board.getBoardInfo().boardSquares[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].getOwner()) {
						if (numPlayers == 2) {
							if (board.getBoardInfo().boardSquares[i][j].getColor() == players[0].getColor()) {
								gui.P1HitPieces[board.getBoardInfo().getP1HitPiecesIndex()].setIcon(gui.squaresPanels[i][j].getIcon());
								board.getBoardInfo().setP1HitPiecesIndex(board.getBoardInfo().getP1HitPiecesIndex() + 1);
							} else if (board.getBoardInfo().boardSquares[i][j].getColor() == players[1].getColor()) {
								gui.P2HitPieces[board.getBoardInfo().getP2HitPiecesIndex()].setIcon(gui.squaresPanels[i][j].getIcon());
								board.getBoardInfo().setP2HitPiecesIndex(board.getBoardInfo().getP2HitPiecesIndex() + 1);
							}
						} else {
							if (board.getBoardInfo().boardSquares[i][j].getColor() == players[0].getColor()) {
								gui.P1HitPieces[board.getBoardInfo().getP1HitPiecesIndex()].setIcon(gui.squaresPanels[i][j].getIcon());
								board.getBoardInfo().setP1HitPiecesIndex(board.getBoardInfo().getP1HitPiecesIndex() + 1);
							} else if (board.getBoardInfo().boardSquares[i][j].getColor() == players[1].getColor()) {
								gui.P2HitPieces[board.getBoardInfo().getP2HitPiecesIndex()].setIcon(gui.squaresPanels[i][j].getIcon());
								board.getBoardInfo().setP2HitPiecesIndex(board.getBoardInfo().getP2HitPiecesIndex() + 1);
							} else if (board.getBoardInfo().boardSquares[i][j].getColor() == players[2].getColor()) {
								gui.P3HitPieces[board.getBoardInfo().getP3HitPiecesIndex()].setIcon(gui.squaresPanels[i][j].getIcon());
								board.getBoardInfo().setP3HitPiecesIndex(board.getBoardInfo().getP3HitPiecesIndex() + 1);
							} else if (board.getBoardInfo().boardSquares[i][j].getColor() == players[3].getColor()) {
								gui.P4HitPieces[board.getBoardInfo().getP4HitPiecesIndex()].setIcon(gui.squaresPanels[i][j].getIcon());
								board.getBoardInfo().setP4HitPiecesIndex(board.getBoardInfo().getP4HitPiecesIndex() + 1);
							}
						}
						gui.squaresPanels[i][j].setIcon(gui.squaresPanels[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].getIcon());
						gui.squaresPanels[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].setIcon(null);
						gui.squaresPanels[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].setBackground(gui.boardColorPurple);
						board.getBoardInfo().boardSquares[i][j].setType(board.getBoardInfo().boardSquares[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].getType());
						board.getBoardInfo().boardSquares[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].setType(-1);
						board.getBoardInfo().boardSquares[i][j].setOwner(board.getBoardInfo().boardSquares[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].getOwner());
						board.getBoardInfo().boardSquares[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].setOwner(0);
						board.getBoardInfo().boardSquares[i][j].setDirection(board.getBoardInfo().boardSquares[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].getDirection());
						board.getBoardInfo().boardSquares[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].setDirection(0);
						board.getBoardInfo().boardSquares[i][j].setColor(board.getBoardInfo().boardSquares[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].getColor());
						board.getBoardInfo().boardSquares[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].setColor("");
						board.getBoardInfo().setPieceActive(false);
						gui.rotateLeftBut.setEnabled(false);
						gui.rotateRightBut.setEnabled(false);
						gui.guiPrintLine("pieza movida");
					} else {
						gui.guiPrintLine("pieza pertenece al jugador");
					}
				} else {
					gui.guiPrintLine("pieza rotada, imposible mover");
				}
			} else {
				gui.squaresPanels[board.getBoardInfo().getLastI()][board.getBoardInfo().getLastJ()].setBackground(gui.boardColorPurple);
				board.getBoardInfo().setPieceActive(false);
				gui.rotateLeftBut.setEnabled(false);
				gui.rotateRightBut.setEnabled(false);
				if (board.getBoardInfo().boardSquares[i][j].getDirection() != board.getBoardInfo().getOriginalDirection()) {
					gui.guiPrintLine("pieza rotada");
				} else {
					gui.guiPrintLine("pieza no movida");
				}
			}
		}
	}
}
