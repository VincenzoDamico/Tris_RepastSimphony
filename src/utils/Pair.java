package utils;

import java.util.Objects;

public class Pair<E,V> implements Cloneable{
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

	@Override
	public String toString() {
		return "Pair [firstEl=" + firstEl + ", secondEl=" + secondEl + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		return firstEl.equals(other.firstEl) && secondEl.equals(other.secondEl);
	}
	
	//trovare sol per clonare gli oggetti
	@SuppressWarnings("unchecked")
	@Override
	public Pair<E,V> clone(){
		try {
	       Pair<E, V> cloned = (Pair<E, V>) super.clone(); // Chiamata al clone di Object
	        cloned.firstEl =  firstEl;
	        cloned.secondEl = secondEl;
	        return cloned;
		} catch (CloneNotSupportedException e) {
	       System.err.print("Errore clone elemento Pair");
	       return null;
	    }
	}
}
