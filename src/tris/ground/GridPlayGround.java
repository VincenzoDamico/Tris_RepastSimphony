package tris.ground;
import java.util.HashMap;
import repast.simphony.context.Context;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StrictBorders;
import tris.player.PlayerGrid2DAbstract;
import utils.Costants;

public class GridPlayGround <T> {
	private Grid <  GridEl<T> > gridTris;
	private Context<Object> context;
	private boolean restart=false;
	private String oldTurnMark= Costants.MARKPL1;
	private PlayerGrid2DAbstract[] opponents;
	private DashBoard dash;
		
	public GridPlayGround(Context<Object> context, DashBoard dash){
		GridFactory gridFactory = GridFactoryFinder . createGridFactory ( new HashMap() );
		GridBuilderParameters gparam=GridBuilderParameters.singleOccupancyND
				( new SimpleGridAdder<Object>(), new StrictBorders(),  Costants.DIMGRIDX,Costants.DIMGRIDY);
		this.dash=dash;
		this.gridTris = gridFactory . createGrid ("gridTris", context , gparam);
		this.context=context;
		
	}

//Informazioni sulla griglia
	public boolean isEmpty() {
		for (int j = Costants.DIMGRIDY-1; j >=0; j--) {	    
			for (int i = 0; i <Costants.DIMGRIDX; i++) {
				if(gridTris.getObjectAt(i,j)!=null)
					return false;
			}
		}
		return true;
	}
	
	//inverto la griglia poichè viene rappresentata in questo modo sulla gui di repest
	public String extractConf() {
		String ret="";
		for (int j = Costants.DIMGRIDY-1; j >=0; j--) {	    
			for (int i = 0; i <Costants.DIMGRIDX; i++) {
				ret+=(gridTris.getObjectAt(i,j)==null ? Costants.EMPTYSYMBOL+" \t":gridTris.getObjectAt(i,j).toString()+" \t");	
			}
			ret+="\n";
		}
		return ret;
    }
	public void printGrid() {
		System.out.println(extractConf());	
	}
	public Integer size() {
		return gridTris.size();
	}	
	

	public GridEl<T> getElAt(int... location) {
		GridEl<T> s=null;
		try {
		  s =(GridEl<T>)gridTris.getObjectAt(location);
		  
		} catch (ClassCastException e) {
		}
		return s;
	} 
	

//Modifiche sulla griglia	
	
	public void clear() {
	    for (int i = 0; i < Costants.DIMGRIDX; i++) {
			for (int j = 0; j < Costants.DIMGRIDY; j++) {
				GridEl<T> el = gridTris.getObjectAt(i, j);
		            if (el != null) {
		                context.remove(el);
		            }	
			}
		}
	    dash.updateMatches();
    }
	
	public void changeState(GridEl<T> element) {
		//player1
		if (element instanceof AgentX) {
			//ricorda che la matrice è ribaltata
			//player1
			AgentX<T> el= new AgentX<>(2,element.getEl(),element.getPosY(),Costants.DIMGRIDY-1-element.getPosX());
			context.add(el);
			gridTris.moveTo(el,element.getPosY(),Costants.DIMGRIDY-1-element.getPosX());		
		}else {
			//player2
			//ricorda che la matrice è ribaltata
			Agent0<T> el= new Agent0<>(2,element.getEl(),element.getPosY(),Costants.DIMGRIDY-1-element.getPosX());
			context.add(el);
			gridTris.moveTo(el,element.getPosY(),Costants.DIMGRIDY-1-element.getPosX());	
		}
	}

// verifica risultato della partita
	public boolean isFullGrid() {
		return  size()==Costants.DIMGRIDX*Costants.DIMGRIDY;
	}
	
	public  boolean isWinner(String s) {
	    for (int i = 0; i < Costants.DIMGRIDX; i++) {
	    	for (int j = 0; j < Costants.DIMGRIDY; j++) {
	    		if( getElAt( i,j)!=null && getElAt( i,j).getEl().equals(s)) {
	    			int[] countV= {Costants.WINCOUNT-1,Costants.WINCOUNT-1,Costants.WINCOUNT-1,Costants.WINCOUNT-1};
    				boolean[] exitFlag= new boolean[4];
    				for (int t=1; t<Costants.WINCOUNT;t++) {
    					// analizzo la colonna
    					if( !exitFlag[0] &&i+t<Costants.DIMGRIDX && getElAt(i+t,j)!=null&&  getElAt(i+t,j).getEl().equals(s)) countV[0]--;
    					else exitFlag[0]=true;
    					// analizzo la riga
    					if( !exitFlag[1] && j+t<  Costants.DIMGRIDY && getElAt(i,j+t)!=null&& getElAt(i,j+t).getEl().equals(s)) countV[1]--;
    					else exitFlag[1]=true;	
    					// analizzo la la diagonale primaria
    					if( !exitFlag[2] && j+t<  Costants.DIMGRIDY &&i+t<Costants.DIMGRIDX && getElAt(i+t,j+t)!=null&& getElAt(i+t,j+t).getEl().equals(s)) countV[2]--;
    					else exitFlag[2]=true;
    					// analizzo la diagonale secondaria
    					if( !exitFlag[3] && j-t>=0 &&i+t<Costants.DIMGRIDX&& getElAt(i+t,j-t)!=null&& getElAt(i+t,j-t).getEl().equals(s)) countV[3]--;
    					else exitFlag[3]=true;
    					
    					if(exitFlag[0]&&exitFlag[1]&&exitFlag[2]&&exitFlag[3]) break;
    				}
    				if ( countV[0]==0 ||countV[1]==0 ||countV[2]==0||countV[3]==0) {
    					return true;
    				}
	    				
	    			}
	    		}
	
	    	}
		
		return false;

	}
	
//logica mosse Opponente
	public void setOppenets(PlayerGrid2DAbstract[] opponents) {
		this.opponents= opponents;
	}
	public PlayerGrid2DAbstract getOpponent(int playerOrder) {
		return opponents[playerOrder-1];
	}
	
//logica mecccanismo a turni 
	public boolean isRestarting() {
		return restart;
	}
	
	public void notifyRestart() {
		restart= !restart;
	}
	public boolean isGameOver() {
		return dash.getMatches()==Costants.MAXREP;
	}

	public void notifyStartTurn(String mark) {
		oldTurnMark=mark;
	}

	public boolean isYourTurn(String mark) {
		return !oldTurnMark.equals(mark);
	}
	
	public void updateOldQtable() {
		for(PlayerGrid2DAbstract p:opponents) {
			p.updateOldQtable();
		}
	}
	
//Stampa finale risultati parita
	//lo chiamo con uno stratagemma alla fine poichè ha l'oggeto contesto 
	public void printFinalData() {
		for (Object obj : context) {
		    if (obj instanceof PlayerGrid2DAbstract) {
		    	PlayerGrid2DAbstract agent = (PlayerGrid2DAbstract) obj;
		    	int el=agent.getMark().getOrder();
		    	System.out.println("\nReward accumulate dal Player"+agent.getMark() +" sono uguali a "+dash.getReward(el));
		    	System.out.println("Vittorie: "+dash.getWins(el)+" Pareggi: "+dash.getTies()+" Sconfitte: "+dash.getLosses(el));

		    }
		}
	}
	
	
	
}