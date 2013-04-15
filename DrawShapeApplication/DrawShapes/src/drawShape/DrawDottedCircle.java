package drawShape;

import java.awt.Graphics;
import java.awt.*;
import java.awt.geom.*;

import shapes.DottedCircle;
import shapes.IShape;

public class DrawDottedCircle implements IDrawShape 
{

	@Override
	public void draw(IShape shape, Graphics g)
	{
		DottedCircle dottedCircle = (DottedCircle)shape;
	
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.setPaint(Color.black);
                BasicStroke stroke = new BasicStroke(2.0f,
                  BasicStroke.CAP_BUTT,
                  BasicStroke.JOIN_BEVEL, 0, new float[] {10}, 0);
                graphics2D.setStroke(stroke);
                graphics2D.draw(new Ellipse2D.Double((int) dottedCircle.getX(),
                        (int)dottedCircle.getY(), (int)dottedCircle.getRadius(),
                        (int)dottedCircle.getRadius()));
	}

}
