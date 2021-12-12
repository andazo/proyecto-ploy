package PloyGame;


public class PieceFactory {
	//use getShape method to get object of type shape 
	public PieceInterface makePiece(int PieceType) {
		PieceInterface piece = null;
		if (PieceType == 0) {
			piece = new Commander();
	    } else if (PieceType == 1) {
	    	piece = new Lance1();
	    } else if (PieceType == 2) {
	    	piece = new Lance2();
	    } else if (PieceType == 3) {
	    	piece = new Lance3();
	    } else if (PieceType == 4) {
	    	piece = new Probe1();
	    } else if (PieceType == 5) {
	    	piece = new Probe2();
	    } else if (PieceType == 6) {
	    	piece = new Probe3();
	    } else if (PieceType == 7) {
	    	piece = new Probe4();
	    } else if (PieceType == 8) {
	    	piece = new Shield();
	    }
		return piece;
	}
}

class Commander extends PloyPiece implements PieceInterface {

	/**
	 * Instantiates a new Commander.
	 */
	Commander() {
		this.type = 0;
	}
	
	/*
	 * Valid moves
	 * 
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|O|-|O|-|-|
	 * |-|-|-|P|-|-|-| Commander
	 * |-|-|O|-|O|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 */
	@Override
	public String[][] getMoves() {
		String[][] moves = new String[7][7];
		moves[3][3] = "O";
		moves[2][2] = "O";
		moves[2][4] = "O";
		moves[4][2] = "O";
		moves[4][4] = "O";
		return moves;
	}
}

class Lance1 extends PloyPiece implements PieceInterface {

	/**
	 * Instantiates a new Lance1.
	 */
	Lance1() {
		this.type = 1;
	}
	
	/*
	 * Valid moves
	 * 
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |O|O|O|P|O|O|O| Lance 1
	 * |-|-|-|O|-|-|-|
	 * |-|-|-|O|-|-|-|
	 * |-|-|-|O|-|-|-|
	 */
	@Override
	public String[][] getMoves() {
		String[][] moves = new String[7][7];
		moves[3][3] = "O";
		moves[3][2] = "O";
		moves[3][1] = "O";
		moves[3][0] = "O";
		moves[4][3] = "O";
		moves[5][3] = "O";
		moves[6][3] = "O";
		moves[3][4] = "O";
		moves[3][5] = "O";
		moves[3][6] = "O";
		return moves;
	}
}

class Lance2 extends PloyPiece implements PieceInterface {

	/**
	 * Instantiates a new Lance2.
	 */
	Lance2() {
		this.type = 2;
	}
	
	/*
	 * Valid moves
	 * 
	 * |-|-|-|O|-|-|-|
	 * |-|-|-|O|-|-|-|
	 * |-|-|-|O|-|-|-|
	 * |-|-|-|P|-|-|-| Lance 2
	 * |-|-|O|-|O|-|-|
	 * |-|O|-|-|-|O|-|
	 * |O|-|-|-|-|-|O|
	 */
	@Override
	public String[][] getMoves() {
		String[][] moves = new String[7][7];
		moves[3][3] = "O";
		moves[2][3] = "O";
		moves[1][3] = "O";
		moves[0][3] = "O";
		moves[4][2] = "O";
		moves[5][1] = "O";
		moves[6][0] = "O";
		moves[4][4] = "O";
		moves[5][5] = "O";
		moves[6][6] = "O";
		return moves;
	}
}

class Lance3 extends PloyPiece implements PieceInterface {

	/**
	 * Instantiates a new Lance3.
	 */
	Lance3() {
		this.type = 3;
	}
	
	/*
	 * Valid moves
	 * 
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|P|-|-|-| Lance 3
	 * |-|-|O|O|O|-|-|
	 * |-|O|-|O|-|O|-|
	 * |O|-|-|O|-|-|O|
	 */
	@Override
	public String[][] getMoves() {
		String[][] moves = new String[7][7];
		moves[3][3] = "O";
		moves[4][2] = "O";
		moves[5][1] = "O";
		moves[6][0] = "O";
		moves[4][3] = "O";
		moves[5][3] = "O";
		moves[6][3] = "O";
		moves[4][4] = "O";
		moves[5][5] = "O";
		moves[6][6] = "O";
		return moves;
	}
}

class Probe1 extends PloyPiece implements PieceInterface {

	/**
	 * Instantiates a new Probe1.
	 */
	Probe1() {
		this.type = 4;
	}
	
	/*
	 * Valid moves
	 * 
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|P|-|-|-| Probe 1
	 * |-|-|O|O|-|-|-|
	 * |-|O|-|O|-|-|-|
	 * |-|-|-|-|-|-|-|
	 */
	@Override
	public String[][] getMoves() {
		String[][] moves = new String[7][7];
		moves[3][3] = "O";
		moves[4][2] = "O";
		moves[5][1] = "O";
		moves[4][3] = "O";
		moves[5][3] = "O";
		return moves;
	}
}

class Probe2 extends PloyPiece implements PieceInterface {

	/**
	 * Instantiates a new Probe2.
	 */
	Probe2() {
		this.type = 5;
	}
	
	/*
	 * Valid moves
	 * 
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|P|-|-|-| Probe 2
	 * |-|-|0|-|0|-|-|
	 * |-|0|-|-|-|0|-|
	 * |-|-|-|-|-|-|-|
	 */
	@Override
	public String[][] getMoves() {
		String[][] moves = new String[7][7];
		moves[3][3] = "O";
		moves[4][2] = "O";
		moves[5][1] = "O";
		moves[4][4] = "O";
		moves[5][5] = "O";
		return moves;
	}
}

class Probe3 extends PloyPiece implements PieceInterface {

	/**
	 * Instantiates a new Probe3.
	 */
	Probe3() {
		this.type = 6;
	}
	
	/*
	 * Valid moves
	 * 
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|O|-|-|-|
	 * |-|-|-|O|-|-|-|
	 * |-|-|-|P|-|-|-| Probe 3
	 * |-|-|-|O|-|-|-|
	 * |-|-|-|O|-|-|-|
	 * |-|-|-|-|-|-|-|
	 */
	@Override
	public String[][] getMoves() {
		String[][] moves = new String[7][7];
		moves[3][3] = "O";
		moves[2][3] = "O";
		moves[1][3] = "O";
		moves[4][3] = "O";
		moves[5][3] = "O";
		return moves;
	}
}

class Probe4 extends PloyPiece implements PieceInterface {

	/**
	 * Instantiates a new Probe4.
	 */
	Probe4() {
		this.type = 7;
	}
	
	/*
	 * Valid moves
	 * 
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|P|-|-|-| Probe 4
	 * |-|-|-|0|0|-|-|
	 * |-|-|-|0|-|0|-|
	 * |-|-|-|-|-|-|-|
	 */
	@Override
	public String[][] getMoves() {
		String[][] moves = new String[7][7];
		moves[3][3] = "O";
		moves[4][3] = "O";
		moves[5][3] = "O";
		moves[4][4] = "O";
		moves[5][5] = "O";
		return moves;
	}
}

class Shield extends PloyPiece implements PieceInterface {

	/**
	 * Instantiates a new Shield.
	 */
	Shield() {
		this.type = 8;
	}
	
	/*
	 * Valid moves
	 * 
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|P|-|-|-| Shield
	 * |-|-|-|0|-|-|-|
	 * |-|-|-|-|-|-|-|
	 * |-|-|-|-|-|-|-|
	 */
	@Override
	public String[][] getMoves() {
		String[][] moves = new String[7][7];
		moves[3][3] = "O";
		moves[4][3] = "O";
		return moves;
	}
}
