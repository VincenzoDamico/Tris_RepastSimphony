package tris;
import java.util.LinkedList;
import java.util.List;
import utils.Costants;
import utils.Pair;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import tris.ground.Agent0;
import tris.ground.AgentX;
import tris.ground.DashBoard;
import tris.ground.GridPlayGround;
import tris.player.Player1;
import tris.player.Player2;
import tris.player.PlayerGrid2DAbstract;

public class TrisBuilder  implements ContextBuilder<Object> {
	@Override
	public Context build(Context<Object> context) {
		context.setId("tris");		
		
		DashBoard dashBoard=new DashBoard();
		GridPlayGround<String> grid=new GridPlayGround(context,dashBoard);
		
		List<Pair<Integer,Integer>> possibleAction = new LinkedList<>(); 
		Pair.fillPair(possibleAction,Costants.DIMGRIDX,Costants.DIMGRIDY);
		
		Player1 p1=new Player1 (grid,possibleAction,new AgentX<String>(1,Costants.MARKPL1),dashBoard);
		Player2 p2=new Player2 (grid,possibleAction,new Agent0<String>(2,Costants.MARKPL2),dashBoard);

		PlayerGrid2DAbstract[] opponets={p2,p1};
		grid.setOppenets(opponets);	
		
		context.add(p1);
		context.add(p2);
		return context;
	}
}
