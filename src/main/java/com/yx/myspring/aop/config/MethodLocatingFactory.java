package com.yx.myspring.aop.config;

import java.lang.reflect.Method;

import com.yx.myspring.beans.BeanUtils;
import com.yx.myspring.beans.factory.BeanFactory;
import com.yx.myspring.util.Assert;
import com.yx.myspring.util.StringUtils;

public class MethodLocatingFactory {

	private String targetBeanName;
	private String methodName;
	private Method method;

	public void setTargetBeanName(String targetBeanName) {
		this.targetBeanName = targetBeanName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		Assert.notNull(beanFactory, "BeanFactory is null");
		if (!StringUtils.hasText(this.targetBeanName)) {
			throw new IllegalArgumentException("Property 'targetBeanName' is required");
		}
		if (!StringUtils.hasText(this.methodName)) {
			throw new IllegalArgumentException("Property 'methodName' is required");
		}
		Class<?> beanClass = beanFactory.getType(targetBeanName);
		if(beanClass == null) {
			throw new IllegalArgumentException("Can't determine type of bean with name '" + this.targetBeanName + "'");
		}
		this.method = BeanUtils.resolveSignature(beanClass, this.methodName);

		if (this.method == null) {
			throw new IllegalArgumentException("Unable to locate method [" + this.methodName +
					"] on bean [" + this.targetBeanName + "]");
		}
	}

	public Method getObject() {
		return this.method;
	}

}
