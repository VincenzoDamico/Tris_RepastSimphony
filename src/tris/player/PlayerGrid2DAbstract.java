package tris.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import repast.simphony.engine.environment.RunEnvironment;
import tris.QlearnigTemplate;
import tris.Qtable;
import tris.ground.DashBoard;
import tris.ground.GridEl;
import tris.ground.GridPlayGround;
import utils.Costant;
import utils.Pair;

public abstract class PlayerGrid2DAbstract extends QlearnigTemplate implements Player	{
	private GridPlayGround<String> grid ;
	private DashBoard dashBoard;
	private Qtable q_table;
	private Qtable old_Qtable;
	private GridEl<String> mark;
	private List<Pair<Integer,Integer>>possibleAction;
	public float countStep=0;
	private boolean opponentMove=false;
	private Pair<String, Pair<Integer,Integer>> state_actionOpp;
	private PlayerGrid2DAbstract opponent;
	
	public PlayerGrid2DAbstract (  GridPlayGround grid, List<Pair<Integer,Integer>>possibleAction, GridEl<String> mark,DashBoard dashBoard) {
		this.grid = grid ;
		this.dashBoard=dashBoard;		
		this.mark=mark;
		loadQtable();
		this.possibleAction=possibleAction;
	    old_Qtable=q_table.clone();
	}
	
    public void  notifyOpponetAction(){
    	opponentMove=true;
	}
    public void  resetOppMove(){
    	opponentMove=false;
	}

	//può essere attivato o disattivatoda costant
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
		possibleAction.clear();
		grid.updateOldQtable();
		grid.clear();
		Pair.fillPair(possibleAction, Costant.DIMGRIDX,  Costant.DIMGRIDY );

	}
	
	protected boolean epsilonPolicy() {
		if (countStep==0)
			opponent=grid.getOpponent(mark.getOrder());
		
		if(!opponent.opponentMove) {
			countStep++;
		}
		
		System.out.println("");
		if (grid.isRestarting()) {
			reload();
			grid.notifyRestart();		
			System.out.println("Ricominciamo la partita");	
		}	
		
		if (grid.isEmpty())
			grid.notifyStartTurn(mark.getEl());
		
		System.out.println("Inizio del match numero: "+dashBoard.getMatches()+
				"\nTocca a Player "+mark.getEl());
		
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
	

	protected Pair<String, Pair<Integer,Integer>> exploreAction() {
		if(!opponent.opponentMove) {
			System.out.println("Recap situazione:");
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
			return new Pair<String, Pair<Integer,Integer>>(configuration,action);
		}
		System.out.println("-----------RECUPERO LA MIA MOSSA EFFETUATA---------------");
		System.out.println("Stato precedente:");
		System.out.println(opponent.state_actionOpp.getFirst());
		System.out.println("Azione scelta:");
		System.out.println(opponent.state_actionOpp.getSecond());
		opponent.resetOppMove();
		return opponent.state_actionOpp;
	}
	
	protected Pair<String, Pair<Integer,Integer>> greedyAction() {
		if(!opponent.opponentMove) {
			System.out.println("Recap situazione:");
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
			
			return new Pair<String, Pair<Integer,Integer>>(configuration,action);
		}
		System.out.println("-----------RECUPERO LA MIA MOSSA EFFETUATA---------------");
		System.out.println("Stato precedente:");
		System.out.println(opponent.state_actionOpp.getFirst());
		System.out.println("Azione scelta:");
		System.out.println(opponent.state_actionOpp.getSecond());
		System.out.println("---------------------------------------------------------");

		opponent.resetOppMove();
		return opponent.state_actionOpp;

	}

	protected void updateQtable(String oldState, Pair<Integer,Integer> oldAction) {
		boolean won=grid.isWinner(mark.getEl());
		boolean draw=grid.isFullGrid();
		//aggiorno anche i punteggi
		
        String neWconfiguration=grid.extractConf();

		float reward=0;		
		if (won || draw) {
			if (won) {
				reward=Costant.WIN_REWARD;
				dashBoard.updateWin(mark.getOrder());
				System.out.println("\nIl Player: "+mark+" ha vintooooooo!!");
			}else {
				if (draw) {
					reward=Costant.DRAW_REWARD;
					dashBoard.updateDraw();
					System.out.println("\nHanno pareggiato!!");
				}
			}
			System.out.println("----------RISULTATI---------------");
			System.out.println("\nFine match numero: "+dashBoard.getMatches());
			System.out.println("Il Player"+mark+" -> Numero di vittorie: "+getWins()+" Numero di pareggi: "+getTies()+" Numero di sconfitte: "+dashBoard.getLosses(mark.getOrder()));
			System.out.println("Valore parametro di confronto: "+q_table.hasLearned(old_Qtable));
			System.out.println("----------------------------------");

			grid.notifyRestart();
				
		}else {
				System.out.println("-----------INIZIO MOSSA OPPONENTE---------------");
				state_actionOpp=opponent.epsilonGreedyPolicy();
				System.out.println("-----------FINE MOSSA OPPONENTE---------------");
				notifyOpponetAction();
				if(grid.isWinner(opponent.getMark().getEl()))
					reward=Costant.LOSE_REWARD;
				else{
					if(grid.isFullGrid()) {
						reward=Costant.DRAW_REWARD;
					}
				}
		}
		 //è un versione dell'aggiornameneto della q-table che ho provato è funziona meglio
		 if(Costant.VERSIONS_QTABLE_UPDATE) {
	        neWconfiguration=grid.extractConf();
		}

		float oldValue=q_table.getValue(oldState, oldAction);

		float val=oldValue+Costant.ALPHA*(reward+Costant.DISCOUNT_FACTOR*q_table.maxValue(neWconfiguration) - oldValue);
		q_table.setValue(oldState,oldAction,val);	
		System.out.println("Nuovo valore: "+val);	
	}

	
	protected boolean isDone() {
		boolean won=grid.isWinner(mark.getEl());
		boolean draw=grid.isFullGrid();
		
		if (won || draw) {

			/*if (q_table.hasLearned(old_Qtable)<Costant.DELTA) {
				System.out.println("Sono arrivato a una stagnazione è inutile continuare ");
				return true;
			}*/
			if (grid.isGameOver()) {
				System.out.println("ho raggiunto il massimo numero di simulazione");
				return true;
			}
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
			/*System.out.println("---------------------------");
			System.out.println("\nIl Player"+mark+" la nuova old Q table:");
			System.out.println(old_Qtable);
			System.out.println("---------------------------");*/

			return ret;
		}
		return -1;
	}

	protected void end() {
		grid.printFinalData();
		RunEnvironment.getInstance().endRun();
	}

	
	public float getReward() {
		return dashBoard.getReward(mark.getOrder());
	}

	
	public GridEl<String> getMark() {
		return mark;
	}
	
	//vengono richiamate da Repast per vedere se l'agente ha imparato
	public int getWins() {
		return dashBoard.getWins(mark.getOrder());
	}
	//vengono richiamate da Repast per vedere se l'agente ha imparato
	public int getTies() {
		return dashBoard.getTies();
	}

	public void updateOldQtable() {
		old_Qtable=q_table.clone();
	}		
	
}