package com.yx.myspring.aop.aspectj;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import com.yx.myspring.aop.Advice;
import com.yx.myspring.aop.PointCut;

public class AspectJBeforeAdvice extends AbstractAspectJAdvice {
	
	public AspectJBeforeAdvice(Method adviceMethod, Object adviceObject, PointCut pc) {
		super(adviceMethod,adviceObject,pc);
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		invokeAdviceMethod();
		Object o=invocation.proceed();
		return o;
	}

}
