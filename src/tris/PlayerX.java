package tris;

import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;
import repast.simphony.space.grid.Grid;

public class PlayerX  {
	private Grid <Object> grid ;
	private  boolean moved;
	private int gridDim;

	public PlayerX(  Grid < Object > grid, int gridDim) {
		this.grid = grid ;
		this.gridDim=gridDim;
	}
	@Watch ( watcheeClassName = "tris.Player0",
			watcheeFieldNames = "moved",
			query = "colocated", //colocated - true if the watcher and the watchee are in the same context.
			whenToTrigger = WatcherTriggerSchedule . IMMEDIATE )
	public void run () {
		
	}
	
}
