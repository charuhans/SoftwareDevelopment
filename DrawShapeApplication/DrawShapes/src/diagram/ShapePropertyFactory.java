package diagram;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import drawShape.ShapeReflectionException;

import shapes.IShape;

public class ShapePropertyFactory 
{
	private HashMap<String, Class<?>> classMap;
	public  Map<Class<?>, Class<?>> map;
	
	public ShapePropertyFactory() throws Exception
	{
		populateClassMap();
		populatePrimitiveMap();
	}
	
	public void populatePrimitiveMap()
	{
		map = new HashMap<Class<?>, Class<?>>();
	    map.put(boolean.class, Boolean.TYPE);
	    map.put(byte.class, Byte.TYPE);
	    map.put(short.class, Short.TYPE);
	    map.put(char.class, Character.TYPE);
	    map.put(int.class, Integer.TYPE);
	    map.put(long.class, Long.TYPE);
	    map.put(float.class, Float.TYPE);
	    map.put(double.class, Double.TYPE);
	}
	
	public Class<?> getClassFromName(String name)
	{
		return classMap.get(name);
	}
	
	public Object getNewInstanceByClassName(String name) 
	  throws Exception 
	{
		return classMap.get(name).newInstance();
	}
	
	public Object[] getClassNames()
	{
		Set<String> setOfClasses = classMap.keySet();
		setOfClasses.remove("Group");
		return setOfClasses.toArray();
	}
	
	public void populateClassMap() throws Exception
	{
		classMap = new HashMap<String, Class<?>>();
		ClassLoader classLoader = Thread.currentThread()
		  .getContextClassLoader();
		
		Class<?>[] classes = getClasses("shapes", classLoader);
		for(int i = 0; i < classes.length; i++)
		{
			String name = classes[i].getName();
			if(! (name.substring(name.length()-4).equals("Test")
			  || name.substring(name.length()-6)
			  .equals("IShape")))
			{
				String[] classnames = name.split("\\.");
				
				String className = 
				  classnames[classnames.length-1];
				
				Class<?> shapeClass = Class.forName(name);
				Class<?>[] interfaces = shapeClass.getInterfaces();
				
				getClassImplementingIShapeInterface(className, 
				  shapeClass, interfaces);
			}
		}
	}

	public void getClassImplementingIShapeInterface(String className,
	  Class<?> shapeClass, Class<?>[] interfaces) 
	{
		for(int index = 0 ; index < interfaces.length; index++)
		{
			if(interfaces[index].getName().
			  equalsIgnoreCase("shapes.IShape"))
			{
				classMap.put(className, shapeClass);
				break;
			}
		}
	}
	
	public Class<?>[] getClasses(String packageName, 
	  ClassLoader classLoader) throws Exception
	{
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources;
		
	    resources = classLoader.getResources(path);
		
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) 
	    {
	        URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    ArrayList<Class<?>> classes = 
	      new ArrayList<Class<?>>();
	    
	    for (File directory : dirs) 
	    {
	        classes.addAll(findClasses(
	          directory, packageName));
	    }
    	return classes.toArray(new Class<?>[classes.size()]);
	}
	
	public List<Class<?>> findClasses(File directory, 
	  String packageName) throws Exception
    {
	    List<Class<?>> classes = new ArrayList<Class<?>>();
	    if (!directory.exists()) {
	        return classes;
	    }
	    File[] files = directory.listFiles();
	    for (File file : files)
	    {
	    	classes.addAll(getClassesWithinDirectory(
	    	  packageName, file));
	    	
	        Class<?> resultClass = addToListIfaClass(
	          packageName, file);
	        
	        if(resultClass != null)
	        {
	        	classes.add(resultClass);
	        }
	    }
	    return classes;
	}

	public Class<?> addToListIfaClass(String packageName, 
			File file) throws ClassNotFoundException
			{
		Class<?> classes = null;
		if (file.getName().endsWith(".class")) 
		{
		    classes = (Class.forName(packageName +'.'+ 
		      file.getName().substring(0, 
		      file.getName().length() - 6)));
		}
		return classes;
	}

	public List<Class<?>> getClassesWithinDirectory(
	  String packageName, File file) throws Exception
	{
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (file.isDirectory()) 
		{
		    classes.addAll(findClasses(file, 
		      packageName + "." + file.getName()));
		}
		return classes;
	}
	
	public IShape getShapeObject(String shapeName) 
	  throws Exception
	{
		return (IShape)getNewInstanceByClassName(shapeName);
	}
	
	public Object[] getValues(IShape shape) throws Exception
	{
		ArrayList<Object> listValues = 
		  new ArrayList<Object>();
		
		PropertyDescriptor[] descriptors = getDescriptors(shape);
		
		for(PropertyDescriptor pd : descriptors)
		{
			if(pd.getReadMethod().getName().equals("getClass"))
				continue;
		    listValues.add(pd.getReadMethod().invoke(shape));
		}
		return listValues.toArray();
	}
	
	public Object[] getFields(IShape shape) throws Exception
	{
		ArrayList<String> propertyNames = 
		  new ArrayList<String>();
		
		PropertyDescriptor[] descriptors = getDescriptors(shape);
		
		for(PropertyDescriptor pd : descriptors)
		{
			if( pd.getName().equalsIgnoreCase("class"))
				continue;
				
			String methodName = pd.getWriteMethod().getName();
			
			propertyNames.add(
			  methodName.substring(3, methodName.length()));
		}
		return propertyNames.toArray();
	}

	public PropertyDescriptor[] getDescriptors(IShape shape)
			throws IntrospectionException {
		BeanInfo beanInfo = Introspector
		  .getBeanInfo(shape.getClass());
		
		PropertyDescriptor[] descriptors = 
		  beanInfo.getPropertyDescriptors();
		return descriptors;
	}
}
