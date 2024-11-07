package tris;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Player0 {
	private Grid <Object> grid ;
	private  boolean moved;
	public Player0 (  Grid < Object > grid ) {
		this.grid = grid ;
	}
	
	@ScheduledMethod( start = 1 , interval = 1)
	public void step () {
	}

}
