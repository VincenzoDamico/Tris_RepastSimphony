package tris.ground;
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
public class GridPlayGround <T> implements PlayGround<T>{
	private Grid <  GridEl<T> > gridTris;
	Context<Object> context;
	int [] dim;
	
	public GridPlayGround(Context<Object> context,int... dim){
		
		GridFactory gridFactory = GridFactoryFinder . createGridFactory ( new HashMap() );
		GridBuilderParameters gparam=GridBuilderParameters.singleOccupancyND( new SimpleGridAdder<Object>(), new StrictBorders(),  dim);
		this.gridTris = gridFactory . createGrid ("gridTris", context , gparam);
		this.context=context;
		this.dim=dim;
	}
	public void PrintGrid() {
	    System.out.println("__________________________ \nStampa griglia 2D");
	    
	    for (int i = 0; i < dim[1]; i++) {
			for (int j = 0; j < dim[0]; j++) {
				System.out.print(gridTris.getObjectAt(i,j)+" \t");
			}
		    System.out.println();
		}
		System.out.println();	
		
    }
				


	@Override
	public void ChangeState(T element, int... state) {
		  GridEl<T> el= new GridEl(element,state);
		PrintGrid();
	    context.add(el);
		//gridTris.getAdder().add(gridTris, element);
		gridTris.moveTo(el,state);	
		PrintGrid();
	}
	
	@Override
	public Integer size() {
		return gridTris.size();
	}
	
	@Override
	public GridEl<T> getElAt(int... location) {
		GridEl<T> s=null;
		try {
		  s =(GridEl<T>)gridTris.getObjectAt(location);
		  
		} catch (ClassCastException e) {
		}
		return s;
	}


	
	
}