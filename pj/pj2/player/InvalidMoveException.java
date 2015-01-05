package player;

/* This is my Exception class that is thrown whenever an invalid move is created. 
 * This will also print out the correct error message to the type of invalid move.
 */

class InvalidMoveException extends Exception{

	public InvalidMoveException(){
		super("This move is not a valid move!");
	}

	public InvalidMoveException(String s){
		super(s);
	}
}