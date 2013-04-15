package gui;

import game.Colors;
import game.Mode;
import game.PeaceOfMind;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class PeaceOfMindGui extends JFrame implements ActionListener {

	private Color colorArray[] = {Color.white, Color.orange, Color.red, Color.yellow, Color.blue, Color.green, Color.cyan, 
			new Color(148,0,211), Color.pink, new Color(128,64,0)};
	private String gameLevelArray[] = {"Beginner", "Intermediate", "Advanced"};
	
	private static final long serialVersionUID = 1L;
	
	private PeaceOfMind _peaceOfMind;
	private Mode mode;
	private  int numberOfTries = 0;
	private  final int MAX_NUMBER_OF_TRIES = 20;
	private Colors[] userGuessedColor;
	
	private JRadioButton[] radioButtonLevel;
	private JButton buttonSelectMode;
	private JPanel gameLevelSelectorPanel;
	private JPanel gamePlayPanel;
	
	private JPanel gamePlaylUserGuessPanel;
	private JPanel gamePlaylResultPanel;
	private JPanel gamePlaylInfoPanel;
	private JLabel gamePlayNumberofTriesLeft;
	private JTextField textNumberOfTriesLeft;
	private JButton gamePlayUserGuess;
	private JButton gamePlayExitGame;
	private JLabel gamePlayResultLabel;
	private JTextField[] gamePlayResult;
	private JButton[] userGuessButton;
	private JPanel gamePlayModeDisplayPanel;
	private JLabel gamePlayModeLabel;
	private JLabel label;
	
	private JPanel colorPalletePanel;
	private JButton[] colorPalleteButton;
	
	private int indexOfUserGuess;
	
	public PeaceOfMindGui() 
	{
		getContentPane().setLayout(null);		
		_peaceOfMind = new PeaceOfMind();
		userGuessedColor = new Colors[6];
		setUpGame();
	}
	
	private void setUpGame()
	{
		setPeaceOfMindFrameProperties();
		setUpGameLevelSelectorPanel();
		setUpGamePanel();
		setUpColorPallettePanel();
	}

	private void setPeaceOfMindFrameProperties()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 436, 347);
		this.setTitle("Peace of Mind Game");
	}
	
	private void setUpColorPallettePanel()
	{
		colorPalleteButton = new JButton[10];		
		colorPalletePanel = new JPanel();
		setUpGuiComponents(colorPalletePanel, new Rectangle(33, 157, 358, 83), true, null, null, null, BorderFactory.createRaisedBevelBorder());
		
		for(int indexOfColor = 0; indexOfColor<10; indexOfColor++)
		{
			colorPalleteButton[indexOfColor] = new JButton();
			setUpGuiComponents(colorPalleteButton[indexOfColor], new Rectangle(10+indexOfColor*34, 20, 31, 46), false, null, colorArray[indexOfColor], null, null);
			colorPalletePanel.add(colorPalleteButton[indexOfColor]);
			colorPalleteButton[indexOfColor].addActionListener(this);
		}		
	}
	
	private void setUpGameLevelSelectorPanel()
	{
		gameLevelSelectorPanel = new JPanel();
		setUpGuiComponents(gameLevelSelectorPanel, new Rectangle(74, 35, 269, 226), true, null, null, null, BorderFactory.createRaisedBevelBorder());
		getContentPane().add(gameLevelSelectorPanel);
		
		radioButtonLevel = new JRadioButton[3];				
		for(int indexOfGameLevel = 0; indexOfGameLevel < 3; indexOfGameLevel++)
		{
			radioButtonLevel[indexOfGameLevel] = new JRadioButton(gameLevelArray[indexOfGameLevel]);
			
			setUpGuiComponents(radioButtonLevel[indexOfGameLevel], new Rectangle(53, 64+indexOfGameLevel*36, 154, 23), 
					false, Color.BLACK, null, new Font("Courier New", Font.BOLD, 15), null);
			
			radioButtonLevel[indexOfGameLevel].addActionListener(this);
			gameLevelSelectorPanel.add(radioButtonLevel[indexOfGameLevel]);
		}
		radioButtonLevel[0].setSelected(true);
		mode = Mode.BEGINNER;
				
		buttonSelectMode = new JButton("Submit");
		setUpGuiComponents(buttonSelectMode, new Rectangle(149, 181, 98, 23), false, null, null, null, null);
		buttonSelectMode.addActionListener(this);
		gameLevelSelectorPanel.add(buttonSelectMode);
		
		JLabel lblSelectGameLevel = new JLabel("Select Game Level");
		setUpGuiComponents(lblSelectGameLevel, new Rectangle(26, 23, 213, 23), false, Color.RED, null, new Font("Courier New", Font.BOLD, 20), null);
		gameLevelSelectorPanel.add(lblSelectGameLevel);		
	}
	
	private void setUpGamePanel()
	{
		gamePlayPanel = new JPanel();
		setUpGuiComponents(gamePlayPanel, new Rectangle(10, 11, 400, 291), true, null, Color.BLUE, null, BorderFactory.createLoweredSoftBevelBorder());
		
		setUpGamePlayUserGuessPanel();
		setUpGamePlayResultPanel();			
		setUpGamePlayInformationPanel();
		setUpGamePlayModeInformationPanel();			
	}
	
	private void setUpGamePlayModeInformationPanel() 
	{
		gamePlayModeDisplayPanel = new JPanel();
		setUpGuiComponents(gamePlayModeDisplayPanel, new Rectangle(10, 11, 380, 32), false, null, null, null, null);
		gamePlayPanel.add(gamePlayModeDisplayPanel);
		
		gamePlayModeLabel = new JLabel("CHOSEN MODE");
		setUpGuiComponents(gamePlayModeLabel, null, false, Color.RED, null, new Font("Courier New", Font.BOLD, 20), null);
		gamePlayModeDisplayPanel.add(gamePlayModeLabel);
	}

	private void setUpGamePlayResultPanel() 
	{
		gamePlaylResultPanel = new JPanel();
		setUpGuiComponents(gamePlaylResultPanel, new Rectangle(10, 142, 380, 89), false, null, null, null, null);
		gamePlayPanel.add(gamePlaylResultPanel);
		gamePlaylResultPanel.setLayout(null);
		
		gamePlayResultLabel = new JLabel("RESULT");
		setUpGuiComponents(gamePlayResultLabel, new Rectangle(148, 11, 81, 18), false, Color.GREEN, null, new Font("Courier New", Font.BOLD, 20), null);
		gamePlaylResultPanel.add(gamePlayResultLabel);		

		gamePlayResult = new JTextField[6];		
		for(int i = 0; i < 6; i++)
		{
			gamePlayResult[i] = new JTextField();
			setUpGuiComponents(gamePlayResult[i], new Rectangle(20+i*60, 41, 40, 33), false, null, Color.WHITE, null, null);
			gamePlayResult[i].setEnabled(false);
			gamePlayResult[i].setColumns(10);
			gamePlayResult[i].setVisible(false);
			gamePlaylResultPanel.add(gamePlayResult[i]);
		}
	}

	private void setUpGamePlayUserGuessPanel() 
	{
		gamePlaylUserGuessPanel = new JPanel();
		setUpGuiComponents(gamePlaylUserGuessPanel, new Rectangle(10, 48, 380, 89), true, null, null, null, null);
		gamePlayPanel.add(gamePlaylUserGuessPanel);
		
		label = new JLabel("Choose Color");
		setUpGuiComponents(label, new Rectangle(110, 11, 150, 18), false, Color.BLUE, null, new Font("Courier New", Font.BOLD, 20), null);
		gamePlaylUserGuessPanel.add(label);
		
		userGuessButton = new JButton[6];
		for(int indexOfUserGuess = 0 ; indexOfUserGuess< userGuessButton.length; indexOfUserGuess++)
		{
			userGuessButton[indexOfUserGuess] = new JButton("click");
			setUpGuiComponents(userGuessButton[indexOfUserGuess], new Rectangle(2 + indexOfUserGuess *63, 43, 62, 23), 
					false, null, new Color(250, 250, 210), null, null);
			
			gamePlaylUserGuessPanel.add(userGuessButton[indexOfUserGuess]);
			userGuessButton[indexOfUserGuess].addActionListener(this);
		}
	}

	private void setUpGamePlayInformationPanel() 
	{
		gamePlaylInfoPanel = new JPanel();
		setUpGuiComponents(gamePlaylInfoPanel, new Rectangle(10, 239, 380, 46), false, null, null, null, null);
		gamePlayPanel.add(gamePlaylInfoPanel);
		gamePlaylInfoPanel.setLayout(null);
		
		gamePlayNumberofTriesLeft = new JLabel("Number of tries left");
		setUpGuiComponents(gamePlayNumberofTriesLeft, new Rectangle(10, 6, 110, 30), false, null, null, null, null);
		gamePlaylInfoPanel.add(gamePlayNumberofTriesLeft);
		
		textNumberOfTriesLeft = new JTextField();
		setUpGuiComponents(textNumberOfTriesLeft, new Rectangle(124, 11, 30, 20), false, null, null, null, null);
		textNumberOfTriesLeft.setEnabled(false);
		gamePlaylInfoPanel.add(textNumberOfTriesLeft);
		textNumberOfTriesLeft.setColumns(8);
		textNumberOfTriesLeft.setText(Integer.toString(MAX_NUMBER_OF_TRIES));
		
		gamePlayUserGuess = new JButton("Guess");
		setUpGuiComponents(gamePlayUserGuess, new Rectangle(189, 8, 74, 27), false, null, null, null, null);
		gamePlayUserGuess.addActionListener(this);
		gamePlaylInfoPanel.add(gamePlayUserGuess);
		
		gamePlayExitGame = new JButton("Exit Game");
		setUpGuiComponents(gamePlayExitGame, new Rectangle(273, 8, 95, 27), false, null, null, null, null);
		gamePlayExitGame.addActionListener(this);
		gamePlaylInfoPanel.add(gamePlayExitGame);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("Beginner"))
		{
			mode = Mode.BEGINNER;
			setRadioButtonSelected(Arrays.asList(Mode.values()).indexOf(mode));
		}
		else if(e.getActionCommand().equals("Intermediate"))
		{
			mode = Mode.INTERMEDIATE;
			setRadioButtonSelected(Arrays.asList(Mode.values()).indexOf(mode));
		}
		else if(e.getActionCommand().equals("Advanced"))
		{
			mode = Mode.ADVANCED;
			setRadioButtonSelected(Arrays.asList(Mode.values()).indexOf(mode));
		}
		else if(e.getActionCommand().equals("Submit"))
		{
			_peaceOfMind.generateSystemColor(mode);
			getContentPane().removeAll();
			getContentPane().add(gamePlayPanel);
			gamePlayModeLabel.setText(mode.toString().toUpperCase());
		}	
		else if(e.getActionCommand().equals("Guess"))
		{
			if(isAllColorsSelectedByUser())
			{
				if(numberOfTries < 20)
				{
					numberOfTries++;
					textNumberOfTriesLeft.setText(Integer.toString(MAX_NUMBER_OF_TRIES - numberOfTries));
					showResult(_peaceOfMind.evaluateUserGuess(userGuessedColor));
					if(_peaceOfMind.isGameOver())
					{
						showColorGeneratedBySystem(true);
					}
				}
				else if(_peaceOfMind.isGameLost())
				{
					showColorGeneratedBySystem(false);
				}
			}
			else { JOptionPane.showMessageDialog(this, "Please Guess all the colors", "Color Not Guessed", JOptionPane.OK_OPTION, null); }
		}
		else if(e.getActionCommand().equals("Exit Game")) { showColorGeneratedBySystem(false); }
		else
		{
			for(int indexOfGuess = 0; indexOfGuess < 6; indexOfGuess++)
			{
				if(e.getSource().equals(userGuessButton[indexOfGuess]))
				{				
					getContentPane().add(colorPalletePanel);
					getContentPane().setComponentZOrder(colorPalletePanel, 0);
					indexOfUserGuess = indexOfGuess;
					break;
				}
			}
			
			for(int indexOfChosenColor = 0; indexOfChosenColor < 10; indexOfChosenColor++)
			{
				if(e.getSource().equals(colorPalleteButton[indexOfChosenColor]))
				{				
					userGuessButton[indexOfUserGuess].setBackground(colorArray[indexOfChosenColor]);
					userGuessedColor[indexOfUserGuess] = Colors.values()[indexOfChosenColor];					
					getContentPane().remove(colorPalletePanel);
					break;
				}
			}
		}
		repaint();
	}

	private void setRadioButtonSelected(int index) 
	{
		for(int i = 0; i< 3 ; i++)
			if(i == index)
				radioButtonLevel[i].setSelected(true);
			else
				radioButtonLevel[i].setSelected(false);
	}
	
	private void showColorGeneratedBySystem(boolean isGamewon)
	{
		ArrayList<Colors> systemColors = _peaceOfMind.getSystemGeneratedColors();
		Color[] systemColorAWT = getSystemColorAWT(systemColors.toArray());
		SystemGeneratedColorRevealor revealor = new SystemGeneratedColorRevealor(systemColorAWT, isGamewon);
		revealor.setAlwaysOnTop(true);
		revealor.setVisible(true);
		setVisible(false);
	}
	
	private Color[] getSystemColorAWT(Object[] systemColors)
	{
		Color[] systemColorAWT = new Color[6];
		for(int i = 0; i < systemColors.length; i++)
		{
			int index = Arrays.asList(Colors.values()).indexOf((Colors)systemColors[i]);
			systemColorAWT[i] = colorArray[index];
		}
		return systemColorAWT;
	}
	
	private void showResult(Colors[] result)
	{
		for (int i = 0; i < 6; i++)
		{
			gamePlayResult[i].setVisible(false);
			gamePlayResult[i].setBackground(Color.white);
		}
		
		for (int i = 0; i < result.length; i++) 
		{
			if(result[i].equals(Colors.BLACK))
			{
				gamePlayResult[i].setBackground(Color.black);
			}
			else if(result[i].equals(Colors.SILVER))
			{
				gamePlayResult[i].setBackground(new Color(220,220,220));
			}
			gamePlayResult[i].setVisible(true);			
		}
		
		repaint();
	}
	
	private boolean isAllColorsSelectedByUser()
	{
		for(int i = 0; i < 6; i++)
			if(!Arrays.asList(colorArray).contains(userGuessButton[i].getBackground()))
				return false;
		
		return true;
	}

	private void setUpGuiComponents(JComponent component, Rectangle _bounds, boolean _setlayout, Color _foreGround, Color _backGround, Font _font, Border _border)
	{
		if(_bounds != null)    { component.setBounds(_bounds); 		   }
		if(_setlayout)         { component.setLayout(null); 		   }
		if(_foreGround!=null)  { component.setForeground(_foreGround); }
		if(_backGround != null){ component.setBackground(_backGround); }
		if(_font != null)      { component.setFont(_font); 			   }
		if(_border != null)    { component.setBorder(_border); 		   }
	}
}
