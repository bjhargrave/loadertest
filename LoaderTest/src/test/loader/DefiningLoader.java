package test.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DefiningLoader extends BaseLoader {
	
	private String folder;

	public DefiningLoader(String name, String folder) {
		super(name);
		this.folder = folder;
	}

	@Override
	protected synchronized Class<?> loadClass(String className,
			boolean resolveClass) throws ClassNotFoundException {
		System.out.println(loaderName+": loadClass for "+className);
		return super.loadClass(className, resolveClass);
	}

	@Override
	protected Class<?> findClass(String className)
			throws ClassNotFoundException {
		File file = new File(folder,className.replace('.', '/').concat(".class"));
		int length = (int)file.length();
		byte[] classFile = new byte[length];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(classFile);
			in.close();
		}
		catch (IOException e)
		{
			throw new ClassNotFoundException("IOException reading class",e);
		}
		
		return defineClass(className,classFile,0,length);
	}
}
