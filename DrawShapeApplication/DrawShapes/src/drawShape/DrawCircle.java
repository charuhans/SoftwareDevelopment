package drawShape;

import java.awt.Graphics;

import shapes.Circle;
import shapes.IShape;

public class DrawCircle implements IDrawShape 
{

	@Override
	public void draw(IShape shape, Graphics g)
	{
		Circle circle = (Circle)shape;
	
		g.drawOval(circle.getX(),
		  circle.getY(), 
		  2*circle.getRadius(), 
		  2*circle.getRadius());
	}

}
