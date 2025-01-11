package tris.ground;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StrictBorders;
import tris.player.PlayerGrid2DAbstract;
import utils.ElementWrap;
import utils.Pair;

//pensa a come generalizzarlo 
public class GridPlayGround <T> implements PlayGround<T>{
	private Grid <  GridEl<T> > gridTris;
	private Context<Object> context;
	private int [] dim;
	private int countMatch=1;
	private int winCount;
	private int maxRep;
	private boolean restart=false;

	
	public GridPlayGround(int wincount,int maxRep,Context<Object> context,int... dim){
		
		GridFactory gridFactory = GridFactoryFinder . createGridFactory ( new HashMap() );
		GridBuilderParameters gparam=GridBuilderParameters.singleOccupancyND( new SimpleGridAdder<Object>(), new StrictBorders(),  dim);
		this.gridTris = gridFactory . createGrid ("gridTris", context , gparam);
		this.context=context;
		this.maxRep=maxRep;
		this.dim=dim;
		this.winCount=wincount;
	}
	public void printGrid() {
		System.out.println("\nGriglia di gioco:\n");
	    for (int i = 0; i < dim[1]; i++) {
			for (int j = 0; j < dim[0]; j++) {
				System.out.print(gridTris.getObjectAt(i,j)+" \t");
			}
		    System.out.println();
		}
		System.out.println();	
		
    }
	@Override	
	public int  getDimX() {
	 return dim[0];
	}
	@Override	
	public int  getDimY() {
		 return dim[1];
	}	
	
	@Override
	public int getNubMatch() {
		// TODO Auto-generated method stub
		return countMatch;
	}

	
	@Override	
	public void clear() {
	    for (int i = 0; i < dim[1]; i++) {
			for (int j = 0; j < dim[0]; j++) {
				GridEl<T> el = gridTris.getObjectAt(i, j);
		            if (el != null) {
		                context.remove(el);
		            }	
			}
		}
	    restart=true;
	    countMatch++;
    }
	
	@Override
	public boolean isRestarting() {
		boolean ret = restart;
		if (restart)
			restart=false;
		return ret;
	}



	@Override
	public void changeState(GridEl<T> element) {		
		if (element instanceof AgentX) {
			AgentX<T> el= new AgentX<>(element.getEl(),element.getPos());
			context.add(el);
			gridTris.moveTo(el,el.getPos());	
		}else {
			Agent0<T> el= new Agent0<>(element.getEl(),element.getPos());
			context.add(el);
			gridTris.moveTo(el,el.getPos());	
		}
		printGrid();
	}
	
	@Override
	public Integer size() {
		return gridTris.size();
	}
	
	@Override
	public GridEl<T> getElAt(int... location) {
		GridEl<T> s=null;
		try {
		  s =(GridEl<T>)gridTris.getObjectAt(location);
		  
		} catch (ClassCastException e) {
		}
		return s;
	} 
	


	@Override
	public boolean isFullGrid() {
		return  size()==getDimX()*getDimY();
	}
	
	@Override
	public  boolean isWinner(GridEl<T>  s) {
	    for (int i = 0; i < getDimX(); i++) {
	    	for (int j = 0; j < getDimY(); j++) {
	    		if( getElAt( i,j)!=null && getElAt( i,j).getEl().equals(s.getEl())) {
	    			int[] countV= {winCount-1,winCount-1,winCount-1,winCount-1};
    				boolean[] exitFlag= new boolean[4];
    				for (int t=1; t<winCount;t++) {
    					if( !exitFlag[0] &&i+t<getDimX() && getElAt(i+t,j)!=null&&  getElAt(i+t,j).getEl().equals(s.getEl())) countV[0]--;
    					else exitFlag[0]=true;
    					
    					if( !exitFlag[1] && j+t< getDimY() && getElAt(i,j+t)!=null&& getElAt(i,j+t).getEl().equals(s.getEl())) countV[1]--;
    					else exitFlag[1]=true;	
    					 
    					if( !exitFlag[2] && j+t< getDimY() &&i+t<getDimX() && getElAt(i+t,j+t)!=null&& getElAt(i+t,j+t).getEl().equals(s.getEl())) countV[2]--;
    					else exitFlag[2]=true;
    					
    					if( !exitFlag[3] && j-t>=0 &&i-t>=0&& getElAt(i-t,j-t)!=null&& getElAt(i-t,j-t).getEl().equals(s.getEl())) countV[3]--;
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
	@Override
	public boolean isGameOver() {
		return countMatch==maxRep;
	}
	
	public void printFinalData() {
		for (Object obj : context) {
		    if (obj instanceof PlayerGrid2DAbstract) {
		    	PlayerGrid2DAbstract agent = (PlayerGrid2DAbstract) obj;
		    	System.out.println("\nReward accumulate dal Player"+agent.getMark() +" sono uguali a "+agent.getReward());
		    	System.out.println("Vittorie: "+agent.getWins()+" Pareggi: "+agent.getTies()+" Sconfitte: "+(countMatch-agent.getWins()-agent.getTies()));

		    }
		}
	}

	
	
}