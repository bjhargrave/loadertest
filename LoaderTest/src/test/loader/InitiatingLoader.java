package test.loader;

public class InitiatingLoader extends BaseLoader {
	private ClassLoader[] loaders;
	private int context;

	public InitiatingLoader(String name, ClassLoader[] loaders) {
		super(name);
		this.loaders = loaders;
	}
	
	public int getContext() {
		return context;
	}

	public void setContext(int context) {
		this.context = context;
		if (context >= loaders.length) {
			throw new IllegalArgumentException("invalid context");
		}
	}

	@Override
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		System.out.println(loaderName+": loadClass for "+className);
		return loaders[context].loadClass(className);
	}

	public ClassLoader[] getLoaders() {
		return loaders;
	}

	public void setLoaders(ClassLoader[] loaders) {
		this.loaders = loaders;
	}
}
