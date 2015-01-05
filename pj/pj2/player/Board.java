/* Board.java */
package player;
import list.*;
import java.util.Arrays;

class Board{
  
  /**
   * A class that is extended by all Network players (human and machine). 
   * This is the player's internal representation of the board. 
   * This representation of the board is intended for the Game Tree Search Module to get and set contents on the board.  
   */

  private int blackChips;
  private int whiteChips;
  private Chip[][] gameBoard = new Chip[8][8];
  
  
  /////////////////////////////////////////////////////
  //    ~Main Public Methods for the Board class~    //
  /////////////////////////////////////////////////////


  /* This is the constructor for the Board class that takes in no parameters and 
   * creates an empty board with the white and black chips in their respective places.
   */
  public Board() {
  	for (int x = 0; x < 8; x++) {
  		for (int y = 0; y < 8; y++) {
  			gameBoard[x][y] = null;
  		}
  	}
    System.out.println("Board being constructed...");
    blackChips = 10;
    whiteChips = 10;
  }

  /** 
   * The makeMove function records the given move in its board. It returns true if the move 
   * was valid, and thus recorded. It returns false if wasn't.
   **/
  public boolean makeMove(Move m, String color) {
    if (m.moveKind == Move.STEP) {
      Boolean prev = this.removeChip(color, m.x2, m.y2);
      //Checking to see if the chip you removed existed.
      if (!prev){
        return false;
      } else {
        return this.placeChip(color, m.x1, m.y1);
      }
    } else if (m.moveKind == Move.ADD) {
      return this.placeChip(color, m.x1, m.y1);
    } else {
     return false; 
    }
  }

  /** 
   * The makeMove function records the given move in its board. It returns true if the move 
   * was valid, and thus recorded. It returns false if wasn't.
   **/  
  public boolean undoMove(Move m, String color) {
    Boolean prev = this.removeChip(color, m.x1, m.y1);
    if (m.moveKind == Move.STEP){
      return this.placeChip(color, m.x2, m.y2);
    }
    return prev;
  }


  /* This method places a chip on the board given the location x, y.
   * It first checks if NONE of the invariants are violated to 
   * see if it is able to place a chip there.
   */
  public boolean placeChip(String color, int x, int y) {
    if (!chipsLeft(color)){
      System.out.println("There are no more Chips!");
      return false;
    }
    if (validMove(color, x, y)) {
      gameBoard[x][y] = new Chip(x, y, color);
    } else {
      return false;
    }
    adjustChips("Remove", color);
    return true;
  }
    /*try{
      if (validMove(color, x, y)) {
      gameBoard[x][y] = new Chip(x, y, color);
      }
    }catch(InvalidMoveException e){       ////////////MIGHT NEED TO GET RID OF THIS InvalidMoveException?
      //System.out.println(e);
      return false;
    }

    adjustChips("Remove", color);
    return true;
  } */

  /* This method removes a chip from the board of a given location x, y if 
   * a chip of the correct color exists there.
	 */ 
	public boolean removeChip(String color, int x, int y) {
    Chip chip = gameBoard[x][y];
    if (checkChip(color, x, y, chip)) {
      gameBoard[x][y] = null;
    } else {
      return false;
    }
    adjustChips("Add", color);
    return true;
  }
    /* try{
      if (checkChip(color, x, y, chip)) {
        gameBoard[x][y] = null;
      }
    }catch(InvalidMoveException e){
      //System.out.println(e);
      return false;
    }
    adjustChips("Add", color);
    return true;
	} */

	/*This method returns the chip at the given location.
   */
  public Chip getChip(int x, int y) {
      if (x < 0 || x > 7 || y < 0 || y > 7) {
        return null;
      }
      return gameBoard[x][y];
  }

  /* This method return true if the location is a goal.
   */
  public boolean isGoal(int x, int y){
    if (isNotCorner(x, y)){
      return x == 0 || x == 7 || y == 0 || y == 7;
    }
    return false;
  }

  /*This helper function checks to see if there are any available chips left.
   */
  public boolean chipsLeft(String color){
    if (color.equals("BLACK")){
      //System.out.println("Blackchips are: " + blackChips);
      return blackChips != 0;        //JUST CHANGED TO == so change back to !=
    }
    //System.out.println("Whitechips are: " + whiteChips);
    return whiteChips != 0;
  }

	/* This method generates a String representation of the board.
	 */
  public String toString() {
    String result = "";
    for (int y = 0; y < 8; y++) {
      result += "[" + getString(0, y);
      for (int x = 1; x < 8; x++) {
        result += "," + getString(x, y);
      }
    	result += "]\n";
    }
  return result;
  }

