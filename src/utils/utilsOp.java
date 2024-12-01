package utils;

import java.util.Random;

import repast.simphony.space.grid.Grid;

public class utilsOp {
	public static void inizialize(double [][] q_table,int gridDimX,int gridDimY) {
		Random r=new Random();
        for (int i = 0; i < gridDimY; i++) 
            for (int j = 0; j < gridDimX; j++) {
            	q_table[i][j]=r.nextDouble();
        }
	}
	
	public static boolean isWinner(String s, int winCount,int gridDimX,int gridDimY,Grid < Object > grid) {
		boolean flagCD=true;
		int count=winCount;
	    for (int i = 0; i < gridDimX; i++) {
	    	for (int j = 0; j < gridDimY; j++) {
	    		if(grid.getObjectAt(i,j).equals(s)) {
	    			count--;
	    			if (count==0) {
	    				return true;
	    			}
	    			
	    			if(flagCD) {
	    				int[] countV= {winCount-1,winCount-1,winCount-1};
	    				boolean[] exitFlag= {false,false,false};

	    				
	    				for (int t=1; t<winCount;t++) {
	    					if (i+t>= gridDimY) {
	    						flagCD=false;
	    						break;
	    					}
	    					if(!exitFlag[0] && grid.getObjectAt(i+t,j).equals(s)) countV[0]--;
	    					else exitFlag[0]=true;
	    					
	    					if(!exitFlag[1] && j-t>=0 &&grid.getObjectAt(i+t,j-t).equals(s)) countV[1]--;
	    					else exitFlag[1]=true;	
	    					 
	    					if(!exitFlag[2] && j+t<gridDimX && grid.getObjectAt(i+t,j+t).equals(s)) countV[2]--;
	    					else exitFlag[2]=true;
	    					
	    					if(exitFlag[0]&&exitFlag[1]&&exitFlag[2]) break;
	    				}
	    				if ( countV[0]==0 ||countV[1]==0 ||countV[2]==0) {
	    					return true;
	    				}
	    			}
	    		}else
                    count=winCount;
	    	}
	    }
		return false;
	}

}
