/*package tris20;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import cern.colt.Arrays;
import repast.simphony.context.Context;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StrictBorders;
import tris.ground.AgentX;
import tris.ground.Agent0;

import tris.ground.GridEl;
import tris.player.PlayerGrid2DAbstract;
import utils.Costant;
import utils.ElementWrap;
import utils.Pair;

// rileva valor somma dei quadrati come si modifica ad ogni mossa

public class GridEnv <T> {
	private Grid <  Player<T> > gridTris;
	private Context<Object> context;
	private int countMatch=1;
	private boolean restart=false;
	private float[] rewards;
	private String oldTurnMark= Costant.MARKPL1;
		
	public GridEnv(Context<Object> context){
		GridFactory gridFactory = GridFactoryFinder . createGridFactory ( new HashMap() );
		GridBuilderParameters gparam=GridBuilderParameters.singleOccupancyND
				( new SimpleGridAdder<Object>(), new StrictBorders(),  Costant.DIMGRIDX,Costant.DIMGRIDY);
		
		this.gridTris = gridFactory . createGrid ("gridTris", context , gparam);
		rewards= new float[Costant.NUM_PLAYERS];		
		this.context=context;
	}
	
		
	public void updateWinReward(int i) {
		System.out.println(i);
		
		rewards[i-1]+=Costant.WIN_REWARD;
		for (int j=0; j<rewards.length;j++)
			if(j!=(i-1))
				rewards[j]+=Costant.LOSE_REWARD;
		
		System.out.println(Arrays.toString(rewards));
	}
		
	public void updateDrawReward() {
		for (int j=0; j<rewards.length;j++)
			rewards[j]+=Costant.DRAW_REWARD;
	}
	public float getReward(int i) {
		return rewards[i-1];
	}
	
	public boolean isEmpty() {
		for (int j = Costant.DIMGRIDY-1; j >=0; j--) {	    
			for (int i = 0; i <Costant.DIMGRIDX; i++) {
				if(gridTris.getObjectAt(i,j)!=null)
					return false;
			}
		}
		return true;
	}
	
	//inverto la griglia poichè viene rappresentata in questo modo dul grafico
	public String extractConf() {
		String ret="";
		for (int j = Costant.DIMGRIDY-1; j >=0; j--) {	    
			for (int i = 0; i <Costant.DIMGRIDX; i++) {
				ret+=(gridTris.getObjectAt(i,j)==null ? "- \t":gridTris.getObjectAt(i,j).toString()+" \t");	
			}
			ret+="\n";
		}
		return ret;
    }
	public void printGrid() {
		System.out.println(extractConf());	
	}
		
	public int getNubMatch() {
		// TODO Auto-generated method stub
		return countMatch;
	}

	
		
	public void clear() {
	    for (int i = 0; i < Costant.DIMGRIDX; i++) {
			for (int j = 0; j < Costant.DIMGRIDY; j++) {
				Player<T> el = gridTris.getObjectAt(i, j);
		            if (el != null) {
		                context.remove(el);
		            }	
			}
		}
	    countMatch++;
    }
	

	
	
	public boolean isRestarting() {
		return restart;
	}
	
	
	public void notifyRestart() {
		restart= !restart;
	}
	// ho individuato 3 fasi 
	// -> la prima il player che ha perso libera il campo da gioco e fa la priam mossa sul nuovo campo 
	// -> la seconda i 2 player giocano normalmaente fino a vincere o a pareggiare
	// -> la seconda il player che ha vinto capisce che il gioco è ricominciato e inizia a giocare

	
	public void changeState(Player<T> element) {
		//player1
		if (element instanceof AgentX) {
			//ricorda che la matrice è ribaltata
			//player1
			AgentX<T> el= new AgentX<>(2,element.getEl(),element.getPos()[1],Costant.DIMGRIDY-1-element.getPos()[0]);
			context.add(el);
			gridTris.moveTo(el,element.getPos()[1],Costant.DIMGRIDY-1-element.getPos()[0]);		
		}else {
			//player2
			//ricorda che la matrice è ribaltata
			Agent0<T> el= new Agent0<>(2,element.getEl(),element.getPos()[1],Costant.DIMGRIDY-1-element.getPos()[0]);
			context.add(el);
			gridTris.moveTo(el,element.getPos()[1],Costant.DIMGRIDY-1-element.getPos()[0]);	
		}
	}
	
	
	public Integer size() {
		return gridTris.size();
	}
	
	
	public Player<T> getElAt(int... location) {
		GridEl<T> s=null;
		try {
		  s =(Player<T>)gridTris.getObjectAt(location);
		  
		} catch (ClassCastException e) {
		}
		return s;
	} 
	


	
	public boolean isFullGrid() {
		return  size()==Costant.DIMGRIDX*Costant.DIMGRIDY;
	}
	
	
	public  boolean isWinner(String s) {
	    for (int i = 0; i < Costant.DIMGRIDX; i++) {
	    	for (int j = 0; j < Costant.DIMGRIDY; j++) {
	    		if( getElAt( i,j)!=null && getElAt( i,j).getEl().equals(s)) {
	    			int[] countV= {Costant.WINCOUNT-1,Costant.WINCOUNT-1,Costant.WINCOUNT-1,Costant.WINCOUNT-1};
    				boolean[] exitFlag= new boolean[4];
    				for (int t=1; t<Costant.WINCOUNT;t++) {
    					// analizzo la colonna
    					if( !exitFlag[0] &&i+t<Costant.DIMGRIDX && getElAt(i+t,j)!=null&&  getElAt(i+t,j).getEl().equals(s)) countV[0]--;
    					else exitFlag[0]=true;
    					// analizzo la riga
    					if( !exitFlag[1] && j+t<  Costant.DIMGRIDY && getElAt(i,j+t)!=null&& getElAt(i,j+t).getEl().equals(s)) countV[1]--;
    					else exitFlag[1]=true;	
    					// analizzo la la diagonale primaria
    					if( !exitFlag[2] && j+t<  Costant.DIMGRIDY &&i+t<Costant.DIMGRIDX && getElAt(i+t,j+t)!=null&& getElAt(i+t,j+t).getEl().equals(s)) countV[2]--;
    					else exitFlag[2]=true;
    					// analizzo la diagonale secondaria
    					if( !exitFlag[3] && j-t>=0 &&i+t<Costant.DIMGRIDX&& getElAt(i+t,j-t)!=null&& getElAt(i+t,j-t).getEl().equals(s)) countV[3]--;
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
	
	public boolean isGameOver() {
		return countMatch==Costant.MAXREP;
	}
	
	public void printFinalData() {
		for (Object obj : context) {
		    if (obj instanceof PlayerGrid2DAbstract) {
		    	PlayerGrid2DAbstract agent = (PlayerGrid2DAbstract) obj;
		    	System.out.println("\nReward accumulate dal Player"+agent.getMark() +" sono uguali a "+agent.getReward());
		    	int lost=countMatch-agent.getWins()-agent.getTies();
		    	System.out.println("Vittorie: "+agent.getWins()+" Pareggi: "+agent.getTies()+" Sconfitte: "+lost);

		    }
		}
	}

	public void notifyStartTurn(String mark) {
		oldTurnMark=mark;
	}

	public boolean isYourTurn(String mark) {
		return !oldTurnMark.equals(mark);
	}
	
	
	
	
}*/