package tris.ground;

import utils.ElementWrap;

public interface PlayGround<T> {
	public GridEl<T> getElAt(int... location);
	public void changeState(GridEl<T> element);
	public Integer size();
	public void clear();
	public boolean isWinner(String mark);
	public boolean isFullGrid() ;
	public int  getNubMatch();
	public boolean isRestarting();
	public void notifyRestart();
	public boolean isGameOver();
	public void printFinalData();
	public void updateWinReward(int i);
	public void updateDrawReward();
	public float getReward(int i);
}
