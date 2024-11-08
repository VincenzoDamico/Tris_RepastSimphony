package tris;

import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;
import repast.simphony.space.grid.Grid;

public class PlayerX  {
	private Grid <Object> grid ;
	private  boolean moved;
	public PlayerX (  Grid < Object > grid ) {
		this.grid = grid ;
	}
	@Watch ( watcheeClassName = "tris.Player0",
			watcheeFieldNames = "moved",
			query = "within_moore 1",
			whenToTrigger = WatcherTriggerSchedule . IMMEDIATE )
	public void run () {}
	
}
