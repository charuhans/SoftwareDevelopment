package shapes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import shapes.Circle;
import shapes.Group;
import shapes.IShape;
import shapes.Square;

public class GroupTest
{
	private Group _group;
	private ArrayList<IShape> _listOfShapes;
	
	@Before
	public void setUp()
	{
		_group = new Group();
		
		_listOfShapes = new ArrayList<IShape>();
	}
	
	@Test
	public void createGroupFromShapes()
	{
		_listOfShapes.add(new Circle(30, 12, 20));
		_listOfShapes.add(new Square(22, 34, 15));
		
		_group.addShapesToGroup(_listOfShapes);
		ArrayList<IShape> returnedShape = _group.unGroup();
		assertEquals(_listOfShapes, returnedShape);
	}
	
	@Test
	public void createGroupFromShapesAndGroup()
	{
		Group group = new Group();
		ArrayList<IShape> shapeList = new ArrayList<IShape>();
		shapeList.add(new Circle(15, 222, 24));
		shapeList.add(new Square(10, 45, 78));
		group.addShapesToGroup(shapeList);

		_listOfShapes.add(new Circle(87, 12, 99));
		_listOfShapes.add(new Square(43, 65, 12));
		_listOfShapes.add(group);
		
		_group.addShapesToGroup(_listOfShapes);
		
		ArrayList<IShape> groupList = _group.unGroup();
		assertEquals(_listOfShapes, groupList);
	}
	
	
	@Test
	public void moveGroup()
	{
		_listOfShapes.add(new Circle(30, 12, 20));
		
		_group.addShapesToGroup(_listOfShapes);
		_group.moveBy(2, 3);
		
		ArrayList<IShape> returnedShapes = _group.unGroup();
		Circle circle = (Circle)returnedShapes.get(0);
		
		assertTrue(32 == circle.getX() 
				&& 15 == circle.getY());
	}
	
	@Test
	public void unGroup()
	{
		Group group = new Group();
		ArrayList<IShape> shapeList = new ArrayList<IShape>();
		shapeList.add(new Circle(20, 23, 15));
		shapeList.add(new Square(50, 46, 25));
		group.addShapesToGroup(shapeList);

		_listOfShapes.add(new Circle(30, 12, 20));
		_listOfShapes.add(new Square(22, 34, 15));
		_listOfShapes.add(group);
		
		_group.addShapesToGroup(_listOfShapes);
		
		ArrayList<IShape> unGroupedList = _group.unGroup();
		
		assertEquals(_listOfShapes, unGroupedList);
	}
	
	@Test
	public void containsPoint()
	{
		_listOfShapes.add(new Circle(30, 30, 20));
		_listOfShapes.add(new Square(22, 34, 15));
		
		_group.addShapesToGroup(_listOfShapes);
		
		assertTrue(_group.contains(43, 43));
	}
	
	@Test
	public void doNotContainPoint()
	{
		_listOfShapes.add(new Circle(30, 30, 20));
		_listOfShapes.add(new Square(22, 34, 15));
		
		_group.addShapesToGroup(_listOfShapes);
		assertFalse(_group.contains(1, 1));
	}
}
