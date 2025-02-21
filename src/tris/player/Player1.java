package tris.player;
import utils.Costant;
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
	private GridEl<String> mark;

	public Player1(GridPlayGround grid, List<Pair<Integer, Integer>> possibleAction, 
			GridEl<String> mark,ElementWrap<Integer> countPar) {
		super(grid, possibleAction, mark, countPar);
		this.grid=grid;
		this.mark=mark;

	}
	
	@Override
	@ScheduledMethod( start = 1 , interval = 2) 
	public void step () {
			if(grid.isRestarting()&& !grid.isYourTurn(mark.getEl()) && Costant.TURN_MODE) {
				//System.out.println("Siamo nel"+ mark.getEl());

				return;
			}
			System.out.println("\nSiamo nel Match numero: "+grid.getNubMatch());
			QlearningAlg();
			
	}
	 
}
