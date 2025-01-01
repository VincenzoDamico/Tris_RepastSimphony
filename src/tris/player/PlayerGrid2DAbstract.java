package tris.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.grid.Grid;
import tris.QlearnigTemplate;
import tris.ground.GridEl;
import tris.ground.GridPlayGround;
import utils.ElementWrap;
import utils.Pair;

public abstract class PlayerGrid2DAbstract extends QlearnigTemplate implements Player	{
	private GridPlayGround<String> grid ;
	private  final int gridDimX;
	private final int gridDimY;
	private final int winCount;
	private final float delta;

	private int countWin=0;
	private int countPar=0;


	private  float reward =0;
	private final float alpha;
	private final float discount_factor;
	private final float epsilon;
	private Map<Pair<Integer,Integer>,Float> q_table;
	private Map<Pair<Integer,Integer>,Float> old_Qtable;
	private List<Pair<Integer, Integer>> knownAction;
	private Pair<Integer,Integer> state;
	private GridEl<String> mark;
	private List<Pair<Integer,Integer>>possibleAction;
	private List<Pair<Integer,Integer>>notExplored;
	private ElementWrap<Boolean> restart;
	
	public PlayerGrid2DAbstract (  GridPlayGround grid, int gridDimX, int gridDimY, int winCount, float alpha, float discount_factor, float epsilon, List<Pair<Integer,Integer>>possibleAction, GridEl<String> mark,float delta,ElementWrap<Boolean> restart) {
		this.grid = grid ;
		this.gridDimX=gridDimX;
		this.gridDimY=gridDimY;
		this.winCount=winCount;
		this.delta=delta;
		this.restart=restart;

		//creazione q-table random 
		this.alpha=alpha;
		this.discount_factor= discount_factor;
		this.epsilon=epsilon;
		this.possibleAction=possibleAction;
		this.mark=mark;
		q_table=new HashMap<>(gridDimX*gridDimY); 
		utils.utilsOp.inizialize( q_table,gridDimX,gridDimY);
		old_Qtable=utils.utilsOp.copy(q_table);
		inizialize();
	}
	private void inizialize() {
		System.out.println("restart inizialize"+ restart);

		knownAction=new LinkedList<Pair<Integer, Integer>>();
		notExplored=new LinkedList<>(possibleAction);
		int inizialState=new Random().nextInt(possibleAction.size());
		state=possibleAction.remove(inizialState);
		notExplored.remove(inizialState);
		mark.setPos(state.getFirst(),state.getSecond());
		grid.ChangeState(mark);
	}
	private void reinizialize() {
		possibleAction.clear();
		grid.clear();
		utils.utilsOp.fillPair(possibleAction,gridDimX, gridDimY );
		old_Qtable=utils.utilsOp.copy(q_table);
		inizialize();

	}
	
	protected boolean epsilonPolicy() {
		System.out.println("restart epsilonPolicy "+ restart);

		if (restart.getEl()== true) {
			inizialize();
			restart.setEl(false);
		}
		updateExp();
		float el=(float) Math.random();
		return (knownAction.isEmpty() || el<epsilon) && !notExplored.isEmpty();
	}
	
	private void updateExp() {
		List<Pair<Integer,Integer>>rem =new LinkedList<>();
		for( Pair<Integer,Integer> p: notExplored) {
			if(!possibleAction.contains(p)) {
				rem.add(p);
			}
		}
		for( Pair<Integer,Integer> p: rem) {
			notExplored.remove(p);
		}
		rem.clear();;
		for( Pair<Integer,Integer> p: knownAction) {
			if(!possibleAction.contains(p)) {
				rem.add(p);
			}
		}
		for( Pair<Integer,Integer> p: rem) {
			knownAction.remove(p);
		}
	}

