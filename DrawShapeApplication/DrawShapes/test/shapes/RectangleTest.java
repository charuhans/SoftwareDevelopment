package shapes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RectangleTest 
{
	private Rectangle _rectangle;
	
	@Before
	public void setUp()
	{
		_rectangle = new Rectangle(2, 2, 10, 4);
	}
	
	@Test
	public void setTopLeftX()
	{
		_rectangle.setX(10);
		assertEquals(10, _rectangle.getX());
	}
	
	@Test
	public void setTopLeftY()
	{
		_rectangle.setY(10);
		assertEquals(10, _rectangle.getY());
	}
	
	@Test
	public void setLengthWhenPositive()
	{
		_rectangle.setHeight(10);
		assertEquals(10, _rectangle.getHeight());
	}
	
	@Test
	public void setLengthWhenNegative()
	{
		_rectangle.setHeight(-2);
		assertEquals(1, _rectangle.getHeight());
	}
	
	@Test
	public void setWidthWhenPositive()
	{
		_rectangle.setWidth(15);
		assertEquals(15, _rectangle.getWidth());
	}
	
	@Test
	public void setWidthWhenNegative()
	{
		_rectangle.setWidth(-15);
		assertEquals(1, _rectangle.getWidth());
	}
	
	@Test
	public void moveSquare()
	{
		_rectangle.moveBy(1, 2);
		assertTrue(3 == _rectangle.getX() 
		  && 4 == _rectangle.getY());
	}

	@Test
	public void containsPoint()
	{
		assertTrue(_rectangle.contains(3, 3));
	}
	
	@Test
	public void doNotContainPoint()
	{
		assertFalse(_rectangle.contains(0, 0));
	}
	
	@Test
	public void doNotContainPointX()
	{
		assertFalse(_rectangle.contains(1000, 3));
	}
	
	@Test
	public void doNotContainPointY()
	{
		assertFalse(_rectangle.contains(3, 1000));
	}
	
	
	@Test
	public void testDefaultSetUp()
	{
		Rectangle defaultRect = new Rectangle();
		assertTrue(20 == defaultRect.getX()
		  && 20 == defaultRect.getY()
		  && 50 == defaultRect.getHeight()
		  && 100 == defaultRect.getWidth());
	}
	
}
