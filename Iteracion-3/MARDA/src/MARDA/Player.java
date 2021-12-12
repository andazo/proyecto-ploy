
/**
 * Abstract class that represents a player
 */
abstract class Player {
	protected int id;
	protected String name, color;
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
