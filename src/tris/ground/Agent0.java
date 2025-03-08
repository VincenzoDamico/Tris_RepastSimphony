package tris.ground;

import java.util.Objects;

public class Agent0<T> extends GridEl<T>{
	private int order;

	public Agent0(int order,T el ) {
		super(order,el);		
	}
	public Agent0( int order,T el, int psX,int psY ) {
		super(order,el,psX,psY);
	}

}