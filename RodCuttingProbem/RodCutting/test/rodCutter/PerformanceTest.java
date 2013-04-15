package rodCutter;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

public class PerformanceTest
{
	private long timeTakenForCut(RodCutterSimple rodCutter)
	{	
		long startTimeForAlgorithm = System.nanoTime();
		rodCutter.cutRod(20, new ArrayList<Integer>());
		return System.nanoTime() - startTimeForAlgorithm;
	}
	
	@Test
	public void testPerformaceForLength20()
	{
		int[] price = {1, 1, 2, 3, 4, 5, 5, 9, 9, 10};
		
		RodCutterSimple rodCutterSimple = new RodCutterSimple(price);
		
		RodCutterEfficient rodCutterEfficient = 
		  new RodCutterEfficient(price);
		
		long timeSimple = timeTakenForCut(rodCutterSimple);
		long timeEfficient = timeTakenForCut(rodCutterEfficient);
		
		assertTrue(timeSimple - timeEfficient >=  timeSimple/3);
	}
}
