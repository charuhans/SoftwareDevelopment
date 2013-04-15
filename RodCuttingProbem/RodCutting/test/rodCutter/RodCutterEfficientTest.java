package rodCutter;

public class RodCutterEfficientTest extends RodCutterTest
{
	protected  RodCutterSimple getRodCutter(int[] price)
	{
		return new RodCutterEfficient(price);
	}
}