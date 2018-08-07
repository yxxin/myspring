package com.yx.myspring.test.v5;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Test;

import com.yx.myspring.aop.MethodMatcher;
import com.yx.myspring.aop.aspectj.AspectJExpressionPointCut;
import com.yx.myspring.service.v5.TestYX;

public class PointCutTest {

	@Test
	public void testPointCut() throws Exception {
		String expression="execution(* com.yx.myspring.service.v5.*.test1(..))";
		AspectJExpressionPointCut pc=new AspectJExpressionPointCut();
		pc.setExpression(expression);
		MethodMatcher matcher=pc.getMethodMatcher();
		
		Method method1=TestYX.class.getMethod("test1");
		assertTrue(matcher.matches(method1));
		
		Method method2=TestYX.class.getMethod("getAccountDao");
		assertFalse(matcher.matches(method2));
	}

}
