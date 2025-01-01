package tris.ground;

import utils.ElementWrap;

public interface PlayGround<T> {
	public GridEl<T> getElAt(int... location);
	public void ChangeState(GridEl<T> element);
	public Integer size();
	public void clear();
}
