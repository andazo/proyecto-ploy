import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

	private Message msg;
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

	public Controller(Message msg, Player[] players, PloyGUI gui, PloyBoard board) {
        this.msg = msg;
        this.players = players;
		this.gui = gui;
        this.board = board;
    }
	
	public void startGame(int gameMode) {
		gui.makeGUI(this, players, gameMode);
		gui.populateBoard(players, gameMode);
		board.populateBoard(players, gameMode);
	}

    /**
     * Ejecuta las acciones de los botones.
     */
    public void actionPerformed(ActionEvent evento) {
        if (evento.getActionCommand().equals("Reglas")) {
            msg.printMessageWithTitle(GAME_RULES, "Reglas del juego");
        } else if (evento.getActionCommand().equals("Jugador 1")) {
        	gui.showHitPieces("p1", board.getBoard().getP1HitPiecesIndex());
        } else if (evento.getActionCommand().equals("Jugador 2")) {
        	gui.showHitPieces("p2", board.getBoard().getP2HitPiecesIndex());
        } else if (evento.getActionCommand().equals("Jugador 3")) {
        	gui.showHitPieces("p3", board.getBoard().getP3HitPiecesIndex());
        } else if (evento.getActionCommand().equals("Jugador 4")) {
        	gui.showHitPieces("p4", board.getBoard().getP4HitPiecesIndex());
        } else if (evento.getActionCommand().equals("Girar izq")) {
        	gui.rotatePiece(board.getBoard().getLastI(), board.getBoard().getLastJ(), 315);
        	board.rotatePiece(board.getBoard().getLastI(), board.getBoard().getLastJ(), -45);
        } else if (evento.getActionCommand().equals("Girar der")) {
        	gui.rotatePiece(board.getBoard().getLastI(), board.getBoard().getLastJ(), 45);
        	board.rotatePiece(board.getBoard().getLastI(), board.getBoard().getLastJ(), 45);
        }
    }
    
    public void clickedOn(int i, int j, int numPlayers, Player[] players) {
		if (!board.getBoard().getPieceActive()) {
			if (board.getBoard().boardSquares[i][j].getType() != -1) {
				board.getBoard().setPieceActive(true);
		  		gui.squaresPanels[i][j].setBackground(gui.boardColorHighlight);
		  		board.getBoard().setLastI(i);
		  		board.getBoard().setLastJ(j);
		  		board.getBoard().setOriginalDirection(board.getBoard().boardSquares[i][j].getDirection());
		  		gui.rotateLeftBut.setEnabled(true);
				gui.rotateRightBut.setEnabled(true);
		  		gui.guiPrintLine("pieza activa");
			}
		} else {
			if (!(board.getBoard().getLastI() == i && board.getBoard().getLastJ() == j)) {
				if (board.getBoard().boardSquares[board.getBoard().getLastI()][board.getBoard().getLastJ()].getDirection() == board.getBoard().getOriginalDirection() || board.getBoard().boardSquares[board.getBoard().getLastI()][board.getBoard().getLastJ()].getType() == 8) {
					if (board.getBoard().boardSquares[i][j].getOwner() != board.getBoard().boardSquares[board.getBoard().getLastI()][board.getBoard().getLastJ()].getOwner()) {
						if (numPlayers == 2) {
							if (board.getBoard().boardSquares[i][j].getColor() == players[0].getColor()) {
								gui.P1HitPieces[board.getBoard().getP1HitPiecesIndex()].setIcon(gui.squaresPanels[i][j].getIcon());
								board.getBoard().setP1HitPiecesIndex(board.getBoard().getP1HitPiecesIndex() + 1);
							} else if (board.getBoard().boardSquares[i][j].getColor() == players[1].getColor()) {
								gui.P2HitPieces[board.getBoard().getP2HitPiecesIndex()].setIcon(gui.squaresPanels[i][j].getIcon());
								board.getBoard().setP2HitPiecesIndex(board.getBoard().getP2HitPiecesIndex() + 1);
							}
						} else {
							if (board.getBoard().boardSquares[i][j].getColor() == players[0].getColor()) {
								gui.P1HitPieces[board.getBoard().getP1HitPiecesIndex()].setIcon(gui.squaresPanels[i][j].getIcon());
								board.getBoard().setP1HitPiecesIndex(board.getBoard().getP1HitPiecesIndex() + 1);
							} else if (board.getBoard().boardSquares[i][j].getColor() == players[1].getColor()) {
								gui.P2HitPieces[board.getBoard().getP2HitPiecesIndex()].setIcon(gui.squaresPanels[i][j].getIcon());
								board.getBoard().setP2HitPiecesIndex(board.getBoard().getP2HitPiecesIndex() + 1);
							} else if (board.getBoard().boardSquares[i][j].getColor() == players[2].getColor()) {
								gui.P3HitPieces[board.getBoard().getP3HitPiecesIndex()].setIcon(gui.squaresPanels[i][j].getIcon());
								board.getBoard().setP3HitPiecesIndex(board.getBoard().getP3HitPiecesIndex() + 1);
							} else if (board.getBoard().boardSquares[i][j].getColor() == players[3].getColor()) {
								gui.P4HitPieces[board.getBoard().getP4HitPiecesIndex()].setIcon(gui.squaresPanels[i][j].getIcon());
								board.getBoard().setP4HitPiecesIndex(board.getBoard().getP4HitPiecesIndex() + 1);
							}
						}
						gui.squaresPanels[i][j].setIcon(gui.squaresPanels[board.getBoard().getLastI()][board.getBoard().getLastJ()].getIcon());
						gui.squaresPanels[board.getBoard().getLastI()][board.getBoard().getLastJ()].setIcon(null);
						gui.squaresPanels[board.getBoard().getLastI()][board.getBoard().getLastJ()].setBackground(gui.boardColorPurple);
						board.getBoard().boardSquares[i][j].setType(board.getBoard().boardSquares[board.getBoard().getLastI()][board.getBoard().getLastJ()].getType());
						board.getBoard().boardSquares[board.getBoard().getLastI()][board.getBoard().getLastJ()].setType(-1);
						board.getBoard().boardSquares[i][j].setOwner(board.getBoard().boardSquares[board.getBoard().getLastI()][board.getBoard().getLastJ()].getOwner());
						board.getBoard().boardSquares[board.getBoard().getLastI()][board.getBoard().getLastJ()].setOwner(0);
						board.getBoard().boardSquares[i][j].setDirection(board.getBoard().boardSquares[board.getBoard().getLastI()][board.getBoard().getLastJ()].getDirection());
						board.getBoard().boardSquares[board.getBoard().getLastI()][board.getBoard().getLastJ()].setDirection(0);
						board.getBoard().boardSquares[i][j].setColor(board.getBoard().boardSquares[board.getBoard().getLastI()][board.getBoard().getLastJ()].getColor());
						board.getBoard().boardSquares[board.getBoard().getLastI()][board.getBoard().getLastJ()].setColor("");
						board.getBoard().setPieceActive(false);
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
				gui.squaresPanels[board.getBoard().getLastI()][board.getBoard().getLastJ()].setBackground(gui.boardColorPurple);
				board.getBoard().setPieceActive(false);
				gui.rotateLeftBut.setEnabled(false);
				gui.rotateRightBut.setEnabled(false);
				if (board.getBoard().boardSquares[i][j].getDirection() != board.getBoard().getOriginalDirection()) {
					gui.guiPrintLine("pieza rotada");
				} else {
					gui.guiPrintLine("pieza no movida");
				}
			}
		}
	}
}
