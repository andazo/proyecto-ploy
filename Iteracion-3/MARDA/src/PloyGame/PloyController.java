package PloyGame;
import java.awt.event.ActionEvent;

import MARDA.Controller;

/**
 * Concrete controller class for Ploy 
 */
public class PloyController extends Controller {
	private PloyGUI gui;
	private PloyBoard board;
	private PloyFileManager fm;
	private PloyModerator mod;
	private PloyPlayer[] players;
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

	/**
	 * Instantiates all the classes needed for the game and gets all the user's input
	 * such as user information, game mode and player amount. The game is ready to be played after 
	 * this function is executed.
	 */ 
	@Override
	public void startGame() {
		gui = (PloyGUI) initGUI();
		board = (PloyBoard) initBoard();
		fm = new PloyFileManager();
		mod = new PloyModerator();
		players = null;
		gameMode = 0;

		char newGame = getNewGame();
		int numPlayers = 0;

		if (newGame == 'Y') {
			numPlayers = getNumPlayers();
			players = getPlayers(numPlayers);
			gameMode = getMode(numPlayers);
			gui.drawBoard(players, gameMode);
			gui.populateBoard(players, gameMode);
			board.populateBoard(players, gameMode);
			setMenuActions(gameMode);
			setActions();
		} else {
			loadGame();
		}		
	}

	/**
	 * Loads in a saved game.
	 */
	@Override
	protected void loadGame() {
		int gameMode = 0;

		int loadStatus = fm.loadFile();
		if (loadStatus == 0) {
			if (gui.frame != null) {
				gui.closeWindow();
			}
			players = (PloyPlayer[]) fm.getPlayers();
			gameMode = fm.getGameMode();
			gui = (PloyGUI) initGUI();
			gui.drawBoard(players, gameMode);
			board.loadBoard(players, gameMode, fm.getBoardData());
			gui.loadBoard(players, gameMode, fm.getBoardData());
			board.loadHitPiecesIndexes(fm.getHitPiecesIndexes());
			board.loadHitPieces(gameMode, fm.getHitPieces());
			board.loadCurrentPlayer(fm.getCurrentPlayer());
			setMenuActions(gameMode);
			setActions();
			gui.showSaveLoadMessage("La partida fue cargada satisfactoriamente", "Partida Cargada");
		} else if (loadStatus == 1) {
			gui.showSaveLoadMessage("Error al cargar la partda, puede que el archivo no exista", "Error");
			if (gui.frame == null) {
				System.exit(0);
			}
		} else {
			gui.showSaveLoadMessage("El proceso de cargado fue cancelado", "Cargado cancelado");
			if (gui.frame == null) {
				System.exit(0);
			}
		}
	}

	/**
	 * Initializes the GUI.
	 *
	 * @return the GUI object
	 */
	@Override
	public Object initGUI() {
		return new PloyGUI(600);
	}

	/**
	 * Initializes the board.
	 *
	 * @return the board object
	 */
	@Override
	public Object initBoard() {
		return new PloyBoard();
	}

	/**
	 * Brings up a prompt allowing the user to select the amount of players that will be in the game.
	 *
	 * @return the number of players that will play
	 */
	@Override
	public int getNumPlayers() {
		// Number of players that can be in a game, either 2 or 4
		String[] options = {"2", "4"};
		int numPlayers = 0;
		int input = -1;
		input = gui.inputMessageWithOptions("Seleccione la cantidad de jugadores para la partida", "Cantidad de jugadores", options);
		if (input == 0) {
			numPlayers = 2;
		} else if (input == 1) {
			numPlayers = 4;
		} else if (input == -1) {
			System.exit(0);
		}
		return numPlayers;
	}

	/**
	 * Generates prompts to get all the players' information such as name and color of the pieces.
	 *
	 * @param numPlayers the number of players in the game
	 * @return the array of initialized players with all their information 
	 */
	@Override
	public PloyPlayer[] getPlayers(int numPlayers) {
		//Arreglo de opciones de colores para los jugadores
		String[] choices = {"Verde", "Rojo", "Azul", "Amarillo"};
		PloyPlayer[] players  = new PloyPlayer[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			//Nombre
			String player = gui.inputMessage("Nombre jugador " + (i + 1));
			if (player == null) {
				System.exit(0);
			}
			if (player.isBlank()) {
				player = "Anonimo";
			}
			//Color
			String color;
			boolean used = false;
			while (true) {
				color = gui.inputQuestionMessage("Color", "Color para el jugador" + player, choices, choices[i]);
				for (int j = 0; j < i; j++) {
					if (color == players[j].getColor()) {
						gui.printMessage("Color ya fue seleccionado");
						used= true;
						j = i;
					}
				}
				if (used == false) {
					break;
				}
			}

			players[i] = new PloyPlayer(player, i + 1, color);

			if (players[i].getColor() == null) {
				System.exit(0);
			}
		}

		String playersInfo = "";
		for (int i = 0; i < numPlayers; i++) {
			playersInfo = playersInfo + "Jugador " + players[i].getID() + ": " + players[i].getName()
					+ "\nColor: " + players[i].getColor() + "\n\n";
		}
		//Display info
		gui.printMessage(playersInfo);

		return players;
	}

