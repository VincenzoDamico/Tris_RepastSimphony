package tris.ground;

public class GridEl<T> {
	private int[] pos=new int[2];
	private T elememt;
	public GridEl(T el) {
		this.elememt=el;		
	}

	public GridEl(T el, int ...ps ) {
		int i=0;
		for (int v:ps ) {
			if (i==2)
				break;
			pos[i]=v;
			i++;
		}
		this.elememt=el;		
	}
	@Override
	public String toString() {
		return elememt + "";
	}
	
	public T getEl() { 
		return elememt;
	}
	public int[] getPos() { 
		return pos;
	}
	public void setPos(int ...ps) { 
		int i=0;
		for (int v:ps ) {
			if (i==2)
				break;
			pos[i]=v;
			i++;
		}
		
	}

}