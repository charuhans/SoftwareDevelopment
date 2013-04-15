package rodCutter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class RodCutterEfficient extends RodCutterSimple
{	
	private Map<Integer, SimpleEntry<Integer, List<Integer>>> 
      bestCombinationForLength = new HashMap<>();
	
	public RodCutterEfficient(int[] priceLength)
	{
		super(priceLength);
	}
	
	public int cutRod(int length, List<Integer> cuts)
	{
		List<Integer> listofint = new ArrayList<>();
		
		if(bestCombinationForLength.get(length) == null)
			bestCombinationForLength.put(length, new SimpleEntry<>
			  (super.cutRod(length, listofint), listofint));
		
		cuts.addAll(bestCombinationForLength.get(length).getValue());
		return bestCombinationForLength.get(length).getKey();
	}
}
