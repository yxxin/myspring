package com.yx.myspring.beans.factory;

import com.yx.myspring.beans.BeanException;

public class NoSuchBeanDefinitionException extends BeanException {

	private String beanName;
	
	public NoSuchBeanDefinitionException(String beanName) {
		super("No bean named '"+beanName+"' is defined");
		this.beanName = beanName;
	}
	
	public String getBeanName() {
		return beanName;
	}

}
