package com.yx.myspring.aop;

import org.aopalliance.intercept.MethodInterceptor;

public interface Advice extends MethodInterceptor {
	PointCut getPointCut();
}
