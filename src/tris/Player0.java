package tris;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
 //posso farlo senza la griglia con una semplice matrice 
//vedere come apllicare il q learning 
public class Player0 {
	//la gestisco come un hash map le chiave è un dupla che rappresenta le posizioni 
	private Grid <Object> grid ;
	private  boolean moved;
	private int gridDim;
	public Player0 (  Grid < Object > grid, int gridDim) {
		this.grid = grid ;
		this.gridDim=gridDim;
	}
	//la mia idea è rappresentare il campo da gioco come un griglia 3*3 che 2 agenti possono marcare con dei segni propri 0 e x
	
	@ScheduledMethod( start = 1 , interval = 1) //non so come inserire la condizione per feramrelo schedule
	public void step () {
		if(!isWinner("0") ) {
			RunEnvironment.getInstance().endRun();
		}
		//è tua l'ultima mossa
		//verifico se qualcuno a vinto o se è finata in patta 
		//l'idea è che l'agente deve valutare se li conviene fermare l'avversario o cercare di vincere 
		//come faccio sta valutazione?
		if(!isWinner("X") || !isFullGrid()) {
			moved=true;
		}else {
			RunEnvironment.getInstance().endRun();}
	}
	private boolean isFullGrid() {
		return grid.size()==9;
	}

	private boolean isWinner(String s ) { //devo capire come esplorare la griglia 
		for(int i=0; i<gridDim;i++) {
			for(int j=0; j<gridDim;j++) {
				String el=(String) grid.getObjectAt(i,j);
				if (el.equals(s)) {
					
				}
			}
		}
		return false;
	}

}
