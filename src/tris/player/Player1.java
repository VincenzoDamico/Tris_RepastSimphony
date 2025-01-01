package tris.player;
import utils.ElementWrap;
import utils.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.grid.Grid;
import tris.ground.GridEl;
import tris.ground.GridPlayGround;

public class Player1 extends PlayerGrid2DAbstract	{	
	public int cnt=0;
	public final int maxRep;
	public Player1(GridPlayGround grid, int gridDimX, int gridDimY, int winCount, float alpha, float discount_factor,
			float epsilon, List<Pair<Integer, Integer>> possibleAction, GridEl<String> mark,float delta, int maxRep,ElementWrap<Boolean> restartFlag ) {
		super(grid, gridDimX, gridDimY, winCount, alpha, discount_factor, epsilon, possibleAction, mark, delta,restartFlag);
		this.maxRep=maxRep;
	}
	
	@Override
	@ScheduledMethod( start = 1 , interval = 2) 
	public void step () {
		if(cnt<maxRep) {
			QlearningAlg();
			cnt++;
		}else {
			RunEnvironment.getInstance().endRun();
		}
	}
	 
}
