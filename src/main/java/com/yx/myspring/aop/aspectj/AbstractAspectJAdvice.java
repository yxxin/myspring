package com.yx.myspring.aop.aspectj;

import java.lang.reflect.Method;

import com.yx.myspring.aop.Advice;
import com.yx.myspring.aop.PointCut;

public abstract class AbstractAspectJAdvice implements Advice {
	private Method adviceMethod;
	private Object adviceObject;
	private PointCut pc;
	
	public AbstractAspectJAdvice(Method adviceMethod, Object adviceObject, PointCut pc) {
		this.adviceMethod = adviceMethod;
		this.adviceObject = adviceObject;
		this.pc = pc;
	}
	
	public void invokeAdviceMethod() throws Throwable {
		adviceMethod.invoke(adviceObject);
	}
	
	public PointCut getPointCut() {
		return this.pc;
	}
}
