package com.yx.myspring.beans.factory.config;

import com.yx.myspring.beans.BeanException;

public interface BeanPostProcessor {
	Object beforeInitialization(Object bean,String beanName) throws BeanException;
	Object afterInitialization(Object bean,String beanName) throws BeanException;
}
