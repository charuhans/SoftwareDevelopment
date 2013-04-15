package gui;




import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SystemGeneratedColorRevealor extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField[] textFieldResult;
	private JLabel colorCombinationLabel;
	private JButton okButton;

	public SystemGeneratedColorRevealor(Color[] systemColor, boolean isGameWon) 
	{
		textFieldResult = new JTextField[6];
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 418, 185);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		for(int i = 0; i < 6 ; i++)
		{
			textFieldResult[i] =  new JTextField();
			textFieldResult[i].setBounds(23 +60*i, 53, 50, 41);
			contentPane.add(textFieldResult[i]);
			textFieldResult[i].setEnabled(false);
			textFieldResult[i].setColumns(10);
			textFieldResult[i].setBackground(systemColor[i]);
		}
		
		colorCombinationLabel = new JLabel("Correct Color Combination");
		colorCombinationLabel.setForeground(Color.RED);
		colorCombinationLabel.setBackground(Color.WHITE);
		colorCombinationLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		colorCombinationLabel.setBounds(90, 14, 243, 28);
		contentPane.add(colorCombinationLabel);
		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		okButton.setBounds(152, 105, 89, 31);
		contentPane.add(okButton);
		
		if(isGameWon)
			setTitle("Game Won");
		else
			setTitle("Game Lost");
	}

	@Override
	public void actionPerformed(ActionEvent e) { System.exit(0); }
}
