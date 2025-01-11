package tris.ground;

import utils.ElementWrap;

public interface PlayGround<T> {
	public GridEl<T> getElAt(int... location);
	public void changeState(GridEl<T> element);
	public Integer size();
	public void clear();
	public int  getDimX();
	public int  getDimY();
	public boolean isWinner(GridEl<T> mark);
	public boolean isFullGrid() ;
	public int  getNubMatch();
	public boolean isRestarting();
	public boolean isGameOver();
	public void printFinalData();
}
