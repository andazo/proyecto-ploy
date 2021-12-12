package MARDA;

/**
 * Abstract class that represents a player
 */
public abstract class Player {
	/** The id of the player. */
	protected int id;
	/** The name of the player. */
	protected String name;
	/** The color chosen by the player. */
	protected String color;
	/** Says if the player is still in the game or not. */
	protected boolean lost;

	public int getID() {
		return id;
	}

	public void setName(String playerName) {
		name = playerName;
	}

	public String getName() {
		return name;
	}

	public void setColor(String playerColor) {
		color = playerColor;
	}

	public String getColor() {
		return color;
	}

	public void setLost(boolean cond) {
		lost = cond;
	}

	public boolean getLost() {
		return lost;
	}

}
