package com.yx.myspring.beans.factory.config;

import com.yx.myspring.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {
	public Object resolveDependency(DependencyDescriptor descriptor);
}
