package com.yx.myspring.beans.factory.config;

import java.util.List;

public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
	void setBeanClassLoader(ClassLoader cl);
	ClassLoader getBeanClassLoader();
	void addBeanPostProcessor(BeanPostProcessor postProcessor);
	List<BeanPostProcessor> getBeanPostProcessors();
}
