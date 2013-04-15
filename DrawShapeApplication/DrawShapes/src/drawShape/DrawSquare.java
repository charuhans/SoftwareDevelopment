package drawShape;

import java.awt.Graphics;

import shapes.IShape;
import shapes.Square;

public class DrawSquare implements IDrawShape 
{

	@Override
	public void draw(IShape shape, Graphics g) 
	{
		Square square = (Square)shape;
		
		
		g.drawRect(square.getX(), 
		  square.getY(), 
		  square.getLength(), 
		  square.getLength());
	}

}
