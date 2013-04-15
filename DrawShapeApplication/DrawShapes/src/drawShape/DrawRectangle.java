package drawShape;

import java.awt.Graphics;

import shapes.IShape;
import shapes.Rectangle;

public class DrawRectangle implements IDrawShape 
{

	@Override
	public void draw(IShape shape, Graphics g) 
	{
		Rectangle rectangle = (Rectangle)shape;
		g.drawRect(rectangle .getX(), 
		  rectangle .getY(), 
		  rectangle .getWidth(), 
		  rectangle .getHeight());
	}
}