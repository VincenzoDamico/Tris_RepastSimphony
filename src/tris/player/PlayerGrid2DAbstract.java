package tris.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.grid.Grid;
import tris.QlearnigTemplate;
import tris.Qtable;
import tris.ground.GridEl;
import tris.ground.GridPlayGround;
import utils.Costant;
import utils.ElementWrap;
import utils.Pair;

public abstract class PlayerGrid2DAbstract extends QlearnigTemplate implements Player	{
	private GridPlayGround<String> grid ;
	private int countWin=0;
	private ElementWrap<Integer > countTies;
	private Qtable q_table;
	private Qtable old_Qtable;
	private GridEl<String> mark;
	private List<Pair<Integer,Integer>>possibleAction;
	public float countStep=0;


	
	public PlayerGrid2DAbstract (  GridPlayGround grid, List<Pair<Integer,Integer>>possibleAction, GridEl<String> mark,ElementWrap<Integer > countTies) {
		this.grid = grid ;
		this.countTies=countTies;		
		this.mark=mark;
		loadQtable();
		this.possibleAction=possibleAction;
	    old_Qtable=q_table.clone();
	}
	
	private void loadQtable() {
		File file = new File(Costant.NAME_FILE+mark.getEl());
		if (!file.exists()||!Costant.LOAD_QTABLE) {
			q_table=new Qtable(Costant.DIMGRIDX, Costant.DIMGRIDY,Costant.MARKPL2,Costant.MARKPL1); 
		}else {
	        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
	        	q_table = (Qtable)ois.readObject();
	            if (q_table.getGridDimX()!=Costant.DIMGRIDX || q_table.getGridDimY()!=Costant.DIMGRIDY) {
	                    q_table=new Qtable(Costant.DIMGRIDX, Costant.DIMGRIDY,Costant.MARKPL2,Costant.MARKPL1);
	    	            System.out.println("Qtable"+mark.getEl()+" si riferisce ad una griglia differente da quella che si sta esaminando" );
	            }else{
    	            System.out.println("Qtable"+mark.getEl()+" letta con successo!");
	            }
	        	ois.close();
	        }catch(IOException |  ClassNotFoundException e){
	            System.err.println("Errore durante la lettura del file: " + e.getMessage());
				q_table=new Qtable(Costant.DIMGRIDX, Costant.DIMGRIDY,Costant.MARKPL2,Costant.MARKPL1); 
	        }
		}
	}
	
	
	private void reload() {
		//azioni iniziali condivise basta che le segue un singolo agente
		//old_Qtable=q_table.clone();
		possibleAction.clear();
		grid.clear();
		Pair.fillPair(possibleAction, Costant.DIMGRIDX,  Costant.DIMGRIDY );

	}
	
	protected boolean epsilonPolicy() {

		countStep++;
		System.out.println("");
		if (grid.isRestarting()) {
			reload();
			grid.notifyRestart();		
			System.out.println("Ricominciamo la partita");	
		}	
		
		if (grid.isEmpty())
			grid.notifyStartTurn(mark.getEl());
		
		System.out.println("Inizio del match numero: "+grid.getNubMatch()
		+"\nTocca a Player "+mark.getEl());
		
		float el=(float) Math.random();
		String configuration=grid.extractConf();
		
		float exploration_rate=explorationRate();
		System.out.println("Exploration rate: "+exploration_rate); 
		return q_table.getKnowAction(configuration)==null||el<exploration_rate;
	}
	
	//faccio il metodo così lo posso graficare 
	public float explorationRate() {
		float exponential_dec=(float) (Costant.EPSION*Math.exp(-countStep*Costant.EXPONENTIAL_DECAY));
		float exploration_rate=exponential_dec>Costant.EPSION_MIN?exponential_dec:Costant.EPSION_MIN;
		return exploration_rate;
	}
	

	protected void exploreAction() {
		System.out.println("Recap situazione:");
		printInfo();
		System.out.println("Player "+mark.getEl()+ " sta esporando.......");
		String configuration=grid.extractConf();
		System.out.println("Vecchia Configurazione");
		grid.printGrid();
		Pair<Integer,Integer> action=q_table.explore(configuration,possibleAction);
		System.out.println("Nuova posizione scoperta -> "+action);
		possibleAction.remove(action);
		mark.setPos(action.getFirst(),action.getSecond());
		grid.changeState(mark);
		System.out.println("Nuova Configurazione");
		grid.printGrid();
		updateQtable(configuration,action);
	}
	
	protected void greedyAction() {
		System.out.println("Recap situazione:");
		printInfo();
		System.out.println("Player "+mark.getEl()+ " sta applicando greedy policy.......");
		String configuration=grid.extractConf();
		System.out.println("Vecchia Conf");
		grid.printGrid();
		Pair<Integer,Integer> action=q_table.maxKnowAction(configuration,possibleAction);
		possibleAction.remove(action);
		mark.setPos(action.getFirst(),action.getSecond());
		grid.changeState(mark);
		System.out.println("Nuova Configurazione");
		grid.printGrid();

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

	
	protected boolean isDone() {
		boolean won=grid.isWinner(mark.getEl());
		boolean draw=grid.isFullGrid();
		
		if (won || draw) {
			if (won) {
	    		grid.updateWinReward(mark.getOrder());
				System.out.println("\nIl Player: "+mark+" ha vintooooooo!!");
				countWin++;
			}else {
				if (draw) {
					grid.updateDrawReward();
					System.out.println("\nHanno pareggiato!!");
					countTies.setEl(countTies.getEl()+1);
				}
		}
			System.out.println("\nFine match numero: "+grid.getNubMatch());
			int loss=grid.getNubMatch()-countTies.getEl()-countWin;
			System.out.println("Il Player"+mark+" -> Numero di vittorie: "+countWin+" Numero di pareggi: "+countTies+" Numero di sconfitte: "+loss);
			//System.out.println("Valore parametro di confronto: "+q_table.hasLearned(old_Qtable));

			/*if (q_table.hasLearned(old_Qtable)<Costant.DELTA) {
				System.out.println("Sono arrivato a una stagnazione è inutile continuare ");
				return true;
			}*/
			if (grid.isGameOver()) {
				System.out.println("ho raggiunto il massimo numero di simulazione");
				return true;
			}
			grid.notifyRestart();
		}

		return false;
	}
	
	//lo chiamo con uno stratagemma alla fine
	public int  saveData() {
		try ( ObjectOutputStream pw = new ObjectOutputStream(new FileOutputStream(Costant.NAME_FILE+mark.getEl()))){
			pw.writeObject(q_table);
			System.out.println("Qtable"+mark.getEl()+" salvata con successo.");
			pw.close();

		}
		catch(IOException e) {
			System.err.println("Si è verificato un errore durante la scrittura nel file.");
            e.printStackTrace();
		}
		return 0;
	}
	
 //viene trigghrerato alla fine di ogni azione lo clono qui la qtable per entrambi
	public float hasPlotLearned() {
		//System.out.println("---------------------------");

		if (grid.isRestarting()) {
			/*System.out.println("---------------------------");
			System.out.println("\nIl Player"+mark+" la  vecchia old Q table:");
			System.out.println(old_Qtable);
			System.out.println("---------------------------");*/
			float ret=q_table.hasLearned(old_Qtable);
			System.out.println("Valore parametro di confronto: "+ret);
			old_Qtable=q_table.clone();
			/*System.out.println("---------------------------");
			System.out.println("\nIl Player"+mark+" la nuova old Q table:");
			System.out.println(old_Qtable);
			System.out.println("---------------------------");*/

			return ret;
		}
		return -30000;
	}

	protected void end() {
		grid.printFinalData();
		RunEnvironment.getInstance().endRun();
	}
	
	private void printInfo() {
		//System.out.println("\nIl Player"+mark+" la Q table:");
		//System.out.println(q_table);
		//System.out.println("\nIl Player"+mark+" la old Q table:");
		//System.out.println(old_Qtable);
	}
	
	public float getReward() {
		return grid.getReward(mark.getOrder());
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