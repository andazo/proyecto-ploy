
public class Main {
	
	private static int getNumPlayers(Message msg) {
		// Numero de jugadores en la partida, puede ser de 2 o 4
	    int numPlayers = 0;
	    String input = "";
	    while (true) {
	    	input = msg.inputMessage("Ingrese la cantidad de jugadores para la partida 2 o 4");
	    	if (input != null) {
	    		try {
	        		numPlayers = Integer.parseInt(input);
	        	} catch (NumberFormatException e) {	}
	    	} else {
	    		System.exit(0);
	    	}
	        if (numPlayers == 2 || numPlayers == 4) {
	            break;
	        } else {
	            msg.printMessage("Numero de jugadores invalido");
	        }
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
	    	String input = "";
		    while (true) {
		    	input = msg.inputMessage("Modo de juego (1 = 1v1v1v1 || 2 = 2v2)");
		        if (input != null) {
		        	try {
		        		gameMode = Integer.parseInt(input);
		           	} catch (NumberFormatException e) {	}
		        } else {
		        	System.exit(0);
		        }
		        if (gameMode == 1 || gameMode == 2) {
		            break;
		        } else {
		            msg.printMessage("Modo invalido");
		        }
		    }
	    }
		return gameMode;
	}

	public static void main(String[] args) {
		Message msg = new Message();
		int numPlayers = getNumPlayers(msg);
		Player[] players = getPlayers(msg, numPlayers);
		int gameMode = getMode(msg, numPlayers);
		
		PloyGUI gui = new PloyGUI();
		PloyBoard board = new PloyBoard();
		
		Controller controller = new Controller(msg, players, gui, board);
		controller.startGame(gameMode);
	}

}
