package com.yx.myspring.beans.factory.config;

import com.yx.myspring.beans.BeanException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
	Object beforeInstantiation(Class<?> beanClass,String beanName) throws BeanException;
	boolean afterInstantiation(Object bean,String beanName) throws BeanException;
	void postProcessPropertyValues(Object bean,String beanName) throws BeanException;
}
