package com.yx.myspring.util;

public abstract class Assert {
	public static void notNull(Object object,String message) {
		if(object==null) {
			throw new IllegalArgumentException(message);
		}
	}
	public static void isTrue(boolean expression,String message) {
		if(!expression) {
			throw new IllegalArgumentException(message);
		}
	}
}
