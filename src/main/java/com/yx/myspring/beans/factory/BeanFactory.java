package com.yx.myspring.beans.factory;

public interface BeanFactory {
	public Object getBean(String beanID);
	Class<?> getType(String beanID) throws NoSuchBeanDefinitionException;
}
