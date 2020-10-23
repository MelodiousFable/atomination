public class Player {
	private int gridsOwned;
	private String colour;
	private String concatenated;
	private boolean eliminated;
	private boolean hadFirstMove;
	
	public Player(String colour, String c) {
		this.gridsOwned = 0;
		this.colour = colour;
		this.eliminated = false;
		this.hadFirstMove = false;
		this.concatenated = c;
	}
	
	public String getColour() { return this.colour; }
	public int getGridsOwned() { return this.gridsOwned; }
	public boolean isEliminated() { return this.eliminated; }
	public String getC() { return this.concatenated; }
	
	public void incrementGrids() { this.gridsOwned += 1; }
	public void decrementGrids() { if(this.gridsOwned > 0) {this.gridsOwned -= 1;} }
	public boolean checkEliminated() { return this.eliminated; }
	public void eliminate() {
		if(this.hadFirstMove) {
			if(this.gridsOwned == 0) {
				this.eliminated = true;
			}
		}
	}
	public void move() { this.hadFirstMove = true; }
	public String toString() {
		return this.colour;
	}
}
