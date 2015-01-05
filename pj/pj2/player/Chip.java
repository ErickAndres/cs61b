/* Chip.java */
package player;

class Chip {
    //fields maybe needed
    private int x;
    private int y;
    private String color;
    
	/* This is the constructor for our chip class that takes in the type of
	 * the chip (represented by a number) and creates the chip.
	 */
     public Chip(int x, int y, String color) {
    	this.x = x; 
    	this.y = y;
    	this.color = color;
	} 

	/* This method tells us the color of the chip.
	 */ 
	public String getType() {
	  return color;
	}

	/* This function returns the X - value location of the chip. The x - value 
	 * is the column of the board.
	 */
	public int getColumn() {
      return x;
	}

	/* This function returns the Y - value location of the chip. The y - value
	 * is the row of the board.
	 */
	public int getRow() {
	  return y;
	}
}