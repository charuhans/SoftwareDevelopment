package drawShape;

import java.awt.Graphics;
import java.util.ArrayList;


import shapes.Group;
import shapes.IShape;

public class DrawGroup  implements IDrawShape
{

	@Override
	public void draw(IShape shape, Graphics g)
	{
		Group group = (Group)shape;
		ArrayList<IShape> listOfShapes = group.unGroup();
		for (IShape ishape : listOfShapes) 
		{
			String classNameWtPackage = ishape.getClass().getName();
			String[] pathToClass = classNameWtPackage.split("\\.");
			String drawClassName = "drawShape.Draw"
			  + pathToClass[pathToClass.length-1];

			IDrawShape drawShape;
			try 
			{
				drawShape = (IDrawShape) 
				  Class.forName(drawClassName).newInstance();
				drawShape.draw(ishape, g);
			} 
			catch (Exception e) 
			{
				throw new ShapeReflectionException(e);
			}
		}
	}

}
