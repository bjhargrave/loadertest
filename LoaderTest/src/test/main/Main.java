package test.main;

import test.loader.BaseLoader;
import test.loader.DefiningLoader;
import test.loader.InitiatingLoader;

public class Main {

	public static String test0Dir;
	public static String test1Dir;
	public static String className;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		test0Dir = args[0]+"/test0/bin";
		test1Dir = args[0]+"/test1/bin";
		className = "test.Test";
		
		testLoadClass();
		testForName();
		testForNameThenLoadClass();
		testLoadClassThenForName();
	}
	
	public static void testLoadClass() throws Exception {
		System.out.println("\n=========================");
		System.out.println("Test ClassLoader.loadClass");
		BaseLoader[] definingLoaders = new BaseLoader[2];
		definingLoaders[0] = new DefiningLoader("test0", test0Dir);
		definingLoaders[1] = new DefiningLoader("test1", test1Dir);
		
		InitiatingLoader initiatingLoader = new InitiatingLoader("initiating", definingLoaders);

		System.out.println("Before load from context 0");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		definingLoaders[1].checkForLoadedClass(className);

		System.out.println("loadClass from context 0");
		initiatingLoader.setContext(0);
		Class<?> clazz = initiatingLoader.loadClass(className);
		invokeClass(clazz);

		System.out.println("After load from context 0");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		definingLoaders[1].checkForLoadedClass(className);

		System.out.println("loadClass from context 1");
		initiatingLoader.setContext(1);
		clazz = initiatingLoader.loadClass(className);
		invokeClass(clazz);
		
		System.out.println("After load from context 1");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		definingLoaders[1].checkForLoadedClass(className);
	}

	public static void testForName() throws Exception {
		System.out.println("\n=========================");
		System.out.println("Test Class.forName");
		BaseLoader[] definingLoaders = new BaseLoader[2];
		definingLoaders[0] = new DefiningLoader("test0", test0Dir);
		definingLoaders[1] = new DefiningLoader("test1", test1Dir);
		
		InitiatingLoader initiatingLoader = new InitiatingLoader("initiating", definingLoaders);
		
		System.out.println("Before load from context 0");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		definingLoaders[1].checkForLoadedClass(className);
		
		System.out.println("forName from context 0");
		initiatingLoader.setContext(0);
		Class<?> clazz = Class.forName(className,false,initiatingLoader);
		invokeClass(clazz);
		
		System.out.println("After load from context 0");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		definingLoaders[1].checkForLoadedClass(className);
		
		System.out.println("forName from context 1");
		initiatingLoader.setContext(1);
		clazz = Class.forName(className,false,initiatingLoader);
		invokeClass(clazz);
		
		System.out.println("After load from context 1");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		definingLoaders[1].checkForLoadedClass(className);
	}
	
	public static void testForNameThenLoadClass() throws Exception {
		System.out.println("\n=========================");
		System.out.println("Test Class.forName then ClassLoader.loadClass");
		BaseLoader[] definingLoaders = new BaseLoader[2];
		definingLoaders[0] = new DefiningLoader("test0", test0Dir);
		definingLoaders[1] = new DefiningLoader("test1", test1Dir);
		
		InitiatingLoader initiatingLoader = new InitiatingLoader("initiating", definingLoaders);
		
		System.out.println("Before load from context 0");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		definingLoaders[1].checkForLoadedClass(className);
		
		System.out.println("forName from context 0");
		initiatingLoader.setContext(0);
		Class<?> clazz = Class.forName(className,false,initiatingLoader);
		invokeClass(clazz);
		
		System.out.println("After load from context 0");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		definingLoaders[1].checkForLoadedClass(className);
		
		System.out.println("loadClass from context 1");
		initiatingLoader.setContext(1);
		//clazz = Class.forName(className,false,initiatingLoader);
		clazz = initiatingLoader.loadClass(className);
		invokeClass(clazz);
		
		System.out.println("After load from context 1");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		definingLoaders[1].checkForLoadedClass(className);
	}

	public static void testLoadClassThenForName() throws Exception {
		System.out.println("\n=========================");
		System.out.println("Test ClassLoader.loadClass then Class.forName");
		BaseLoader[] definingLoaders = new BaseLoader[2];
		definingLoaders[0] = new DefiningLoader("test0", test0Dir);
		definingLoaders[1] = new DefiningLoader("test1", test1Dir);
		
		InitiatingLoader initiatingLoader = new InitiatingLoader("initiating", definingLoaders);
		
		System.out.println("Before load from context 0");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		definingLoaders[1].checkForLoadedClass(className);
		
		System.out.println("forName from context 0");
		initiatingLoader.setContext(0);
		Class<?> clazz = initiatingLoader.loadClass(className);
		invokeClass(clazz);
		
		System.out.println("After load from context 0");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		definingLoaders[1].checkForLoadedClass(className);
		
		System.out.println("loadClass from context 1");
		initiatingLoader.setContext(1);
		clazz = Class.forName(className,false,initiatingLoader);
		invokeClass(clazz);
		
		System.out.println("After load from context 1");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		definingLoaders[1].checkForLoadedClass(className);
	}
	
	public static void invokeClass(Class<?> clazz) throws Exception {
		Runnable object = (Runnable)clazz.newInstance();
		
		object.run();
	}
}
