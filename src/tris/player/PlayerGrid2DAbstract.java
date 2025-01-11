package tris.player;

import java.util.Arrays;
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
	private final float delta;
	private int countWin=0;
	private ElementWrap<Integer > countTies;
	private  boolean  restart =false;
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

	
	public PlayerGrid2DAbstract (  GridPlayGround grid, float alpha, float discount_factor, float epsilon, List<Pair<Integer,Integer>>possibleAction, GridEl<String> mark,float delta,ElementWrap<Integer > countTies) {
		this.grid = grid ;
		this.delta=delta;
		this.countTies=countTies;		
		this.mark=mark;
		this.alpha=alpha;
		this.discount_factor= discount_factor;
		this.epsilon=epsilon;
		//gestione mosse + q table 
		this.possibleAction=possibleAction;
		q_table=new HashMap<>(grid.getDimX()*grid.getDimY()); 
		System.out.println("Inizializzazione valore casuali qtable player"+mark.getEl());
		utils.utilsOp.inizialize( q_table,grid.getDimX(),grid.getDimY());
		old_Qtable=utils.utilsOp.copy(q_table);
		inizialize();
	}
	
	private void inizialize() {
		System.out.println("\nInizio del match numero: "+grid.getNubMatch()
		+"\nTocca a Player "+mark.getEl());

		knownAction=new LinkedList<Pair<Integer, Integer>>();
		notExplored=new LinkedList<>(possibleAction);
		int inizialState=new Random().nextInt(possibleAction.size());
		state=possibleAction.remove(inizialState);
		notExplored.remove(inizialState);
		mark.setPos(state.getFirst(),state.getSecond());
		grid.changeState(mark);
		
	}
	
	private void reinizialize() {
		possibleAction.clear();
		grid.clear();
		System.out.println("Ricominciamo la partita");
		grid.printGrid();
		utils.utilsOp.fillPair(possibleAction,grid.getDimX(), grid.getDimY() );
		old_Qtable=utils.utilsOp.copy(q_table);
		inizialize();
	}
	
	protected boolean epsilonPolicy() {
		if (!grid.isRestarting()) {
			restart=false;
			updateExp();		
			float el=(float) Math.random();
			return (knownAction.isEmpty() || el<epsilon) && !notExplored.isEmpty();
		}
		restart=true;
		inizialize();
		return false;
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
	
	protected void explore() {
		System.out.println("Recap situazione:");
		printInfo();

		System.out.println("Player "+mark.getEl()+ " sta esporando.......");
		int el=new Random().nextInt(notExplored.size());
		Pair<Integer, Integer> p= notExplored.get(el);
		System.out.println("Nuova posizione scoperta -> "+p);

		notExplored.remove(el);
		knownAction.add(p);

	

	}
	
	protected void updateQtable() {
		if (!restart) {
		
			int el =MaxAction();
			Pair <Integer,Integer> newState=knownAction.get(el);
			float oldVal=q_table.get(state);
			float val=oldVal+alpha*(reward+discount_factor*q_table.get(newState) -oldVal);
			q_table.put(state,val);
			
			System.out.println("Vecchia posizione -> " +state+"\nPosizione attuale -> " +newState+ "\nVecchio valore nella q-table:"+oldVal+ "\nNuovo valore nella q-table:"+val);

			state=newState;	
			mark.setPos(state.getFirst(),state.getSecond());
			grid.changeState(mark);
			possibleAction.remove(state);
			knownAction.remove(el); 
	
			printInfo();
		}
	}


	private Integer MaxAction() {

		int el=0;
		Pair<Integer, Integer> p0= knownAction.get(el);        
		float max=q_table.get(p0);
		
		for(int i=1;i< knownAction.size(); i++) {
			Pair <Integer , Integer> p=knownAction.get(i);		
			if (q_table.get(p)>max) {
				el=i;
				max=q_table.get(p);
			}
		}
		return el;
	}
	
	protected boolean isDone() {		
		if (!restart && (isWinner() || isFullGrid())) {
			System.out.println("\nFine match numero: "+grid.getNubMatch());

			System.out.println("Il Player: "+mark+" numero di vittorie: "+countWin+" numero di pareggi: "+countTies);
			System.out.println("Valore parametro di confronto: "+hasLearned());
			
			System.out.println("\nOld Qtable");
			utils.utilsOp.PrintTable(old_Qtable,grid.getDimX(),grid.getDimY());
			System.out.println("Qtable");
			utils.utilsOp.PrintTable(q_table,grid.getDimX(),grid.getDimY());
			
			if (hasLearned()<delta) {
				System.out.println("Sono arrivato a una stagnazione è inutile continuare ");
				return true;
			}
			if (grid.isGameOver()) {
				System.out.println("ho raggiunto il massimo numero di simulazione");
				return true;
			}
			reinizialize();
			
		}

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
		boolean won=grid.isWinner(mark);
		if (won) {
			reward +=10;
			System.out.println("\nIl Player: "+mark+" ha vintooooooo!!");
			countWin++;
		}
		return won;
	}

	private boolean isFullGrid() {
        reward ++;
        boolean ret=grid.isFullGrid();
        if(ret) {
        	System.out.println("\nHanno pareggiato!!");
        	countTies.setEl(countTies.getEl()+1);
        }
		return ret;
	}

	protected void end() {
		grid.printFinalData();
		RunEnvironment.getInstance().endRun();
	}
	
	private void printInfo() {
		System.out.println("\nQ table");
		utils.utilsOp.PrintTable(q_table,grid.getDimX(),grid.getDimY());
		
		System.out.println("\nLista elementi attualmente conosciuti:");
		System.out.println(Arrays.toString(knownAction.toArray()));
		
		System.out.println("\nLista elementi ancora da esporare conosciuti:");
		System.out.println(Arrays.toString(notExplored.toArray()));
		
		System.out.println("\nLista elementi che si possono eaplorare e conoscere");
		System.out.println(Arrays.toString(possibleAction.toArray()));
	}
	
	public float getReward() {
		return reward;
	}
	public GridEl<String> getMark() {
		return mark;
	}
	public int getWins() {
		return countWin;
	}
	public int getTies() {
		return countTies.getEl();
	}	
	
}