package tris20;
/*
import java.util.List;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import tris.Qtable;
import utils.Costant;
import utils.Pair;

public class TicTacToe {
	private Qtable2[] q= {new Qtable2(Costant.DIMGRIDX,Costant.DIMGRIDY),new Qtable2(Costant.DIMGRIDX,Costant.DIMGRIDY)};
	private Qtable2[] Oldq= {new Qtable2(Costant.DIMGRIDX,Costant.DIMGRIDY),new Qtable2(Costant.DIMGRIDX,Costant.DIMGRIDY)};
	private GridEnv grid;    
	private int numberEpoc=0;
	private int numberMatch=0;
	
	private List<Pair<Integer,Integer>>possibleAction;

	
	//li dovro raggrupare in un unico oggetto 
	private String giocatore=Costant.MARKPL1; //quando il macth finisce li scambio
	private String avversario=Costant.MARKPL2;
	
	private int giocatoreId=0; //quando il macth finisce li scambio
	private int avversarioId=1;
	
	public TicTacToe(GridEnv grid) {
		this.grid=grid;
		Pair.fillPair(possibleAction,Costant.DIMGRIDX,Costant.DIMGRIDY);
	}
	
	@ScheduledMethod( start = 1 , interval = 1) 
	public void QlearningAlg () {
		if (numberMatch<Costant.MAXREP) {
			numberEpoc++;
			if(epsilonPolicy(giocatoreId)) { 
				exploreAction();
			}else {
				greedyAction();
			}
		}else {
			RunEnvironment.getInstance().endRun();
		}
		
	}
	private boolean epsilonPolicy(int id) {	
		
		float el=(float) Math.random();
		String configuration=grid.extractConf();
		float exploration_rate=explorationRate();
		System.out.println("Exploration rate: "+exploration_rate); 
		return q[id].getKnowAction(configuration)==null||el<exploration_rate;
	}
	public float explorationRate() {
		float exponential_dec=(float) (Costant.EPSION*Math.exp(-numberEpoc*Costant.EXPONENTIAL_DECAY));
		float exploration_rate=exponential_dec>Costant.EPSION_MIN?exponential_dec:Costant.EPSION_MIN;
		return exploration_rate;
	}
	
	private void exploreAction() {
		
		String configuration=grid.extractConf();
		
		Pair<Integer,Integer> action=q_table.explore(configuration,possibleAction);
		possibleAction.remove(action);
		
		mark.setPos(action.getFirst(),action.getSecond());
		grid.changeState(mark);
		
		updateQtable(configuration,action);
	}
	
	
	private void greedyAction() {
		
		Pair<Integer,Integer> action=q_table.maxKnowAction(configuration,possibleAction);
		possibleAction.remove(action);
		
		mark.setPos(action.getFirst(),action.getSecond());
		grid.changeState(mark);
		
		updateQtable(configuration,action);
	}

	private void updateQtable(String oldState, Pair<Integer,Integer> oldAction) {
		boolean won=grid.isWinner(mark.getEl());
		boolean draw=grid.isFullGrid();
		//aggiorno anche i punteggi
		float reward=0;
		if (won) {
			reward=Costant.WIN_REWARD;
			}else {
				if (draw) {
					reward=Costant.DRAW_REWARD;
				}
		}
		
		float oldValue=q_table.getValue(oldState, oldAction);
		String neWconfiguration=grid.extractConf();

		float val=oldValue+Costant.ALPHA*(+Costant.DISCOUNT_FACTOR*q_table.maxValue(neWconfiguration) - oldValue);
		q_table.setValue(oldState,oldAction,val);	
		printInfo();
	
	}
}*/
