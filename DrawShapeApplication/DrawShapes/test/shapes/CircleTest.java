package shapes;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class CircleTest {
	
	private Circle _circle;
	
	@Before
	public void setUp()
	{
		_circle = new Circle(2,2,2);
	}
	
	@Test
	public void canary()
	{
		assertTrue(true);
	}
	
	@Test
	public void setCenterX()
	{
		_circle.setX(10);
		assertEquals(10, _circle.getX());
	}
	
	@Test
	public void setCenterY()
	{
		_circle.setY(10);
		assertEquals(10, _circle.getY());
	}
	
	@Test
	public void setRadiusWhenPositive()
	{
		_circle.setRadius(10);
		assertEquals(10, _circle.getRadius());
	}
	
	@Test
	public void setRadiusWhenNegative()
	{
		_circle.setRadius(-2);
		assertEquals(1, _circle.getRadius());
	}
	
	@Test
	public void moveCircle()
	{
		_circle.moveBy(2, 3);
		assertTrue(4 == _circle.getX() 
		  && 5 == _circle.getY());
		
	}
	
	@Test
	public void containsPoint()
	{
		assertTrue(_circle.contains(3, 3));
	}
	
	@Test
	public void doNotContainPoint()
	{
		assertFalse(_circle.contains(10, 10));
	}
	
	@Test
	public void testDefaultSetUp()
	{
		Circle defaultCircle = new Circle();
		assertTrue(50 == defaultCircle.getX()
		  && 50 == defaultCircle.getY()
		  && 50 == defaultCircle.getRadius());
	}
}
