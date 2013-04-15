package shapes;

import java.io.Serializable;

public class DottedCircle implements IShape , Serializable 
{
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private int radius;
	
	public DottedCircle()
	{
		x = 50;
		y = 50;
		radius = 50;
	}
	
	public DottedCircle(int posX, int posY, int radius)
	{
		setX(posX);
		setY(posY);
		setRadius(radius);
	}
	
	public void setX(int posX)
	{
		x = posX;
	}
	
	public void setY(int posY)
	{
		y = posY;
	}
	
	public void setRadius(int radiusOfCircle)
	{
		radius = radiusOfCircle <= 0 ? 1 : radiusOfCircle;
	}
	
	public int getX() 
	{
		return x;
	}

	public int getY() 
	{
		return y;
	}

	public int getRadius()
	{
		return radius;
	}

	@Override
	public void moveBy(int movementX, int movementY) 
	{
		setX(x + movementX);
		setY(y + movementY);	
	}

	@Override
	public boolean contains(int pointX, int pointY)
	{
		return (Math.pow((x+radius - pointX), 2) 
		  + Math.pow((y+radius-pointY), 2) 
		  <= Math.pow(radius, 2));
	}
}
