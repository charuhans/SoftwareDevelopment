package diagram;

import java.io.*;
import java.util.ArrayList;

import shapes.Group;
import shapes.IShape;

public class Diagram implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<IShape> listOfShapes;
	private ArrayList<IShape> selectedShape;
	
	public Diagram() 
	{
		listOfShapes = new ArrayList<IShape>();
		selectedShape = new ArrayList<IShape>();
	}
	
	public void addShapeToList(IShape shape)
	{
		listOfShapes.add(shape);
	}
	
	public ArrayList<IShape> getListOfShapes()
	{
		return listOfShapes;
	}
	
	public void deleteShapeFromDiagram()
	{
		selectedShape.clear();
	}
	
	public void makeGroups()
	{
		Group group = new Group();
		group.addShapesToGroup(selectedShape);
		
		selectedShape.clear();
		selectedShape.add(group);
	}
	
	public void unGroup()
	{
		if(selectedShape.size() == 1)
			if(selectedShape.get(0) instanceof Group)
				selectedShape = 
				  ((Group)selectedShape.get(0)).unGroup();
	}

	public ArrayList<IShape> selectTheShape(int x, 
	  int y, boolean isJustClick) 
	{
		if(selectedShape.size() >  0 && isJustClick)
		{
			for (int i = 0; i < selectedShape.size(); i++)
			  addShapeToList(selectedShape.get(i));
			selectedShape.clear();
		}
		
		for (int i = 0; i < listOfShapes.size(); i++)
		{
			if(listOfShapes.get(i).contains(x, y))
			{
				selectedShape.add(listOfShapes.get(i));
				listOfShapes.remove(i);
				break;
			}
		}
		
		return selectedShape;
	}
	
	public ArrayList<IShape> getSelectedShapes()
	{
		return selectedShape;
	}

	public void moveSelectedShape(int movementX, int movementY) 
	{
		for(IShape shape : selectedShape)
			shape.moveBy(movementX, movementY);
	}

	public void setSelectedImageByDefault(IShape shape) 
	{
		if(selectedShape.size() > 0)
		{
			for (IShape selectShape : selectedShape)
				listOfShapes.add(selectShape);
			
			selectedShape.clear();
		}
		selectedShape.add(shape);
	}
	
	public void saveDiagram(String fileName) throws IOException
	{  
		ArrayList<IShape> fullList = new ArrayList<IShape>();
		
		for(IShape shape : listOfShapes)
			fullList.add(shape);
		for(IShape shape : selectedShape)
			fullList.add(shape);
		
		FileOutputStream fileOutStream;
		ObjectOutputStream objOutStream = null;
		try
		{
			fileOutStream = new FileOutputStream(fileName);
			objOutStream = 
			  new ObjectOutputStream (fileOutStream);	
			
			writeToFile(objOutStream, fullList);
		}
		finally
		{
			objOutStream.close();
		}
	}
	
	public void writeToFile(ObjectOutputStream outStream, 
	  ArrayList<IShape> fullList) throws IOException
	{
		outStream.writeObject (fullList);
	}
	
	public static Diagram loadDiagram(String fileName) 
	  throws IOException, ClassNotFoundException
	{
		FileInputStream fileInStream = 
		  new FileInputStream(fileName);
		try
		{
			ObjectInputStream objInStream = 
			  new ObjectInputStream (fileInStream);
		
			java.util.List<?> shapes = 
			  (java.util.List<?>) objInStream.readObject();		   
	   
		 Diagram diagram = new Diagram();
		 for(Object shape : shapes)
			 diagram.addShapeToList((IShape) shape);
		 
		 return diagram;
		}
		finally
		{
			fileInStream.close();
		}
	}
}
