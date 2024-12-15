package tris;
import utils.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.grid.Grid;

public class Player2 extends PlayerAbstract	{
	public Player2(GridPlayGround grid, int gridDimX, int gridDimY, int winCount, float alpha, float discount_factor,
			float epsilon, List<Pair<Integer, Integer>> possibleAction, String mark) {
		super(grid, gridDimX, gridDimY, winCount, alpha, discount_factor, epsilon, possibleAction, mark);
	}	
	@ScheduledMethod( start = 2 , interval = 2) 
	public void step () {
		QlearningAlg();
	}
	 

}