import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainClass implements ActionListener {

	private PloyGUI gui;
    private Message msg;
	private final String GAME_RULES = "Para 2 jugadores:\nEl objetivo es capturar al Comandante del oponente o todas sus piezas excepto el Comandante.\nEn el juego de dos jugadores solo se utilizan los conjuntos de color rojo y verde, ya que los conjuntos amarillo y azul\ntienen un n�mero diferente de piezas para adaptarse a la menor cantidad de piezas utilizadas en el juego de cuatro jugadores.\nEl jugador verde va primero. En cada turno un jugador, puede realizar un movimiento o un cambio de direcci�n.\nEl Comandante se puede desplazar 1 espacio. Las Lanzas se pueden desplazar 1,2 o 3 espacios. Las Probes 1 o 2 espacios.\nLos Escudos 1 espacio.\n\nCon 4 jugadores 1v1v1v1:\nEl objetivo es ser el �ltimo jugador en pie despu�s de que los dem�s hayan sido eliminados.\nEn el turno de un jugador, puede realizar un movimiento o un cambio de direcci�n. Si el Comandante de un jugador es capturado,\nlas piezas restantes quedan bajo el mando del jugador que lo captura. Si todas las piezas de un jugador, excepto el Comandante,\nhan sido capturadas, el Comandante se retira del juego y el jugador queda fuera del juego. El juego contin�a en el sentido de las\nagujas del reloj hasta que quede un jugador.\n\nCon 4 jugadores 2v2:\nUna vez que el comandante de un jugador es absorbido, su compa�ero de equipo se hace cargo de todas sus piezas restantes.\nEl compa�ero tambi�n toma el turno de su compa�ero de equipo y puede usar todas las piezas del equipo para sus movimientos.";

	public mainClass() {
		gui = null;
        msg = new Message();
    }

    /**
     * Ejecuta las acciones de los botones.
     */
    public void actionPerformed(ActionEvent evento) {        
        if(evento.getActionCommand().equals("Reglas")){
            msg.printMessageWithTitle(GAME_RULES, "Reglas del juego");
        }
    }

    public void startGame() {
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

        //Arreglo de opciones de colores para los jugadores
        String[] choices = { "Verde", "Rojo", "Azul", "Amarillo"};

        //Caso 2 jugadores
        if (numPlayers == 2) {

        	player[] players = new player[2];
            //Objeto jugador 1
            players[0] = new player();
            //Objeto jugador 2
            players[1] = new player();

            //Info jugador 1

            //Nombre
            String player1 = msg.inputMessage("Nombre jugador 1");
            players[0].setName(player1);
            
            if (players[0].name == null) {
            	System.exit(0);
            }
            
            if (players[0].name.isBlank()) {
            	players[0].name = "An�nimo";
            }
            
            //Color
            String color1 = msg.inputQuestionMessage("Color", "Color para el jugador" + player1, choices, choices[0]);
            players[0].setColor(color1);
            
            if (players[0].color == null) {
            	System.exit(0);
            }

            //Info jugador 2

            //Nombre
            String player2 = msg.inputMessage("Nombre jugador 2");
            players[1].setName(player2);
            
            if (players[1].name == null) {
            	System.exit(0);
            }
            
            if (players[1].name.isBlank()) {
            	players[1].name = "An�nimo";
            }

            //Color
            String color2 = "";
            while (true) {
                color2 = msg.inputQuestionMessage("Color", "Color para el jugador" + player2, choices, choices[1]);
                if (color2 != color1) {
                    break;
                } else {
                    msg.printMessage("Color ya fue seleccionado");
                }
            }
            players[1].setColor(color2);
            
            if (players[1].color == null) {
            	System.exit(0);
            }

            //Display info
            msg.printMessage("Player1: " + players[0].getName() + "\nColor: " + players[0].getColor() + "\n\nPlayer2: " +  players[1].getName() + "\nColor: " + players[1].getColor());
            
            int mode = 0;
            
        	gui = new PloyGUI(this, numPlayers, players, mode);
            
        } else if (numPlayers == 4) { //Caso 4 jugadores

        	player[] players = new player[4];
            //Objeto jugador 1
            players[0] = new player();
            //Objeto jugador 2
            players[1] = new player();
            //Objeto jugador 3
            players[2] = new player();
            //Objeto jugador 4
            players[3] = new player();

            //Info jugador 1

            //Nombre
            String player1 = msg.inputMessage("Nombre jugador 1");
            players[0].setName(player1);
            
            if (players[0].name == null) {
            	System.exit(0);
            }
            
            if (players[0].name.isBlank()) {
            	players[0].name = "Anonimo";
            }

            //Color
            String color1 = msg.inputQuestionMessage("Color", "Color para el jugador" + player1, choices, choices[0]);
            players[0].setColor(color1);
            
            if (players[0].color == null) {
            	System.exit(0);
            }

            //Info jugador 2

            //Nombre
            String player2 = msg.inputMessage("Nombre jugador 2");
            players[1].setName(player2);

            if (players[1].name == null) {
            	System.exit(0);
            }
            
            if (players[1].name.isBlank()) {
            	players[1].name = "Anonimo";
            }
            
            //Color
            String color2 = "";
            while (true) {
                color2 = msg.inputQuestionMessage("Color", "Color para el jugador" + player2, choices, choices[1]);
                if (color2 != color1) {
                    break;
                } else {
                    msg.printMessage("Color ya fue seleccionado");
                }
            }
            players[1].setColor(color2);
            
            if (players[1].color == null) {
            	System.exit(0);
            }

            //Info jugador 3

            //Nombre
            String player3 = msg.inputMessage("Nombre jugador 3");
            players[2].setName(player3);
            
            if (players[2].name == null) {
            	System.exit(0);
            }

            if (players[2].name.isBlank()) {
            	players[2].name = "Anonimo";
            }
            
            //Color
            String color3 = "";
            while (true) {
                color3 = msg.inputQuestionMessage("Color", "Color para el jugador" + player3, choices, choices[2]);
                if (color3 != color1 && color3 != color2) {
                    break;
                } else {
                    msg.printMessage("Color ya fue seleccionado");
                }
            }
            players[2].setColor(color3);
            
            if (players[2].color == null) {
            	System.exit(0);
            }

            //Info jugador 4

            //Nombre
            String player4 = msg.inputMessage("Nombre jugador 4");
            players[3].setName(player4);
            
            if (players[3].name == null) {
            	System.exit(0);
            }
            
            if (players[3].name.isBlank()) {
            	players[3].name = "Anonimo";
            }

            //Color
            String color4 = "";
            while (true) {
                color4 = msg.inputQuestionMessage("Color", "Color para el jugador" + player4, choices, choices[3]);
                if (color4 != color1 && color4 != color2 && color4 != color3) {
                    break;
                } else {
                    msg.printMessage("Color ya fue seleccionado");
                }
            }
            players[3].setColor(color4);
            
            if (players[3].color == null) {
            	System.exit(0);
            }

            //Display info
            msg.printMessage("Player 1: " + players[0].getName() + "\nColor: " + players[0].getColor() + "\n\nPlayer 2: " +  players[1].getName() + "\nColor: " + players[1].getColor() 
            + "\n\nPlayer 3: " +  players[2].getName() + "\nColor: " + players[2].getColor() + "\n\nPlayer 4: " +  players[3].getName() + "\nColor: " + players[3].getColor());
            
            int mode = 0;
            while (true) {
            	input = msg.inputMessage("Modo de juego (1 = 1v1v1v1 || 2 = 2v2)");
            	if (input != null) {
            		try {
            			mode = Integer.parseInt(input);
                	} catch (NumberFormatException e) {	}
            	} else {
            		System.exit(0);
            	}
                if (mode == 1 || mode == 2) {
                    break;
                } else {
                    msg.printMessage("Modo invalido");
                }
            }
            
            gui = new PloyGUI(this, numPlayers, players, mode);
        }

    }
    
    public static void main(String[] args) {
       mainClass controller = new mainClass();
       controller.startGame();
    }
}
