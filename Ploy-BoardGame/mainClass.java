import javax.swing.JOptionPane;
import java.awt.Color;

public class mainClass {
    public static void main(String[] args) {

        // Numero de jugadores en la partida, puede ser de 2 o 4
        int numPlayers = 0;
        while (true) {
        	try {
        		numPlayers = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de jugadores para la partida 2 o 4"));
        	} catch (NumberFormatException e) { }
            if(numPlayers == 2 || numPlayers == 4) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Numero de jugadores invalido");
            }
        }

        //Arreglo de opciones de colores para los jugadores
        String[] choices = { "Green", "Red", "Blue", "Yellow"};

        //Caso 2 jugadores
        if (numPlayers == 2) {

        	player[] players = new player[2];
            //Objeto jugador 1
            players[0] = new player();
            //Objeto jugador 2
            players[1] = new player();

            //Info jugador 1

            //Nombre
            String player1 = JOptionPane.showInputDialog("Nombre jugador 1");
            players[0].setName(player1);
            //Color
            String color1 = (String) JOptionPane.showInputDialog(null, "Color", "Color para el jugador" + player1, JOptionPane.QUESTION_MESSAGE, null,choices,choices[1]);
            players[0].setColor(color1);

            //Info jugador 2

            //Nombre
            String player2 = JOptionPane.showInputDialog("Nombre jugador 2");
            players[1].setName(player2);

            //Color
            String color2 = "";
            while (true) {
                color2 = (String) JOptionPane.showInputDialog(null, "Color", "Color para el jugador" + player1, JOptionPane.QUESTION_MESSAGE, null,choices,choices[1]);
                if (color2 != color1) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Color ya fue seleccionado");
                }
            }
            players[1].setColor(color2);

            //Display info
            JOptionPane.showMessageDialog(null, "Player1: " + players[0].getName() + "\nColor: " + players[0].getColor() + "\n\nPlayer2: " +  players[1].getName() + "\nColor: " + players[1].getColor());
            
        	PloyGUI gui = new PloyGUI(numPlayers, players);
            
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
            String player1 = JOptionPane.showInputDialog("Nombre jugador 1");
            players[0].setName(player1);

            //Color
            String color1 = (String) JOptionPane.showInputDialog(null, "Color", "Color para el jugador" + player1, JOptionPane.QUESTION_MESSAGE, null,choices,choices[0]);
            players[0].setColor(color1);

            //Info jugador 2

            //Nombre
            String player2 = JOptionPane.showInputDialog("Nombre jugador 2");
            players[1].setName(player2);

            //Color
            String color2 = "";
            while (true) {
                color2 = (String) JOptionPane.showInputDialog(null, "Color", "Color para el jugador" + player2, JOptionPane.QUESTION_MESSAGE, null,choices,choices[0]);
                if (color2 != color1) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Color ya fue seleccionado");
                }
            }
            players[1].setColor(color2);

            //Info jugador 3

            //Nombre
            String player3 = JOptionPane.showInputDialog("Nombre jugador 3");
            players[2].setName(player3);

            //Color
            String color3 = "";
            while (true) {
                color3 = (String) JOptionPane.showInputDialog(null, "Color", "Color para el jugador" + player3, JOptionPane.QUESTION_MESSAGE, null,choices,choices[0]);
                if (color3 != color1 && color3 != color2) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Color ya fue seleccionado");
                }
            }
            players[2].setColor(color3);

            //Info jugador 4

            //Nombre
            String player4 = JOptionPane.showInputDialog("Nombre jugador 4");
            players[3].setName(player4);

            //Color
            String color4 = "";
            while (true) {
                color4 = (String) JOptionPane.showInputDialog(null, "Color", "Color para el jugador" + player4, JOptionPane.QUESTION_MESSAGE, null,choices,choices[0]);
                if (color4 != color1 && color4 != color2 && color4 != color3) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Color ya fue seleccionado");
                }
            }
            players[3].setColor(color4);

            //Display info
            JOptionPane.showMessageDialog(null, "Player 1: " + players[0].getName() + "\nColor: " + players[0].getColor() + "\n\nPlayer 2: " +  players[1].getName() + "\nColor: " + players[1].getColor() 
            + "\n\nPlayer 3: " +  players[2].getName() + "\nColor: " + players[2].getColor() + "\n\nPlayer 4: " +  players[3].getName() + "\nColor: " + players[3].getColor());
            
            PloyGUI gui = new PloyGUI(numPlayers, players);
        }
    }
}
