package drawShape;

import java.awt.Graphics;
import java.awt.*;
import java.awt.geom.*;

import shapes.ColoredCircle;
import shapes.IShape;

public class DrawColoredCircle implements IDrawShape 
{

	@Override
	public void draw(IShape shape, Graphics g)
	{
		ColoredCircle coloredCircle = (ColoredCircle)shape;
	
		Color color = null;
                try {
                  color =
                    (Color) Color.class.getField(coloredCircle.getColor()).get(null);
                } catch(Exception ex) {
                  color = Color.black;
                }

                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.setPaint(color);
                graphics2D.draw(new Ellipse2D.Double((int) coloredCircle.getX(),
                        (int)coloredCircle.getY(), (int)coloredCircle.getRadius(),
                        (int)coloredCircle.getRadius()));
	}

}