	private void stampaC(String s) {
		System.out.println("**************************** "+ mark);

		System.out.println(s);
		System.out.println("\n Q table");
		
		for (Pair<Integer, Integer> p : q_table.keySet()) {
			System.out.print(p+ "valore = ");
			System.out.println(q_table.get(p));
		}
		
		System.out.println("\n knownAction");
		for (Pair<Integer, Integer> p : knownAction) {
			System.out.println(p);
		}
		System.out.println("\n notExplored");
		for (Pair<Integer, Integer> p : notExplored) {
			System.out.println(p);
		}
		
		System.out.println("\n possibleAction");
		for (Pair<Integer, Integer> p : possibleAction) {
			System.out.println(p);
		}
		System.out.println("****************************"+ mark);
		
	}
	
	protected void updateQtable() {
		//stampaC("updateQtableI");
		Pair <Double, Integer> maxAction= MaxAction();
		double maxValue=maxAction.getFirst();
		int el =maxAction.getSecond();		
		Pair <Integer,Integer> newState=knownAction.get(el);

		float val=q_table.get(state)+alpha*(reward+discount_factor*q_table.get(newState) -q_table.get(state));
		q_table.put(state,val);
		
		state=newState;	
		mark.setPos(state.getFirst(),state.getSecond());
		grid.ChangeState(mark);

		possibleAction.remove(state);
		
		knownAction.remove(el); 
		//stampaC("updateQtableF");

	}


	protected void explore() {
		//stampaC("exploreI");

		int el=new Random().nextInt(notExplored.size());
		Pair<Integer, Integer> p= notExplored.get(el);
		System.out.print("elemento aggiunto alla list adelle azioni:");
		System.out.println(p);
		notExplored.remove(el);
		knownAction.add(p);
		//stampaC("exploreF");

	}


	private Pair<Double, Integer> MaxAction() {
		//stampaC("MaxActionI");

		int el=0;
		Pair<Integer, Integer> p0= knownAction.get(el);        
		double max=q_table.get(p0);
		
		for(int i=1;i< knownAction.size(); i++) {
			Pair <Integer , Integer> p=knownAction.get(i);		
			if (q_table.get(p)>max) {
				el=i;
				max=q_table.get(p);
			}
		}
		//stampaC("MaxActionF");
		return new Pair<Double, Integer>(max, el);
	}
	
	protected boolean isDone() {
		if (isWinner() || isFullGrid()) {
			System.out.println("\n Il Player: "+mark+" numero di vittorie: "+countWin+" numero di pareggi: "+countPar);
			System.out.println(hasLearned());
			System.out.println("Old Qtable -----------------------");
			utils.utilsOp.PrintTable(old_Qtable,gridDimX,gridDimY);
			System.out.println("Qtable -----------------------");
			utils.utilsOp.PrintTable(q_table,gridDimX,gridDimY);
			System.out.println(hasLearned()<delta);
			if (hasLearned()<delta) {
				return true;
			}
			System.out.println("Ricominciamo la partita");
			restart.setEl(true);
			reinizialize();
			
		}
		System.out.println("restart "+restart);

		return false;
	}
	
	private float hasLearned() {
		float count=0;
		for (Pair<Integer, Integer> p: old_Qtable.keySet()) {
			count+=Math.pow((old_Qtable.get(p)-q_table.get(p)),2);
		}
		count= (float) (Math.round(count*100.0)/100.0);
		return count;
	}


	private boolean isWinner() {
		boolean won=utils.utilsOp.isWinner(mark,winCount,gridDimX,gridDimY,grid);
		if (won) {
			reward +=10;
			System.out.println("\n Il Player: "+mark+" ha vintooooooo!!");
			countWin++;
		}
		return won;
	}

	private boolean isFullGrid() {
        reward ++;
        boolean ret=grid.size()==gridDimX*gridDimY;
        if(ret) {
        	System.out.println("\n Hanno pareggiato");
        	countPar++;
        }
		return ret;
	}

	protected void end() {
		RunEnvironment.getInstance().endRun();
	}
}