
/**
 * Abstract class for representing the information of the pieces placed on squares.
 */
abstract class BoardSquare {
	// The type of piece currently on the square
	protected int type;
	// The owner of the piece on the square
	protected int owner;
	protected String color;


  public void setType(int type) {
  	this.type = type;
  }

  public int getType() {
      return type;
  }

  public void setOwner(int owner) {
  	this.owner = owner;
  }

  public int getOwner() {
      return owner;
  }
  
  public void setColor(String color) {
  	this.color = color;
  }

  public String getColor() {
      return color;
  }
}
