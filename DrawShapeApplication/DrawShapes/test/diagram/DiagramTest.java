package diagram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import shapes.Circle;
import shapes.Group;
import shapes.IShape;
import shapes.Rectangle;
import shapes.Square;
import drawShape.ShapeReflectionException;

public class DiagramTest 
{
	private Diagram _diagram;
	
	@Before
	public void setUp()
	{
		_diagram = new Diagram();
	}
	
	@Test
	public void addShapeToDiagram()
	{
		_diagram.addShapeToList(new Circle(10, 10, 5));
		assertEquals(1, _diagram.getListOfShapes().size());
	}
	
	@Test
	public void deleteShapeFromDiagram()
	{
		_diagram.addShapeToList(new Circle(10, 10, 10));
		_diagram.selectTheShape(15, 15, true);
		_diagram.deleteShapeFromDiagram();
		assertTrue(0 == _diagram.getListOfShapes().size()
		  && 0 == _diagram.getSelectedShapes().size());
	}
	
	@Test
	public void deleteShapeFromDiagramWhenShapeNotSelected()
	{
		_diagram.addShapeToList(new Circle(10, 10, 10));
		_diagram.selectTheShape(1, 1, true);
		_diagram.deleteShapeFromDiagram();
		assertTrue(1 == _diagram.getListOfShapes().size());
	}
	
	@Test 
	public void groupShapes()
	{
		Circle circle = new Circle(10, 10, 10);
		Square square = new Square(30, 30, 50);
		Rectangle rect = new Rectangle(15, 25, 39, 67);
		_diagram.addShapeToList(circle);
		_diagram.addShapeToList(square);
		_diagram.addShapeToList(rect);
		
		_diagram.selectTheShape(15, 15, false);
		_diagram.selectTheShape(35, 35, false);
		
		_diagram.makeGroups();
		
		ArrayList<IShape> result = _diagram.getSelectedShapes();
		
		assertTrue( 1 == result.size() 
		  && result.get(0) instanceof Group);
	}
	
	@Test 
	public void unGroupShapes()
	{
		Circle circle = new Circle(10, 10, 10);
		Square square = new Square(30, 30, 50);
		Rectangle rect = new Rectangle(125, 125, 39, 67);
		_diagram.addShapeToList(circle);
		_diagram.addShapeToList(square);
		_diagram.addShapeToList(rect);
		
		_diagram.selectTheShape(15, 15, false);
		_diagram.selectTheShape(35, 35, false);
		
		_diagram.makeGroups();
		_diagram.unGroup();
		
		ArrayList<IShape> result = _diagram.getSelectedShapes();
		
		assertTrue( 2 == result.size()
		  && circle == result.get(0)
		  && square == result.get(1));
	}
	
	@Test 
	public void unGroupShapesWhenNoGroupsAvailable()
	{
		Circle circle = new Circle(10, 10, 10);
		_diagram.addShapeToList(circle);
		_diagram.selectTheShape(1, 1, true);
		_diagram.unGroup();
		
		ArrayList<IShape> result = _diagram.getSelectedShapes();
		
		assertEquals( 0, result.size());
	}

	@Test 
	public void selectShapeWhenContainsPoint()
	{
		Circle circle = new Circle(10, 10, 10);
		_diagram.addShapeToList(circle);
		
		ArrayList<IShape> selectedShape = 
		  _diagram.selectTheShape(15, 15, true);
		
		assertEquals(circle,selectedShape.get(0));
	}
	
	@Test 
	public void selectShapeWhenDoNotContainPoint()
	{
		Circle circle = new Circle(10, 10, 10);
		_diagram.addShapeToList(circle);
		
		ArrayList<IShape> selectedShape = 
		  _diagram.selectTheShape(1, 1, true);
		
		assertTrue(0 == selectedShape.size());
	}
	
	@Test 
	public void selectNewShape()
	{
		ArrayList<IShape> selectedShape;
		Circle circle = new Circle(20, 20, 10);
		Rectangle rect = new Rectangle(1, 1, 10, 10);
		_diagram.addShapeToList(circle);
		_diagram.addShapeToList(rect);
		
		selectedShape = _diagram.selectTheShape(25, 25, true);
		selectedShape = _diagram.selectTheShape(5, 5, true);
		
		assertEquals(rect, selectedShape.get(0));
	}
	
	@Test 
	public void selectMoreThanOneShape()
	{
		ArrayList<IShape> selectedShape;
		Circle circle = new Circle(20, 20, 10);
		Rectangle rect = new Rectangle(1, 1, 10, 10);
		_diagram.addShapeToList(circle);
		_diagram.addShapeToList(rect);
		
		selectedShape = _diagram.selectTheShape(25, 25, false);
		selectedShape = _diagram.selectTheShape(5, 5, false);
		
		assertTrue(2 == selectedShape.size());
	}
	
