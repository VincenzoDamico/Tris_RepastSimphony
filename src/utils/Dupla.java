package utils;

import java.util.StringTokenizer;

public class Dupla {
	private int x;
	private int y;
	public Dupla(int x, int y) {
		this.x=x;
		this.y=y;
	}


	
	public int getX() {return x;}
	public int getY() {return y;}
	
	public static Dupla read(String s) {
		if(s.matches("\\(\\d+,\\d+\\)")) {
			StringTokenizer stToken= new StringTokenizer(s,"(,)");
			int i=Integer.parseInt(stToken.nextToken());
			int j=Integer.parseInt(stToken.nextToken());		
			return new Dupla(i,j);
		}
		return null;
	}
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
