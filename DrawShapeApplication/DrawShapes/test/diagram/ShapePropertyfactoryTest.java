package diagram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import shapes.Circle;
import shapes.IShape;

public class ShapePropertyfactoryTest
{
	ShapePropertyFactory factory;

	@Before
	public void setUp() throws Exception
	{
		factory = new ShapePropertyFactory();
	}
	
	@Test
	public void getShapeObject() throws Exception
	{
		Object obj = null;
		obj = factory.getShapeObject("Circle");
		
		assertEquals("shapes.Circle", 
		  obj.getClass().getName());
	}
	
	@Test
	public void getClassfromName()
	{
		Class<?> classShape = factory
		  .getClassFromName("Circle");
		
		Class<?> expectedclass = null;
		
		try 
		{
			expectedclass = Class.forName("shapes.Circle");
		} catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		assertTrue( expectedclass == classShape );
	}

	@Test
	public void getInstanceByName() throws Exception
	{
		Object shape = null;

		shape = factory
		  .getNewInstanceByClassName("Circle");
		
		assertEquals("shapes.Circle", 
		  shape.getClass().getName());
	}
	
	@Test
	public void getInstanceByWrongName() throws Exception
	{
		try
		{
			factory.getNewInstanceByClassName("NOCircle");
			fail();
		}
		catch(Exception e)
		{
			
		}
	}
	
	@Test
	public void getFields() throws Exception
	{
		IShape shape = new Circle(10,15,20);;
		Object[] fields;

		fields = factory.getFields(shape);
		assertTrue( "Radius".equals(fields[0]) 
		  && "X".equals(fields[1])
		  && "Y".equals(fields[2]));
	}
	
	@Test
	public void getSetOfClasses()
	{
		Object[] setOfClasses = factory.getClassNames();
		assertTrue(setOfClasses.length > 0);
	}
	
	@Test
	public void getClasses() throws Exception
	{
		factory.populateClassMap();
		assertTrue(true);
	}
	
	@Test
	public void getFieldValuesFromShape() throws Exception
	{
		Object[] values = factory.getValues(new Circle(10,10,10));
		assertTrue(10 == (int)values[0]);
	}

	@Test
	public void findClassesDirectoryDoesntExist() throws Exception
	{
		List<Class<?>> classes = factory
		  .findClasses(new File("nodir"), "shape");
		
		assertEquals(0, classes.size());
	}
	
	@Test
	public void addClassesToMapWhenNoClassesFound() throws Exception
	{
		ShapePropertyFactory mockFactory = 
		  new ShapePropertyFactory()
		{
			@Override
			public Class<?> addToListIfaClass(String packageName, 
					File file) throws ClassNotFoundException
		    {
			    return null;
			}
		};
		  
			mockFactory.populateClassMap();
			Class<?> classGot = mockFactory
			  .getClassFromName("Circle");
			
			assertEquals(null, classGot);
	}
	
	@Test
	public void addClassesToMapWhenDirectoryEmpty() throws Exception
	{
		ShapePropertyFactory mockFactory = 
		  new ShapePropertyFactory()
		{
			@Override
			public List<Class<?>> findClasses(File directory, 
			  String packageName) throws Exception
		    {
			    List<Class<?>> classes = new ArrayList<Class<?>>();
			    Class<?> classGot = addToListIfaClass(
			      packageName, directory);
			    
			    return classes;
			}
		};
		  
			mockFactory.populateClassMap();
			Class<?> classGot = mockFactory
			  .getClassFromName("Circle");
			
			assertEquals(null, classGot);
	}
	
	@Test
	public void getClassWithinDirectory() throws Exception
	{
		ShapePropertyFactory mockFactory = 
		  new ShapePropertyFactory()
		{
			int counter = 0;
			@Override
			public List<Class<?>> findClasses(File directory, 
			  String packageName) throws Exception
		    {
			    List<Class<?>> classes = 
			      new ArrayList<Class<?>>();
			    
			    counter++;
			    if(counter == 1)
			    {
			    	getClassesWithinDirectory(
			    	  packageName, directory);
			    }
			    return classes;
			}
		};
		  
			mockFactory.populateClassMap();
			Class<?> classGot = mockFactory
			  .getClassFromName("Circle");
			
			assertEquals(null, classGot);
	}
	
	@Test
	public void getClassWhenNoInterface() throws Exception
	{
		try
		{
		ShapePropertyFactory mockFactory =
		  new ShapePropertyFactory()
		{
			@Override
			public void populateClassMap() throws Exception
			{
				Class<?> shapeClass = Class.
				  forName("shapes.Circle");
				
				Class<?>[] interfaces = new Class<?>[0];
				
				getClassImplementingIShapeInterface(
				  "ShapePropertyFactory", shapeClass, interfaces);
			}
		};
		
		  	mockFactory.getClassFromName("Circle");
		  	fail("Test failed. Should have thrown exception");
		}
		catch(Exception e)
		{
			
		}
	}
	
	@Test
	public void getClassWhenIShapeNotImplemented() throws Exception
	{
		try
		{
		ShapePropertyFactory mockFactory =
		  new ShapePropertyFactory()
		{
			@Override
			public void populateClassMap() throws Exception
			{
				Class<?> shapeClass = Class.
				  forName("shapes.Circle");
				
				Class<?>[] interfaces = new Class<?>[1];
				interfaces[0] = Class.
				  forName("diagram.Diagram").getInterfaces()[0];
				
				getClassImplementingIShapeInterface(
				  "ShapePropertyFactory", shapeClass, interfaces);
			}
		};
		
		  	mockFactory.getClassFromName("Circle");
		  	fail("Test failed. Should have thrown exception");
		}
		catch(Exception e)
		{
			
		}
	}
}
