package game;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class VenkatsModifiedTest {
  int[] distinctColors = new int[] {0, 1, 2, 3, 4, 5};
  int[] distinctColorsShiftedLeft = new int[] {1, 2, 3, 4, 5, 0};
  int[] duplicatedTarget = new int[] {0, 1, 2, 3, 4, 1};
  
  int[] allBlack = new int[] {7, 7, 7, 7, 7, 7};
  int[] allSilver = new int[] {8, 8, 8, 8, 8, 8};
  
  @Test public void testPerfectMatch() {
    int[] result = matchColors(distinctColors, distinctColors);
    assertArrayEquals(allBlack, result);
  }

  @Test public void testMatchColorsButNotPosition() {
    int[] result = matchColors(distinctColors, distinctColorsShiftedLeft);
    assertArrayEquals(allSilver, result);
  }
  
  @Test public void testMatchGuessWithAllOneColor() {
    int[] result = matchColors(distinctColors, new int[] {1, 1, 1, 1, 1, 1});
    assertArrayEquals(new int[] {7, -1, -1, -1, -1, -1}, result);    
  }

  @Test public void testMatchGuessOnePosition() {
    int[] result = matchColors(distinctColors, new int[] {6, 1, 6, 6, 6, 6});
    assertArrayEquals(new int[] {7, -1, -1, -1, -1, -1}, result);    
  }

  @Test public void testMatchGuessOneNonPosition() {
    int[] result = matchColors(distinctColors, new int[] {1, 6, 6, 6, 6, 6});
    assertArrayEquals(new int[] {8, -1, -1, -1, -1, -1}, result);    
  }

  @Test public void testMatchGuessOneNonPositionWithDuplicatedGuess() {
    int[] result = matchColors(distinctColors, new int[] {1, 6, 1, 6, 6, 6});
    assertArrayEquals(new int[] {8, -1, -1, -1, -1, -1}, result);    
  }

  @Test public void testMatchGuessOnePositionWithDuplicatedGuess() {
    int[] result = matchColors(distinctColors, new int[] {6, 1, 1, 6, 6, 6});
    assertArrayEquals(new int[] {7, -1, -1, -1, -1, -1}, result);    
  }

  @Test public void testMatchGuessOnePositionWithDuplicatedTarget() {
    int[] result = matchColors(duplicatedTarget, 
      new int[] {1, 1, 6, 6, 6, 6});
    assertArrayEquals(new int[] {7, 8, -1, -1, -1, -1}, result);    
  }
  
  @Test public void testMatchGuessTwoPositionWithDuplicatedTarget() {
    int[] result = matchColors(duplicatedTarget, 
      new int[] {6, 1, 6, 6, 6, 1});
    assertArrayEquals(new int[] {7, 7, -1, -1, -1, -1}, result);    
  }

  //Since each pair of students may use a different interface, below is the
  //code to map the tests to the appropriate interfaces created by students.

  private PeaceOfMind pieceOfMindLogic;
  
  List<Colors> colors =  Arrays.asList( Colors.RED, Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.YELLOW, Colors.VIOLET, Colors.CYAN, Colors.BLACK, Colors.SILVER);	

  @Before
  public void initialize() {
    pieceOfMindLogic = new PeaceOfMind();    
  }
  
  public int[] matchColors(int[] targetColors, int[] guessColors) {
    List<Colors> guessInput = new ArrayList<>();
    List<Colors> targetInput = new ArrayList<>();

    for (int i = 0; i < targetColors.length; i++)
    {
     guessInput.add(colors.get(guessColors[i]));
     targetInput.add(colors.get(targetColors[i]));
    }

    Colors[] targetInputArray = targetInput.toArray(new Colors[6]);
    Colors[] guessInputArray = guessInput.toArray(new Colors[6]);
    List<Colors> result = Arrays.asList(
      pieceOfMindLogic.compareColors(
        targetInputArray, guessInputArray));

    for(Colors clr : result) {
      System.out.print(clr + " ");
    }
    System.out.println("");
    int[] resultColors = new int[] {-1, -1, -1, -1, -1, -1};
    for(int i = 0; i < result.size(); i++) 
    {
        resultColors[i] = colors.indexOf(result.get(i));
    }

    return resultColors;
  }

}