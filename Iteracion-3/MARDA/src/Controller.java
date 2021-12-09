abstract class Controller {
	Object gui;
	Object board;

	// metodo plantilla
	public void startGame() {
		gui = initGUI();
		char newGame = getNewGame();
		int numPlayers = 0;
		PloyPlayer[] players = initPlayers();
		int gameMode = 0;
		
		if (checkOption(newGame)) {
			numPlayers = getNumPlayers();
			players = getPlayers(numPlayers);
			gameMode = getMode(numPlayers);
		}
		
		board = initBoard();
		gui.drawBoard();  
	}
	
	// métodos abstractos 
	
	abstract Object initGUI();
	
	abstract Object[] initPlayers();
	
	abstract Object initBoard();
	
	abstract boolean checkOption(char newGame);
	
	abstract int getNumPlayers();

	abstract char getNewGame();

	abstract Object[] getPlayers(int numPlayers);

	abstract int getMode(int numPlayers);

}
