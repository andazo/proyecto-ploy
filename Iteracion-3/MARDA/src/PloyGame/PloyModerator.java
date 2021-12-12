package PloyGame;
import MARDA.Moderator;

/**
 * Concrete class for a moderator for Ploy in charge of reviewing the game's state to determine 
 * if a player is out, legal moves and game over conditions. 
 */
public class PloyModerator extends Moderator {

	/**
	 * Handles the clicking of a piece in case it is not currently active. This includes
	 * checking whose turn it is, highlighting legal moves and enabling rotation buttons.
	 * In the case a piece is currently active, it handles legality of moves, capturing 
	 * enemy pieces and checking the state of the game after a move.
	 *
	 * @param i location on the x axis of the board
	 * @param j location on the y axis of the board
	 * @param numPlayers the number of players in the game
	 * @param gameMode the game mode being played
	 * @param players array of players in the game
	 * @param board current instance of the board 
	 * @param gui current instance of the gui
	 */
	@Override
	public void clickedOn(int i, int j, int numPlayers, int gameMode, Object[] players, Object board, Object gui) {
		PieceInterface piece = (PieceInterface) ((PloyBoard) board).boardSquares[i][j].getPiece();
		String[][] moves = null;
		if (piece != null && !((PloyBoard) board).getPieceActive()) {
			if (((PloyPiece) piece).getOwner() == ((PloyBoard) board).getCurrentPlayer()) {	
				((PloyBoard) board).setPieceActive(true);
				((PloyGUI) gui).squaresPanels[i][j].setBackground(((PloyGUI) gui).boardColorHighlight);
				((PloyBoard) board).setLastI(i);
				((PloyBoard) board).setLastJ(j);
				int direction = ((PloyPiece) piece).getDirection();
				((PloyBoard) board).setOriginalDirection(direction);
				((PloyGUI) gui).rotateLeftBut.setEnabled(true);
				((PloyGUI) gui).rotateRightBut.setEnabled(true);
				((PloyGUI) gui).guiPrintLine("pieza activa");
				moves = getValidMoves(i, j, board);
				highlightMoves(moves, i, j, gui);
			} else {
				((PloyGUI) gui).guiPrintLine("Pieza no pertenece al jugador");
			}
		} else if (((PloyBoard) board).getPieceActive()) {
			int lastI = ((PloyBoard) board).getLastI();
			int lastJ = ((PloyBoard) board).getLastJ();
			if (!(lastI == i && lastJ == j)) {
				int originalDirection = ((PloyBoard) board).getOriginalDirection();
				if (((PloyPiece) ((PloyBoard) board).boardSquares[lastI][lastJ].getPiece()).getDirection() == originalDirection || ((PloyPiece) ((PloyBoard) board).boardSquares[lastI][lastJ].getPiece()).getType() == 8) {
					int targetPieceOwner = 0;
					if (((PloyBoard) board).boardSquares[i][j].getPiece() != null) {
						targetPieceOwner = ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner();
					}
					if (((PloyGUI) gui).squaresPanels[i][j].getBackground() == ((PloyGUI) gui).boardColorHighlight) {
						moves = getValidMoves(lastI, lastJ, board);
						cancelHighlightMoves(moves, lastI, lastJ, gui);
						if (targetPieceOwner != 0) {
							((PloyPlayer) players[targetPieceOwner - 1]).setNumPieces(((PloyPlayer) players[targetPieceOwner - 1]).getNumPieces() - 1);
							checkGameOver(((PloyBoard) board).boardSquares[i][j], ((PloyBoard) board).boardSquares[((PloyBoard) board).getLastI()][((PloyBoard) board).getLastJ()], gameMode, players, board, gui);
						}
						String targetPieceType = "";
						String targetPieceColor = "";
						if (((PloyBoard) board).boardSquares[i][j].getPiece() != null) {
							targetPieceType = Integer.toString(((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getType());
							targetPieceColor = ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getColor();
						}
						if (numPlayers == 2) {
							if (targetPieceColor.equals(((PloyPlayer) players[0]).getColor())) {
								((PloyBoard) board).p1HitPieces[((PloyBoard) board).getP1HitPiecesIndex()][0] = targetPieceType;
								((PloyBoard) board).p1HitPieces[((PloyBoard) board).getP1HitPiecesIndex()][1] = targetPieceColor;
								((PloyBoard) board).setP1HitPiecesIndex(((PloyBoard) board).getP1HitPiecesIndex() + 1);
							} else if (targetPieceColor.equals(((PloyPlayer) players[1]).getColor())) {
								((PloyBoard) board).p2HitPieces[((PloyBoard) board).getP2HitPiecesIndex()][0] = targetPieceType;
								((PloyBoard) board).p2HitPieces[((PloyBoard) board).getP2HitPiecesIndex()][1] = targetPieceColor;
								((PloyBoard) board).setP2HitPiecesIndex(((PloyBoard) board).getP2HitPiecesIndex() + 1);
							}
						} else {
							if (targetPieceColor.equals(((PloyPlayer) players[0]).getColor())) {
								((PloyBoard) board).p1HitPieces[((PloyBoard) board).getP1HitPiecesIndex()][0] = targetPieceType;
								((PloyBoard) board).p1HitPieces[((PloyBoard) board).getP1HitPiecesIndex()][1] = targetPieceColor;
								((PloyBoard) board).setP1HitPiecesIndex(((PloyBoard) board).getP1HitPiecesIndex() + 1);
							} else if (targetPieceColor.equals(((PloyPlayer) players[1]).getColor())) {
								((PloyBoard) board).p2HitPieces[((PloyBoard) board).getP2HitPiecesIndex()][0] = targetPieceType;
								((PloyBoard) board).p2HitPieces[((PloyBoard) board).getP2HitPiecesIndex()][1] = targetPieceColor;
								((PloyBoard) board).setP2HitPiecesIndex(((PloyBoard) board).getP2HitPiecesIndex() + 1);
							} else if (targetPieceColor.equals(((PloyPlayer) players[2]).getColor())) {
								((PloyBoard) board).p3HitPieces[((PloyBoard) board).getP3HitPiecesIndex()][0] = targetPieceType;
								((PloyBoard) board).p3HitPieces[((PloyBoard) board).getP3HitPiecesIndex()][1] = targetPieceColor;
								((PloyBoard) board).setP3HitPiecesIndex(((PloyBoard) board).getP3HitPiecesIndex() + 1);
							} else if (targetPieceColor.equals(((PloyPlayer) players[3]).getColor())) {
								((PloyBoard) board).p4HitPieces[((PloyBoard) board).getP4HitPiecesIndex()][0] = targetPieceType;
								((PloyBoard) board).p4HitPieces[((PloyBoard) board).getP4HitPiecesIndex()][1] = targetPieceColor;
								((PloyBoard) board).setP4HitPiecesIndex(((PloyBoard) board).getP4HitPiecesIndex() + 1);
							}
						}
						((PloyGUI) gui).squaresPanels[i][j].setIcon(((PloyGUI) gui).squaresPanels[lastI][lastJ].getIcon());
						((PloyGUI) gui).squaresPanels[lastI][lastJ].setIcon(null);
						((PloyGUI) gui).squaresPanels[lastI][lastJ].setBackground(((PloyGUI) gui).boardColorPurple);
						((PloyBoard) board).boardSquares[i][j].setPiece((PloyPiece) ((PloyBoard) board).boardSquares[lastI][lastJ].getPiece());
						((PloyBoard) board).boardSquares[lastI][lastJ].setPiece(null);;
						((PloyBoard) board).setPieceActive(false);
						((PloyGUI) gui).rotateLeftBut.setEnabled(false);
						((PloyGUI) gui).rotateRightBut.setEnabled(false);
						((PloyGUI) gui).guiPrintLine("Pieza movida");
						((PloyBoard) board).setCurrentPlayer(((PloyBoard) board).getCurrentPlayer() + 1);
						if (numPlayers == 2) {
							if (((PloyBoard) board).getCurrentPlayer() == 3) {
								((PloyBoard) board).setCurrentPlayer(((PloyBoard) board).getCurrentPlayer() - 2);
							}
						} else {
							if (((PloyBoard) board).getCurrentPlayer() == 5) {
								((PloyBoard) board).setCurrentPlayer(((PloyBoard) board).getCurrentPlayer() - 4);
							}
							while (((PloyPlayer) players[((PloyBoard) board).getCurrentPlayer() - 1]).getLost() == true) {
								((PloyBoard) board).setCurrentPlayer(((PloyBoard) board).getCurrentPlayer() + 1);
								if (((PloyBoard) board).getCurrentPlayer() == 5) {
									((PloyBoard) board).setCurrentPlayer(((PloyBoard) board).getCurrentPlayer() - 4);
								}
							}
						}
					} else {
						((PloyGUI) gui).guiPrintLine("Movimiento no permitido");
					}
				} else {
					((PloyGUI) gui).guiPrintLine("Pieza rotada, imposible mover");
				}
			} else {
				((PloyGUI) gui).squaresPanels[lastI][lastJ].setBackground(((PloyGUI) gui).boardColorPurple);
				((PloyBoard) board).setPieceActive(false);
				((PloyGUI) gui).rotateLeftBut.setEnabled(false);
				((PloyGUI) gui).rotateRightBut.setEnabled(false);
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getDirection() != ((PloyBoard) board).getOriginalDirection()) {
					((PloyGUI) gui).guiPrintLine("Pieza rotada");
					((PloyBoard) board).setCurrentPlayer(((PloyBoard) board).getCurrentPlayer() + 1);
					if (numPlayers == 2) {
						if (((PloyBoard) board).getCurrentPlayer() == 3) {
							((PloyBoard) board).setCurrentPlayer(((PloyBoard) board).getCurrentPlayer() - 2);
						}
					} else {
						if (((PloyBoard) board).getCurrentPlayer() == 5) {
							((PloyBoard) board).setCurrentPlayer(((PloyBoard) board).getCurrentPlayer() - 4);
						}
						while (((PloyPlayer) players[((PloyBoard) board).getCurrentPlayer() - 1]).getLost() == true) {
							((PloyBoard) board).setCurrentPlayer(((PloyBoard) board).getCurrentPlayer() + 1);
							if (((PloyBoard) board).getCurrentPlayer() == 5) {
								((PloyBoard) board).setCurrentPlayer(((PloyBoard) board).getCurrentPlayer() - 4);
							}
						}
					}
				} else {
					((PloyGUI) gui).guiPrintLine("Pieza no movida");
				}
				moves = getValidMoves(((PloyBoard) board).getLastI(), ((PloyBoard) board).getLastJ(), board);
				cancelHighlightMoves(moves, ((PloyBoard) board).getLastI(), ((PloyBoard) board).getLastJ(), gui);
			}
		}
	}

	/**
	 * Checks if the game is over after a move has been made.
	 *
	 * @param hitInfo the information of the piece that was captured
	 * @param attackerInfo the information of the piece that made the attack
	 * @param gameMode the game mode being played
	 * @param players the players in the game
	 * @param board instance of the board
	 * @param gui instance of the gui
	 */
	@Override
	protected void checkGameOver(Object hitInfo, Object attackerInfo, int gameMode, Object[] players, Object board, Object gui) {
		if (((PloyPiece) ((PloyBoardSquare) hitInfo).getPiece()).getType() == 0 || ((PloyPlayer) players[((PloyPiece) ((PloyBoardSquare) hitInfo).getPiece()).getOwner()-1]).getNumPieces() == 1) {
			playerLost(hitInfo, attackerInfo, gameMode, players, board, gui);
		}
	}

	/**
	 * Shows a prompt allowing the user to decide what to do after a game is finished.
	 *
	 * @param gui instance of the gui
	 * @return variable indicating if the user wants a new game
	 */
	@Override
	protected char finished(Object gui) {
		String[] options = {"Mirar Tablero", "Terminar"};
		char checkBoard = ' ';
		int input = 0;
		input = ((PloyGUI) gui).inputMessageWithOptions("Seleccione lo que desea hacer", "Menu Gamer Over", options);
		if (input == 0) {
			checkBoard = 'Y';
		} else if (input == 1) {
			System.exit(0);
		}

		return checkBoard;
	}

	/**
	 * Removes a player from the match
	 *
	 * @param hitInfo the information of the piece that was captured
	 * @param attackerInfo the information of the piece that made the attack
	 * @param gameMode the game mode being played
	 * @param players the players in the game
	 * @param board instance of the board
	 * @param gui instance of the gui
	 */
	@Override
	protected void playerLost(Object hitInfo, Object attackerInfo, int gameMode, Object[] players, Object board, Object gui) {
		switch(gameMode) {
		case 0: //1v1
			((PloyPlayer) players[((PloyPiece) ((PloyBoardSquare) hitInfo).getPiece()).getOwner()-1]).setLost(true);
			((PloyGUI) gui).guiPrintLine("Game Over, tablero bloqueado"); 
			((PloyGUI) gui).printSimpleMessage("Game Over \n" + ((PloyPlayer) players[((PloyPiece) ((PloyBoardSquare) attackerInfo).getPiece()).getOwner()-1]).getName() + " ha ganado");
			char finishedGame = finished(gui);
			if (finishedGame == 'Y' ) {
				removeActions(gui);
			} else {
				System.exit(0);
			}
			break;
		case 1: //1v1v1v1
			((PloyPlayer) players[((PloyPiece) ((PloyBoardSquare) hitInfo).getPiece()).getOwner()-1]).setLost(true);
			((PloyBoard) board).setActivePlayers(((PloyBoard) board).getActivePlayers()-1);
			if (((PloyBoard) board).getActivePlayers() == 1) {
				removeActions(gui);
				((PloyGUI) gui).printSimpleMessage("Game Over \n" + ((PloyPlayer) players[((PloyPiece) ((PloyBoardSquare) attackerInfo).getPiece()).getOwner()-1]).getName() + " ha ganado");
				((PloyGUI) gui).guiPrintLine("Game Over 1v1v1v1, tablero bloqueado");
				char finishedGame1 = finished(gui);
				if (finishedGame1 == 'Y' ) {
					removeActions(gui);
				} else {
					System.exit(0);
				}
			} else {
				((PloyBoard) board).updateOwner(((PloyPiece) ((PloyBoardSquare) hitInfo).getPiece()).getOwner(), ((PloyPiece) ((PloyBoardSquare) attackerInfo).getPiece()).getOwner()); //jugador controla las piezas ahora
				((PloyGUI) gui).printSimpleMessage(((PloyPlayer) players[((PloyPiece) ((PloyBoardSquare) hitInfo).getPiece()).getOwner()-1]).getName() + " ha perdido, ahora "
						+ ((PloyPlayer) players[((PloyPiece) ((PloyBoardSquare) hitInfo).getPiece()).getOwner()-1]).getName() + " controla sus piezas.");
			}
			break;
		case 2: //2v2
			((PloyPlayer) players[((PloyPiece) ((PloyBoardSquare) hitInfo).getPiece()).getOwner()-1]).setLost(true);
			int friend = ((PloyPlayer) players[(((PloyPiece) ((PloyBoardSquare) hitInfo).getPiece()).getOwner()-1)]).getFriend()-1;
			if (((PloyPlayer) players[friend]).getLost()) { //si los dos ya perdieron
				removeActions(gui);
				((PloyGUI) gui).printSimpleMessage("Game Over \n" + ((PloyPlayer) players[((PloyPiece) ((PloyBoardSquare) attackerInfo).getPiece()).getOwner()-1]).getName()
						+ " y " + ((PloyPlayer) players[((PloyPlayer) players[((PloyPiece) ((PloyBoardSquare) attackerInfo).getPiece()).getOwner()-1]).getFriend()-1]).getName() + " han ganado");
				((PloyGUI) gui).guiPrintLine("Game Over 2v2, tablero bloqueado");
				char finishedGame3 = finished(gui);
				if (finishedGame3 == 'Y' ) {
					removeActions(gui);
				} else {
					System.exit(0);
				}
			} else {
				((PloyBoard) board).updateOwner(((PloyPiece) ((PloyBoardSquare) hitInfo).getPiece()).getOwner(), ((PloyPlayer) players[((PloyPiece) ((PloyBoardSquare) hitInfo).getPiece()).getOwner()-1]).getFriend()); //amigo controla las piezas ahora
				((PloyBoard) board).setActivePlayers(((PloyBoard) board).getActivePlayers()-1);
				((PloyGUI) gui).printSimpleMessage(((PloyPlayer) players[((PloyPiece) ((PloyBoardSquare) hitInfo).getPiece()).getOwner()-1]).getName() + " ha perdido");
			}
			break;
		}
	}

	/**
	 * Removes the actions from the board's squares, locking the board.
	 *
	 * @param gui instance of the gui
	 */
	@Override
	protected void removeActions(Object gui) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				try {
					((PloyGUI) gui).squaresPanels[i][j].removeMouseListener(((PloyGUI) gui).squaresPanels[i][j].getMouseListeners()[0]);
				} catch (Exception e) { }
			}
		}
	}

	/**
	 * Gets the valid moves for the piece currently in play.
	 *
	 * @param i location on the x axis of the board
	 * @param j location on the y axis of the board
	 * @param board instance of the board
	 * @return matrix of valid moves for the piece
	 */
	@Override
	protected String[][] getValidMoves(int i, int j, Object board) {
		PieceInterface piece = (PieceInterface) ((PloyBoard) board).boardSquares[i][j].getPiece();
		String[][] moves = piece.getMoves();
		moves = rotateMoves(moves, i, j, board);

		// Exclude other pieces
		// Allies

		// -1 | +1
		if (i - 1 >= 0 && j - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 1][j - 1].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 1][j - 1].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[2][2] = "-";
					moves[1][1] = "-";
					moves[0][0] = "-";
				}
			}
		}
		if (i - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 1][j].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 1][j].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[2][3] = "-";
					moves[1][3] = "-";
					moves[0][3] = "-";
				}
			}
		}
		if (i - 1 >= 0 && j + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i - 1][j + 1].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 1][j + 1].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[2][4] = "-";
					moves[1][5] = "-";
					moves[0][6] = "-";
				}
			}
		}
		if (j - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i][j - 1].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i][j - 1].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[3][2] = "-";
					moves[3][1] = "-";
					moves[3][0] = "-";
				}
			}
		}
		if (j + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i][j + 1].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i][j + 1].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[3][4] = "-";
					moves[3][5] = "-";
					moves[3][6] = "-";
				}
			}
		}
		if (i + 1 <= 8 && j - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i + 1][j - 1].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 1][j - 1].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[4][2] = "-";
					moves[5][1] = "-";
					moves[6][0] = "-";
				}
			}
		}
		if (i + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 1][j].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 1][j].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[4][3] = "-";
					moves[5][3] = "-";
					moves[6][3] = "-";
				}
			}
		}
		if (i + 1 <= 8 && j + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 1][j + 1].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 1][j + 1].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[4][4] = "-";
					moves[5][5] = "-";
					moves[6][6] = "-";
				}
			}
		}

		// -2 | +2
		if (i - 2 >= 0 && j - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 2][j - 2].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 2][j - 2].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[1][1] = "-";
					moves[0][0] = "-";
				}
			}
		}
		if (i - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 2][j].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 2][j].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[1][3] = "-";
					moves[0][3] = "-";
				}
			}
		}
		if (i - 2 >= 0 && j + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i - 2][j + 2].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 2][j + 2].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[1][5] = "-";
					moves[0][6] = "-";
				}
			}
		}
		if (j - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i][j - 2].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i][j - 2].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[3][1] = "-";
					moves[3][0] = "-";
				}
			}
		}
		if (j + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i][j + 2].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i][j + 2].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[3][5] = "-";
					moves[3][6] = "-";
				}
			}
		}
		if (i + 2 <= 8 && j - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i + 2][j - 2].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 2][j - 2].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[5][1] = "-";
					moves[6][0] = "-";
				}
			}
		}
		if (i + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 2][j].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 2][j].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[5][3] = "-";
					moves[6][3] = "-";
				}
			}
		}
		if (i + 2 <= 8 && j + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 2][j + 2].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 2][j + 2].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[5][5] = "-";
					moves[6][6] = "-";
				}
			}
		}

		// -3 | +3
		if (i - 3 >= 0 && j - 3 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 3][j - 3].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 3][j - 3].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[0][0] = "-";
				}
			}
		}
		if (i - 3 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 3][j].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 3][j].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[0][3] = "-";
				}
			}
		}
		if (i - 3 >= 0 && j + 3 <= 8) {
			if (((PloyBoard) board).boardSquares[i - 3][j + 3].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 3][j + 3].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[0][6] = "-";
				}
			}
		}
		if (j - 3 >= 0) {
			if (((PloyBoard) board).boardSquares[i][j - 3].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i][j - 3].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[3][0] = "-";
				}
			}
		}
		if (j + 3 <= 8) {
			if (((PloyBoard) board).boardSquares[i][j + 3].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i][j + 3].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[3][6] = "-";
				}
			}
		}
		if (i + 3 <= 8 && j - 3 >= 0) {
			if (((PloyBoard) board).boardSquares[i + 3][j - 3].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 3][j - 3].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[6][0] = "-";
				}
			}
		}
		if (i + 3 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 3][j].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 3][j].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[6][3] = "-";
				}
			}
		}
		if (i + 3 <= 8 && j + 3 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 3][j + 3].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 3][j + 3].getPiece()).getOwner() == ((PloyPiece) piece).getOwner()) {
					moves[6][6] = "-";
				}
			}
		}

		// Enemies

		// -2 | +2
		if (i - 2 >= 0 && j - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 2][j - 2].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 2][j - 2].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[1][1] == "O") {
						moves[0][0] = "-";
					}
				}
			}
		}
		if (i - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 2][j].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 2][j].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[1][3] == "O") {
						moves[0][3] = "-";
					}
				}
			}
		}
		if (i - 2 >= 0 && j + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i - 2][j + 2].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 2][j + 2].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[1][5] == "O") {
						moves[0][6] = "-";
					}
				}
			}
		}
		if (j - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i][j - 2].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i][j - 2].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[3][1] == "O") {
						moves[3][0] = "-";
					}
				}
			}
		}
		if (j + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i][j + 2].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i][j + 2].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[3][5] == "O") {
						moves[3][6] = "-";
					}
				}
			}
		}
		if (i + 2 <= 8 && j - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i + 2][j - 2].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 2][j - 2].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[5][1] == "O") {
						moves[6][0] = "-";
					}
				}
			}
		}
		if (i + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 2][j].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 2][j].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[5][3] == "O") {
						moves[6][3] = "-";
					}
				}
			}
		}
		if (i + 2 <= 8 && j + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 2][j + 2].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 2][j + 2].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[5][5] == "O") {
						moves[6][6] = "-";
					}
				}
			}
		}

		// -1 | +1
		if (i - 1 >= 0 && j - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 1][j - 1].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 1][j - 1].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[2][2] == "O") {
						moves[1][1] = "-";
						moves[0][0] = "-";
					}
				}
			}
		}
		if (i - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 1][j].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 1][j].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[2][3] == "O") {
						moves[1][3] = "-";
						moves[0][3] = "-";
					}
				}
			}
		}
		if (i - 1 >= 0 && j + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i - 1][j + 1].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i - 1][j + 1].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[2][4] == "O") {
						moves[1][5] = "-";
						moves[0][6] = "-";
					}
				}
			}
		}
		if (j - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i][j - 1].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i][j - 1].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[3][2] == "O") {
						moves[3][1] = "-";
						moves[3][0] = "-";
					}
				}
			}
		}
		if (j + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i][j + 1].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i][j + 1].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[3][4] == "O") {
						moves[3][5] = "-";
						moves[3][6] = "-";
					}
				}
			}
		}
		if (i + 1 <= 8 && j - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i + 1][j - 1].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 1][j - 1].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[4][2] == "O") {
						moves[5][1] = "-";
						moves[6][0] = "-";
					}
				}
			}
		}
		if (i + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 1][j].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 1][j].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[4][3] == "O") {
						moves[5][3] = "-";
						moves[6][3] = "-";
					}
				}
			}
		}
		if (i + 1 <= 8 && j + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 1][j + 1].getPiece() != null) {
				if (((PloyPiece) ((PloyBoard) board).boardSquares[i + 1][j + 1].getPiece()).getOwner() != ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getOwner()) {
					if (moves[4][4] == "O") {
						moves[5][5] = "-";
						moves[6][6] = "-";
					}
				}
			}
		}

		return moves;
	}

	/**
	 * Highlights the legal moves for the piece currently in play.
	 *
	 * @param moves the valid moves for a piece
	 * @param i location on the x axis of the board
	 * @param j location on the y axis of the board
	 * @param gui instance of the gui
	 */
	@Override
	protected void highlightMoves(String[][] moves, int i, int j, Object gui) {

		for (int x = 0; x < 7; x++) {
			for (int y = 0; y < 7; y++) {
				try {
					if (moves[x][y] == "O") {
						((PloyGUI) gui).squaresPanels[(i - 3) + x][(j - 3) + y].setBackground(((PloyGUI) gui).boardColorHighlight);
					}
				} catch (Exception e) { }
			}
		}
	}

	/**
	 * Removes the highlights of available moves.
	 *
	 * @param moves the valid moves for a piece
	 * @param i location on the x axis of the board
	 * @param j location on the y axis of the board
	 * @param gui instance of the gui
	 */
	@Override
	protected void cancelHighlightMoves(String[][] moves, int i, int j, Object gui) {
		for (int x = 0; x < 7; x++) {
			for (int y = 0; y < 7; y++) {
				if (!(x == 3 && y == 3)) {
					try {
						((PloyGUI) gui).squaresPanels[(i - 3) + x][(j - 3) + y].setBackground(((PloyGUI) gui).boardColorPurple);
					} catch (Exception e) { }
				}
			}
		}
	}

	/**
	 * Rotates the highlights for legal moves for a piece.
	 *
	 * @param moves the valid moves for a piece
	 * @param i location on the x axis of the board
	 * @param j location on the y axis of the board
	 * @param board instance of the board
	 * @return matrix of valid moves for the piece after rotation
	 */
	protected String[][] rotateMoves(String[][] moves, int i, int j, Object board) {
		String[][] rotatedMoves = new String[7][7];
		int direction = ((PloyPiece) ((PloyBoard) board).boardSquares[i][j].getPiece()).getDirection();
		while (direction > 0) {
			for (int x = 0; x < 7; x++) {
				for (int y = 0; y < 7; y++) {
					if (moves[x][y] != null) {
						if (x == 0) {
							if (y < 4) {
								rotatedMoves[x][y + 3] = moves[x][y];
							} else if (y == 4) {
								rotatedMoves[x + 1][y + 2] = moves[x][y];
							} else if (y == 5) {
								rotatedMoves[x + 2][y + 1] = moves[x][y];
							} else if (y == 6) {
								rotatedMoves[x + 3][y] = moves[x][y];
							}
						} else if (x == 1) {
							if (y < 4) {
								rotatedMoves[x][y + 2] = moves[x][y];
							} else if (y == 4) {
								rotatedMoves[x + 1][y + 1] = moves[x][y];
							} else if (y == 5) {
								rotatedMoves[x + 2][y] = moves[x][y];
							}
						} else if (x == 2) {
							if (y < 4) {
								rotatedMoves[x][y + 1] = moves[x][y];
							} else if (y == 4) {
								rotatedMoves[x + 1][y] = moves[x][y];
							}
						} else if (x == 3) {
							if (y == 4) {
								rotatedMoves[x + 1][y] = moves[x][y];
							} else if (y == 5) {
								rotatedMoves[x + 2][y] = moves[x][y];
							} else if (y == 6) {
								rotatedMoves[x + 3][y] = moves[x][y];
							} else if (y == 2) {
								rotatedMoves[x - 1][y] = moves[x][y];
							} else if (y == 1) {
								rotatedMoves[x - 2][y] = moves[x][y];
							} else if (y == 0) {
								rotatedMoves[x - 3][y] = moves[x][y];
							} else {
								rotatedMoves[x][y] = moves[x][y];
							}
						} else if (x == 4) {
							if (y > 2) {
								rotatedMoves[x][y - 1] = moves[x][y];
							} else if (y == 2) {
								rotatedMoves[x - 1][y] = moves[x][y];
							}
						} else if (x == 5) {
							if (y > 2) {
								rotatedMoves[x][y - 2] = moves[x][y];
							} else if (y == 2) {
								rotatedMoves[x - 1][y - 1] = moves[x][y];
							} else if (y == 1) {
								rotatedMoves[x - 2][y] = moves[x][y];
							}
						} else if (x == 6) {
							if (y > 2) {
								rotatedMoves[x][y - 3] = moves[x][y];
							} else if (y == 2) {
								rotatedMoves[x - 1][y - 2] = moves[x][y];
							} else if (y == 1) {
								rotatedMoves[x - 2][y - 1] = moves[x][y];
							} else if (y == 0) {
								rotatedMoves[x - 3][y] = moves[x][y];
							}
						}
					}
				}
			}
			moves = rotatedMoves;			
			rotatedMoves = new String[7][7];
			direction = direction - 45;
		}
		return moves;
	}
}
