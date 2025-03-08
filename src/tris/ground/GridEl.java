package tris.ground;

public class GridEl<T> {
	private int posX;
	private int posY;
	private int order;
	private T elememt;
	
	public GridEl(int order,T el) {
		this.elememt=el;
		this.order=order;

	}
	
	public int getOrder() {
		return order;
	}

	public GridEl(int order,T el, int dimX,int dimY ) {
		posX=dimX;
		posY=dimY;
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
	public int getPosX() { 
		return posX;
	}
	public int getPosY() { 
		return posY;
	}
	public void setPos( int dimX,int dimY) { 
		posX=dimX;
		posY=dimY;
		
	}

}