  /* This method returns true is the location is empty.
   */
  public boolean isEmpty(int x, int y) {
    if (x < 0 || x > 7 || y < 0 || y > 7) {
        return false;
    }
    return getChip(x, y) == null;
  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
  ////////////////////////////////////
  // Network/Connection Functions!! //
  ////////////////////////////////////

  public boolean hasNetwork(String color){
    //for every chip within our goal, we are going to create a connection.
    //Start for the x or y being 0!
    Chip chip;
    boolean network;
    int[] checked;
    List lst;

    if (color.equals("BLACK")){
      for (int i = 1; i <= 6; i++){
        chip = getChip(i, 0);
        if (chip != null){
          lst = new SList();
          lst.insertFront(chip.getColumn()*10 + chip.getRow());
          checked = new int[10];
          network = makeConnection(chip, lst, checked, 0);
          if (network) {
            return true;
          }
        }
      }
    }
    if (color.equals("WHITE")){
      for (int i = 1; i <= 6; i++){
        chip = getChip(0, i);
        if (chip != null){
          lst = new SList();
          lst.insertFront(chip.getColumn()*10 + chip.getRow());
          checked = new int[10];
          network = makeConnection(chip, lst, checked, 0);
          if (network) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /* This is the function that searches for the neighboring
   * chips that can be used to form a connection.
   */
  public Chip[] findChips(Chip orig_chip){
    int x = orig_chip.getColumn();
    int y = orig_chip.getRow();
    String player = orig_chip.getType();

    Chip chip1 = chipAround("Above", x, y - 1, player);
    Chip chip2 = chipAround("Below", x, y + 1, player);
    Chip chip3 = chipAround("Left", x - 1, y, player);
    Chip chip4 = chipAround("Right", x + 1, y, player);
    Chip chip5 = chipDiagonal("TopRight", x + 1, y - 1, player);
    Chip chip6 = chipDiagonal("BottomRight", x + 1, y + 1, player);
    Chip chip7 = chipDiagonal("TopLeft", x - 1, y - 1, player);
    Chip chip8 = chipDiagonal("BottomLeft", x - 1, y + 1, player);
    Chip[] chips = {chip1, chip2, chip3, chip4, chip5, chip6, chip7, chip8};
    return chips;
  }
  
  /* This function searches through all of the possible connections, and checks to see
   * which connections are good by updating the given connections list. It returns true as soon as 
   * it finds the first "good" connection. (A good connection means a network!!)
   */
  private boolean makeConnection(Chip chip, List connections, int[] checked, int index){
    Chip curr; 
    Chip[] chips;
    boolean success;
    int mark = index;
    List copy;
    ListNode back;
    chips = findChips(chip);

    for (int i = 0; i < chips.length; i++){
      curr = chips[i];
      if (curr != null){
        String player = curr.getType();
        int x = curr.getColumn();
        int y = curr.getRow();
        int location = (x * 10) + y;

    ///////////////////////////////////////////////////
    // This is the functions I used to help debug!! ///
    ///////////////////////////////////////////////////
    //    System.out.println(location);              //
    //    System.out.println(index);                 //
    //    System.out.println(connections.toString());//
    ///////////////////////////////////////////////////

      // These are the base cases for checking for good connections:
      //   1) If a location is already in the connection skip it.
      //   2) If the location is a goal, check to see if it creates a valid network.
      //      If so, then we are done. If not, then skip it because we do not want it.
      //

        if (containsNum(location, checked)){
          continue;
        }
        if (whoseGoal(x, y) == player){
          connections.insertBack(location);
          if (isValidNetwork(connections, player)){
            return true;
          }else{
            try{
              back = connections.back();
              back.remove();
            }catch(InvalidNodeException e){
              System.out.println(e);
            }finally{
              continue;
            }
          }
        }

        //This part inserts the location and checks if it can create a good connection.
        // If it doesn't then it removes the location from the connections, and skips it.
        //
        checked[index++] = location;
        connections.insertBack(location);
        copy = connections;
        success = makeConnection(curr, copy, checked, index);
        if (success){
          return success;
        }else{
          try{
            back = connections.back();
            back.remove();
            checked[mark] = 0;
          }catch(InvalidNodeException e){
            System.out.println(e);
          }
        }
      }
    }
    //If there are no good connections return FALSE!!!
    return false;
  }
  
  /* The following methods are the functions that performs the search for
   * the chips according to its direction. It returns null if there is no
   * chip in the path or if it hits a chip not of its color. Otherwise,
   * it returns the first chip it runs into.
   */ 

  private Chip chipAround(String direction, int x, int y, String player){
    Chip chip;
    int increment;
    if (direction.equals("Above") || direction.equals("Below")) {
      increment = y;
    }else{
      increment = x;
    }
    while(increment >= 0 && increment <= 7){
      if (direction.equals("Above") || direction.equals("Below")) {
        chip = getChip(x, increment);
      }else{
        chip = getChip(increment, y);
      }
      if (chip != null){
        if (chip.getType() != player){
          return null;
        }
        return chip;
      }
      if (direction.equals("Below") || direction.equals("Right")) {
        increment++;
      }else{
        increment--;
      }
    }
    return null;
  }

  private Chip chipDiagonal(String direction, int x, int y, String player){
    Chip chip;
    while (x >= 0 && x <= 7 && y >= 0 && y <= 7){
      chip = getChip(x, y);
      if (chip != null){
        if (chip.getType() != player){
          return null;
        }
        return chip;
      }
      if (direction.equals("BottomRight") || direction.equals("TopRight")) {
        x++;
      }else{
        x--;
      }
      if (direction.equals("BottomLeft") || direction.equals("BottomRight")) {
        y++;
      }else{
        y--;
      }
    }
    return null;
  }

 ///////////////////////////////////////////////////////////////////////
 //* The following are the helper functions for finding Networks!!!! *//
 ///////////////////////////////////////////////////////////////////////

  private boolean isValidNetwork(List connections, String player){
    ListNode curr = connections.front();
    ListNode back = connections.back();
    int[] locations = new int[connections.length()];
    int count = 0;

    try{
      while (curr != back){
        locations[count] = (int)curr.item();
        count++;
        curr = curr.next();
      }
      locations[count] = (int)curr.item();
    }catch(InvalidNodeException e) {
      System.out.println("End of the line!");
    }
    
    if (isNotSameDir(locations) && twoInGoal(locations, player)) {
      return connections.length() >= 6;
    }
    return false;
  }
  
  /* This method returns true if no three consecutive chips is in the same direction.
   * This means that no three consecutive chips have:
   *    1) The same first number.
   *    2) The same second number.
   *    3) If the difference of two consecutive chips is a multiple of 11 for three chips.
   *      Ex. 13 -> 35 -> 46 wouldn't work because 35 - 13 = 22 and 46 - 35 = 11. Both 11 and 22
   *        are multiples of 11. 
   *        34 -> 56 -> 12 also wouldn't work because of the same issue. 
   *    4) Same rule as #3 but for 9.
   *      Ex. 26 -> 35 -> 53 wouldn't work because 35 - 26 = 9 and 53 - 35 = 18. Both 9 and 18
   *        are multiples of 9.
   */
  private boolean isNotSameDir(int[] locations){
    int curr, next, third;
    for (int i = 0; i < locations.length - 2; i++) {
      curr = locations[i];
      next = locations[i+1];
      third = locations[i+2];
      if (curr/10 == next/10){
        if (next/10 == third/10){
          return false;
        }
      }
      if (curr%10 == next%10){
        if (next%10 == third%10){
          return false;
        }
      }
      if ((next - curr) % 11 == 0){
        if ((third - next) % 11 == 0){
          return false;
        }
      }
      if ((next - curr) % 9 == 0){
        if ((third - next) % 9 == 0){
          return false;
        }
      }
    }
    return true;
  }

  /* This method return true if and only if there is EXACTLY two chips contained within the goal areas.
   */
  private boolean twoInGoal(int[] locations, String player){
    int last = locations[locations.length - 1];
    int curr;
    if (player.equals("BLACK")) {
      if (last % 10 != 7){
        return false;
      }
    }
    if (player.equals("WHITE")) {
      if (last/10 != 7){
        return false;
      }
    }
    for(int i = 1; i < locations.length - 1; i++){
      curr = locations[i];
      if (!whoseGoal(curr/10, curr%10).equals("NOT A GOAL")) {   ///WE HAD THIS if (whoseGoal(curr/10, curr%10) != "NOT A GOAL")
        return false;
      }
    }
    return true;
  }

  /* A method to check if a number is contained within a given array.
   */
  private boolean containsNum(int number, int[] numbers){
    for (int i = 0; i < numbers.length; i++){
      if (number == numbers[i]){
        return true;
      }
    }
    return false;
  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  //////////////////////////////
  // Checking For Valid Moves //
  //////////////////////////////

  /* The following methods all enforce the following invariants:
   *   1) You cannot place a chip in a corner.
   *   2) You cannot place a chip in an opponents goal
   *   3) You can only place a chip in an empty space.
   *   4) You cannot form clusters (3 "touching" chips) of chips.
   */

  protected boolean validMove(String color, int x, int y) {  //WAS AFTER ')' THROWS INVALIDMOVE EXCEPTION '{'
    if (isNotCorner(x, y) && isEmpty(x, y) && isCorrectGoal(color, x, y) && noCluster(color, x, y, 0)){
      return true;
    }
    return false;      //WAS THIS ---> throw new InvalidMoveException();
  }

  private boolean isNotCorner(int x, int y){
    if ((x == 0 || x == 7) && (y == 0 || y == 7)){
      return false;
    }
    return true;  
  }

  private boolean isCorrectGoal(String color, int x, int y) {
    if (whoseGoal(x, y) == color || whoseGoal(x, y).equals("NOT A GOAL")) {
      return true;
    }
    return false;
  }

  private String whoseGoal(int x, int y) {
    if ((y == 0 || y == 7) && (x >= 1 && x <= 6)) {
      return "BLACK";
    }else{
      if ((x == 0 || x == 7) && (y >= 1 && x <= 6)){
        return "WHITE";
      }else{
        return "NOT A GOAL";
      }
    }
  }

  private boolean noCluster(String player, int x, int y, int count) {
    if (count == 2){
      return false;
    }
    Chip[] chips = neighbors(x, y);
    Chip chip;
    Chip check = null;
    int total = 0;
    for (int i = 0; i < chips.length; i++) {
      chip = chips[i];
      if (chip != null && chip.getType() == player) {
        total++;
        check = chip;
      }
    }
    if (total >= 2){
      return false;
    }
    if(check != null){
      return noCluster(player, check.getColumn(), check.getRow(), count+1);
    }
    return true;
  }
  
  private Chip[] neighbors(int x, int y) {
    Chip[] neighbor = new Chip[8];
    neighbor[0] = getChip(x - 1, y -1);
    neighbor[1] = getChip(x, y - 1);
    neighbor[2] = getChip(x + 1, y - 1);
    neighbor[3] = getChip(x - 1, y);
    neighbor[4] = getChip(x + 1, y);
    neighbor[5] = getChip(x - 1, y + 1);
    neighbor[6] = getChip(x, y + 1);
    neighbor[7] = getChip(x + 1, y + 1);
    return neighbor;
  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  ///////////////////////////////////////  
  // Helper Functions For Main Methods //
  ///////////////////////////////////////


  /* This is a helper function for the removeChip method. This method checks the chip
   * passed from the remove method and creates an InvalidMoveException with the CORRECT
   * message or just return true if none of the Invariants are violated.
   */
  private boolean checkChip(String color, int x, int y, Chip chip) {  //WAS THIS BEFORE --->throws InvalidMoveException{

    if (chip == null){
      return false; //WAS THIS BEFORE --> throw new InvalidMoveException("There is NO chip in box " + x + "" + y + "!");
      }
    if (chip.getType() != color) {
      return false;//WAS THIS BEFORE --> throw new InvalidMoveException("Chip is not of color " + color + "!");
    }else{
      return true;
    }
  }

  /*This helper function adjusts the number of chips for each type.
   */
  private void adjustChips(String command, String color){
    if (color.equals("BLACK")) {
      if (command.equals("Remove")){
        //System.out.println("Decreasing blackChips Now");
        blackChips--;
      }else{
        //System.out.println("Incrementing blackChips Now");
        blackChips++;
      }
    } else {
      if (command.equals("Remove")){
        whiteChips--;
        System.out.println("Decreasing whiteChips Now and whiteChips are: " + whiteChips);
      } else {  //added if statement
          whiteChips++;
          System.out.println("Incrementing whiteChips Now and Whitechips are: " + whiteChips);
      }
    }
  }

  /* This is a helper function for the toString method. It returns the proper
   * representation for whatever occupies the given space on the board.
   */
  private String getString(int x, int y) {
    Chip chip = gameBoard[x][y];
    if (chip == null) {
      return " ";
    }else{
      if (chip.getType().equals("BLACK")){
        return "B";
      }else{
        return "W";
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public List generateMoves(String color) { //changed int to String
    List goodMoves = (List) new SList();
    Chip newChip;
    if (this.chipsLeft(color)){
      return addMoves(null, goodMoves, color);
    } else {  //handles possible step moves
      //System.out.println("NOT SUPPOSE TO BE HERE!!!"); 
        for (int i = 0; i < 8; i++) {
          for (int j = 0; j < 8; j++) {         
            newChip = this.getChip(i, j);      
            if (newChip != null) {   
              goodMoves = addMoves(newChip, goodMoves, color);
            }
          }
        }
      }
   //System.out.println(goodMoves.length());
   return goodMoves;
  }

  private List addMoves(Chip chip, List goodMoves, String color) {
    boolean valid;
    Move m; 
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (chip != null){
          if (i == chip.getColumn() && j == chip.getRow()){
          continue;
        }
          m = new Move(i, j, chip.getColumn(), chip.getRow());
          //System.out.println(i);
          //System.out.println(j);
        } else {
          m = new Move(i, j);
        }
        valid = this.makeMove(m, color);
        //System.out.println("HERE!-GM3");
        if (valid) {
          goodMoves.insertBack(m);
          this.undoMove(m, color);
        }

      }
    }
    //System.out.println("I have the goodMoves!");
    //System.out.println(goodMoves.length());
  return goodMoves;
  }


}
