package com.yx.myspring.aop.aspectj;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import com.yx.myspring.aop.Advice;
import com.yx.myspring.aop.PointCut;

public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice {

	public AspectJAfterThrowingAdvice(Method adviceMethod, Object adviceObject, PointCut pc) {
		super(adviceMethod,adviceObject,pc);
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		try {
			return invocation.proceed();
		}catch(Throwable t) {
			invokeAdviceMethod();
			throw t;
		}
	}
	
}
