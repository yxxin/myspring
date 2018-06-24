package com.yx.myspring.beans.factory.config;

import com.yx.myspring.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory {
	void setBeanClassLoader(ClassLoader cl);
	ClassLoader getBeanClassLoader();
}
