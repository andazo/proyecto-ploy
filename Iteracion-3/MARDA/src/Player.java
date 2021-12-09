
/**
 * Abstract class that represents a player
 */
abstract class Player {
	protected String name, color;
	protected int id;
	protected boolean lost;
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getID() {
		return id;
	}
	
	public void setLost() {
		lost = true;
	}
	
	public boolean getLost() {
		return lost;
	}
	
}
