package utils;

public class Pair<E,V> {
	private E firstEl;
	private V secondEl;

	public Pair(E firstEl, V secondEl ){
		this.firstEl=firstEl;
		this.secondEl=secondEl;
	}

	public E getFirst() {
		return firstEl;
	}

	public void setFirst(E firstEl) {
		this.firstEl = firstEl;
	}

	public V getSecond() {
		return secondEl;
	}

	public void setSecond(V secondEl) {
		this.secondEl = secondEl;
	}

	
}
