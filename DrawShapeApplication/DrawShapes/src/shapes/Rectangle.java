package shapes;

import java.io.Serializable;

public class Rectangle implements IShape, Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	private int height;
	private int width;
	
	public Rectangle()
	{
		x = 20;
	    y = 20;
		height = 50;
		width = 100;
	}
	
	public Rectangle(int topLeftX, int topLeftY, 
	  int length, int width) 
	{
		setX(topLeftX);
		setY(topLeftY); 
		setHeight(length);
		setWidth(width);
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getX() 
	{
		return x;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}

	public void setX(int positionX)
	{
		x = positionX;
	}
	
	public void setY(int positionY)
	{
		y = positionY;
	}
	
	public void setHeight(int rectangleHeight)
	{
		height = rectangleHeight <= 0 ? 1 : rectangleHeight;
	}
	
	public void setWidth(int rectangleWidth)
	{
		width = rectangleWidth <= 0 ? 1 : rectangleWidth;
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
		  = x <= pointX && (x+width) >= pointX;
		boolean yComponent 
		  = y <= pointY && (y+height) >= pointY;
		  
		return (xComponent && yComponent);
	}
}
