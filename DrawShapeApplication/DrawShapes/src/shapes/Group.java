package shapes;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements IShape , Serializable
{
	private static final long serialVersionUID = 1L;
	
	ArrayList<IShape> listOfShapes;
	
	public Group()
	{
		listOfShapes = new ArrayList<IShape>();
	}
	
	public void addShapesToGroup(ArrayList<IShape> shapes)
	{
		for (IShape shape : shapes)
			listOfShapes.add(shape);
	}
	
	public ArrayList<IShape> unGroup()
	{
		return listOfShapes;
	}

	@Override
	public void moveBy(int movementX, int movementY)
	{
		for( IShape shape : listOfShapes)
			shape.moveBy(movementX, movementY);
	}

	@Override
	public boolean contains(int pointX, int pointY) 
	{
		for( IShape shape : listOfShapes)
			if(shape.contains(pointX, pointY))
				return true;

		return false;
	}
}
