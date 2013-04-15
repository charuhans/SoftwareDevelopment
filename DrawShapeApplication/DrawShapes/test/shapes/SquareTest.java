package shapes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SquareTest 
{
	private Square _square;
	
	@Before
	public void setUp()
	{
		_square = new Square(2,2,4);
	}
		
	@Test
	public void setTopLeftX()
	{
		_square.setX(10);
		assertEquals(10, _square.getX());
	}
	
	@Test
	public void setTopLeftY()
	{
		_square.setY(10);
		assertEquals(10, _square.getY());
	}
	
	@Test
	public void setLengthWhenPositive()
	{
		_square.setLength(10);
		assertEquals(10, _square.getLength());
	}
	
	@Test
	public void setLengthWhenNegative()
	{
		_square.setLength(-2);
		assertEquals(1, _square.getLength());
	}
	
	@Test
	public void moveSquare()
	{
		_square.moveBy(1, 2);
		assertTrue(3 == _square.getX() 
		  && 4 == _square.getY());
	}

	@Test
	public void containsPoint()
	{
		assertTrue(_square.contains(3, 3));
	}
	
	@Test
	public void doNotContainPoint()
	{
		assertFalse(_square.contains(100, 100));
	}
	
	@Test
	public void doNotContainPointX()
	{
		assertFalse(_square.contains(100, 3));
	}
	
	@Test
	public void doNotContainPointY()
	{
		assertFalse(_square.contains(3, 100));
	}
	
	
	@Test
	public void testDefaultSetUp()
	{
		Square defaultSquare = new Square();
		assertTrue(50 == defaultSquare.getX()
		  && 50 == defaultSquare.getY()
		  && 100 == defaultSquare.getLength());
	}
}
