package factory_method;
import java.lang.reflect.InvocationTargetException;


public class FactoryLoader {

	public static Object getInstance(String className) throws ClassNotFoundException, IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		@SuppressWarnings("rawtypes")
		Class c = Class.forName(className);
		Object o = c.newInstance();
		return o;
	}
}

