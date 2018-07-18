package com.yx.myspring.beans.factory.support;

import com.yx.myspring.beans.factory.BeanFactory;
import com.yx.myspring.beans.factory.config.RuntimeBeanReference;
import com.yx.myspring.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {
	private BeanFactory factory;

	public BeanDefinitionValueResolver(BeanFactory factory) {
		this.factory = factory;
	}
	
	public Object resolveValueIfNecessary(Object value) {
		// TODO 目前只支持两种
		if(value instanceof RuntimeBeanReference) {
			RuntimeBeanReference ref=(RuntimeBeanReference)value;
			String beanName=ref.getBeanName();
			//TODO getBean这里有个循环引用的问题
			return this.factory.getBean(beanName);
		}else if(value instanceof TypedStringValue) {
			return ((TypedStringValue)value).getValue();
		}else {
			throw new RuntimeException("the value " + value +" has not implemented");
		}
	}
}
