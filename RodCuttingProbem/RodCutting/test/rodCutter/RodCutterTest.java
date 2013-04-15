package rodCutter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RodCutterTest
{
	private List<Integer> cuts;
	private RodCutterSimple _rodCutter;
	
	protected RodCutterSimple getRodCutter(int[] price)
	{
		return new RodCutterSimple(price);
	}
	
	@Before
	public void setUp()
	{
		int[] price = {1, 1, 2, 3, 4, 5, 5, 9, 9, 10};
		_rodCutter = getRodCutter(price);
		cuts = new ArrayList<Integer>();
	}
	
	@Test
	public void canary()
	{
		assertTrue(true);
	}
	
	@Test
	public void cutRodLength0()
	{
		assertEquals(0,  _rodCutter.cutRod(0, cuts));
		assertEquals(Arrays.asList(), cuts);
	}
	
	@Test
	public void cutRodLengthNegative()
	{
		assertEquals(0, _rodCutter.cutRod(-5, cuts));
		assertEquals(Arrays.asList(), cuts);
	}
	
	@Test
	public void cutRodLength1()
	{
		assertEquals(1, _rodCutter.cutRod(1, cuts));
		assertEquals(Arrays.asList(1), cuts);
		
	}
	
	@Test
	public void cutRodLength3()
	{	
		assertEquals(3, _rodCutter.cutRod(3, cuts));
		assertEquals(Arrays.asList(1,1,1), cuts);
	}
	
	@Test
	public void cutRodLength20()
	{	
		assertEquals(22, _rodCutter.cutRod(20, cuts));
		assertEquals(Arrays.asList(1,1,1,1,8,8), cuts);
	}
}
