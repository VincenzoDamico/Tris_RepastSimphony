package tris;

//creare ambiente di gioco con griglia e con i metodi che permetto di giocare (il simulatura) logica di gioco e di reward classe astratta
//impostare i run 


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import utils.Pair;


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
	final public static int DIMGRIDX=3;
	final public static int DIMGRIDY=3;
	final public static int WINCOUNT=3;
	final public static float ALPHA=3;
	final public static float EPSION=3;
	final public static float DISCOUNT_FACTOR=3;

	@Override
	public Context build(Context<Object> context) {
		context.setId("tris");
		GridPlayGround grid=new GridPlayGround(DIMGRIDX,DIMGRIDY,context);
		
		List<Pair<Integer,Integer>> possibleAction =new LinkedList<>(); 
		//in teoria non c'è bisogno di gestire un aceso concorrenziale poichè i 2 agenti eseguono gli step uno dopo l'altro 
		
		fill(possibleAction);
		
		
		context.add(new Player1 (grid,DIMGRIDX,DIMGRIDY,WINCOUNT, ALPHA, EPSION, DISCOUNT_FACTOR,possibleAction,"1"));
		
		context.add(new Player2 (grid,DIMGRIDX,DIMGRIDY,WINCOUNT, ALPHA, EPSION, DISCOUNT_FACTOR,possibleAction,"2"));

		return context;
	}


	private void fill(List<Pair<Integer, Integer>> possibleAction) {
			for (int i=0; i<DIMGRIDX; i++) {
				for(int j=0; j<DIMGRIDY; j++) {
					possibleAction.add(new Pair<>(i,j));
				}
			}
	}

}
