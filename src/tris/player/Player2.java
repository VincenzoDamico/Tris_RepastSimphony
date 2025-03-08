package tris.player;
import utils.Costant;
import utils.Pair;
import java.util.List;
import repast.simphony.engine.schedule.ScheduledMethod;
import tris.ground.DashBoard;
import tris.ground.GridEl;
import tris.ground.GridPlayGround;

public class Player2 extends PlayerGrid2DAbstract	{	
	private GridPlayGround grid;
	private GridEl<String> mark;
	private DashBoard dashBoard;
	public Player2(GridPlayGround grid, List<Pair<Integer, Integer>> possibleAction, 
			GridEl<String> mark,DashBoard dashBoard) {
		super(grid, possibleAction, mark,dashBoard);
		this.grid=grid;
		this.mark=mark;
		this.dashBoard=dashBoard;
	}
	
	@Override
	@ScheduledMethod( start = 2 , interval = 2) 
	public void step () {
			if(grid.isRestarting()&& !grid.isYourTurn(mark.getEl()) && Costant.TURN_MODE) {
				//Sto saltando la mossa perchè non è il mio turno
				return;
			}
			System.out.println("\nSiamo nel Match numero: "+dashBoard.getMatches());
			QlearningAlg();
	}
	 
}