package net.encodey.SkyblockVisualize.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
/**
 * @author k1rzy
 */
public class ClassManager {
	//  idk why i did this

	/**
	 * @param clazz - instantiate class
	 * @param c - instantiate object (fast class)
	 */
	public static void ClassLoad(Class<?> clazz, Object c) {
        Map<String, Class<?>> className = new HashMap<>();
        className.put(clazz.getName(), clazz);
        for(Map.Entry<String, Class<?>> classes : className.entrySet()) {
            try {
            	if(classes.getValue().isInterface()) {
            		throw new ClassNotFoundException();
            	}
                for(String loaded : className.keySet()) {
                    if(!loaded.equals("app")) {
                        System.out.println("No valid classes were found");
                        return;
                    }
                }
                clazz.getClassLoader().loadClass(classes.getValue().toString().replace("class ", ""));
                System.out.println("Class successfully loaded! Class loaded: " + classes.getValue());
            }
            catch (ClassNotFoundException | NullPointerException ex) {
                System.out.println("Caught exception while initializing classes: " + ex);
            }
        }
    }
	
	/**
	 * @param clazz
	 * @param c
	 */
	public static void ClassLoad(Class<?>[] clazz, Object c) {
        Map<String, Class<?>> className = new HashMap<>();
        for(Class<?> classI : clazz) {
        	className.put(classI.getName(), classI);
            for(Map.Entry<String, Class<?>> classes : className.entrySet()) {
                try {
                	if(classes.getValue().isInterface()) {
                		throw new ClassNotFoundException();
                	}
                    for(String loaded : className.keySet()) {
                        if(!loaded.equals("app")) {
                            System.out.println("No valid classes were found");
                            return;
                        }
                    }
                    classI.getClassLoader().loadClass(classes.getValue().toString().replace("class ", ""));
                    System.out.println("Class successfully loaded! Class loaded: " + classes.getValue());
                }
                catch (ClassNotFoundException | NullPointerException ex) {
                    System.out.println("Caught exception while initializing classes: " + ex);
                }
            }
        }
    }
	/**
	 * @param clazz
	 * @param c
	 */
	public void ClassLoad(Class<?> clazz, Object[] c) {
        Map<String, Class<?>> className = new HashMap<>();
        className.put(clazz.getName(), clazz);
        for(Map.Entry<String, Class<?>> classes : className.entrySet()) {
            try {
            	if(classes.getValue().isInterface()) {
            		throw new ClassNotFoundException();
            	}
                for(String loaded : className.keySet()) {
                    if(!loaded.equals("app")) {
                        System.out.println("No valid classes were found");
                        return;
                    }
                }
                clazz.getClassLoader().loadClass(classes.getValue().toString().replace("class ", ""));
                System.out.println("Class successfully loaded! Class loaded: " + classes.getValue());
            }
            catch (ClassNotFoundException | NullPointerException ex) {
                System.out.println("Caught exception while initializing classes: " + ex);
            }
        }
    }
	
	/**
	 * ALL
	 * @param clazz
	 * @param c
	 */
	public static void ClassLoad(Class<?> clazz[], Object[] c) {
        Map<String, Class<?>> className = new HashMap<>();
        for(Class<?> classI : clazz) {
        	className.put(classI.getName(), classI);
            for(Map.Entry<String, Class<?>> classes : className.entrySet()) {
                try {
                	if(classes.getValue().isInterface()) {
                		throw new ClassNotFoundException();
                	}
                    for(String loaded : className.keySet()) {
                        if(!loaded.equals("app")) {
                            System.out.println("No valid classes were found");
                            return;
                        }
                    }
                    classI.getClassLoader().loadClass(classes.getValue().toString().replace("class ", ""));
                    System.out.println("Class successfully loaded! Class loaded: " + classes.getValue());
                }
                catch (ClassNotFoundException | NullPointerException ex) {
                    System.out.println("Caught exception while initializing classes: " + ex);
                }
            }
        }
    }
	
	/**
	 * @param method
	 */
	public static void AbstractClassMethodLoad(Method method) {
		try {
			if(method == null) return;
			method.invoke(method);
			System.out.println("Method invoke: " + method.toString());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ALL
	 * @param method
	 */
	public static void AbstractClassMethodLoad(Method[] method) {
		try {
			for(Method methods : method) {
				if(methods == null) return;
				methods.invoke(methods);
				System.out.println("Method invoke: " + methods.toString());
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
