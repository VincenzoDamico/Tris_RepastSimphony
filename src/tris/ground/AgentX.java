package tris.ground;

import java.util.Objects;


public class AgentX<T> extends GridEl<T>{
	public AgentX(T el) {
		super(el);		
	}
	public AgentX(T el, int ...ps ) {
		super(el,ps);
	}

}
