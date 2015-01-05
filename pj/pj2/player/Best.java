/* Best.java */
package player;

class Best {
	public double score;
	public Move bestMove;

	public Best(double score, Move bestMove) {
      this.score = score;
      this.bestMove = bestMove;
	}
	public Best(double score) {
		this.score = score;
	}
	public Best() {
	}
}