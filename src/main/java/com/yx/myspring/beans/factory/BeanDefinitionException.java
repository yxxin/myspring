package com.yx.myspring.beans.factory;

import com.yx.myspring.beans.BeanException;

public class BeanDefinitionException extends BeanException {
	public BeanDefinitionException(String message) {
		super(message);
	}
	public BeanDefinitionException(String message, Throwable cause) {
		super(message, cause);
	}

}
