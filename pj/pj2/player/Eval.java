/* Eval.java */

/**
 * The Eval class takes in a Board and evaluates it. It goes through the Board, and 
 * assigns each 
 **/ 
package player;
import ScoreBoard.*;

class Eval{
	
	private static final int win = 1000000;
	private static final int lose = -1000000;

	/* The function that returns the score for the given Board
	 */
	public static int eval(Board b, String player){
		if (b.hasNetwork(player)){
			return win;
		}
		if (b.hasNetwork(oppoColor(player))){
			return lose;
		} else {
			return rawScore(b, player);
		}
	}

	/* Returns the 
	 */
	public static int rawScore(Board b, String player){
		Chip chip;
		int score = 0;
		int oppoScore = 0;
		int chips = 0;
		int oppoChips = 0;
		for (int x = 0; x <= 7; x++){
			for (int y = 0; y <= 7; y++){
				if (!b.isEmpty(x, y)){
					chip = b.getChip(x, y);
					if(chip.getType() == player){
						score += score(b, chip);
						chips++;
					}else{
						oppoScore += score(b, chip);
						oppoChips++;
					}
				}
			}
		}
		if (chips == 0){
			chips++;
		}
		if (oppoChips == 0){
			oppoChips++;
		}
		//System.out.println(oppoGScore);
		//System.out.println(gScore);
		//System.out.println(oppoScore);
		//System.out.println(score);
		int myScore = score / chips;
		int otherScore = oppoScore / oppoChips;
		//System.out.println(myScore);
		//System.out.println(otherScore);
		return myScore - otherScore;
	}

	/* This method assigns a regular Chip (in the given Board) a score.
	 */
	protected static int score(Board b, Chip chip){
		Chip[] chips = b.findChips(chip);
		Chip curr;
		int total = 0;
		for(int i = 0; i < chips.length; i++){
			curr = chips[i];
			if (curr != null){	
				total++;
			}
		}
		return total;
	}

	/* This method "scores" a goal Chip.
	protected static int scoreGChip(Board b, Chip chip){
		Chip[] chips = b.findChips(chip);
		Chip curr;
		int total = 0;
		for (int i = 0; i < chips.length; i++){
			curr = chips[i];
			if (curr != null){
				if (!b.isGoal(curr.getColumn(), curr.getRow())){
					total += Math.max(score(b, curr), 1);
				}
			}
		}
		return total;
	}
	*/

	/* This gives me the opponents color.
	 */
	protected static String oppoColor(String player){
		if (player.equals("BLACK")) {
			return "WHITE";
		} else {
			return "BLACK";
		}
	}



}