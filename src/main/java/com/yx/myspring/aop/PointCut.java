package com.yx.myspring.aop;

public interface PointCut {
	MethodMatcher getMethodMatcher();
	String getExpression();
}
