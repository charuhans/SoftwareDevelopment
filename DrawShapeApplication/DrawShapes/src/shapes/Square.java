package shapes;

import java.io.Serializable;

public class Square implements IShape , Serializable
{	
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	private int length;
	
	public Square()
	{
		x = 50;
		y = 50;
		length = 100;
	}
	
	public Square(int posX, int posY, int length)
	{
		setX(posX);
		setY(posY);
		setLength(length);
	}
	
	public void setX(int posX)
	{
		x = posX;;
	}
	
	public void setY(int posY)
	{
		y = posY;
	}
	
	public void setLength(int squareLength)
	{
		length = squareLength <= 0 ? 1 : squareLength;
	}
	
	public int getX() 
	{
		return x;
	}
	
	public int getY() 
	{
		return y;
	}
	
	public int getLength()
	{
		return length;
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
		boolean xComponent 
		  = x <= pointX && (x+length) >= pointX;
		boolean yComponent 
		  = y <= pointY && (y+length) >= pointY;
		  
		return (xComponent && yComponent);
	}
}
