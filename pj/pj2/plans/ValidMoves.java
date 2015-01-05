/* This class does several things. First, it describes all of the moves
 * that are allowed within the game.
 */

interface ValidMoves{

	/* This method will returns true if this chip will NOT be contained within a 
	 * cluster. If it is then THAT'S BAD!!!  
	 */ 
	private abstract boolean isNotClustered(int x, int y);

	/* This method returns true if the given location (determined through the x and y value)
	 * is not a corner.
	
	/* This method returns true if the spot is of a goal of the correct color.
	 */
	private abstract boolean isMyGoal(String color, int x, int y);

	/* return true if this move is a valid move
	 */
	public abstract boolean isValidMove(int x, int y);
}