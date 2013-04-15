package rodCutter;

import java.util.List;
import java.util.ArrayList;

public class RodCutterSimple
{
	private int[] prices;
	
	public RodCutterSimple(int[] priceLength)
	{
		prices = priceLength;
	}
	
	public int cutRod(int length, List<Integer> cuts)
	{
		int priceForLength = 0;
  
		if(length > 0) 
		{
			if(length <= prices.length) 
			{
				priceForLength = prices[length-1];
				cuts.add(length);
			}
	      
			for(int i = 1; i < length; i++)
			{
				List<Integer> cutsForPart1 = new ArrayList<Integer>();
				List<Integer> cutsForPart2 = new ArrayList<Integer>();
				
				int totalPrice = cutRod(i, cutsForPart1) +
				  cutRod(length - i, cutsForPart2);
	        
				if(totalPrice > priceForLength)
				{
					cuts.clear();
					cuts.addAll(cutsForPart1);
					cuts.addAll(cutsForPart2);
					priceForLength = totalPrice;
				}
			}
		}
		return priceForLength;
	}
}