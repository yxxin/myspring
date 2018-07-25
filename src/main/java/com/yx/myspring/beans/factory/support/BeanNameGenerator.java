package com.yx.myspring.beans.factory.support;

import com.yx.myspring.beans.BeanDefinition;

public interface BeanNameGenerator {
	String generateBeanName(BeanDefinition bd);
}
