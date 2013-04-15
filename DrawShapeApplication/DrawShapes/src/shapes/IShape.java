package shapes;

public interface IShape
{
	public boolean contains(int pointX, int pointY);
	public void moveBy(int movementX, int movementY);
}
