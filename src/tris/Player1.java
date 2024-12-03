package tris;
import utils.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.grid.Grid;

//posso farlo senza la griglia con una semplice matrice 
//vedere come apllicare il q learning 
public class Player1 extends QlearnigTemplate	{
	//la gestisco come un hash map le chiave è un dupla che rappresenta le posizioni 
	private Grid <Object> grid ;
	private  boolean moved;
	private  final int gridDimX;
	private final int gridDimY;
	private final int winCount;
	private  float reward =0;
	private final float alpha;
	private final float discount_factor;
	private final float epsilon;
	
	private Map<Pair<Integer,Integer>,Float> q_table;
	
	private List<Pair<Integer, Integer>> knownAction=new LinkedList<>();
	private Pair<Integer,Integer> state;
	private String mark;
	List<Pair<Integer,Integer>>possibleAction;
	
	public Player1 (  Grid < Object > grid, int gridDimX, int gridDimY, int winCount, float alpha, float discount_factor, float epsilon, List<Pair<Integer,Integer>>possibleAction, String mark) {
		this.grid = grid ;
		this.gridDimX=gridDimX;
		this.gridDimY=gridDimY;
		this.winCount=winCount;
		//creazione q-table random 
		this.alpha=alpha;
		this.discount_factor= discount_factor;
		this.epsilon=epsilon;
		this.possibleAction=possibleAction;
		this.mark=mark;
		q_table=new HashMap<>(gridDimX*gridDimY); 
		utils.utilsOp.inizialize(q_table, gridDimX, gridDimY);
		
		state=new Pair<>(new Random().nextInt(gridDimX),new Random().nextInt(gridDimY));
		
	}
	
	
	@ScheduledMethod( start = 1 , interval = 2) 
	public void step () {
		QlearningAlg();
	}
	 
	protected boolean epsilonPolicy() {
		return (knownAction.isEmpty() || Math.random()<epsilon) && !possibleAction.isEmpty();
	}
	
	protected void updateQtable() {
		Pair <Double, Integer> maxAction= MaxAction();
		double maxValue=maxAction.getFirst();
		int el =maxAction.getSecond();		
		Pair <Integer,Integer> newState=knownAction.get(el);
		float val=q_table.get(state)+alpha*(reward+discount_factor*q_table.get(newState) -q_table.get(state));
		q_table.put(state,val);
		state=newState;
		
		grid.getAdder().add(grid, mark);
		grid.moveTo(mark,state.getFirst(),state.getSecond() );
		knownAction.remove(el); 		
	}


	protected void explore() {
		int el=new Random().nextInt(possibleAction.size());
		Pair<Integer, Integer> p= possibleAction.get(el);
		possibleAction.remove(el);
		knownAction.add(p);
	}


	private Pair<Double, Integer> MaxAction() {
		int el=0;
		Pair<Integer, Integer> p0=knownAction.get(el);
		double max=q_table.get(p0);
		
		for(int i=1;i< knownAction.size(); i++) {
			Pair <Integer , Integer> p=knownAction.get(i);
			if (q_table.get(p)>max) {
				el=i;
				max=q_table.get(p);
			}
		}
		return new Pair<Double, Integer>(max, el);
	}
	
	protected boolean isDone() {
		return isWinner() || isFullGrid();
	}

	private boolean isWinner() {
		boolean won=utils.utilsOp.isWinner(mark,winCount,gridDimX,gridDimY,grid);
		if (won)
			reward +=10;
		return won;
	}

	private boolean isFullGrid() {
        reward ++;
		return grid.size()==gridDimX*gridDimY;
	}

	protected void end() {
		RunEnvironment.getInstance().endRun();
	}
}
