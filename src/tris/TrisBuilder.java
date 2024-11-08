package tris;

import java.util.HashMap;
import java.util.Map;

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
import utils.Dupla;

public class TrisBuilder implements ContextBuilder<Object> {
	final public static int DIMGRID=3;
	@Override
	public Context build(Context<Object> context) {
		context.setId("tris");
		Map<String, Object> h=new HashMap();
		initializeGrid(h);
		GridFactory gridFactory = GridFactoryFinder . createGridFactory ( h );
		GridBuilderParameters gparam=GridBuilderParameters.singleOccupancy2D( new SimpleGridAdder<Object>(), new StrictBorders(), DIMGRID, DIMGRID);
		Grid < Object > grid = gridFactory . createGrid ("grid", context , gparam);
		
		context.add(new Player0 (grid));
		context.add(new PlayerX (grid));

		return context;
	}

	private void initializeGrid(Map<String, Object> h) {
		for (int i=0; i<DIMGRID;i++)
			for (int j=0; i<DIMGRID;j++) {
				Dupla d=new Dupla(i,j);
				h.put(d.toString(), "");
			}
	}

}
