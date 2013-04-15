package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Field;

import javax.swing.*;
import javax.swing.border.LineBorder;

import shapes.*;
import diagram.*;
import drawShape.*;

public class Canvas extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private JPanel propertyPanel;
	private JPanel shapeChoserPanel;
	private JLabel propertyLabel;
	private DrawPanel drawingCanvasPanel;
	
	private JButton[] shapeButtons;

	JMenuItem menu;

	private Diagram diagram;
	private ShapePropertyFactory factory;
	
	private ArrayList<JTextField> propertiesField;
	private ArrayList<JLabel> propertiesLabel;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem[] fileMenuItems;
	
 	public static void main(String[] args) 
	{
		Canvas window = new Canvas();
		window.setVisible(true);
	}
	
	public Canvas() {
		setTitle("Draw Shapes");
		initialize();
	}

	private void initialize() 
	{
		try 
		{
			factory = new ShapePropertyFactory();
		}
		catch (Exception e) 
		{
			throw new ShapeReflectionException(e);
		}
		
		setUpShapeChoserPanel();
		setUpDrawingCanvasPanel();
		setUpPropertyPanel();
		setUpFileAboutMenu();
		setUpDrawFrame();
		diagram = new Diagram();
	}
	
	private void setUpShapeChoserPanel()
	{
		shapeChoserPanel = new JPanel();
		shapeChoserPanel.setBorder(
		  new LineBorder(new Color(0, 0, 0), 2));
		shapeChoserPanel.setBounds(450, 34, 134, 227);
		shapeChoserPanel.setLayout(null);
		
		setUpShapeButtons();
	}
	
	private void setUpShapeButtons()
	{
		Object[] classNames = factory.getClassNames();
		shapeButtons = new JButton[classNames.length];
		
		for(int i = 0; i < shapeButtons.length; i++)
		{
			shapeButtons[i] = new JButton((String)classNames[i]);
			shapeButtons[i].setBounds(10, 11+40*i, 114, 32);
			shapeButtons[i].addActionListener(this);
			shapeChoserPanel.add(shapeButtons[i]);
		}
	}
	
	private void setUpPropertyPanel()
	{
		propertyPanel = new JPanel();
		propertyPanel.setBorder(
	      new LineBorder(new Color(0, 0, 0), 2));
		
		propertyPanel.setBounds(450, 265, 134, 219);
		propertyPanel.setLayout(null);
		
		propertyLabel = new JLabel("Property Manager");
		propertyLabel.setFont(
		  new Font("Times New Roman", Font.BOLD, 14));
		
		propertyLabel.setBounds(10, 11, 114, 18);
		propertyPanel.add(propertyLabel);
		
		propertiesField = new ArrayList<JTextField>();
		propertiesLabel = new ArrayList<JLabel>();
	}
		
	private void setUpDrawingCanvasPanel()
	{	
		drawingCanvasPanel = new DrawPanel();
	}

	private void setUpDrawFrame()
	{
		setBounds(100, 100, 607, 528);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		getContentPane().add(shapeChoserPanel);
		getContentPane().add(drawingCanvasPanel);
		getContentPane().add(propertyPanel);
		getContentPane().add(menuBar);
	}

	private void setUpFileAboutMenu()
	{
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 594, 25);
		createFileMenu();
		menuBar.add(fileMenu);
	}
	
	private void createFileMenu()
	{
		fileMenu = new JMenu("File");
		fileMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
		menuBar.add(fileMenu);
		fileMenuItems = new JMenuItem[3];
		
		fileMenuItems[0] = new JMenuItem("Save");
		fileMenuItems[1] = new JMenuItem("Load");
		fileMenuItems[2] = new JMenuItem("Exit");
		
		for(int i = 0; i < 3; i++)
		{
			fileMenuItems[i].setFont(new Font(
			  "Segoe UI", Font.BOLD, 14));
			
			fileMenu.add(fileMenuItems[i]);
			fileMenuItems[i].addActionListener(this);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{		
		if(e.getSource() instanceof JTextField)
		{
			try 
			{
				modifySelectedShapeProperties(
				  (JTextField)e.getSource());
			} 
			catch (Exception e1)
			{
				throw new 
				  ShapeReflectionException(new Exception());
			}
		}
		else if(e.getSource() instanceof JButton)
		{
			createNewShapeonCanvas(e.getActionCommand());
		}
		else
		{
			String command = e.getActionCommand();
			menuCommands(command);
		}
	}
	
	public void menuCommands(String command)
	{
		if(command.equalsIgnoreCase("Exit"))
		{
			dispose();
		}
		else if(command.equalsIgnoreCase("Load"))
		{
			loadSavedDiagram();
		}
		else if(command.equalsIgnoreCase("Save"))
		{
			saveCurrentDiagram();
		}
	}
	
	public void loadSavedDiagram()
	{
		JFileChooser chooser = new JFileChooser("..");
	    chooser.setDialogTitle("Load");
	    chooser.setApproveButtonText("Load");
	    
	    if(chooser.showOpenDialog(this) != 
	      JFileChooser.APPROVE_OPTION)
	    	
	      return;

	    try 
	    {
	       String fileName = chooser.getSelectedFile().getPath();	
           diagram = Diagram.loadDiagram(fileName);
	    }
	    catch(IOException e)
	    {
	      JOptionPane.showMessageDialog(this, 
	        "Could not load diagram "+e.getMessage());
	    } catch (ClassNotFoundException e) 
	    {
	    	JOptionPane.showMessageDialog(this, 
	    	        "Could not load diagram "+e.getMessage());
		}
	}
	public void saveCurrentDiagram()
	{
	    JFileChooser chooser = new JFileChooser("..");
	    chooser.setDialogTitle("Save");
	    chooser.setApproveButtonText("Save");
	    
	    if(chooser.showSaveDialog(this) != 
	      JFileChooser.APPROVE_OPTION)
	    	
	      return;

	    try 
	    {
	       String fileName = chooser.getSelectedFile().getPath();	
           diagram.saveDiagram(fileName);
	    }
	    catch(IOException e)
	    {
	      JOptionPane.showMessageDialog(this, 
	        "Could not save diagram "+e.getMessage());
	    }                
	  }

	private void createNewShapeonCanvas(String shapeName)
	{
		drawingCanvasPanel.removeAll();
		try 
		{
			IShape shape = factory.getShapeObject(shapeName);
			
			Object[] fields = factory.getFields(shape);
			Object[] values = factory.getValues(shape);
			
			createPropertyList(fields);
			
			for(int i = 0; i < values.length; i++)
				propertiesField.get(i)
				  .setText(values[i].toString()); 
			
			diagram.setSelectedImageByDefault(shape);
		} 
		catch ( Exception e)
		{
			throw new ShapeReflectionException(e);
		}
	}
	
	private void modifySelectedShapeProperties(JTextField textfield) 
	  throws Exception
	{
		try 
		{
			String value = textfield.getText();
			if(checkTextField(value))
				setValueIntoObject(textfield);
			else
				putLastValueBackIntoTextField(textfield);
		} 
		catch (Exception e) 
		{
			throw new ShapeReflectionException(
			  new Exception());
		}
	}

	public void setValueIntoObject(JTextField textfield)
			throws IllegalAccessException {
		Class<?> objectClass = diagram
		  .getSelectedShapes().get(0).getClass();
				
		Field[] fields = objectClass.getDeclaredFields();
		for(int i = 0; i < fields.length; i++)
		{
			fields[i].setAccessible(true);
			if(fields[i].getName()
			  .equalsIgnoreCase(textfield.getName()))
			{
				Integer val = (int)Double
				  .parseDouble(textfield.getText().trim());
				
				fields[i].set(diagram
				  .getSelectedShapes().get(0), val);
				
				textfield.setText(val.toString());
			}
		}
	}

	public void putLastValueBackIntoTextField(JTextField textfield)
	  throws Exception {
		Object[] fields = factory.getFields(
		  diagram.getSelectedShapes().get(0));
		
		Object[] values = factory.getValues(
		  diagram.getSelectedShapes().get(0));
		for(int i = 0; i < fields.length; i++)
		{
			String text = textfield.getName();
			String fieldName = fields[i].toString();
			System.out.println(text+ " : "+fieldName);
			
			if(fieldName.equalsIgnoreCase(text))
			{
				textfield.setText(values[i].toString());
			}	
		}
	}
	
	public boolean checkTextField(String value)
	{
		for(int i = 0; i < value.length(); i++)
		{
			if(Character.isDigit(value.charAt(0))  || 
			  value.charAt(i) == '.')
				continue;
			else
				return false;
				
		}
		return true;
	}
	
	public void updatePropertyPanel(IShape shape)
	{
		try
		{
			Object[] fields = factory.getFields(shape);
			createPropertyList(fields);
			Object[] values = factory.getValues(shape);
			
			for(int i = 0; i < values.length; i++)
			{
				propertiesField.get(i)
				  .setText(values[i].toString()); 
			}
			drawingCanvasPanel.repaint();
		} 
		catch (Exception e) 
		{
			throw new ShapeReflectionException(e);
		}
	}
	
	public void createPropertyList(Object[] fields) 
	{
		cleanPropertyPanel();
		for(int i = 0; i < fields.length; i++)
		{
			String propertyName = fields[i]
			  .toString().toUpperCase();
			
			propertiesLabel.add(new JLabel(propertyName));
			propertiesLabel.get(i).setBounds(10, 40+30*i, 50, 20);
			propertyPanel.add(propertiesLabel.get(i));
			
			propertiesField.add(new JTextField());
			propertiesField.get(i).setBounds(85, 40+30*i, 39, 20);
			propertiesField.get(i).setName(propertyName);
			propertiesField.get(i).addActionListener(this);
			propertyPanel.add(propertiesField.get(i));	
		}
		propertyPanel.repaint();
	}
	
	private void cleanPropertyPanel()
	{
		Component[] components = propertyPanel.getComponents();
		for(Component component : components) 
			if(!component.equals(propertyLabel))
				propertyPanel.remove(component);

		propertiesField.clear();
		propertiesLabel.clear();
		repaint();
	}
	
	private class DrawPanel extends JPanel 
	  implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		private int posX;
		private int posY;
		private JPopupMenu menu;
		private JMenuItem[] items;
		
		public DrawPanel()
		{
			setBorder(new LineBorder(new Color(0, 0, 0), 2));
			setBounds(10, 34, 430, 450);
			setLayout(null);
			setFocusable(true);
			requestFocusInWindow();
			addMouseListenerToPanel();
			addMouseMotionListenerToPanel();
			setUpJmenu(false, false, false);
		}

		public void addMouseListenerToPanel() {
			addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mousePressed(MouseEvent e)
				{
					if(e.getButton() == MouseEvent.BUTTON1)
						eventOnLeftMouseClick(e);
					else if(e.getButton() == MouseEvent.BUTTON3)
						eventOnRightMouseClick(e);

					repaint();
				}
				
			});
		}

		public void addMouseMotionListenerToPanel() {
			addMouseMotionListener(new MouseAdapter() 
			{
				@Override
				public void mouseDragged(MouseEvent e) 
				{
					ArrayList<IShape> selectedShape =
					  diagram.getSelectedShapes();
					
					if(selectedShape.size() == 1)
					{
						diagram.moveSelectedShape(e.getX() - posX, 
						  e.getY()-posY);
						
						updateProperty(selectedShape);
						setPosition(e);
					}
					repaint();
				}
			});
		}
		
		private void setUpJmenu(boolean delete, 
		  boolean group, boolean ungroup)
		{
			menu = new JPopupMenu();
			items = new JMenuItem[3];

			items[0] = new JMenuItem("Delete");
			items[0].setEnabled(delete);
			items[1] = new JMenuItem("Group");
			items[1].setEnabled(group);
			items[2] = new JMenuItem("UnGroup");
			items[2].setEnabled(ungroup);
			for(int i = 0; i < 3; i++)
			{
				items[i].addActionListener(this);
				menu.add(items[i]);
			}
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			setBorder(new LineBorder(new Color(0, 0, 0), 2));
			
			ArrayList<IShape> shapes = 
			  diagram.getListOfShapes();
			
			ArrayList<IShape> selctedShapes = 
			  diagram.getSelectedShapes();
			for(IShape shape: shapes)
			{
				g.setColor(Color.BLACK);
				drawShape(shape, g);
			}
			
			if(selctedShapes.size() > 0)
			{
				for(IShape shape: selctedShapes)
				{
					g.setColor(Color.RED);
					drawShape(shape, g);
				}
			}
		}
		
		private void drawShape(IShape shape, Graphics g)
		{
			try 
			{
				String classNameWtPackage = 
				  shape.getClass().getName();
				
				String[] pathToClass = 
				  classNameWtPackage.split("\\.");
				
				String drawClassName = "drawShape.Draw"
				  + pathToClass[pathToClass.length-1];

				IDrawShape drawShape;
				drawShape = (IDrawShape) 
				  Class.forName(drawClassName).newInstance();
				
				drawShape.draw(shape, g);
			}
			catch (Exception e) 
			{
				throw new ShapeReflectionException(e);
			}
		}
		
		private void setPosition(MouseEvent e)
		{
			posX = e.getX();
			posY = e.getY();
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();

			if(command.equalsIgnoreCase("group"))
			{
				diagram.makeGroups();
			}
			if(command.equalsIgnoreCase("delete"))
			{
				diagram.deleteShapeFromDiagram();
				cleanPropertyPanel();
			}
			if(command.equalsIgnoreCase("ungroup"))
			{
				diagram.unGroup();
			}
			repaint();
			propertyPanel.repaint();
		}
		
		private boolean isShapeAGroup(IShape shape)
		{
			return shape instanceof Group;
		}
		
		private void updateProperty(ArrayList<IShape> selectedShape)
		{
			if(selectedShape.size() == 1 
			  && !isShapeAGroup(selectedShape.get(0)))
			{
				updatePropertyPanel(selectedShape.get(0));
			}
			else
			{
				cleanPropertyPanel();
			}
		}
	
		private void eventOnLeftMouseClick(MouseEvent e)
		{
			setPosition(e);
			ArrayList<IShape> selectedShape = 
			  diagram.selectTheShape( e.getX(), e.getY(),
			  !e.isControlDown());
			
			updateProperty(selectedShape);
		}
		
		private void eventOnRightMouseClick(MouseEvent e)
		{
			ArrayList<IShape> selectedShape = 
			  diagram.getSelectedShapes();
			
			if(selectedShape.size() == 1)
			{
				if(isShapeAGroup(selectedShape.get(0)))
					setUpJmenu(true, false, true);
				else
					setUpJmenu(true, false, false);
			}
			else if(selectedShape.size() > 1)
				setUpJmenu(true, true, false);
			else
				setUpJmenu(false, false, false);

			menu.show(this, e.getX(), e.getY());
		}
	}
}

	
