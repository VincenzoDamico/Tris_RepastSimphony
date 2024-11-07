package tris;

import repast.simphony.context.Context;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.gis.SimpleAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StickyBorders;
import repast.simphony.space.grid.StrictBorders;

public class TrisBuilder implements ContextBuilder<Object> {
	@Override
	public Context build(Context<Object> context) {
		context.setId("tris");
		GridFactory gridFactory = GridFactoryFinder . createGridFactory ( null );
		GridBuilderParameters gparam=GridBuilderParameters.singleOccupancy2D( new SimpleGridAdder<Object>(), new StrictBorders(), 3, 3);
		Grid < Object > grid = gridFactory . createGrid ("grid", context , gparam);
		
		context.add(new Player0 (grid));
		context.add(new PlayerX (grid));

		return context;
	}

}
