/* MachinePlayer.java */

package player;
import list.*;
import java.util.Random;



/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
  protected Board myBoard;
  protected int searchDepth;
  protected String color; 

  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
    if (color == 0){
      this.color = "BLACK";
    }else{
      this.color = "WHITE";
    }
    this.searchDepth = 1;
    myBoard = new Board();
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
    if (color == 0){
      this.color = "BLACK";
    }else{
      this.color = "WHITE";
    }
    this.searchDepth = searchDepth;
    myBoard = new Board();
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
    Move best = findBest(color);
    //System.out.println("HERE??");
    myBoard.makeMove((Move) best, color);
    return best;
  }

  ////MAYBE ADD OTHER PARAMETER BOARD
  public Best chooseMove(String color, double alpha, double beta, int searchDepth, int key) {  //side replaced with color  change int to double in alpha and beta
  Best myBest = new Best(); // My best move Best reply;
  Best reply;  // Opponent’s best reply
  List moves = myBoard.generateMoves(color);
  //System.out.println("I definitely have the List");
  Move m;
  ListNode currNode = moves.front();
  //System.out.println(searchDepth);
  if (searchDepth <= 0 || moves.isEmpty()) { //was <= 0
    //System.out.println("ABOUT DONE!!");
    myBest.score = Eval.eval(myBoard, color);//what ever marsalis has to evaluate board ex. evaluate(myBest);
    return myBest;
  } 
  //System.out.println("Now checking for Network...");
  if (myBoard.hasNetwork(color)) {
    myBest.score = (Eval.eval(myBoard, color) / key); 
    return myBest; 
  }
  //System.out.println("Starting the main part....");
  if (color == this.color) {
  	myBest.score = alpha;
  } else {
  	myBest.score = beta;
  }
  try {
    myBest.bestMove = (Move) currNode.item();
    //System.out.println("Starting the While Loop!!");
    while (currNode != null) {
      m = (Move) currNode.item();
      myBoard.makeMove(m, color);
      reply = chooseMove(opponentColor(color), alpha, beta, searchDepth - 1, key + 1);// made a helper method in the bottom will I use it?
      //System.out.println("Marsalis and Erick in this BITCH!!!!");
      myBoard.undoMove(m, color);
      if (color == this.color && reply.score > myBest.score) {
        myBest.bestMove = m;
        myBest.score = reply.score;
        alpha = reply.score;

      } else if (color != this.color && reply.score < myBest.score) {
        myBest.bestMove = m;
        myBest.score = reply.score;
        beta = myBest.score;      	
      }                             
      if (alpha >= beta){
        return myBest;
      }
      currNode = currNode.next();
    }
  } catch (InvalidNodeException e) {
  	  System.out.println(e);
      //System.out.println(moves.length());
  }
  /*////////////////////////////////
  System.out.println("I have THEE Best Move!!");
  Move check = myBest.bestMove;
  System.out.println(check.moveKind);
  System.out.println(check.x1);
  System.out.println(check.y1);
  ////////////////////////////////////
   */
  //System.out.println("Alpha is: " + alpha);
  //System.out.println("Beta is: " + beta);
  return myBest;
}

/* Helper to get other player */
private static String opponentColor(String color) {
  if (color.equals("BLACK")) {
  	return "WHITE";
  } 
  return "BLACK";
}

/*or   private String opponentColor() {

} */

protected Move findBest(String color){
	  int alpha = Integer.MIN_VALUE;
	  int beta = Integer.MAX_VALUE;
	  Best bestMove = chooseMove(color, alpha, beta, searchDepth, 1);
	  return bestMove.bestMove;
  }



  /*myBest.move = any legal 
  for (each legal move m) {
    perform move m;  // Modifies "this" Grid
    reply = chooseMove(! side, alpha, beta); 
    undo move m; // Restores "this" Grid 
    if (side == COMPUTER && reply.score > myBest.score) { 
      myBest.move = m;
      myBest.score = reply.score; 
      alpha = reply.score;
    } else if (side == HUMAN && reply.score < myBest.score) {
      myBest.move = m;
      myBest.score = reply.score;
      beta = reply.score;
    }
    if (alpha >= beta) { 
      return myBest; 
    }
  }
  return myBest;
}  */

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
    return myBoard.makeMove(m, opponentColor(color));
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
    return myBoard.makeMove(m, color);
  }
}
