package tris.ground;

public class GridEl<T> implements PgEl<T> {
	private int[] pos=new int[3];
	private T elememt;

	public GridEl(T el, int ...ps ) {
		int i=0;
		for (int v:ps ) {
			if (i==3)
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
	@Override
	public T getElement() { 
		return elememt;
	}

}
