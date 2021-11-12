
public class Main {
	
	private static char getNewGame(Message msg) {
		String[] options = {"Nueva partida", "Cargar partida"};
		char newGame = ' ';
    	int input = -1;
	    input = msg.inputMessageWithOptions("Seleccione lo que desea hacer", "Nueva partida / Cargar partida", options);
	    if (input == 0) {
	    	newGame = 'Y';
	    } else if (input == 1) {
	    	newGame = 'N';
	    } else if (input == -1) {
	       	System.exit(0);
	    }
	    return newGame;
	}
	
	private static int getNumPlayers(Message msg) {
		// Numero de jugadores en la partida, puede ser de 2 o 4
		String[] options = {"2", "4"};
	    int numPlayers = 0;
	    int input = -1;
	    input = msg.inputMessageWithOptions("Seleccione la cantidad de jugadores para la partida", "Cantidad de jugadores", options);
	    if (input == 0) {
	    	numPlayers = 2;
	    } else if (input == 1) {
	    	numPlayers = 4;
	    } else if (input == -1) {
	   		System.exit(0);
	   	}
	    return numPlayers;
	}
	
	private static Player[] getPlayers(Message msg, int numPlayers) {
		//Arreglo de opciones de colores para los jugadores
	    String[] choices = { "Verde", "Rojo", "Azul", "Amarillo"};
	    Player[] players  = new Player[numPlayers];
	    for (int i = 0; i < numPlayers; i++) {
	    	//Crea el objeto
	    	players[i] = new Player();
	    	
	    	//Nombre
	    	String player = msg.inputMessage("Nombre jugador " + (i + 1));
	    	players[i].setName(player);
	        
		    if (players[i].getName() == null) {
		    	System.exit(0);
		    }
		        
		    if (players[i].getName().isBlank()) {
		    	players[i].setName("Anonimo");
		    }
		        
		    //Color
		    String color;
		    boolean usado = false;
		    while (true) {
				color = msg.inputQuestionMessage("Color", "Color para el jugador" + player, choices, choices[i]);
				for (int j = 0; j < i; j++) {
					if (color == players[j].getColor()) {
						msg.printMessage("Color ya fue seleccionado");
						usado = true;
						j = i;
			        }
				}
				if (usado == false) {
		            break;
		        }
		    }
		    players[i].setColor(color);
		    
			if (players[i].getColor() == null) {
				System.exit(0);
			}
	    }
	    
	    String playersInfo = "";
	    
	    for (int i = 0; i < numPlayers; i++) {
	    	playersInfo = playersInfo + "Jugador " + (i + 1) + ": " + players[i].getName() + "\nColor: " + players[i].getColor() + "\n\n";
	    }
	
	    //Display info
	    msg.printMessage(playersInfo);
	        
	    return players;
	}
	
	private static int getMode(Message msg, int numPlayers) {
	    int gameMode = 0;
	    if (numPlayers == 4) {
	    	String[] options = {"1v1v1v1", "2v2"};
	    	int input = -1;
		    input = msg.inputMessageWithOptions("Seleccione el modo de juego", "Modo de juego", options);
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

	public static void main(String[] args) {
		Message msg = new Message();
		FileManager fm = new FileManager();
		char newGame = getNewGame(msg);
		int numPlayers = 0;
		Player[] players = null;
		int gameMode = 0;
		
		if (newGame == 'Y') {
			numPlayers = getNumPlayers(msg);
			players = getPlayers(msg, numPlayers);
			gameMode = getMode(msg, numPlayers);
		}
		
		PloyGUI gui = new PloyGUI();
		PloyBoard board = new PloyBoard();
		
		Controller controller = new Controller(msg, fm, players, gui, board, gameMode);
		controller.startGame(newGame);
	}

}
