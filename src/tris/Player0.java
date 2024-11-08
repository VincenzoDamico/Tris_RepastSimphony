package tris;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Player0 {
	//la gestisco come un hash map le chiave è un dupla che rappresenta le posizioni 
	private Grid <Object> grid ;
	private  boolean moved;
	public Player0 (  Grid < Object > grid ) {
		this.grid = grid ;
	}
	//la mia idea è rappresentare il campo da gioco come un griglia 3*3 che 2 agenti possono marcare con dei segni propri 0 e x
	
	@ScheduledMethod( start = 1 , interval = 1)
	public void step () {
		//l'idea è che l'agente deve valutare se li conviene fermare l'avversario o cercare di vincere 
		//come faccio sta valutazione?
	
	}

}
