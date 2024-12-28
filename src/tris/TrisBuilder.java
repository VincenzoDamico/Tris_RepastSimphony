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
import tris.ground.GridPlayGround;
import tris.player.Player1;
import tris.player.Player2;

public class TrisBuilder  implements ContextBuilder<Object> {
	final public static int DIMGRIDX=3;
	final public static int DIMGRIDY=3;
	final public static int WINCOUNT=3;
	final public static float ALPHA=0.5f; //set generally between 0 and 1
	final public static float EPSION=0.3f;
	final public static float DISCOUNT_FACTOR=0.5f;  //limitato tra 0 e 1

	@Override
	public Context build(Context<Object> context) {
		context.setId("tris");
		GridPlayGround<String> grid=new GridPlayGround(context,DIMGRIDX,DIMGRIDY);
		List<Pair<Integer,Integer>> possibleAction = Collections.synchronizedList(new LinkedList<>()); 
		//in teoria non c'è bisogno di gestire un accesso concorrenziale poichè i 2 agenti eseguono gli step uno dopo l'altro 
		fill(possibleAction);

		
		context.add(new Player1 (grid,DIMGRIDX,DIMGRIDY,WINCOUNT, ALPHA, DISCOUNT_FACTOR, EPSION,possibleAction,"1"));
		context.add(new Player2 (grid,DIMGRIDX,DIMGRIDY,WINCOUNT, ALPHA, DISCOUNT_FACTOR, EPSION,possibleAction,"2"));

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
