package tris;

import java.util.Random;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.grid.Grid;

//posso farlo senza la griglia con una semplice matrice 
//vedere come apllicare il q learning 
public class Player0 {
	//la gestisco come un hash map le chiave è un dupla che rappresenta le posizioni 
	private Grid <Object> grid ;
	private  boolean moved;
	private final int gridDim;
	private final int alpha;
	private final int discount_factor;
	private final int epsilon;
	private double q_table[][];
	public Player0 (  Grid < Object > grid, int gridDim, int alpha, int discount_factor, int epsilon) {
		this.grid = grid ;
		this.gridDim=gridDim;
		//creazione q-table random 
		this.alpha=alpha;
		this.discount_factor= discount_factor;
		this.epsilon=epsilon;
		q_table=new double [gridDim][gridDim];
		inizialize();
	}
	private void inizialize() {
		Random r=new Random();
        for (int i = 0; i < gridDim; i++) 
            for (int j = 0; j < gridDim; j++) {
            	q_table[i][j]=r.nextDouble();
        }
	}
	//la mia idea è rappresentare il campo da gioco come un griglia 3*3 che 2 agenti possono marcare con dei segni propri 0 e x
	
	@ScheduledMethod( start = 1 , interval = 1) //non so come inserire la condizione per feramrelo schedule
	public void step () {
		if(!isWinner("0") ) {
			RunEnvironment.getInstance().endRun();
		} 
		
		if ()
		
		//è tua l'ultima mossa
		//verifico se qualcuno a vinto o se è finata in patta 
		
		if(!isWinner("X") || !isFullGrid()) {
			moved=true;
		}else {
			RunEnvironment.getInstance().endRun();}
	}
	
	private boolean isFullGrid() {
		return grid.size()==9;
	}
	
	private boolean isWinner(String s) {
        for (int i = 0; i < gridDim; i++) {
            if ((grid.getObjectAt(i,0).equals(s) &&grid.getObjectAt(i,1).equals(s) && grid.getObjectAt(i,2).equals(s))
            		||(grid.getObjectAt(0,i).equals(s) &&grid.getObjectAt(1,i).equals(s) && grid.getObjectAt(2,i).equals(s)) ) {
                return true;
            }
        }
        // Controllo diagonale principale
        if (grid.getObjectAt(0,0).equals(s) &&grid.getObjectAt(1,1).equals(s) && grid.getObjectAt(2,2).equals(s)) {
            return true;
        }
        // Controllo diagonale secondaria
        if (grid.getObjectAt(2,2).equals(s) &&grid.getObjectAt(1,1).equals(s) && grid.getObjectAt(0,0).equals(s)) {
            return true;
        }
		return false;
	}
}
