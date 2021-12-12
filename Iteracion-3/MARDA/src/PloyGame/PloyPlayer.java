
/**
 * Concrete class representing a player for Ploy.
 */
public class PloyPlayer extends Player {
	int friend, numPieces;

	/**
	 * Instantiates a new Ploy player.
	 *
	 * @param name name of the player
	 * @param id number of the player
	 * @param color the color of the player's pieces
	 */
	public PloyPlayer(String name, int id, String color) {
		this.name = name;
		this.id = id;
		this.color = color;
		this.lost = false;
	}
	
	public int getFriend() {
		return friend;
	}
	
	public void setFriend(int friend) {
		this.friend = friend;
	}
	
	public int getNumPieces() {
		return numPieces;
	}
	
	public void setNumPieces(int numPieces) {
		this.numPieces = numPieces;
	}
}