	/**
	 * Gets the game mode the user wishes to play. 
	 *
	 * @param numPlayers number of players in the game
	 * @return the game mode chosen by the user
	 */
	@Override
	public int getMode(int numPlayers) {
		int gameMode = 0;
		if (numPlayers == 4) {
			String[] options = {"1v1v1v1", "2v2"};
			int input = -1;
			input = gui.inputMessageWithOptions("Seleccione el modo de juego", "Modo de juego", options);
			if (input == 0) {
				gameMode = 1;
			} else if (input == 1) {
				gameMode = 2;
			} else if (input == -1) {
				System.exit(0);
			}
		}
		return gameMode;
	}

	/**
	 * Adds mouse listeners for every square on the board.
	 */
	@Override
	public void setActions() {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				final int tempI = i;
				final int tempJ = j;
				gui.squaresPanels[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
					final int myI = tempI;
					final int myJ = tempJ;
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						mod.clickedOn(myI, myJ, players.length, gameMode, players, board, gui);
					}
				});
			}
		}

		gui.rotateLeftBut.addActionListener(this);
		gui.rotateRightBut.addActionListener(this);
	}


	/**
	 * Adds listeners for every button on the menu
	 *
	 * @param gameMode the game mode being played 
	 */
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

	/**
	 * Handles the click events for every button and square on the board 
	 *
	 * @param evento click event
	 */
	@Override
	public void actionPerformed(ActionEvent evento) {
		if (evento.getActionCommand().equals("Reglas")) {
			gui.printMessageWithTitle(GAME_RULES, "Reglas del juego");
		} else if (evento.getActionCommand().equals("Guardar Partida")) {
			String fileName = gui.inputMessage("Ingrese el nombre del archivo a guardar");
			if (fileName != null) {
				fm.saveFile(players, gameMode, board, fileName);
				gui.showSaveLoadMessage("La partida fue guardada satisfactoriamente", "Partida guardada");
			} else {
				gui.showSaveLoadMessage("El proceso de guardado fue cancelado", "Guardado cancelado");
			}
		} else if (evento.getActionCommand().equals("Cargar Partida")) {
			loadGame();
		} else if (evento.getActionCommand().equals("Jugador 1")) {
			gui.showLostPieces(board.p1HitPieces, board.getP1HitPiecesIndex());
		} else if (evento.getActionCommand().equals("Jugador 2")) {
			gui.showLostPieces(board.p2HitPieces, board.getP2HitPiecesIndex());
		} else if (evento.getActionCommand().equals("Jugador 3")) {
			gui.showLostPieces(board.p3HitPieces, board.getP3HitPiecesIndex());
		} else if (evento.getActionCommand().equals("Jugador 4")) {
			gui.showLostPieces(board.p4HitPieces, board.getP4HitPiecesIndex());
		} else if (evento.getActionCommand().equals("Girar izquierda")) {
			String[][] moves = mod.getValidMoves(board.getLastI(), board.getLastJ(), board);
			mod.cancelHighlightMoves(moves, board.getLastI(), board.getLastJ(), gui);
			gui.rotatePiece(board.getLastI(), board.getLastJ(), 315);
			board.rotatePiece(board.getLastI(), board.getLastJ(), -45);
			int lastI = board.getLastI();
			int lastJ = board.getLastJ();
			int originalDirection = board.getOriginalDirection();
			if (((PloyPiece) board.boardSquares[lastI][lastJ].getPiece()).getDirection() == originalDirection || ((PloyPiece) board.boardSquares[lastI][lastJ].getPiece()).getType() == 8) {
				moves = mod.getValidMoves(board.getLastI(), board.getLastJ(), board);
				mod.highlightMoves(moves, board.getLastI(), board.getLastJ(), gui);
			}
		} else if (evento.getActionCommand().equals("Girar derecha")) {
			String[][] moves = mod.getValidMoves(board.getLastI(), board.getLastJ(), board);
			mod.cancelHighlightMoves(moves, board.getLastI(), board.getLastJ(), gui);
			gui.rotatePiece(board.getLastI(), board.getLastJ(), 45);
			board.rotatePiece(board.getLastI(), board.getLastJ(), 45);
			int lastI = board.getLastI();
			int lastJ = board.getLastJ();
			int originalDirection = board.getOriginalDirection();
			if (((PloyPiece) board.boardSquares[lastI][lastJ].getPiece()).getDirection() == originalDirection || ((PloyPiece) board.boardSquares[lastI][lastJ].getPiece()).getType() == 8) {
				moves = mod.getValidMoves(board.getLastI(), board.getLastJ(), board);
				mod.highlightMoves(moves, board.getLastI(), board.getLastJ(), gui);
			}
		}
	}
}
