
public class Controller {
	PloyGUI gui;


	public void startGame() {
		PloyPlayer p1 = new PloyPlayer("Richie", 1,"red");
		System.out.println(p1.getName());
		System.out.println(p1.getColor());
		System.out.println(p1.getID());
		System.out.println(p1.getLost());
		System.out.println(p1.getFriend());
		System.out.println(p1.getNumPieces());

		gui = new PloyGUI(600);
		gui.drawBoard();

		getPlayers(getNumPlayers());
	}

	private int getNumPlayers() {
		// Numero de jugadores en la partida, puede ser de 2 o 4
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

	private char getNewGame() {
		String[] options = {"Nueva partida", "Cargar partida","Cancelar"};
		char newGame = ' ';
		int input = -1;
		input = gui.inputMessageWithOptions("Para empezar, seleccione lo que desea hacer", "Bienvenido a Ploy BoardGame", options);
		if (input == 0) {
			newGame = 'Y';
		} else if (input == 1) {
			newGame = 'N';
		} else if (input == 2) {
			System.exit(0);
		}
		return newGame;
	}

	private PloyPlayer[] getPlayers(int numPlayers) {
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

	private int getMode(int numPlayers) {
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
}
