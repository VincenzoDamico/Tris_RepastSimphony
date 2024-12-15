package tris;
import java.util.HashMap;

import repast.simphony.context.Context;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StrictBorders;
import utils.Pair;

//pensa a come generalizzarlo 
public class GridPlayGround {
	private Grid < Object > grid;
	GridPlayGround(int dimx, int dimy,Context<Object> context){
		GridFactory gridFactory = GridFactoryFinder . createGridFactory ( new HashMap() );
		GridBuilderParameters gparam=GridBuilderParameters.singleOccupancy2D( new SimpleGridAdder<Object>(), new StrictBorders(),  dimx, dimy);
		Grid < Object > grid = gridFactory . createGrid ("grid", context , gparam);
	}


	public void ChangeState(String element, Pair<Integer,Integer> state) {
		grid.moveTo(element,state.getFirst(),state.getSecond() );		
	}
	public Integer size() {
		return grid.size();
	}
	public String getStringElAt(int... location) {
		String s =(String)grid.getObjectAt(location);
		return s;
	}


	
	
}