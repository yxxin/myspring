package com.yx.myspring.beans.factory.support;

import com.yx.myspring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {
	public BeanDefinition getBeanDefinition(String beanID);
	public void registerBeanDefinition(String beanID,BeanDefinition bd);
}
