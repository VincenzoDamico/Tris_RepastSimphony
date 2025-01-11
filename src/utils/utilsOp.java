package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import repast.simphony.space.grid.Grid;
import tris.ground.GridEl;
import tris.ground.GridPlayGround;

public class utilsOp {
	
	public  static void fillPair(List<Pair<Integer, Integer>> list,int gridDimX,int gridDimY) {
		for (int i=0; i<gridDimX; i++) {
			for(int j=0; j<gridDimY; j++) {
				list.add(new Pair<>(i,j));
			}
		}
	}
	
	
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
	
	public static void PrintTable( Map<Pair<Integer,Integer>,Float> q_table,int gridDimX,int gridDimY) {
	    System.out.println();
        for (int i = 0; i < gridDimY; i++) {
            for (int j = 0; j < gridDimX; j++) {
            	Pair <Integer,Integer> el=new Pair<>(i,j);
            	System.out.print(q_table.get(el)+" \t");
            }
            System.out.println();
        }
        System.out.println();
	}
	
	public static Map<Pair<Integer, Integer>, Float> copy(Map<Pair<Integer, Integer>, Float> table) {
		 Map<Pair<Integer, Integer>, Float> ret= new HashMap<>();
		 for (Pair<Integer, Integer> p: table.keySet()) {
			 Pair<Integer, Integer> p1=p.clone();
			 ret.put(p1, table.get(p));
		 } 
		return ret;
	}

}
