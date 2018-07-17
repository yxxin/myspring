package com.yx.myspring.beans.factory;

import com.yx.myspring.beans.BeanException;

public class BeanCreateException extends BeanException {

	public BeanCreateException(String message) {
		super(message);
	}
	public BeanCreateException(String message,Throwable cause) {
		super(message,cause);
	}
	public BeanCreateException(String beanName,String message) {
		super("Error creating bean with name '" + beanName + "': " + message);
	}
	public BeanCreateException(String beanName,String message,Throwable cause) {
		super("Error creating bean with name '" + beanName + "': " + message,cause);
	}
}
