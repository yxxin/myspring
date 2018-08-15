package com.yx.myspring.aop.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.yx.myspring.aop.Advice;
import com.yx.myspring.aop.aspectj.AspectJExpressionPointCut;

public class AopConfigSupport implements AopConfig {

	private Object targetObject;
	private List<Advice> advices = new ArrayList<Advice>();
	
	public Object getTargetObject() {
		return this.targetObject;
	}

	public void setTargetObject(Object obj) {
		this.targetObject = obj;
	}

	public void addAdvice(Advice advice) {
		this.advices.add(advice);
	}

	public List<Advice> getAdvices() {
		return this.advices;
	}

	public Class<?> getTargetClass() {
		return this.targetObject.getClass();
	}

	public List<Advice> getAdvices(Method method) {
		List<Advice> result = new ArrayList<Advice>();
		for(Advice advice: this.getAdvices()) {
			if(advice.getPointCut().getMethodMatcher().matches(method)) {
				result.add(advice);
			}
		}
		return result;
	}

}
