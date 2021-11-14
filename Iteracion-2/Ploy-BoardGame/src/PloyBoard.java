import javax.swing.JLabel;

public class PloyBoard {
	
	final int pieceOrder1v1P1[] = {1,2,3,0,3,2,1,4,5,6,5,7};
	final int pieceOrder1v1P2[] = {1,2,3,0,3,2,1,7,5,6,5,4};
	final int pieceOrder1v1v1v1[] = {0,1,7,3,5,8,4,8,8};
	final int pieceOrder2v2[] = {1,0,3,4,5,7};
	
	private BoardInfo boardInfo;
	
	public PloyBoard() {
		boardInfo = new BoardInfo();
	}
	
	public BoardInfo getBoardInfo() {
		return boardInfo;
	}
	
	public void populateBoard(Player[] players, int gameMode) {
		switch (gameMode) {
			case 0: // 1v1
				populateBoard1v1(players[0].getColor(), 1);
			  	populateBoard1v1(players[1].getColor(), 2);
			    break;
			case 1: // 1v1v1v1
				populateBoard1v1v1v1(players[0].getColor(), 1);
				populateBoard1v1v1v1(players[1].getColor(), 2);
				populateBoard1v1v1v1(players[2].getColor(), 3);
				populateBoard1v1v1v1(players[3].getColor(), 4);
			    break;
			case 2: // 2v2
				populateBoard2v2(players[0].getColor(), 1);
			  	populateBoard2v2(players[1].getColor(), 2);
			  	populateBoard2v2(players[2].getColor(), 3);
			  	populateBoard2v2(players[3].getColor(), 4);
			    break;
		}
	}
    
