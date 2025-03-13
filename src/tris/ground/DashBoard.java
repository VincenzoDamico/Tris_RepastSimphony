package tris.ground;



import utils.Costants;

public class DashBoard {
	private int countMatches=1;
	private int[] countWin;
	private int countTies=0;
	private float[] rewards;
	
	public DashBoard() {
		countWin=new int[Costants.NUM_PLAYERS];
		rewards=new float[Costants.NUM_PLAYERS];
	}
	
	public void updateWin(int i) {	
		countWin[i-1]+=1;
		rewards[i-1]+=Costants.WIN_REWARD;
		for (int j=0; j<rewards.length;j++)
			if(j!=(i-1))
				rewards[j]+=Costants.LOSE_REWARD;
		
	}
	public void updateDraw() {
		for (int j=0; j<rewards.length;j++)
			rewards[j]+=Costants.DRAW_REWARD;
		countTies+=1;

	}
	public int getTies() {
		return countTies;
	}
	
	public float getReward(int i) {
		return rewards[i-1];
	}
	public int getWins(int i) {
		return countWin[i-1];
	}
	
	public int getLosses(int i) {
		return countMatches-countWin[i-1]-countTies;
	}
	
	
	public int getMatches() {
		return countMatches;
	}
	public void updateMatches() {
		countMatches++;
	}
	
}
