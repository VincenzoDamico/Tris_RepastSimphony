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
import tris.ground.GridPlayGround;
import utils.Pair;

public abstract class PlayerGrid2DAbstract extends QlearnigTemplate implements Player	{
	private GridPlayGround<String> grid ;
	private  final int gridDimX;
	private final int gridDimY;
	private final int winCount;
	private  float reward =0;
	private final float alpha;
	private final float discount_factor;
	private final float epsilon;
	private Map<Pair<Integer,Integer>,Float> q_table;
	private List<Pair<Integer, Integer>> knownAction;
	private Pair<Integer,Integer> state;
	private String mark;
	private List<Pair<Integer,Integer>>possibleAction;
	private List<Pair<Integer,Integer>>notExplored;
	
	public PlayerGrid2DAbstract (  GridPlayGround grid, int gridDimX, int gridDimY, int winCount, float alpha, float discount_factor, float epsilon, List<Pair<Integer,Integer>>possibleAction, String mark) {
		this.grid = grid ;
		this.gridDimX=gridDimX;
		this.gridDimY=gridDimY;
		this.winCount=winCount;
		//creazione q-table random 
		this.alpha=alpha;
		this.discount_factor= discount_factor;
		this.epsilon=epsilon;
		this.possibleAction=possibleAction;
		notExplored=new LinkedList<>(possibleAction);
	
		
		this.mark=mark;
		q_table=new HashMap<>(gridDimX*gridDimY); 
		utils.utilsOp.inizialize( q_table,gridDimX,gridDimY);
		knownAction=new LinkedList<Pair<Integer, Integer>>();
		
		int inizialState=new Random().nextInt(possibleAction.size());
		state=possibleAction.remove(inizialState);
		notExplored.remove(inizialState);
		grid.ChangeState(mark,state.getFirst(),state.getSecond());

	}
	
	protected boolean epsilonPolicy() {
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
		stampaC("updateQtableI");
		Pair <Double, Integer> maxAction= MaxAction();
		double maxValue=maxAction.getFirst();
		int el =maxAction.getSecond();		
		Pair <Integer,Integer> newState=knownAction.get(el);
		
		//non ho idea del perchè
		for (Pair<Integer, Integer> p : q_table.keySet()) {
			System.out.println(p);
			System.out.println(p.equals(newState));
			if(p.equals(newState))
				newState=p;
			System.out.println( q_table.get(newState));
		}
		//non ho idea del perchè
		//non ho idea del perchè
		for (Pair<Integer, Integer> p : q_table.keySet()) {
			System.out.println(p);
			System.out.println(p.equals(state));
			if(p.equals(state))
				state=p;
			System.out.println( q_table.get(state));
		}
		//non ho idea del perchè
		
		
		
		Float val=q_table.get(state)+alpha*(reward+discount_factor*q_table.get(newState) -q_table.get(state));
		q_table.put(state,val);
		
		
		state=newState;	
		grid.ChangeState(mark,state.getFirst(),state.getSecond());
		
		System.out.println("-----------------");
		System.out.println(state);
		System.out.println(possibleAction.remove(state));
		
		knownAction.remove(el); 
		stampaC("updateQtableF");

	}


	protected void explore() {
		stampaC("exploreI");

		int el=new Random().nextInt(notExplored.size());
		Pair<Integer, Integer> p= notExplored.get(el);
		System.out.print("elemento aggiunto alla list adelle azioni:");
		System.out.println(p);
		notExplored.remove(el);
		knownAction.add(p);
		stampaC("exploreF");

	}


	private Pair<Double, Integer> MaxAction() {
		stampaC("MaxActionI");

		int el=0;
		System.out.println(knownAction.size());
		Pair<Integer, Integer> p0= knownAction.get(el);
		
		//da chiedere perchè
		for (Pair<Integer, Integer> p : q_table.keySet()) {
			System.out.println(p);
			System.out.println(p.equals(p0));
			if(p.equals(p0))
				p0=p;
			System.out.println( q_table.get(p));
		}	//non ho idea del perchè
		
		System.out.println("\n"+p0);
	

		System.out.println( q_table.get(p0));
	

        
		double max=q_table.get(p0);
		
		for(int i=1;i< knownAction.size(); i++) {
			Pair <Integer , Integer> p=knownAction.get(i);
			//da chiedere perchè
			for (Pair<Integer, Integer> p3 : q_table.keySet()) {
				System.out.println( q_table.get(p3));
				System.out.println(p.equals(p3));
				if(p.equals(p3))
					p=p3;
				System.out.println( q_table.get(p));
			}		
			//non ho idea del perchè
			 
			
			if (q_table.get(p)>max) {
				el=i;
				max=q_table.get(p);
			}
		}
		stampaC("MaxActionF");
		return new Pair<Double, Integer>(max, el);
	}
	
	protected boolean isDone() {
		return isWinner() || isFullGrid();
	}

	private boolean isWinner() {
		boolean won=utils.utilsOp.isWinner(mark,winCount,gridDimX,gridDimY,grid);
		if (won) {
			reward +=10;
			System.out.println("\n Il Player: "+mark+" ha vintooooooo!!");
		}
		return won;
	}

	private boolean isFullGrid() {
        reward ++;
        boolean ret=grid.size()==gridDimX*gridDimY;
        if(ret) {
        	System.out.println("\n Hanno pareggiato");
        }
		return ret;
	}

	protected void end() {
		RunEnvironment.getInstance().endRun();
	}
}