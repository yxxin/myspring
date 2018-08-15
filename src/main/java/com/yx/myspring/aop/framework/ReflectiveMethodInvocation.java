package com.yx.myspring.aop.framework;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class ReflectiveMethodInvocation implements MethodInvocation {

	private Method targetMethod;
	private Object targetObject;
	private Object[] arguments;
	private List<MethodInterceptor> interceptors;
	private int currentInterceptorIndex = -1;

	public ReflectiveMethodInvocation(Method targetMethod, Object targetObject, Object[] arguments,
			List<MethodInterceptor> interceptors) {
		this.targetMethod = targetMethod;
		this.targetObject = targetObject;
		this.arguments = arguments;
		this.interceptors = interceptors;
	}

	public Object[] getArguments() {
		return this.arguments != null ? this.arguments : new Object[0];
	}

	public Object proceed() throws Throwable {
		if(this.currentInterceptorIndex == this.interceptors.size() -1 ) {
			return targetMethod.invoke(targetObject, arguments);
		}
		this.currentInterceptorIndex++;
		MethodInterceptor interceptor = this.interceptors.get(currentInterceptorIndex);
		return interceptor.invoke(this);
	}

	public Object getThis() {
		return this.targetObject;
	}

	public AccessibleObject getStaticPart() {
		return this.targetMethod;
	}

	public Method getMethod() {
		return this.targetMethod;
	}

}
