package game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class PeaceOfMind 
{
	private int numberOfTries;
	private Colors[] systemColors;
	private boolean gameOver;	
	
	public PeaceOfMind()
	{
		numberOfTries = 0;
		systemColors = new Colors[6];
	}
		
	public void generateSystemColor(Mode mode){
		
		ArrayList<Colors> listOfSystemColors;
		Random randomColorGenerator = new Random();
		int maxDistinctColorSize = 6;
		
		if(mode.equals(Mode.ADVANCED))
			listOfSystemColors = generateRandomColors();
		else
		{
			if(mode.equals(Mode.INTERMEDIATE))
				maxDistinctColorSize = randomColorGenerator.nextInt()%2 == 0 ? 6 : 5;

			listOfSystemColors = generateDistinctColors(maxDistinctColorSize);
		}
 
		setSystemColor(listOfSystemColors.toArray(new Colors[6]));
	}
		
	private ArrayList<Colors> generateDistinctColors(int maxSize)
	{
		ArrayList<Colors> listOfSystemColors = new ArrayList<Colors>();
		
		Random randomColorGenerator = new Random();
		int size = 0;
		Colors color;
		while(size < maxSize)
		{
			int pos = randomColorGenerator.nextInt(Colors.values().length-3);
			color = Colors.values()[pos];
			if(!listOfSystemColors.contains(color))
			{
				listOfSystemColors.add(color);
				size++;
			}
		}
		
		if(listOfSystemColors.size() < 6)
			listOfSystemColors.add(listOfSystemColors.get(randomColorGenerator.nextInt(listOfSystemColors.size())));
		
		return listOfSystemColors;
	}
	
	private ArrayList<Colors> generateRandomColors()
	{
		ArrayList<Colors> listOfSystemColors = new ArrayList<Colors>();
		
		Random randomColorGenerator = new Random();
		for(int size = 0; size < 6; size++)
		{
			int pos = randomColorGenerator.nextInt(Colors.values().length-3);
			listOfSystemColors.add(Colors.values()[pos]);
		}
		return listOfSystemColors;
	}
		
	public ArrayList<Colors> getSystemGeneratedColors()
	{
		return new ArrayList<Colors>(Arrays.asList(systemColors));
	}
		
 	public void setSystemColor(Colors[] newSystemColors)
	{
		checkSystemColorLength(newSystemColors);
		System.arraycopy(newSystemColors, 0, systemColors, 0, systemColors.length);
	}
	
	public Colors[] compareColors(Colors[] systemColors, Colors[] userGuess) 
	{
		checkNumberOfTries();
		checkUserGuessLength(userGuess);
		checkSystemColorLength(systemColors);
		
		Colors[] systemGeneratedColorsCopy = new Colors[6];;
		System.arraycopy(systemColors, 0, systemGeneratedColorsCopy, 0, systemColors.length);
		
		Colors[] userGuessColorsCopy = new Colors[6];;
		System.arraycopy(userGuess, 0, userGuessColorsCopy, 0, userGuess.length);		
		compareColorsExactPosition(systemGeneratedColorsCopy, userGuessColorsCopy);	
		compareColorsDifferentPosition(systemGeneratedColorsCopy, userGuessColorsCopy);
		return extractResultArray(userGuessColorsCopy);
	}

	public void checkNumberOfTries() 
	{
		if(numberOfTries < 20)
			numberOfTries++;
		else
			throw new PeaceOfMindGameException("Number of tries exceeded the maximum number of tries");
	}
	
	private void compareColorsExactPosition(Colors[] systemGeneratedColors, Colors[] userGuessedColors)
	{
		for(int index = 0; index < systemGeneratedColors.length; index++)
			if(userGuessedColors[index] == systemGeneratedColors[index])
			{
				userGuessedColors[index] = Colors.BLACK;
			}
	}
	
	private void compareColorsDifferentPosition(Colors[] systemGeneratedColors, Colors[] userGuessedColors)
	{		
		for (int index = 0; index < userGuessedColors.length; index++) 
		{
			if(Arrays.asList(userGuessedColors[index]).contains(Colors.BLACK))
				systemGeneratedColors[index] = Colors.EMPTY;
		}
		for(Colors color: userGuessedColors) 
		{
			if(Arrays.asList(systemGeneratedColors).contains(color))
			{
				systemGeneratedColors[Arrays.asList(systemGeneratedColors).indexOf(color)] = Colors.EMPTY;
				userGuessedColors[Arrays.asList(userGuessedColors).indexOf(color)] = Colors.SILVER;
			}
		}	
	}

	public Colors[] extractResultArray(Colors[] userGuessedColors) 
	{
		ArrayList<Colors> result = new ArrayList<Colors>();
		Collections.sort(Arrays.asList(userGuessedColors));
		for (Colors colors : userGuessedColors) 
			if(colors.equals(Colors.BLACK) ||colors.equals(Colors.SILVER))
				result.add(colors);	
		return result.toArray(new Colors[result.size()]);
	}
		
	public boolean isGameLost()
	{
		return (numberOfTries >= 20 && !isGameOver());
	}
		
	public Colors[] evaluateUserGuess(Colors[] userGuess)
	{
		Colors[] result = compareColors(systemColors, userGuess);	
		gameOver = !(result.length != 6 || Arrays.asList(result).contains(Colors.SILVER));
		return result;
	}

	private void checkSystemColorLength(Colors[] systemColors) 
	{
		if(systemColors.length != 6) 
			throw new PeaceOfMindGameException("Generated system colors does not match expected length"); 
	}
	
	private void checkUserGuessLength(Colors[] userGuess) 
	{
		if(userGuess.length != 6) 
			throw new PeaceOfMindGameException("User guess length does not match the expected length"); 
	}

	public boolean isGameOver()
	{
		return gameOver;
	}
}
