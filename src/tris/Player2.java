package tris;
import utils.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.grid.Grid;

//posso farlo senza la griglia con una semplice matrice 
//vedere come apllicare il q learning 
public class Player2 extends QlearnigTemplate	{
	//la gestisco come un hash map le chiave è un dupla che rappresenta le posizioni 
	private Grid <Object> grid ;
	private  boolean moved;
	private  final int gridDimX;
	private final int gridDimY;
	private final int winCount;
	private  float reward =0;
	private final int alpha;
	private final int discount_factor;
	private final int epsilon;
	private double q_table[][];
	private List<Pair<Integer, Integer>> knownAction=new LinkedList<>();
	private Pair<Integer,Integer> state;
	private String mark;
	List<Pair<Integer,Integer>>possibleAction;
	
	public Player2 (  Grid < Object > grid, int gridDimX, int gridDimY, int winCount, int alpha, int discount_factor, int epsilon, List<Pair<Integer,Integer>>possibleAction, String mark) {
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
		q_table=new double [gridDimX][gridDimY];
		utils.utilsOp.inizialize(q_table, gridDimX, gridDimY);
		
		state=new Pair<>(new Random().nextInt(gridDimX),new Random().nextInt(gridDimY));
		
	}
	
	
	@ScheduledMethod( start = 2 , interval = 2) 
	public void step () {
		while(true) {
			if(epsilonPolicy()) { 
				explore();
			}else {
				updateQtable();
				break;
			}
		}
		if(isDone()) {
			RunEnvironment.getInstance().endRun();
		}
	}
	 
	protected boolean epsilonPolicy() {
		return (knownAction.isEmpty() || Math.random()<epsilon) && !possibleAction.isEmpty();
	}
	
	protected void updateQtable() {
		Pair <Double, Integer> maxAction= MaxAction();
		double maxValue=maxAction.getFirst();
		int el =maxAction.getSecond();		
		Pair <Integer,Integer> newState=knownAction.get(el);
		q_table[state.getFirst()][state.getSecond()]+=alpha*(reward+discount_factor*q_table[newState.getFirst()][newState.getSecond()] -q_table[state.getFirst()][state.getSecond()]);
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
		double max=q_table[p0.getFirst()][p0.getSecond()];
		
		for(int i=1;i< knownAction.size(); i++) {
			Pair <Integer , Integer> p=knownAction.get(i);
			if (q_table[p.getFirst()][p.getSecond()]>max) {
				el=i;
				max= q_table[p.getFirst()][p.getSecond()];
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

}