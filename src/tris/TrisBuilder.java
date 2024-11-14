package tris;

import java.util.HashMap;
import java.util.Map;


import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.gis.SimpleAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StickyBorders;
import repast.simphony.space.grid.StrictBorders;

public class TrisBuilder  implements ContextBuilder<Object> {
	final public static int DIMGRID=3;
	final public static int ALPHA=3;
	final public static int EPSION=3;
	final public static int DISCOUNT_FACTOR=3;


	@Override
	public Context build(Context<Object> context) {
		context.setId("tris");
	
		GridFactory gridFactory = GridFactoryFinder . createGridFactory ( new HashMap() );
		GridBuilderParameters gparam=GridBuilderParameters.singleOccupancy2D( new SimpleGridAdder<Object>(), new StrictBorders(), DIMGRID, DIMGRID);
		Grid < Object > grid = gridFactory . createGrid ("grid", context , gparam);
		
		
		context.add(new Player0 (grid,DIMGRID, ALPHA, EPSION, DISCOUNT_FACTOR));
		context.add(new PlayerX (grid,DIMGRID));

		return context;
	}

}
