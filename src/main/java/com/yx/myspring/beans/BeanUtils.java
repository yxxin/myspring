package com.yx.myspring.beans;

import java.lang.reflect.Method;

import com.yx.myspring.util.StringUtils;

public abstract class BeanUtils {

	public static Method resolveSignature(Class<?> clazz, String signature) {
		int firstParen = signature.indexOf("(");
		int lastParen = signature.indexOf(")");
		if (firstParen > -1 && lastParen == -1) {
			throw new IllegalArgumentException("Invalid method signature '" + signature +
					"': expected closing ')' for args list");
		}
		else if (lastParen > -1 && firstParen == -1) {
			throw new IllegalArgumentException("Invalid method signature '" + signature +
					"': expected opening '(' for args list");
		}
		else if (firstParen == -1 && lastParen == -1) {
			return findMethodWithMinimalParameters(clazz, signature);
		}else {
			String methodName = signature.substring(0, firstParen);
			String[] parameterTypeNames =
					StringUtils.commaDelimitedListToStringArray(signature.substring(firstParen + 1, lastParen));
			Class<?>[] classTypes=new Class<?>[parameterTypeNames.length];
			for(int i=0;i<parameterTypeNames.length;i++) {
				String parameterTypeName=parameterTypeNames[i].trim();
				try {
					classTypes[i] = clazz.getClassLoader().loadClass(parameterTypeName);
				} catch (Throwable ex) {
					throw new IllegalArgumentException("Invalid method signature: unable to resolve type [" +
							parameterTypeName + "] for argument " + i + ". Root cause: " + ex);
				}
			}
			return findMethod(clazz, methodName, classTypes);
		}
	}

	public static Method findMethod(Class<?> clazz, String methodName, Class<?>... classTypes) {
		try {
			return clazz.getMethod(methodName, classTypes);
		} catch (NoSuchMethodException e) {
			return findDeclaredMethod(clazz, methodName, classTypes);
		}
	}

	public static Method findDeclaredMethod(Class<?> clazz, String methodName, Class<?>... classTypes) {
		try {
			return clazz.getDeclaredMethod(methodName, classTypes);
		} catch (NoSuchMethodException e) {
			if(clazz.getSuperclass() != null) {
				return findDeclaredMethod(clazz.getSuperclass(),methodName,classTypes);
			}
			return null;
		}
	}

	public static Method findMethodWithMinimalParameters(Class<?> clazz, String methodName) {
		Method method=findMethodWithMinimalParameters(clazz.getMethods(), methodName);
		if(method == null) {
			method=findDeclaredMethodWithMinimalParameters(clazz,methodName);
		}
		return method;
	}
	
	public static Method findMethodWithMinimalParameters(Method[] methods, String methodName) {
		Method resultMethod=null;
		int numMethodsFoundWithCurrentMinimumArgs=0;
		for(Method method: methods) {
			if(method.getName().equals(methodName)) {
				int paramNum = method.getParameterCount();
				if(resultMethod==null || paramNum<resultMethod.getParameterCount()) {
					resultMethod=method;
					numMethodsFoundWithCurrentMinimumArgs = 1;
				}else {
					if(paramNum==resultMethod.getParameterCount()) {
						numMethodsFoundWithCurrentMinimumArgs++;
					}
				}
			}
		}
		if(numMethodsFoundWithCurrentMinimumArgs>1) {
			throw new IllegalArgumentException("Cannot resolve method '" + methodName +
					"' to a unique method. Attempted to resolve to overloaded method with " +
					"the least number of parameters, but there were " +
					numMethodsFoundWithCurrentMinimumArgs + " candidates.");
		}
		return resultMethod;
	}
	
	public static Method findDeclaredMethodWithMinimalParameters(Class<?> clazz, String methodName) {
		Method method=findMethodWithMinimalParameters(clazz.getDeclaredMethods(),methodName);
		if(method == null && clazz.getSuperclass() != null) {
			method=findDeclaredMethodWithMinimalParameters(clazz.getSuperclass(),methodName);
		}
		return method;
	}
	
}
