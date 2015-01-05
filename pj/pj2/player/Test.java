package player;

/* This class will contain all of the Tests needed to check if
 * our functions work correctly. The tests are each divided into
 * various parts according to the function they each test.
 */

class Test{

	public static void main(String[] args) {
		System.out.println("Starting ALL Tests!!");
		System.out.println("");


		System.out.println("Now checking the Board constructor:");
		Board b1 = new Board();
		System.out.println("This board should be empty: ");
		System.out.println(b1.toString());
		System.out.println("");


		System.out.println("Now checking the place and remove methods for Board: ");
		System.out.println("Placing a BLACK chip in my goal...");
		b1.placeChip("BLACK", 2, 0);
		System.out.println(b1.toString());
		System.out.println("");

		System.out.println("Placing a WHITE chip in box 33...");
		b1.placeChip("WHITE", 3, 3);
		System.out.println(b1.toString());
		System.out.println("");

		System.out.println("Placing BLACK chip in the opponents goal...");
		b1.placeChip("BLACK", 0, 1);
		//System.out.println(b1.toString());
		System.out.println("");

		System.out.println("Placing BLACK chip in a corner...");
		b1.placeChip("BLACK", 0, 0);
		//System.out.println(b1.toString());
		System.out.println("");

		System.out.println("Placing BLACK chip in an occupied space...");
		b1.placeChip("BLACK", 2, 0);
		//System.out.println(b1.toString());
		System.out.println("");

		System.out.println("Removing the WHITE chip...");
		b1.removeChip("WHITE", 3, 3);
		System.out.println(b1.toString());
		System.out.println("");

		System.out.println("Removing a chip from an empty space...");
		b1.removeChip("BLACK", 4, 0);
		//System.out.println(b1.toString());
		System.out.println("");

		System.out.println("Removing a chip of the wrong color...");
		b1.removeChip("WHITE", 2, 0);
		//System.out.println(b1.toString());
		System.out.println("");

		System.out.println("Trying to form a cluster of chips...");
		b1.placeChip("BLACK", 2, 1);
		b1.placeChip("BLACK", 3, 2);
		b1.placeChip("WHITE", 3, 2);
		System.out.println(b1.toString());
		System.out.println("");




		System.out.println("Now Testing The Network Functions And Chip Limit:");
		Board b2 = new Board();
		b2.placeChip("BLACK", 2, 0);
		b2.placeChip("BLACK", 1, 3);
		b2.placeChip("BLACK", 2, 5);
		b2.placeChip("BLACK", 3, 3);
		b2.placeChip("BLACK", 3, 5);
		b2.placeChip("BLACK", 4, 2);
		b2.placeChip("BLACK", 5, 5);
		b2.placeChip("BLACK", 5, 7);
		b2.placeChip("BLACK", 6, 0);
		b2.placeChip("BLACK", 6, 5);
		System.out.println("Placed ten chips on board.");
		System.out.println(b2.toString());
		System.out.println("");

		System.out.println("Trying to place an extra chip on board...");
		b2.placeChip("BLACK", 6, 2);
		
		System.out.println("Removing a chip and placing it in the same spot...");
		b2.removeChip("BLACK", 5, 7);
		b2.placeChip("BLACK", 5, 7);

		System.out.println(b2.toString());
		System.out.println("This board should have a network for BLACK: " + b2.hasNetwork("BLACK"));
		System.out.println("This board should not have a network for WHITE: " + b2.hasNetwork("WHITE"));
		System.out.println("");

		System.out.println("Now placing a WHITE chip in square 56...");
		b2.placeChip("WHITE", 5, 6);
		System.out.println(b2.toString());
		System.out.println("This board should still have a network: "+ b2.hasNetwork("BLACK"));
		System.out.println("This board should not have a network for WHITE: " + b2.hasNetwork("WHITE"));
		System.out.println("");

		System.out.println("Now Testing The Eval Class: ");
		System.out.println("Starting A Game With Board1. I am BLACK...");
		b1 = new Board();
		b1.placeChip("WHITE", 4, 3);
		b1.placeChip("BLACK", 2, 2);
		b1.placeChip("WHITE", 0, 5);
		System.out.println(b1.toString());
		System.out.println("The score of this board should be 0: " + Eval.eval(b1, "BLACK"));
		System.out.println("");

		b1.placeChip("BLACK", 6, 2);
		b1.placeChip("WHITE", 7, 6);
		System.out.println(b1.toString());
		System.out.println("Do I have a Network?: " + b1.hasNetwork("BLACK"));
		System.out.println("Do Opponent have a Network?: " + b1.hasNetwork("WHITE"));
		System.out.println("The score of this board should be 15. I am winning: " + Eval.eval(b1, "BLACK"));
		System.out.println("");

		b1.placeChip("BLACK", 4, 0);
		System.out.println(b1.toString());
		System.out.println("Do I have a Network?: " + b1.hasNetwork("BLACK"));
		System.out.println("Do Opponent have a Network?: " + b1.hasNetwork("WHITE"));
		System.out.println("The score of this board should be 35: " + Eval.eval(b1, "BLACK"));
		System.out.println("");
		
		b1.placeChip("WHITE", 6, 3);
		b1.placeChip("BLACK", 4, 2);
		b1.placeChip("WHITE", 6, 6);
		b1.placeChip("BLACK", 4, 4);
		b1.placeChip("WHITE", 1, 3);
		System.out.println(b1.toString());
		System.out.println("Do I have a Network?: " + b1.hasNetwork("BLACK"));
		System.out.println("Do Opponent have a Network?: " + b1.hasNetwork("WHITE"));
		System.out.println("The score of this board should be 65: " + Eval.eval(b1, "BLACK"));
		System.out.println("");

		b1.placeChip("BLACK", 2, 7);
		System.out.println(b1.toString());
		System.out.println("Do I have a Network?: " + b1.hasNetwork("BLACK"));
		System.out.println("Do Opponent have a Network?: " + b1.hasNetwork("WHITE"));
		System.out.println("The score of this board should be 1000000: " + Eval.eval(b1, "BLACK"));
		System.out.println("I WIN!!!!!");
		System.out.println("");






	}






















}