	@Test
	public void getSelectedShape()
	{
		Circle circle = new Circle(10, 10, 10);
		_diagram.addShapeToList(circle);
		ArrayList<IShape> selectedShape = 
		  _diagram.selectTheShape(15, 15, false);

		assertEquals(circle, selectedShape.get(0));
	}
	
	@Test
	public void moveSelectedShape()
	{
		int posX = 10, posY = 10;
		Circle circle = new Circle(10, 10, 10);
		_diagram.addShapeToList(circle);
		_diagram.selectTheShape(15, 15, true);
		_diagram.moveSelectedShape(5, 5);
		
		Circle selectedShape = (Circle)_diagram.
		  getSelectedShapes().get(0);
		
		posX = selectedShape.getX();
		posY = selectedShape.getY();
		
		
		assertTrue(15 == posX &&  15 == posY);
	}
	
	@Test
	public void selectShapeByDefault()
	{
		Circle circle = new Circle(10, 10, 10);
		_diagram.setSelectedImageByDefault(circle);
		ArrayList<IShape> selectedShape = 
		  _diagram.getSelectedShapes();
		
		assertEquals(circle, selectedShape.get(0));
	}
	
	@Test
	public void selectShapeByDefaultWhenSelectedShape()
	{
		Circle circle = new Circle(10, 10, 10);
		Rectangle rect = new Rectangle(1, 1, 10, 10);
		_diagram.setSelectedImageByDefault(circle);
		_diagram.setSelectedImageByDefault(rect);
		
		ArrayList<IShape> selectedShape = 
		  _diagram.getSelectedShapes();
		
		assertEquals(rect, selectedShape.get(0));
	}

	@Test
	public void ungroupWhenSelectionIsNotGroup()
	{
		Circle circle = new Circle(10, 10, 10);
		Square square = new Square(30, 30, 50);
		_diagram.addShapeToList(circle);
		_diagram.addShapeToList(square);
		
		_diagram.selectTheShape(15, 15, true);
		_diagram.unGroup();
		
		ArrayList<IShape> result = _diagram.getSelectedShapes();
		
		assertTrue( 1 == result.size()
		  && circle == result.get(0));
	}

	@Test
	public void saveAndLoadDiagram() 
	  throws  ClassNotFoundException, IOException 
	{
		String fileName = "diagram";
		_diagram.addShapeToList(new Circle(10, 10, 50));
		_diagram.addShapeToList(new Square(20, 20 , 50));
		_diagram.saveDiagram(fileName);
		Diagram newDiagram = Diagram.loadDiagram(fileName);
		assertEquals(2,newDiagram.getListOfShapes().size());		
	}
	
	@Test
	public void saveAndLoadDiagramWhenShapesSelected() 
	  throws  ClassNotFoundException, IOException 
	{
		String fileName = "diagram";
		_diagram.addShapeToList(new Circle(10, 10, 10));
		_diagram.addShapeToList(new Square(2, 2 , 10));
		_diagram.addShapeToList(new Circle(40, 40 , 50));
		_diagram.selectTheShape(60, 60, true);
		_diagram.saveDiagram(fileName);
		Diagram newDiagram = Diagram.loadDiagram(fileName);
		assertEquals(3,newDiagram.getListOfShapes().size());		
	}
	
	@Test
	public void loadToThrowReadException() throws 
	  IOException, ClassNotFoundException {
	    try 
	    {
	    	Diagram.loadDiagram("build.gradle");
	    	fail("Expected exception for corrupted input stream");
	    } catch(java.io.StreamCorruptedException ex) {
	      
	    }
	}

	@Test
	public void loadDiagramWhenFileUnavailable() 
	  throws IOException, ClassNotFoundException
	{
		try
		{
			Diagram.loadDiagram("unavailableFile");
			fail("Expected Exception for invalid file name");
		}
		catch(java.io.FileNotFoundException ex)
		{
		}		
	}
	
	@Test
	public void  testExceptionWhileSaveDiagram() throws IOException
	{
		try
		{
			Diagram diagram = new Diagram() 
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void writeToFile(ObjectOutputStream outStream, 
				  ArrayList<IShape> fullList) throws IOException
				{
					throw new ShapeReflectionException(
					  new Exception());
				}
			};
			
			diagram.saveDiagram("file");
			fail("Test fails, it was supposed to" +
			  " throw an exception");
		}
		catch(ShapeReflectionException e)
		{
			
		}
	}
}
