import java.awt.Component;

public class PloyModerator extends Moderator {
	
	/**
     * @param i
     * @param j
     * @param numPlayers
     * @param players
     */
	@Override
    public void clickedOn(int i, int j, int numPlayers, int gameMode, Object[] players, Object board, Object gui) {
    	String[][] moves = null;
    	//if (((PloyBoard) board).boardSquares[i][j].getColor().equals(players[((PloyBoard) board).getCurrentPlayer() - 1].getColor()) && !((PloyBoard) board).getPieceActive()) {
  		if (((PloyBoard) board).boardSquares[i][j].getOwner() == ((PloyBoard) board).getCurrentPlayer() && !((PloyBoard) board).getPieceActive()) {	
				if (((PloyBoard) board).boardSquares[i][j].getType() != -1) {
					((PloyBoard) board).setPieceActive(true);
			  		((PloyGUI) gui).squaresPanels[i][j].setBackground(((PloyGUI) gui).boardColorHighlight);
				  	((PloyBoard) board).setLastI(i);
				  	((PloyBoard) board).setLastJ(j);
				 	int direction = ((PloyBoard) board).boardSquares[i][j].getDirection();
				 	((PloyBoard) board).setOriginalDirection(direction);
			 		((PloyGUI) gui).rotateLeftBut.setEnabled(true);
					((PloyGUI) gui).rotateRightBut.setEnabled(true);
			  		((PloyGUI) gui).guiPrintLine("pieza activa");
			  		moves = getValidMoves(i, j, board);
			  		highlightMoves(moves, i, j, gui);
				}
		} else if (((PloyBoard) board).getPieceActive()) {
			int lastI = ((PloyBoard) board).getLastI();
			int lastJ = ((PloyBoard) board).getLastJ();
			if (!(lastI == i && lastJ == j)) {
				int originalDirection = ((PloyBoard) board).getOriginalDirection();
				if (((PloyBoard) board).boardSquares[lastI][lastJ].getDirection() == originalDirection || ((PloyBoard) board).boardSquares[lastI][lastJ].getType() == 8) {
					int targetPieceOwner = ((PloyBoard) board).boardSquares[i][j].getOwner();
					if (((PloyGUI) gui).squaresPanels[i][j].getBackground() == ((PloyGUI) gui).boardColorHighlight) {
						
						moves = getValidMoves(lastI, lastJ, board);
						cancelHighlightMoves(moves, lastI, lastJ, gui);
						
						if (targetPieceOwner != 0) {
							((PloyPlayer) players[targetPieceOwner - 1]).setNumPieces(((PloyPlayer) players[targetPieceOwner - 1]).getNumPieces() - 1);
							checkGameOver(((PloyBoard) board).boardSquares[i][j], ((PloyBoard) board).boardSquares[((PloyBoard) board).getLastI()][((PloyBoard) board).getLastJ()], gameMode, players, board, gui);
						}
						
						String targetPieceColor = ((PloyBoard) board).boardSquares[i][j].getColor();
						String targetPieceType = Integer.toString(((PloyBoard) board).boardSquares[i][j].getType());
						
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
						((PloyBoard) board).boardSquares[i][j].setType(((PloyBoard) board).boardSquares[lastI][lastJ].getType());
						((PloyBoard) board).boardSquares[lastI][lastJ].setType(-1);
						((PloyBoard) board).boardSquares[i][j].setOwner(((PloyBoard) board).boardSquares[lastI][lastJ].getOwner());
						((PloyBoard) board).boardSquares[lastI][lastJ].setOwner(0);
						((PloyBoard) board).boardSquares[i][j].setDirection(((PloyBoard) board).boardSquares[lastI][lastJ].getDirection());
						((PloyBoard) board).boardSquares[lastI][lastJ].setDirection(0);
						((PloyBoard) board).boardSquares[i][j].setColor(((PloyBoard) board).boardSquares[lastI][lastJ].getColor());
						((PloyBoard) board).boardSquares[lastI][lastJ].setColor("-");
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
				if (((PloyBoard) board).boardSquares[i][j].getDirection() != ((PloyBoard) board).getOriginalDirection()) {
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
    	} else {
    		((PloyGUI) gui).guiPrintLine("Pieza no pertenece al jugador");
    	}
	}
    
    /**
     * @param hitInfo
     * @param attackerInfo
     */
	@Override
    protected void checkGameOver(Object hitInfo, Object attackerInfo, int gameMode, Object[] players, Object board, Object gui) {
    	if (((PloyBoardSquare) hitInfo).getType() == 0 || ((PloyPlayer) players[((PloyBoardSquare) hitInfo).getOwner()-1]).getNumPieces() == 1) {
    		playerLost(hitInfo, attackerInfo, gameMode, players, board, gui);
    	}
    }
    
    /**
     * @param msg
     * @return
     */
	@Override
    protected char finished(Object gui) {
		String[] options = {"Mirar Tablero", "Terminar"};
		char newGame = ' ';
    	int input = 0;
	    input = ((PloyGUI) gui).inputMessageWithOptions("Seleccione lo que desea hacer", "Menu Gamer Over", options);
	    if (input == 0) {
	    	newGame = 'Y';
	    } else if (input == 1) {
	    	System.exit(0);
	    }
	    
	    return newGame;
	}
    
    /**
     * @param hitInfo
     * @param attackerInfo
     * @param gameMode
     */
	@Override
    protected void playerLost(Object hitInfo, Object attackerInfo, int gameMode, Object[] players, Object board, Object gui) {
        switch(gameMode) {
        case 0: //1v1
        	((PloyPlayer) players[((PloyBoardSquare) hitInfo).getOwner()-1]).setLost(true);
        	((PloyGUI) gui).guiPrintLine("Game Over, tablero bloqueado"); 
        	((PloyGUI) gui).printSimpleMessage("Game Over \n" + ((PloyPlayer) players[((PloyBoardSquare) attackerInfo).getOwner()-1]).getName() + " ha ganado");
        	char finishedGame = finished(gui);
        	if (finishedGame == 'Y' ) {
        		removeActions(gui);
        	} else {
        		System.exit(0);
        	}
        	break;
        case 1: //1v1v1v1
            ((PloyPlayer) players[((PloyBoardSquare) hitInfo).getOwner()-1]).setLost(true);
            ((PloyBoard) board).setActivePlayers(((PloyBoard) board).getActivePlayers()-1);
            if (((PloyBoard) board).getActivePlayers() == 1) {
            	removeActions(gui);
            	((PloyGUI) gui).printSimpleMessage("Game Over \n" + ((PloyPlayer) players[((PloyBoardSquare) attackerInfo).getOwner()-1]).getName() + " ha ganado");
	            ((PloyGUI) gui).guiPrintLine("Game Over 1v1v1v1, tablero bloqueado");
	            char finishedGame1 = finished(gui);
	            if (finishedGame1 == 'Y' ) {
	            	removeActions(gui);
	            } else {
	            	System.exit(0);
	            }
            } else {
            	((PloyBoard) board).updateOwner(((PloyBoardSquare) hitInfo).getOwner(), ((PloyBoardSquare) attackerInfo).getOwner()); //jugador controla las piezas ahora
            	((PloyGUI) gui).printSimpleMessage(((PloyPlayer) players[((PloyBoardSquare) hitInfo).getOwner()-1]).getName() + " ha perdido, ahora "
            	+ ((PloyPlayer) players[((PloyBoardSquare) hitInfo).getOwner()-1]).getName() + " controla sus piezas.");
            }
        	break;
        case 2: //2v2
        	((PloyPlayer) players[((PloyBoardSquare) hitInfo).getOwner()-1]).setLost(true);
            int friend = ((PloyPlayer) players[(((PloyBoardSquare) hitInfo).getOwner()-1)]).getFriend()-1;
            if (((PloyPlayer) players[friend]).getLost()) { //si los dos ya perdieron
              removeActions(gui);
              ((PloyGUI) gui).printSimpleMessage("Game Over \n" + ((PloyPlayer) players[((PloyBoardSquare) attackerInfo).getOwner()-1]).getName()
            		+ " y " + ((PloyPlayer) players[((PloyPlayer) players[((PloyBoardSquare) attackerInfo).getOwner()-1]).getFriend()-1]).getName() + " han ganado");
              ((PloyGUI) gui).guiPrintLine("Game Over 2v2, tablero bloqueado");
              char finishedGame3 = finished(gui);
              if (finishedGame3 == 'Y' ) {
               	removeActions(gui);
              } else {
            	System.exit(0);
              }
	        } else {
	        	((PloyBoard) board).updateOwner(((PloyBoardSquare) hitInfo).getOwner(), ((PloyPlayer) players[((PloyBoardSquare) hitInfo).getOwner()-1]).getFriend()); //amigo controla las piezas ahora
	        	((PloyBoard) board).setActivePlayers(((PloyBoard) board).getActivePlayers()-1);
	        	((PloyGUI) gui).printSimpleMessage(((PloyPlayer) players[((PloyBoardSquare) hitInfo).getOwner()-1]).getName() + " ha perdido");
	      }
          break;
        }
    }
    
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
    
    /*
     * Valid moves
     * 
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|O|-|-|-|
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|O|-|-|-|
     * |-|-|O|-|O|-|-|				|-|-|-|-|-|-|-|				|-|-|-|O|-|-|-|
     * |-|-|-|P|-|-|-| commander	|O|O|O|P|O|O|O| lance 1		|-|-|-|P|-|-|-| lance 2
     * |-|-|O|-|O|-|-|				|-|-|-|O|-|-|-|				|-|-|O|-|O|-|-|
     * |-|-|-|-|-|-|-|				|-|-|-|O|-|-|-|				|-|O|-|-|-|O|-|
     * |-|-|-|-|-|-|-|				|-|-|-|O|-|-|-|				|O|-|-|-|-|-|O|
     * 
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * |-|-|-|P|-|-|-| lance 3		|-|-|-|P|-|-|-| probe 1		|-|-|-|P|-|-|-| probe 2
     * |-|-|O|O|O|-|-|				|-|-|O|O|-|-|-|				|-|-|0|-|0|-|-|
     * |-|O|-|O|-|O|-|				|-|O|-|O|-|-|-|				|-|0|-|-|-|0|-|
     * |O|-|-|O|-|-|O|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * 
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * |-|-|-|O|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * |-|-|-|O|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     * |-|-|-|P|-|-|-| probe 3		|-|-|-|P|-|-|-| probe 4		|-|-|-|P|-|-|-| shield
     * |-|-|-|O|-|-|-|				|-|-|-|0|0|-|-|				|-|-|-|0|-|-|-|
     * |-|-|-|O|-|-|-|				|-|-|-|0|-|0|-|				|-|-|-|-|-|-|-|
     * |-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|				|-|-|-|-|-|-|-|
     */
    /**
     * @param i
     * @param j
     * @return
     */
	@Override
    protected String[][] getValidMoves(int i, int j, Object board) {
		String[][] moves = new String[7][7];
		moves[3][3] = "O";
		int type = ((PloyBoard) board).boardSquares[i][j].getType();
		if (type == 0) {
			moves[2][2] = "O";
			moves[2][4] = "O";
			moves[4][2] = "O";
			moves[4][4] = "O";			
		} else if (type == 1) {
			moves[3][2] = "O";
			moves[3][1] = "O";
			moves[3][0] = "O";
			moves[4][3] = "O";
			moves[5][3] = "O";
			moves[6][3] = "O";
			moves[3][4] = "O";
			moves[3][5] = "O";
			moves[3][6] = "O";
		} else if (type == 2) {
			moves[2][3] = "O";
			moves[1][3] = "O";
			moves[0][3] = "O";
			moves[4][2] = "O";
			moves[5][1] = "O";
			moves[6][0] = "O";
			moves[4][4] = "O";
			moves[5][5] = "O";
			moves[6][6] = "O";
		} else if (type == 3) {
			moves[4][2] = "O";
			moves[5][1] = "O";
			moves[6][0] = "O";
			moves[4][3] = "O";
			moves[5][3] = "O";
			moves[6][3] = "O";
			moves[4][4] = "O";
			moves[5][5] = "O";
			moves[6][6] = "O";
		} else if (type == 4) {
			moves[4][2] = "O";
			moves[5][1] = "O";
			moves[4][3] = "O";
			moves[5][3] = "O";
		} else if (type == 5) {
			moves[4][2] = "O";
			moves[5][1] = "O";
			moves[4][4] = "O";
			moves[5][5] = "O";
		} else if (type == 6) {
			moves[2][3] = "O";
			moves[1][3] = "O";
			moves[4][3] = "O";
			moves[5][3] = "O";
		} else if (type == 7) {
			moves[4][3] = "O";
			moves[5][3] = "O";
			moves[4][4] = "O";
			moves[5][5] = "O";
		} else if (type == 8) {
			moves[4][3] = "O";
		}
		moves = rotateMoves(moves, i, j, board);
		
		// Exclude other pieces
		// Allies
		
		// -1 | +1
		if (i - 1 >= 0 && j - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 1][j - 1].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[2][2] = "-";
				moves[1][1] = "-";
				moves[0][0] = "-";
			}
		}
		if (i - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 1][j].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[2][3] = "-";
				moves[1][3] = "-";
				moves[0][3] = "-";
			}
		}
		if (i - 1 >= 0 && j + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i - 1][j + 1].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[2][4] = "-";
				moves[1][5] = "-";
				moves[0][6] = "-";
			}
		}
		if (j - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i][j - 1].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[3][2] = "-";
				moves[3][1] = "-";
				moves[3][0] = "-";
			}
		}
		if (j + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i][j + 1].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[3][4] = "-";
				moves[3][5] = "-";
				moves[3][6] = "-";
			}
		}
		if (i + 1 <= 8 && j - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i + 1][j - 1].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[4][2] = "-";
				moves[5][1] = "-";
				moves[6][0] = "-";
			}
		}
		if (i + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 1][j].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[4][3] = "-";
				moves[5][3] = "-";
				moves[6][3] = "-";
			}
		}
		if (i + 1 <= 8 && j + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 1][j + 1].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[4][4] = "-";
				moves[5][5] = "-";
				moves[6][6] = "-";
			}
		}
		
		// -2 | +2
		if (i - 2 >= 0 && j - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 2][j - 2].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[1][1] = "-";
				moves[0][0] = "-";
			}
		}
		if (i - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 2][j].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[1][3] = "-";
				moves[0][3] = "-";
			}
		}
		if (i - 2 >= 0 && j + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i - 2][j + 2].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[1][5] = "-";
				moves[0][6] = "-";
			}
		}
		if (j - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i][j - 2].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[3][1] = "-";
				moves[3][0] = "-";
			}
		}
		if (j + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i][j + 2].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[3][5] = "-";
				moves[3][6] = "-";
			}
		}
		if (i + 2 <= 8 && j - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i + 2][j - 2].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[5][1] = "-";
				moves[6][0] = "-";
			}
		}
		if (i + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 2][j].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[5][3] = "-";
				moves[6][3] = "-";
			}
		}
		if (i + 2 <= 8 && j + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 2][j + 2].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[5][5] = "-";
				moves[6][6] = "-";
			}
		}
		
		// -3 | +3
		if (i - 3 >= 0 && j - 3 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 3][j - 3].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[0][0] = "-";
			}
		}
		if (i - 3 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 3][j].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[0][3] = "-";
			}
		}
		if (i - 3 >= 0 && j + 3 <= 8) {
			if (((PloyBoard) board).boardSquares[i - 3][j + 3].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[0][6] = "-";
			}
		}
		if (j - 3 >= 0) {
			if (((PloyBoard) board).boardSquares[i][j - 3].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[3][0] = "-";
			}
		}
		if (j + 3 <= 8) {
			if (((PloyBoard) board).boardSquares[i][j + 3].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[3][6] = "-";
			}
		}
		if (i + 3 <= 8 && j - 3 >= 0) {
			if (((PloyBoard) board).boardSquares[i + 3][j - 3].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[6][0] = "-";
			}
		}
		if (i + 3 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 3][j].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[6][3] = "-";
			}
		}
		if (i + 3 <= 8 && j + 3 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 3][j + 3].getOwner() == ((PloyBoard) board).boardSquares[i][j].getOwner()) {
				moves[6][6] = "-";
			}
		}
		
		// Enemies
		
		// -2 | +2
		if (i - 2 >= 0 && j - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 2][j - 2].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i - 2][j - 2].getOwner() != 0) {
				if (moves[1][1] == "O") {
					moves[0][0] = "-";
				}
			}
		}
		if (i - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 2][j].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i - 2][j].getOwner() != 0) {
				if (moves[1][3] == "O") {
					moves[0][3] = "-";
				}
			}
		}
		if (i - 2 >= 0 && j + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i - 2][j + 2].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i - 2][j + 2].getOwner() != 0) {
				if (moves[1][5] == "O") {
					moves[0][6] = "-";
				}
			}
		}
		if (j - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i][j - 2].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i][j - 2].getOwner() != 0) {
				if (moves[3][1] == "O") {
					moves[3][0] = "-";
				}
			}
		}
		if (j + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i][j + 2].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i][j + 2].getOwner() != 0) {
				if (moves[3][5] == "O") {
					moves[3][6] = "-";
				}
			}
		}
		if (i + 2 <= 8 && j - 2 >= 0) {
			if (((PloyBoard) board).boardSquares[i + 2][j - 2].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i + 2][j - 2].getOwner() != 0) {
				if (moves[5][1] == "O") {
					moves[6][0] = "-";
				}
			}
		}
		if (i + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 2][j].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i + 2][j].getOwner() != 0) {
				if (moves[5][3] == "O") {
					moves[6][3] = "-";
				}
			}
		}
		if (i + 2 <= 8 && j + 2 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 2][j + 2].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i + 2][j + 2].getOwner() != 0) {
				if (moves[5][5] == "O") {
					moves[6][6] = "-";
				}
			}
		}
		
		// -1 | +1
		if (i - 1 >= 0 && j - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 1][j - 1].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i - 1][j - 1].getOwner() != 0) {
				if (moves[2][2] == "O") {
					moves[1][1] = "-";
					moves[0][0] = "-";
				}
			}
		}
		if (i - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i - 1][j].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i - 1][j].getOwner() != 0) {
				if (moves[2][3] == "O") {
					moves[1][3] = "-";
					moves[0][3] = "-";
				}
			}
		}
		if (i - 1 >= 0 && j + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i - 1][j + 1].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i - 1][j + 1].getOwner() != 0) {
				if (moves[2][4] == "O") {
					moves[1][5] = "-";
					moves[0][6] = "-";
				}
			}
		}
		if (j - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i][j - 1].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i][j - 1].getOwner() != 0) {
				if (moves[3][2] == "O") {
					moves[3][1] = "-";
					moves[3][0] = "-";
				}
			}
		}
		if (j + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i][j + 1].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i][j + 1].getOwner() != 0) {
				if (moves[3][4] == "O") {
					moves[3][5] = "-";
					moves[3][6] = "-";
				}
			}
		}
		if (i + 1 <= 8 && j - 1 >= 0) {
			if (((PloyBoard) board).boardSquares[i + 1][j - 1].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i + 1][j - 1].getOwner() != 0) {
				if (moves[4][2] == "O") {
					moves[5][1] = "-";
					moves[6][0] = "-";
				}
			}
		}
		if (i + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 1][j].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i + 1][j].getOwner() != 0) {
				if (moves[4][3] == "O") {
					moves[5][3] = "-";
					moves[6][3] = "-";
				}
			}
		}
		if (i + 1 <= 8 && j + 1 <= 8) {
			if (((PloyBoard) board).boardSquares[i + 1][j + 1].getOwner() != ((PloyBoard) board).boardSquares[i][j].getOwner() && ((PloyBoard) board).boardSquares[i + 1][j + 1].getOwner() != 0) {
				if (moves[4][4] == "O") {
					moves[5][5] = "-";
					moves[6][6] = "-";
				}
			}
		}
		
		return moves;
    }
    
    /**
     * @param moves
     * @param i
     * @param j
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
     * @param moves
     * @param i
     * @param j
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
     * @param moves
     * @param i
     * @param j
     * @return
     */
    protected String[][] rotateMoves(String[][] moves, int i, int j, Object board) {
    	String[][] rotatedMoves = new String[7][7];
		int direction = ((PloyBoard) board).boardSquares[i][j].getDirection();
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
