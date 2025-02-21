package tris.ground;

public class GridEl<T> {
	private int[] pos=new int[2];
	private int order;

	private T elememt;
	public GridEl(int order,T el) {
		this.elememt=el;
		this.order=order;

	}
	
	public int getOrder() {
		return order;
	}

	public GridEl(int order,T el, int ...ps ) {
		int i=0;
		for (int v:ps ) {
			if (i==2)
				break;
			pos[i]=v;
			i++;
		}
		
		this.elememt=el;
		this.order=order;

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