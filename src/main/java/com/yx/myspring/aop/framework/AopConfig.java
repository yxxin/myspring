package com.yx.myspring.aop.framework;

import java.lang.reflect.Method;
import java.util.List;

import com.yx.myspring.aop.Advice;

public interface AopConfig {
	Object getTargetObject();
	void setTargetObject(Object obj);
	void addAdvice(Advice advice);
	List<Advice> getAdvices();
	List<Advice> getAdvices(Method method);
	Class<?> getTargetClass();
}
