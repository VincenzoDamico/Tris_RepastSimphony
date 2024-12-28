package tris.ground;

public interface PlayGround<T> {
	public PgEl<T> getElAt(int... location);
	public void ChangeState(T element, int... state);
	public Integer size();
}
