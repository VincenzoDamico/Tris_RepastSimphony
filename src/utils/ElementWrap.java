package utils;

import java.util.Objects;

public class ElementWrap<T> {
	private T el;
	@Override
	public int hashCode() {
		return Objects.hash(el);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementWrap other = (ElementWrap) obj;
		return Objects.equals(el, other.el);
	}
	public ElementWrap(T el){
		this.el=el;
	}
	public T getEl() {
		return el;
	}
	public void setEl(T ele) {
		el=ele;
	}
	public String toString() {
		return el.toString();
	}
}
