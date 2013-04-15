package game;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class PeaceOfMindTest
{
	PeaceOfMind _peaceOfMind;
	Colors[] systemColorsDistinct = {Colors.GREEN, Colors.RED, Colors.YELLOW, Colors.BLUE, Colors.WHITE, Colors.ORANGE};
	Colors[] systemColorsMoreThanOneDuplicateButNotAll = {Colors.GREEN, Colors.RED, Colors.RED, Colors.BLUE, Colors.WHITE, Colors.ORANGE};
	Colors[] systemColorsAllDuplicate = {Colors.RED, Colors.RED, Colors.RED, Colors.RED, Colors.RED, Colors.RED};
	
	@Before
	public void setUp()
	{
		_peaceOfMind = new PeaceOfMind();
	}
	
	@Test
	public void canary()
	{
		assertTrue(true);
	}
	
	@Test
	public void createPeaceOfMind()
	{
		assertFalse(_peaceOfMind.isGameOver());	
	}
	
	@Test
	public void compareColorMatchAndPostionMatch()
	{
		Colors[] userGuess      = {Colors.GREEN, Colors.RED, Colors.YELLOW, Colors.BLUE, Colors.WHITE, Colors.ORANGE};
		Colors[] expectedResult = {Colors.BLACK, Colors.BLACK, Colors.BLACK, Colors.BLACK, Colors.BLACK, Colors.BLACK};
		
		Colors[] result = _peaceOfMind.compareColors(systemColorsDistinct, userGuess);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void compareColorMatchAndNoPostionMatch()
	{
		Colors[] userGuess      = {Colors.RED, Colors.YELLOW, Colors.BLUE, Colors.WHITE, Colors.ORANGE, Colors.GREEN};
		Colors[] expectedResult = {Colors.SILVER, Colors.SILVER, Colors.SILVER, Colors.SILVER, Colors.SILVER, Colors.SILVER};
		
		Colors[] result = _peaceOfMind.compareColors(systemColorsDistinct, userGuess);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void compareColorNoColorMatch()
	{
		Colors[] userGuess      = {Colors.CYAN, Colors.VIOLET, Colors.CYAN, Colors.VIOLET, Colors.CYAN, Colors.VIOLET};
		Colors[] expectedResult = {};
		
		Colors[] result = _peaceOfMind.compareColors(systemColorsDistinct, userGuess);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void compareColorMatchAndSomePositionMatch()
	{
		Colors[] userGuess      = {Colors.GREEN, Colors.RED, Colors.BLUE, Colors.PINK, Colors.RED, Colors.ORANGE};
		Colors[] expectedResult = {Colors.BLACK, Colors.BLACK, Colors.BLACK, Colors.SILVER};
		
		Colors[] result = _peaceOfMind.compareColors(systemColorsDistinct, userGuess);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void compareColorAtleaseOneAndNotAllMatchAndPositionMatches()
	{
		Colors[] userGuess      = {Colors.GREEN, Colors.CYAN, Colors.CYAN, Colors.VIOLET, Colors.VIOLET, Colors.CYAN};
		Colors[] expectedResult = {Colors.BLACK};
		
		Colors[] result = _peaceOfMind.compareColors(systemColorsDistinct, userGuess);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void compareColorAtleaseOneAndNotAllMatchAndSomePositionMatches()
	{
		Colors[] userGuess      = {Colors.GREEN, Colors.WHITE, Colors.CYAN, Colors.VIOLET, Colors.VIOLET, Colors.CYAN};
		Colors[] expectedResult = {Colors.BLACK, Colors.SILVER};
		
		Colors[] result = _peaceOfMind.compareColors(systemColorsDistinct, userGuess);
		assertArrayEquals(expectedResult, result);
	}
	
	public void compareColorMatchAndPostionMatchMoreThanOneDuplicateButNotAll()
	{
		Colors[] userGuess      = {Colors.GREEN, Colors.RED, Colors.RED, Colors.BLUE, Colors.WHITE, Colors.ORANGE};
		Colors[] expectedResult = {Colors.BLACK, Colors.BLACK, Colors.BLACK, Colors.BLACK, Colors.BLACK, Colors.BLACK};
		
		Colors[] result = _peaceOfMind.compareColors(systemColorsMoreThanOneDuplicateButNotAll, userGuess);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void compareColorMatchAndNoPostionMatchMoreThanOneDuplicateButNotAll()
	{
		Colors[] userGuess      = {Colors.RED, Colors.GREEN, Colors.BLUE, Colors.RED, Colors.ORANGE, Colors.WHITE};
		Colors[] expectedResult = {Colors.SILVER, Colors.SILVER, Colors.SILVER, Colors.SILVER, Colors.SILVER, Colors.SILVER};
		
		Colors[] result = _peaceOfMind.compareColors(systemColorsMoreThanOneDuplicateButNotAll, userGuess);
		assertArrayEquals(expectedResult, result);
	}
		
	@Test
	public void compareColorMatchAndSomePositionMatchMoreThanOneDuplicateButNotAll()
	{
		Colors[] userGuess      = {Colors.GREEN, Colors.RED, Colors.BLUE, Colors.BLUE, Colors.RED, Colors.ORANGE};
		Colors[] expectedResult = {Colors.BLACK, Colors.BLACK, Colors.BLACK, Colors.BLACK, Colors.SILVER};
		
		Colors[] result = _peaceOfMind.compareColors(systemColorsMoreThanOneDuplicateButNotAll, userGuess);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void compareColorAtleaseOneAndNotAllMatchAndPositionMatchesMoreThanOneDuplicateButNotAll()
	{
		Colors[] userGuess      = {Colors.GREEN, Colors.CYAN, Colors.CYAN, Colors.BLUE, Colors.VIOLET, Colors.CYAN};
		Colors[] expectedResult = {Colors.BLACK, Colors.BLACK};
		
		Colors[] result = _peaceOfMind.compareColors(systemColorsMoreThanOneDuplicateButNotAll, userGuess);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void compareColorAtleaseOneAndNotAllMatchAndSomePositionMatchesMoreThanOneDuplicateButNotAll()
	{
		Colors[] userGuess      = {Colors.GREEN, Colors.WHITE, Colors.RED, Colors.VIOLET, Colors.VIOLET, Colors.CYAN};
		Colors[] expectedResult = {Colors.BLACK, Colors.BLACK, Colors.SILVER};
		
		Colors[] result = _peaceOfMind.compareColors(systemColorsMoreThanOneDuplicateButNotAll, userGuess);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void compareColorMatchAndPostionMatchAllDuplicate()
	{
		Colors[] userGuess      = {Colors.RED, Colors.RED, Colors.RED, Colors.RED, Colors.RED, Colors.RED};
		Colors[] expectedResult = {Colors.BLACK, Colors.BLACK, Colors.BLACK, Colors.BLACK, Colors.BLACK, Colors.BLACK};
		
		Colors[] result = _peaceOfMind.compareColors(systemColorsAllDuplicate, userGuess);
		assertArrayEquals(expectedResult, result);
	}
		
	@Test
	public void compareColorAtleaseOneAndNotAllMatchAndPositionAllDuplicate()
	{
		Colors[] userGuess      = {Colors.RED, Colors.RED, Colors.RED, Colors.BLUE, Colors.VIOLET, Colors.CYAN};
		Colors[] expectedResult = {Colors.BLACK, Colors.BLACK, Colors.BLACK};
		
		Colors[] result = _peaceOfMind.compareColors(systemColorsAllDuplicate, userGuess);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void compareColorUserGuessesLessThanSixColors()
	{
		Colors[] userGuess = {Colors.GREEN, Colors.RED, Colors.YELLOW, Colors.BLUE, Colors.ORANGE};		
		try
		{
			Colors[] result = _peaceOfMind.compareColors(systemColorsDistinct, userGuess);
			fail("Expected exception since user guessed less than six colors");
		}
		catch (PeaceOfMindGameException e) { }
	}

	@Test
	public void userGuessesBeforeSystemColorIsGenerated()
	{
		Colors[] systemGeneratedColors = {};
		Colors[] userGuess = {Colors.GREEN, Colors.RED, Colors.YELLOW, Colors.BLUE, Colors.ORANGE, Colors.BLUE};
		
		try
		{
			Colors[] result = _peaceOfMind.compareColors(systemGeneratedColors, userGuess);
			fail("Expected exception since user guessed before system color was generated");
		}
		catch (PeaceOfMindGameException e) 
		{
			
		}
	}
	
	@Test
	public void checkGameWon()
	{
		_peaceOfMind.setSystemColor(systemColorsDistinct);
		Colors[] userGuess = systemColorsDistinct;
		_peaceOfMind.evaluateUserGuess(userGuess);
		assertTrue(_peaceOfMind.isGameOver());		
	}
	
	@Test
	public void checkGameNotOverAfter5FailedTries()
	{
		_peaceOfMind.setSystemColor(systemColorsDistinct);
		Colors[] userGuess = {Colors.GREEN, Colors.RED, Colors.YELLOW, Colors.BLUE, Colors.ORANGE, Colors.ORANGE};
		for(int numberofTries = 0; numberofTries < 5; numberofTries++)
		{
			_peaceOfMind.evaluateUserGuess(userGuess);
		}
		assertFalse(_peaceOfMind.isGameOver());	
	}
	
	@Test
	public void checkGameWonAfter7Tries()
	{
		_peaceOfMind.setSystemColor(systemColorsDistinct);
		Colors[] userGuess = {Colors.GREEN, Colors.RED, Colors.YELLOW, Colors.BLUE, Colors.ORANGE, Colors.ORANGE};
		for(int numberofTries = 0; numberofTries < 6; numberofTries++)
		{
			_peaceOfMind.evaluateUserGuess(userGuess);
		}
		userGuess = systemColorsDistinct;
		_peaceOfMind.evaluateUserGuess(userGuess);
		assertTrue(_peaceOfMind.isGameOver());	
	}
	
	@Test
	public void checkGameOverLost()
	{
		_peaceOfMind.setSystemColor(systemColorsDistinct);
		Colors[] userGuess = {Colors.GREEN, Colors.RED, Colors.YELLOW, Colors.BLUE, Colors.ORANGE, Colors.ORANGE};
		for(int numberofTries = 0; numberofTries < 20; numberofTries++)
		{
			_peaceOfMind.evaluateUserGuess(userGuess);
		}
		assertTrue(_peaceOfMind.isGameLost());		
	}
	
	@Test
	public void checkNumberOfTriesExcededLimit()
	{
		_peaceOfMind.setSystemColor(systemColorsDistinct);
		Colors[] userGuess = {Colors.GREEN, Colors.RED, Colors.YELLOW, Colors.BLUE, Colors.ORANGE, Colors.ORANGE};
		for(int numberofTries = 0; numberofTries < 20; numberofTries++)
			_peaceOfMind.evaluateUserGuess(userGuess);
		
		try
		{
			_peaceOfMind.evaluateUserGuess(systemColorsDistinct);
			fail("Expected exception since user tried for more than the maximum limit of tries");
		}
		catch (PeaceOfMindGameException e) 
		{
		}
	}

	@Test
	public void colorGeneratedForBeginnnerLevel()
	{
		_peaceOfMind.generateSystemColor(Mode.BEGINNER);
		ArrayList<Colors> result = _peaceOfMind.getSystemGeneratedColors();
		HashSet<Colors> mp = new HashSet<Colors>();
		mp.addAll(result);
		assertTrue(mp.size() == 6);
	}

	@Test
	public void colorGeneratedForAdvancedLevel()
	{
		_peaceOfMind.generateSystemColor(Mode.ADVANCED);
		ArrayList<Colors> result = _peaceOfMind.getSystemGeneratedColors();
		assertTrue(result.size() == 6);
	}
	
	@Test
	public void colorGeneratedForIntermediateLevel()
	{
		_peaceOfMind.generateSystemColor(Mode.INTERMEDIATE);
		ArrayList<Colors> result = _peaceOfMind.getSystemGeneratedColors();
		HashSet<Colors> mp = new HashSet<Colors>();
		mp.addAll(result);
		assertTrue(mp.size() == 6 || mp.size() == 5);
	}
}
