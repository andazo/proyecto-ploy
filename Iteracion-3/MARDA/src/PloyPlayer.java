
public class PloyPlayer extends Player{
	String name, color;
	int id, friend, numPieces;
	boolean lost;
	
	public PloyPlayer(String name, int id, int numPieces, String color, int friend) {
		this.name = name;
		this.id = id;
		this.numPieces = numPieces;
		this.color = color;
		this.lost = false;
		this.friend = friend;
	}
	
	public String getName() {
		return this.name;
	}
	public String getColor() {
		return this.color;
	}
	public int getID() {
		return this.id;
	}
	
	
	
}
