package utils;

import java.util.Map;
import java.util.Random;

import repast.simphony.space.grid.Grid;
import tris.ground.GridPlayGround;

public class utilsOp {
	public static void inizialize( Map<Pair<Integer,Integer>,Float> q_table,int gridDimX,int gridDimY) {
	    System.out.println();
		Random r=new Random();
        for (int i = 0; i < gridDimY; i++) {
            for (int j = 0; j < gridDimX; j++) {
            	float f=r.nextFloat();
            	Pair <Integer,Integer> el=new Pair<>(i,j);
            	q_table.put(el,f);
            	System.out.print(q_table.get(el)+" ");
            }
            System.out.println();
        }
        System.out.println();
	}
	public static boolean isWinner(String s, int winCount,int gridDimX,int gridDimY,GridPlayGround<String> grid) {
		grid.PrintGrid();
	    for (int i = 0; i < gridDimX; i++) {
	    	for (int j = 0; j < gridDimY; j++) {
	    		if( grid.getElAt( i,j)!=null && grid.getElAt( i,j).getElement().equals(s)) {
	    			int[] countV= {winCount-1,winCount-1,winCount-1,winCount-1};
    				boolean[] exitFlag= new boolean[4];
    				for (int t=1; t<winCount;t++) {
    					if( !exitFlag[0] &&i+t<gridDimX &&grid.getElAt(i+t,j)!=null&&  grid.getElAt(i+t,j).getElement().equals(s)) countV[0]--;
    					else exitFlag[0]=true;
    					
    					if( !exitFlag[1] && j+t<gridDimY &&grid.getElAt(i,j+t)!=null&& grid.getElAt(i,j+t).getElement().equals(s)) countV[1]--;
    					else exitFlag[1]=true;	
    					 
    					if( !exitFlag[2] && j+t<gridDimY &&i+t<gridDimX&&grid.getElAt(i+t,j+t)!=null&& grid.getElAt(i+t,j+t).getElement().equals(s)) countV[2]--;
    					else exitFlag[2]=true;
    					
    					if( !exitFlag[3] && j-t>=0 &&i-t>=0&&grid.getElAt(i-t,j-t)!=null&& grid.getElAt(i-t,j-t).getElement().equals(s)) countV[3]--;
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

}
