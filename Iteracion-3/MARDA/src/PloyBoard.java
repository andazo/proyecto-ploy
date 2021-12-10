
/**
 * Concrete class representing the logic aspects of the game board
 */
public class PloyBoard extends Board {
	/** Matrix of board squares with the information of the pieces on them */
	public PloyBoardSquare[][] boardSquares;

	private int originalDirection;

	private int p1HitPiecesIndex;
	private int p2HitPiecesIndex;
	private int p3HitPiecesIndex;
	private int p4HitPiecesIndex;

	// Each player's lost pieces
	public String[][] p1HitPieces;
	public String[][] p2HitPieces;
	public String[][] p3HitPieces;
	public String[][] p4HitPieces;

	// Order in which the pieces will be placed at the start of the game.
	final int pieceOrder1v1P1[] = {1,2,3,0,3,2,1,4,5,6,5,7};
	final int pieceOrder1v1P2[] = {1,2,3,0,3,2,1,7,5,6,5,4};
	final int pieceOrder1v1v1v1[] = {0,1,7,3,5,8,4,8,8};
	final int pieceOrder2v2[] = {1,0,3,4,5,7};

	/**
	 * Instantiates a new Ploy board.
	 */
	public PloyBoard() {
		boardSquares = new PloyBoardSquare[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				boardSquares[i][j] = new PloyBoardSquare(-1, 0, 0, "-");
			}
		}
		pieceActive = false;
		gameOver = false;
		currentPlayer = 1;
		lastI = 0;
		lastJ = 0;
		originalDirection = 0;
		p1HitPiecesIndex = 0;
		p2HitPiecesIndex = 0;
		p3HitPiecesIndex = 0;
		p4HitPiecesIndex = 0;
	}

	public void setOriginalDirection(int originalDirection) {
		this.originalDirection = originalDirection;
	}

	public int getOriginalDirection() {
		return originalDirection;
	}

	public void setP1HitPiecesIndex(int p1HitPiecesIndex) {
		this.p1HitPiecesIndex = p1HitPiecesIndex;
	}

	public int getP1HitPiecesIndex() {
		return p1HitPiecesIndex;
	}

	public void setP2HitPiecesIndex(int p2HitPiecesIndex) {
		this.p2HitPiecesIndex = p2HitPiecesIndex;
	}

	public int getP2HitPiecesIndex() {
		return p2HitPiecesIndex;
	}

	public void setP3HitPiecesIndex(int p3HitPiecesIndex) {
		this.p3HitPiecesIndex = p3HitPiecesIndex;
	}

	public int getP3HitPiecesIndex() {
		return p3HitPiecesIndex;
	}

	public void setP4HitPiecesIndex(int p4HitPiecesIndex) {
		this.p4HitPiecesIndex = p4HitPiecesIndex;
	}

	public int getP4HitPiecesIndex() {
		return p4HitPiecesIndex;
	}

	/**
	 * Update owner.
	 *
	 * @param currentOwner the current owner
	 * @param newOwner the new owner
	 */
	public void updateOwner(int currentOwner, int newOwner) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if(boardSquares[i][j].getOwner() == currentOwner) {
					boardSquares[i][j].setOwner(newOwner);
				}
			}
		}
	}

	/**
	 * Rotates a piece. Direction = -45 turns the piece left, direction = 45 turns it right.
	 * 
	 * @param x location on the x axis of the board
	 * @param y location on the y axis of the board
	 * @param direction the new direction the piece will face
	 */
	public void rotatePiece(int x, int y, int direction) {
		int newDirection = boardSquares[x][y].getDirection() + direction;
		int type = boardSquares[x][y].getType();
		if (type == 0) {
			if (newDirection < 0) {
				newDirection = newDirection + 90;
			} else if (newDirection >= 90) {
				newDirection = newDirection - 90;
			}
		} else if (type == 6) {
			if (newDirection < 0) {
				newDirection = newDirection + 180;
			} else if (newDirection >= 180) {
				newDirection = newDirection - 180;
			}
		} else {
			if (newDirection < 0) {
				newDirection = newDirection + 360;
			} else if (newDirection >= 360) {
				newDirection = newDirection - 360;
			}
		}
		boardSquares[x][y].setDirection(newDirection);
	}

	/**
	 * Populate the board with the data for every piece.
	 *
	 * @param players array of players that will play the game
	 * @param gameMode the game mode that will be played
	 */
	@Override
	public void populateBoard(Object[] players, int gameMode) {
		switch (gameMode) {
		case 0: // 1v1
			populateBoard1v1(((PloyPlayer) players[0]).getColor(), 1);
			populateBoard1v1(((PloyPlayer) players[1]).getColor(), 2);
			setActivePlayers(2);
			((PloyPlayer) players[0]).setNumPieces(15);
			((PloyPlayer) players[1]).setNumPieces(15);
			break;
		case 1: // 1v1v1v1
			populateBoard1v1v1v1(((PloyPlayer) players[0]).getColor(), 1);
			populateBoard1v1v1v1(((PloyPlayer) players[1]).getColor(), 2);
			populateBoard1v1v1v1(((PloyPlayer) players[2]).getColor(), 3);
			populateBoard1v1v1v1(((PloyPlayer) players[3]).getColor(), 4);
			setActivePlayers(4);
			((PloyPlayer) players[0]).setNumPieces(9);
			((PloyPlayer) players[1]).setNumPieces(9);
			((PloyPlayer) players[2]).setNumPieces(9);
			((PloyPlayer) players[3]).setNumPieces(9);
			break;
		case 2: // 2v2
			populateBoard2v2(((PloyPlayer) players[0]).getColor(), 1);
			populateBoard2v2(((PloyPlayer) players[1]).getColor(), 2);
			populateBoard2v2(((PloyPlayer) players[2]).getColor(), 3);
			populateBoard2v2(((PloyPlayer) players[3]).getColor(), 4);
			setActivePlayers(4);
			((PloyPlayer) players[0]).setNumPieces(9);
			((PloyPlayer) players[1]).setNumPieces(9);
			((PloyPlayer) players[2]).setNumPieces(9);
			((PloyPlayer) players[3]).setNumPieces(9);
			((PloyPlayer) players[0]).setFriend(3);
			((PloyPlayer) players[1]).setFriend(4);
			((PloyPlayer) players[2]).setFriend(1);
			((PloyPlayer) players[3]).setFriend(2);
			break;
		}
	}

	/**
	 * Populate board for 1v1 game mode.
	 *
	 * @param color the color of the player whose chips are being placed
	 * @param playerNum the player whose chips are being placed
	 */
	private void populateBoard1v1(String color, int playerNum) {
		int orderArrayIndex = 0;

		if (playerNum == 1) {
			for (int i = 1; i < 8; i++) {
				boardSquares[8][i].setType(pieceOrder1v1P1[orderArrayIndex]);
				boardSquares[8][i].setOwner(1);
				boardSquares[8][i].setColor(color);

				if (boardSquares[8][i].getType() == 0) {
					boardSquares[8][i].setDirection(0);					
				} else {
					boardSquares[8][i].setDirection(180);
				}

				orderArrayIndex++;
			}
			for (int i = 2; i < 7; i++) {
				boardSquares[7][i].setType(pieceOrder1v1P1[orderArrayIndex]);
				boardSquares[7][i].setOwner(1);
				boardSquares[7][i].setColor(color);

				if (boardSquares[7][i].getType() == 6) {
					boardSquares[7][i].setDirection(0);					
				} else {
					boardSquares[7][i].setDirection(180);
				}

				orderArrayIndex++;
			}
			for (int i = 3; i < 6; i++) {
				boardSquares[6][i].setType(8);
				boardSquares[6][i].setDirection(180);
				boardSquares[6][i].setOwner(1);
				boardSquares[6][i].setColor(color);
			}
			p1HitPieces = new String[15][2];
		} else {
			for (int i = 1; i < 8; i++) {
				boardSquares[0][i].setType(pieceOrder1v1P2[orderArrayIndex]);
				boardSquares[0][i].setDirection(0);
				boardSquares[0][i].setOwner(2);
				boardSquares[0][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 2; i < 7; i++) {
				boardSquares[1][i].setType(pieceOrder1v1P2[orderArrayIndex]);
				boardSquares[1][i].setDirection(0);
				boardSquares[1][i].setOwner(2);
				boardSquares[1][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 3; i < 6; i++) {
				boardSquares[2][i].setType(8);
				boardSquares[2][i].setDirection(0);
				boardSquares[2][i].setOwner(2);
				boardSquares[2][i].setColor(color);
			}
			p2HitPieces = new String[15][2];
		}
	}

	/**
	 * Populate board for the 1v1v1v1 game mode.
	 *
	 * @param color the color of the player whose chips are being placed
	 * @param playerNum the player whose chips are being placed
	 */
	private void populateBoard1v1v1v1(String color, int playerNum) {
		int orderArrayIndex = 0;
		if (playerNum == 1) {
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i == 0) {
					direction = 45;
				} else if (i == 2) {
					direction = 180;
				} else {
					direction = 225;
				}
				boardSquares[8][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				boardSquares[8][i].setDirection(direction);
				boardSquares[8][i].setOwner(1);
				boardSquares[8][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				boardSquares[7][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				boardSquares[7][i].setDirection(225);
				boardSquares[7][i].setOwner(1);
				boardSquares[7][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i == 0) {
					direction = 270;
				} else {
					direction = 225;
				}
				boardSquares[6][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				boardSquares[6][i].setDirection(direction);
				boardSquares[6][i].setOwner(1);
				boardSquares[6][i].setColor(color);
				orderArrayIndex++;
			}
			p1HitPieces = new String[9][2];
		} else if (playerNum == 2) {
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i == 0) {
					direction = 45;
				} else if (i == 2) {
					direction = 270;
				} else {
					direction = 315;
				}
				boardSquares[i][0].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				boardSquares[i][0].setDirection(direction);
				boardSquares[i][0].setOwner(2);
				boardSquares[i][0].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				boardSquares[i][1].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				boardSquares[i][1].setDirection(315);
				boardSquares[i][1].setOwner(2);
				boardSquares[i][1].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i == 0) {
					direction = 0;
				} else {
					direction = 315;
				}
				boardSquares[i][2].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				boardSquares[i][2].setDirection(direction);
				boardSquares[i][2].setOwner(2);
				boardSquares[i][2].setColor(color);
				orderArrayIndex++;
			}
			p2HitPieces = new String[9][2];
		} else if (playerNum == 3) {
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i == 8) {
					direction = 45;
				} else if (i == 6) {
					direction = 0;
				} else {
					direction = 45;
				}
				boardSquares[0][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				boardSquares[0][i].setDirection(direction);
				boardSquares[0][i].setOwner(3);
				boardSquares[0][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				boardSquares[1][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				boardSquares[1][i].setDirection(45);
				boardSquares[1][i].setOwner(3);
				boardSquares[1][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i == 8) {
					direction = 90;
				} else {
					direction = 45;
				}
				boardSquares[2][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				boardSquares[2][i].setDirection(direction);
				boardSquares[2][i].setOwner(3);
				boardSquares[2][i].setColor(color);
				orderArrayIndex++;
			}
			p3HitPieces = new String[9][2];
		} else {
			for (int i = 8; i > 5; i--) {
				int direction = 0;				
				if (i == 8) {
					direction = 45;
				} else if (i == 6) {
					direction = 90;
				} else {
					direction = 135;
				}
				boardSquares[i][8].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				boardSquares[i][8].setDirection(direction);
				boardSquares[i][8].setOwner(4);
				boardSquares[i][8].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				boardSquares[i][7].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				boardSquares[i][7].setDirection(135);
				boardSquares[i][7].setOwner(4);
				boardSquares[i][7].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i == 8) {
					direction = 180;
				} else {
					direction = 135;
				}
				boardSquares[i][6].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				boardSquares[i][6].setDirection(direction);
				boardSquares[i][6].setOwner(4);
				boardSquares[i][6].setColor(color);
				orderArrayIndex++;
			}
			p4HitPieces = new String[9][2];
		}
	}

	/**
	 * Populate the board for the 2v2 game mode.
	 *
	 * @param color the color of the player whose chips are being placed
	 * @param playerNum the player whose chips are being placed
	 */
	private void populateBoard2v2(String color, int playerNum) {
		int orderArrayIndex = 0;
		if (playerNum == 1) {
			for(int i = 1; i < 4; i++) {
				boardSquares[8][i].setType(pieceOrder2v2[orderArrayIndex]);
				boardSquares[8][i].setOwner(1);
				boardSquares[8][i].setColor(color);

				if (boardSquares[8][i].getType() == 0) {
					boardSquares[8][i].setDirection(0);					
				} else {
					boardSquares[8][i].setDirection(180);
				}

				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				boardSquares[7][i].setType(pieceOrder2v2[orderArrayIndex]);
				boardSquares[7][i].setDirection(180);
				boardSquares[7][i].setOwner(1);
				boardSquares[7][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				boardSquares[6][i].setType(8);
				boardSquares[6][i].setDirection(180);
				boardSquares[6][i].setOwner(1);
				boardSquares[6][i].setColor(color);
			}
			p1HitPieces = new String[9][2];
		} else if (playerNum == 2) {
			for(int i = 1; i < 4; i++) {
				boardSquares[0][i].setType(pieceOrder2v2[orderArrayIndex]);
				boardSquares[0][i].setDirection(0);
				boardSquares[0][i].setOwner(2);
				boardSquares[0][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 3; i > 0; i--) {
				boardSquares[1][i].setType(pieceOrder2v2[orderArrayIndex]);
				boardSquares[1][i].setDirection(0);
				boardSquares[1][i].setOwner(2);
				boardSquares[1][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				boardSquares[2][i].setType(8);
				boardSquares[2][i].setDirection(0);
				boardSquares[2][i].setOwner(2);
				boardSquares[2][i].setColor(color);
			}
			p2HitPieces = new String[9][2];
		} else if (playerNum == 3) {
			for(int i = 7; i > 4; i--) {
				boardSquares[8][i].setType(pieceOrder2v2[orderArrayIndex]);
				boardSquares[8][i].setOwner(3);
				boardSquares[8][i].setColor(color);

				if (boardSquares[8][i].getType() == 0) {
					boardSquares[8][i].setDirection(0);					
				} else {
					boardSquares[8][i].setDirection(180);
				}

				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				boardSquares[7][i].setType(pieceOrder2v2[orderArrayIndex]);
				boardSquares[7][i].setDirection(180);
				boardSquares[7][i].setOwner(3);
				boardSquares[7][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				boardSquares[6][i].setType(8);
				boardSquares[6][i].setDirection(180);
				boardSquares[6][i].setOwner(3);
				boardSquares[6][i].setColor(color);
			}
			p3HitPieces = new String[9][2];
		} else {
			for(int i = 7; i > 4; i--) {
				boardSquares[0][i].setType(pieceOrder2v2[orderArrayIndex]);
				boardSquares[0][i].setDirection(0);
				boardSquares[0][i].setOwner(4);
				boardSquares[0][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 7; i > 4; i--) {
				boardSquares[1][i].setType(pieceOrder2v2[orderArrayIndex]);
				boardSquares[1][i].setDirection(0);
				boardSquares[1][i].setOwner(4);
				boardSquares[1][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				boardSquares[2][i].setType(8);
				boardSquares[2][i].setDirection(0);
				boardSquares[2][i].setOwner(4);
				boardSquares[2][i].setColor(color);
			}
			p4HitPieces = new String[9][2];
		}
	}

	/**
	 * Loads the board from a saved file.
	 *
	 * @param players the players in the game
	 * @param gameMode the game mode
	 * @param board the board data
	 */
	public void loadBoard(PloyPlayer[] players, int gameMode, String[][][] board) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				boardSquares[i][j].setType(Integer.parseInt(board[i][j][0]));
				boardSquares[i][j].setDirection(Integer.parseInt(board[i][j][1]));
				boardSquares[i][j].setOwner(Integer.parseInt(board[i][j][2]));
				boardSquares[i][j].setColor(board[i][j][3]);
			}
		}
	}

	/**
	 * Load the hit pieces indexes from a save file.
	 *
	 * @param hitPiecesIndexes the hit pieces indexes from the file
	 */
	public void loadHitPiecesIndexes(int[] hitPiecesIndexes) {
		setP1HitPiecesIndex(hitPiecesIndexes[0]);
		setP2HitPiecesIndex(hitPiecesIndexes[1]);
		setP3HitPiecesIndex(hitPiecesIndexes[2]);
		setP4HitPiecesIndex(hitPiecesIndexes[3]);
	}

	/**
	 * Load each player's lost pieces from a save file.
	 *
	 * @param gameMode the game mode being played
	 * @param hitPieces each player's lost pieces
	 */
	public void loadHitPieces(int gameMode, String[][][] hitPieces) {
		if (gameMode == 0) {
			p1HitPieces = new String[15][2];
			for (int i = 0; i < getP1HitPiecesIndex(); i++) {
				if (hitPieces[0][i] != null) {
					p1HitPieces[i] = hitPieces[0][i];
				}
			}
			p2HitPieces = new String[15][2];
			for (int i = 0; i < getP2HitPiecesIndex(); i++) {
				if (hitPieces[1][i] != null) {
					p2HitPieces[i] = hitPieces[1][i];
				}
			}
		} else {
			p1HitPieces = new String[9][2];
			for (int i = 0; i < getP1HitPiecesIndex(); i++) {
				if (hitPieces[0][i] != null) {
					p1HitPieces[i] = hitPieces[0][i];
				}
			}
			p2HitPieces = new String[9][2];
			for (int i = 0; i < getP2HitPiecesIndex(); i++) {
				if (hitPieces[1][i] != null) {
					p2HitPieces[i] = hitPieces[1][i];
				}
			}
			p3HitPieces = new String[9][2];
			for (int i = 0; i < getP3HitPiecesIndex(); i++) {
				if (hitPieces[2][i] != null) {
					p3HitPieces[i] = hitPieces[2][i];
				}
			}
			p4HitPieces = new String[9][2];
			for (int i = 0; i < getP4HitPiecesIndex(); i++) {
				if (hitPieces[3][i] != null) {
					p4HitPieces[i] = hitPieces[3][i];
				}
			}
		}
	}

	/**
	 * Set the player whose turn it is
	 *
	 * @param currentPlayer the current player
	 */
	public void loadCurrentPlayer(int currentPlayer) {
		setCurrentPlayer(currentPlayer);
	}
}
