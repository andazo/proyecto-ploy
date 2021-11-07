# Proyecto I Diseño de Software

## Profesor:
### Alan Calderón Castro

## Integrantes:
### Andrés Azofeifa García B90837
### Gabriel Zúñiga Orozco B98755
### Luis Domian B62377
### Rodrigo Piedra

## Juego Desarrollado:
### Ploy Boardgame

## Limitaciones:
Primera iteración - sin limitaciones

## User Stories:

### User Story 1

Como jugador requiero escoger al principio del juego con cuál de los posibles colores de pieza voy a jugar así como mis adversarios. Se requiere asociar cada color con un nombre.

Para el primer user story se realizó una ventana gráfica emergente con la herramienta JOptionPane que permite las siguientes acciones:

- Digitar la cantidad de jugadores en la partida (2 o 4)
- Digitar el nombre de los jugadores 
- Seleccionar el color de cada jugador
- En caso de jugar con 4 jugadores, se puede seleccionar el modo de juego 2v2 o 1v1v1v1

Se realizó programación defensiva para ayudar a los jugadores, por lo cual no se puede repetir color ni digitar una cantidad invalida de jugadores

### User Story 2

Como jugador requiero se muestre siempre el estado del juego para escoger la mejor jugada en cada momento. El estado del juego abarca las posiciones de fichas en el tablero de todos los jugadores.

Para segundo user story se implementó una interfaz gráfica donde en todo momento el o los jugadores pueden ver en tiempo real el tabplero donde se está juugando. Con esto se puede analizar la jugada que van a realizar. También se actualiza en tiempo real las piezas eliminadas, rotación de piezas y los movimientos de estas. Se agregó imagenes png de las piezas reales que se utilizan en el juego ploy, por lo cual es más facil para el jugador saber cual es exactamente cada pieza, el valor de esta y los movimientos que esta puede realizar dentro de las reglas.

### User Story 5

Como jugador requiero se muestre continuamente la piezas que he perdido para valorar

Dentro de la interfáz gráfica donde se encuentra el tablero en tiempo real, se implementó una pestaña en la esquina izquierda donde se puede consultar las piezas eliminadas de cada jugador.

### User Story 6

Como jugador novato requiero poder consultar las reglas del juego en cualquier momento.

Al igual que en el User Story 6, se implementó una pestaña en la esquina izquierda de la interfáz gráfica donde se pueden consultar las reglas del juego para los jugadores novatos. Dentro de esta se puede encontrar las reglas de 1v1, 2v2 y 1v1v1v1. Junto a esto, en la interfaz se encuntra un segmento donde se encuentra el log de los movimientos que se han dado en la partida, al inicio, en este se pueden ver unas breves reglas sobre el juego.

## Compilación:

