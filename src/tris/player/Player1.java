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
	private GridPlayGround grid;
	public Player1(GridPlayGround grid, float alpha, float discount_factor,float epsilon, List<Pair<Integer, Integer>> possibleAction, 
			GridEl<String> mark,float delta,ElementWrap<Integer> countPar) {
		super(grid, alpha, discount_factor, epsilon, possibleAction, mark, delta,countPar);
		this.grid=grid;
	}
	
	@Override
	@ScheduledMethod( start = 1 , interval = 2) 
	public void step () {
	//	if(!grid.isGameOver()) {		
			System.out.println("\nSiamo nel Match numero: "+grid.getNubMatch());
			QlearningAlg();
		/*}else {
			System.out.println("\nReward accumulate dal Player1 "+super.getReward());
			RunEnvironment.getInstance().endRun();
		}*/
	}
	 
}
