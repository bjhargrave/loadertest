package test.loader;

public class BaseLoader extends ClassLoader {
	
	protected final String loaderName;

	public BaseLoader(String loaderName) {
		super();
		this.loaderName = loaderName;
	}

	public Class<?> checkForLoadedClass(String name) {
		Class<?> check = findLoadedClass(name);
		if (check != null) {
			System.out.println(loaderName+": "+name+" is cached ");
		}
		else {
			System.out.println(loaderName+": "+name+" is not cached ");
		}
		return check;
	}
}
