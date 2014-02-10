package os.rabbit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import os.rabbit.components.Component;

public class Help {

	public static Method getMethod(Class c, String methodName, Class... parameterTypes) {

		Method method = null;
		while (c != null && method == null) {
			try {
				if (parameterTypes != null) {
					method = c.getDeclaredMethod(methodName, parameterTypes);
				} else
					method = c.getDeclaredMethod(methodName);
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			}
			if (method == null)
				c = c.getSuperclass();
		}
		return method;
	}

	public static Field getField(Class c, String name) {

		Field field = null;
		while (c != null && field == null) {
			try {
				field = c.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
			} catch (SecurityException e) {
			}
			if (field == null)
				c = c.getSuperclass();
		}
		return field;
	}

}
