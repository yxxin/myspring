package com.yx.myspring.util;

import java.util.HashMap;
import java.util.Map;

public abstract class ClassUtils {
	public static final String ARRAY_SUFFIX = "[]";
	private static final Map<Class<?>, Class<?>> wrapperToPrimitiveTypeMap = new HashMap<Class<?>, Class<?>>(8);
	private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new HashMap<Class<?>, Class<?>>(8);
	static {
		wrapperToPrimitiveTypeMap.put(Boolean.class, boolean.class);
		wrapperToPrimitiveTypeMap.put(Character.class, char.class);
		wrapperToPrimitiveTypeMap.put(Byte.class, byte.class);
		wrapperToPrimitiveTypeMap.put(Short.class, short.class);
		wrapperToPrimitiveTypeMap.put(Integer.class, int.class);
		wrapperToPrimitiveTypeMap.put(Long.class, long.class);
		wrapperToPrimitiveTypeMap.put(Float.class, float.class);
		wrapperToPrimitiveTypeMap.put(Double.class, double.class);
		for (Map.Entry<Class<?>, Class<?>> entry : wrapperToPrimitiveTypeMap.entrySet()) {
			primitiveTypeToWrapperMap.put(entry.getValue(), entry.getKey());
		}
	}

	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		cl = Thread.currentThread().getContextClassLoader();
		if (cl == null) {
			cl = ClassUtils.class.getClassLoader();
			if (cl == null) {
				cl = ClassLoader.getSystemClassLoader();
			}
		}
		return cl;
	}

	public static boolean isAssignableValue(Class<?> type, Object value) {
		Assert.notNull(type, "Type must not be null");
		return value != null ? isAssignable(type, value.getClass()) : !type.isPrimitive();
	}

	public static boolean isAssignable(Class<?> lType, Class<?> rType) {
		Assert.notNull(lType, "Left-hand side type must not be null");
		Assert.notNull(rType, "Right-hand side type must not be null");
		if (lType.isAssignableFrom(rType)) {
			return true;
		}
		if (lType.isPrimitive()) {
			Class<?> resolvedPrimitive = wrapperToPrimitiveTypeMap.get(rType);
			if (resolvedPrimitive != null && lType == resolvedPrimitive) {
				return true;
			}
		} else {
			Class<?> resolvedWarpper = primitiveTypeToWrapperMap.get(rType);
			if (resolvedWarpper != null && lType.isAssignableFrom(resolvedWarpper)) {
				return true;
			}
		}
		return false;
	}

	public static boolean matchesTypeName(Class<?> type, String typeName) {
		return typeName != null && (typeName.equals(type.getName()) || typeName.equals(type.getSimpleName())
				|| (type.isArray() && type.equals(getQualifiedNameForArray(type))));
	}
	private static String getQualifiedNameForArray(Class<?> type) {
		StringBuilder result = new StringBuilder();
		while (type.isArray()) {
			type = type.getComponentType();
			result.append(ARRAY_SUFFIX);
		}
		result.insert(0, type.getName());
		return result.toString();
	}
}
