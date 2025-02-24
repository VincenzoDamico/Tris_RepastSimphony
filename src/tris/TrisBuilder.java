package tris;

//creare ambiente di gioco con griglia e con i metodi che permetto di giocare (il simulatura) logica di gioco e di reward classe astratta
//impostare i run 


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jogamp.newt.Display;

import utils.Costant;
import utils.ElementWrap;
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
import repast.simphony.visualization.engine.DisplayDescriptor;
import tris.ground.Agent0;
import tris.ground.AgentX;
import tris.ground.GridEl;
import tris.ground.GridPlayGround;
import tris.player.Player1;
import tris.player.Player2;
import tris.player.PlayerGrid2DAbstract;

public class TrisBuilder  implements ContextBuilder<Object> {
	@Override
	public Context build(Context<Object> context) {
		context.setId("tris");
		GridPlayGround<String> grid=new GridPlayGround(context);
		List<Pair<Integer,Integer>> possibleAction = new LinkedList<>(); 
		Pair.fillPair(possibleAction,Costant.DIMGRIDX,Costant.DIMGRIDY);
		
		ElementWrap<Integer > countPar=new ElementWrap<>(0);
		Player1 p1=new Player1 (grid,possibleAction,new AgentX<String>(1,Costant.MARKPL1),countPar);
		Player2 p2=new Player2 (grid,possibleAction,new Agent0<String>(2,Costant.MARKPL2),countPar);

		PlayerGrid2DAbstract[] opponets={p2,p1};
		grid.setOppenets(opponets);	
		
		context.add(p1);
		context.add(p2);
		return context;
	}


}
