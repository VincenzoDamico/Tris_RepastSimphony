package tris.ground;

import java.util.Objects;


public class AgentX<T> extends GridEl<T>{

	public AgentX( int order,T el) {
		super(order,el);		

	}
	public AgentX(int order,T el, int ...ps ) {
		super(order,el,ps);
	}
	

}