    private void populateBoard1v1(String color, int playerNum) {
		int orderArrayIndex = 0;
		if (playerNum == 1) {
			for (int i = 1; i < 8; i++) {
				getBoardInfo().boardSquares[8][i].setType(pieceOrder1v1P1[orderArrayIndex]);
				getBoardInfo().boardSquares[8][i].setDirection(180);
				getBoardInfo().boardSquares[8][i].setOwner(1);
				getBoardInfo().boardSquares[8][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 2; i < 7; i++) {
				getBoardInfo().boardSquares[7][i].setType(pieceOrder1v1P1[orderArrayIndex]);
				getBoardInfo().boardSquares[7][i].setDirection(180);
				getBoardInfo().boardSquares[7][i].setOwner(1);
				getBoardInfo().boardSquares[7][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 3; i < 6; i++) {
				getBoardInfo().boardSquares[6][i].setType(8);
				getBoardInfo().boardSquares[6][i].setDirection(180);
				getBoardInfo().boardSquares[6][i].setOwner(1);
				getBoardInfo().boardSquares[6][i].setColor(color);
			}
			getBoardInfo().p1HitPieces = new String[15][2];
		} else {
			for (int i = 1; i < 8; i++) {
				getBoardInfo().boardSquares[0][i].setType(pieceOrder1v1P2[orderArrayIndex]);
				getBoardInfo().boardSquares[0][i].setDirection(0);
				getBoardInfo().boardSquares[0][i].setOwner(2);
				getBoardInfo().boardSquares[0][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 2; i < 7; i++) {
				getBoardInfo().boardSquares[1][i].setType(pieceOrder1v1P2[orderArrayIndex]);
				getBoardInfo().boardSquares[1][i].setDirection(0);
				getBoardInfo().boardSquares[1][i].setOwner(2);
				getBoardInfo().boardSquares[1][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 3; i < 6; i++) {
				getBoardInfo().boardSquares[2][i].setType(8);
				getBoardInfo().boardSquares[2][i].setDirection(0);
				getBoardInfo().boardSquares[2][i].setOwner(2);
				getBoardInfo().boardSquares[2][i].setColor(color);
			}
			getBoardInfo().p2HitPieces = new String[15][2];
		}
	}
	
	private void populateBoard1v1v1v1(String color, int playerNum) {
		int orderArrayIndex = 0;
		if (playerNum == 1) {
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i != 2) {
					direction = 225;
				} else {
					direction = 180;
				}
				getBoardInfo().boardSquares[8][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				getBoardInfo().boardSquares[8][i].setDirection(direction);
				getBoardInfo().boardSquares[8][i].setOwner(1);
				getBoardInfo().boardSquares[8][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				getBoardInfo().boardSquares[7][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				getBoardInfo().boardSquares[7][i].setDirection(225);
				getBoardInfo().boardSquares[7][i].setOwner(1);
				getBoardInfo().boardSquares[7][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i == 0) {
					direction = 270;
				} else {
					direction = 225;
				}
				getBoardInfo().boardSquares[6][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				getBoardInfo().boardSquares[6][i].setDirection(direction);
				getBoardInfo().boardSquares[6][i].setOwner(1);
				getBoardInfo().boardSquares[6][i].setColor(color);
				orderArrayIndex++;
			}
			getBoardInfo().p1HitPieces = new String[9][2];
		} else if (playerNum == 2) {
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i != 2) {
					direction = 315;
				} else {
					direction = 270;
				}
				getBoardInfo().boardSquares[i][0].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				getBoardInfo().boardSquares[i][0].setDirection(direction);
				getBoardInfo().boardSquares[i][0].setOwner(2);
				getBoardInfo().boardSquares[i][0].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				getBoardInfo().boardSquares[i][1].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				getBoardInfo().boardSquares[i][1].setDirection(315);
				getBoardInfo().boardSquares[i][1].setOwner(2);
				getBoardInfo().boardSquares[i][1].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 0; i < 3; i++) {
				int direction = 0;
				if (i == 0) {
					direction = 0;
				} else {
					direction = 315;
				}
				getBoardInfo().boardSquares[i][2].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				getBoardInfo().boardSquares[i][2].setDirection(direction);
				getBoardInfo().boardSquares[i][2].setOwner(2);
				getBoardInfo().boardSquares[i][2].setColor(color);
				orderArrayIndex++;
			}
			getBoardInfo().p2HitPieces = new String[9][2];
		} else if (playerNum == 3) {
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i != 6) {
					direction = 45;
				} else {
					direction = 0;
				}
				getBoardInfo().boardSquares[0][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				getBoardInfo().boardSquares[0][i].setDirection(direction);
				getBoardInfo().boardSquares[0][i].setOwner(3);
				getBoardInfo().boardSquares[0][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				getBoardInfo().boardSquares[1][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				getBoardInfo().boardSquares[1][i].setDirection(45);
				getBoardInfo().boardSquares[1][i].setOwner(3);
				getBoardInfo().boardSquares[1][i].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i == 8) {
					direction = 90;
				} else {
					direction = 45;
				}
				getBoardInfo().boardSquares[2][i].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				getBoardInfo().boardSquares[2][i].setDirection(direction);
				getBoardInfo().boardSquares[2][i].setOwner(3);
				getBoardInfo().boardSquares[2][i].setColor(color);
				orderArrayIndex++;
			}
			getBoardInfo().p3HitPieces = new String[9][2];
		} else {
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i != 6) {
					direction = 135;
				} else {
					direction = 90;
				}
				getBoardInfo().boardSquares[i][8].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				getBoardInfo().boardSquares[i][8].setDirection(direction);
				getBoardInfo().boardSquares[i][8].setOwner(4);
				getBoardInfo().boardSquares[i][8].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				getBoardInfo().boardSquares[i][7].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				getBoardInfo().boardSquares[i][7].setDirection(135);
				getBoardInfo().boardSquares[i][7].setOwner(4);
				getBoardInfo().boardSquares[i][7].setColor(color);
				orderArrayIndex++;
			}
			for (int i = 8; i > 5; i--) {
				int direction = 0;
				if (i == 8) {
					direction = 180;
				} else {
					direction = 135;
				}
				getBoardInfo().boardSquares[i][6].setType(pieceOrder1v1v1v1[orderArrayIndex]);
				getBoardInfo().boardSquares[i][6].setDirection(direction);
				getBoardInfo().boardSquares[i][6].setOwner(4);
				getBoardInfo().boardSquares[i][6].setColor(color);
				orderArrayIndex++;
			}
			getBoardInfo().p4HitPieces = new String[9][2];
		}
	}
	
	private void populateBoard2v2(String color, int playerNum) {
		int orderArrayIndex = 0;
		if (playerNum == 1) {
			for(int i = 1; i < 4; i++) {
				getBoardInfo().boardSquares[8][i].setType(pieceOrder2v2[orderArrayIndex]);
				getBoardInfo().boardSquares[8][i].setDirection(180);
				getBoardInfo().boardSquares[8][i].setOwner(1);
				getBoardInfo().boardSquares[8][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				getBoardInfo().boardSquares[7][i].setType(pieceOrder2v2[orderArrayIndex]);
				getBoardInfo().boardSquares[7][i].setDirection(180);
				getBoardInfo().boardSquares[7][i].setOwner(1);
				getBoardInfo().boardSquares[7][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				getBoardInfo().boardSquares[6][i].setType(8);
				getBoardInfo().boardSquares[6][i].setDirection(180);
				getBoardInfo().boardSquares[6][i].setOwner(1);
				getBoardInfo().boardSquares[6][i].setColor(color);
			}
			getBoardInfo().p1HitPieces = new String[9][2];
		} else if (playerNum == 2) {
			for(int i = 1; i < 4; i++) {
				getBoardInfo().boardSquares[0][i].setType(pieceOrder2v2[orderArrayIndex]);
				getBoardInfo().boardSquares[0][i].setDirection(0);
				getBoardInfo().boardSquares[0][i].setOwner(2);
				getBoardInfo().boardSquares[0][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 3; i > 0; i--) {
				getBoardInfo().boardSquares[1][i].setType(pieceOrder2v2[orderArrayIndex]);
				getBoardInfo().boardSquares[1][i].setDirection(0);
				getBoardInfo().boardSquares[1][i].setOwner(2);
				getBoardInfo().boardSquares[1][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 1; i < 4; i++) {
				getBoardInfo().boardSquares[2][i].setType(8);
				getBoardInfo().boardSquares[2][i].setDirection(0);
				getBoardInfo().boardSquares[2][i].setOwner(2);
				getBoardInfo().boardSquares[2][i].setColor(color);
			}
			getBoardInfo().p2HitPieces = new String[9][2];
		} else if (playerNum == 3) {
			for(int i = 7; i > 4; i--) {
				getBoardInfo().boardSquares[8][i].setType(pieceOrder2v2[orderArrayIndex]);
				getBoardInfo().boardSquares[8][i].setDirection(180);
				getBoardInfo().boardSquares[8][i].setOwner(3);
				getBoardInfo().boardSquares[8][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				getBoardInfo().boardSquares[7][i].setType(pieceOrder2v2[orderArrayIndex]);
				getBoardInfo().boardSquares[7][i].setDirection(180);
				getBoardInfo().boardSquares[7][i].setOwner(3);
				getBoardInfo().boardSquares[7][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				getBoardInfo().boardSquares[6][i].setType(8);
				getBoardInfo().boardSquares[6][i].setDirection(180);
				getBoardInfo().boardSquares[6][i].setOwner(3);
				getBoardInfo().boardSquares[6][i].setColor(color);
			}
			getBoardInfo().p3HitPieces = new String[9][2];
		} else {
			for(int i = 7; i > 4; i--) {
				getBoardInfo().boardSquares[0][i].setType(pieceOrder2v2[orderArrayIndex]);
				getBoardInfo().boardSquares[0][i].setDirection(0);
				getBoardInfo().boardSquares[0][i].setOwner(4);
				getBoardInfo().boardSquares[0][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 7; i > 4; i--) {
				getBoardInfo().boardSquares[1][i].setType(pieceOrder2v2[orderArrayIndex]);
				getBoardInfo().boardSquares[1][i].setDirection(0);
				getBoardInfo().boardSquares[1][i].setOwner(4);
				getBoardInfo().boardSquares[1][i].setColor(color);
				orderArrayIndex++;
			}
			for(int i = 5; i < 8; i++) {
				getBoardInfo().boardSquares[2][i].setType(8);
				getBoardInfo().boardSquares[2][i].setDirection(0);
				getBoardInfo().boardSquares[2][i].setOwner(4);
				getBoardInfo().boardSquares[2][i].setColor(color);
			}
			getBoardInfo().p4HitPieces = new String[9][2];
		}
	}
	
	public void loadBoard(Player[] players, int gameMode, String[][][] board) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				getBoardInfo().boardSquares[i][j].setType(Integer.parseInt(board[i][j][0]));
				getBoardInfo().boardSquares[i][j].setDirection(Integer.parseInt(board[i][j][1]));
				getBoardInfo().boardSquares[i][j].setOwner(Integer.parseInt(board[i][j][2]));
				getBoardInfo().boardSquares[i][j].setColor(board[i][j][3]);
			}
		}
	}
	
	public void loadHitPiecesIndexes(int[] hitPiecesIndexes) {
		getBoardInfo().setP1HitPiecesIndex(hitPiecesIndexes[0]);
		getBoardInfo().setP2HitPiecesIndex(hitPiecesIndexes[1]);
		getBoardInfo().setP3HitPiecesIndex(hitPiecesIndexes[2]);
		getBoardInfo().setP4HitPiecesIndex(hitPiecesIndexes[3]);
	}
	
	public void loadHitPieces(int gameMode, String[][][] hitPieces) {
		if (gameMode == 0) {
			getBoardInfo().p1HitPieces = new String[15][2];
			for (int i = 0; i < getBoardInfo().getP1HitPiecesIndex(); i++) {
				if (hitPieces[0][i] != null) {
					getBoardInfo().p1HitPieces[i] = hitPieces[0][i];
				}
			}
			getBoardInfo().p2HitPieces = new String[15][2];
			for (int i = 0; i < getBoardInfo().getP2HitPiecesIndex(); i++) {
				if (hitPieces[1][i] != null) {
					getBoardInfo().p2HitPieces[i] = hitPieces[1][i];
				}
			}
		} else {
			getBoardInfo().p1HitPieces = new String[9][2];
			for (int i = 0; i < getBoardInfo().getP1HitPiecesIndex(); i++) {
				if (hitPieces[0][i] != null) {
					getBoardInfo().p1HitPieces[i] = hitPieces[0][i];
				}
			}
			getBoardInfo().p2HitPieces = new String[9][2];
			for (int i = 0; i < getBoardInfo().getP2HitPiecesIndex(); i++) {
				if (hitPieces[1][i] != null) {
					getBoardInfo().p2HitPieces[i] = hitPieces[1][i];
				}
			}
			getBoardInfo().p3HitPieces = new String[9][2];
			for (int i = 0; i < getBoardInfo().getP3HitPiecesIndex(); i++) {
				if (hitPieces[2][i] != null) {
					getBoardInfo().p3HitPieces[i] = hitPieces[2][i];
				}
			}
			getBoardInfo().p4HitPieces = new String[9][2];
			for (int i = 0; i < getBoardInfo().getP4HitPiecesIndex(); i++) {
				if (hitPieces[3][i] != null) {
					getBoardInfo().p4HitPieces[i] = hitPieces[3][i];
				}
			}
		}
	}
	
	// direction = -45 rota hacia la izquierda, direction = 45 rota hacia la derecha
	public void rotatePiece(int x, int y, int direction) {
		int newDirection = getBoardInfo().boardSquares[x][y].getDirection() + direction;
		if (newDirection < 0) {
			newDirection = newDirection + 360;
		}
		getBoardInfo().boardSquares[x][y].setDirection(newDirection);
	}
}
