package test.main;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import test.loader.BaseLoader;
import test.loader.DefiningLoader;
import test.loader.InitiatingLoader;

public class PinTest {

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
		
		testForName();
	}
	
	public static void testForName() throws Exception {
		System.out.println("\n=========================");
		System.out.println("Test Class.forName");
		BaseLoader[] definingLoaders = new BaseLoader[1];
		definingLoaders[0] = new DefiningLoader("test0", test0Dir);
		
		InitiatingLoader initiatingLoader = new InitiatingLoader("initiating", definingLoaders);
		
		System.out.println("Before load from context 0");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		
		System.out.println("forName from context 0");
		initiatingLoader.setContext(0);
		Class<?> clazz = Class.forName(className,false,initiatingLoader);
		invokeClass(clazz);
		
		System.out.println("After load from context 0");
		initiatingLoader.checkForLoadedClass(className);
		definingLoaders[0].checkForLoadedClass(className);
		
		System.out.println("Before GC");
		ReferenceQueue<Class<?>> queue = new ReferenceQueue<Class<?>>();
		Reference<Class<?>> ref = new WeakReference<Class<?>>(clazz, queue);
		
		// dereference things
		clazz = null;
		definingLoaders = null;
		initiatingLoader.setLoaders(null);
		
		for (int i = 0; i < 1000; i++) {
			if (ref == queue.poll()) {
				System.out.println("Weak reference appeared at time: "+i);
				break;
			}
			System.gc();
		}
		
		System.out.println("After GC");
		initiatingLoader.checkForLoadedClass(className);
	}
	
	public static void invokeClass(Class<?> clazz) throws Exception {
		Runnable object = (Runnable)clazz.newInstance();
		
		object.run();
	}
}
