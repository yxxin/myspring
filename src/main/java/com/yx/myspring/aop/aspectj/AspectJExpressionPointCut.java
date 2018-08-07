package com.yx.myspring.aop.aspectj;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.weaver.reflect.ReflectionWorld.ReflectionWorldException;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParameter;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.aspectj.weaver.tools.ShadowMatch;

import com.yx.myspring.aop.MethodMatcher;
import com.yx.myspring.aop.PointCut;
import com.yx.myspring.util.ClassUtils;
import com.yx.myspring.util.StringUtils;

public class AspectJExpressionPointCut implements PointCut,MethodMatcher {

	private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES=new HashSet<PointcutPrimitive>();
	
	static {
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
	}
	
	private String expression;
	private ClassLoader pointcutClassLoader;
	private PointcutExpression pointcutExpression;
	
	public boolean matches(Method method) {
		//获取PointcutExpression
		checkReadyToMatch();
		//使用PointcutExpression匹配方法
		ShadowMatch shadowMatch = getShadowMatch(method);
		if (shadowMatch.alwaysMatches()) {
			return true;
		}
		return false;
	}

	public MethodMatcher getMethodMatcher() {
		return this;
	}

	public String getExpression() {
		return this.expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	private void checkReadyToMatch() {
		if(this.getExpression() == null) {
			throw new IllegalStateException("Must set property 'expression' before attempting to match");
		}
		if(this.pointcutExpression == null) {
			this.pointcutClassLoader = ClassUtils.getDefaultClassLoader();
			this.pointcutExpression = buildPointcutExpression(this.pointcutClassLoader);
		}
	}
	
	private PointcutExpression buildPointcutExpression(ClassLoader classLoader) {
		PointcutParser parse=PointcutParser
				.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
						SUPPORTED_PRIMITIVES, classLoader);
		
		return parse.parsePointcutExpression(
				replaceBooleanOperators(this.getExpression()), null, new PointcutParameter[0]);
	}
	
	private String replaceBooleanOperators(String pcExpr) {
		String result = StringUtils.replace(pcExpr, " and ", " && ");
		result = StringUtils.replace(result, " or ", " || ");
		result = StringUtils.replace(result, " not ", " ! ");
		return result;
	}
	
	private ShadowMatch getShadowMatch(Method method) {
		ShadowMatch shadowMatch = null;
		try {
			shadowMatch = this.pointcutExpression.matchesMethodExecution(method);
		}
		catch (ReflectionWorldException ex) {
			throw new RuntimeException("not implemented yet");
		}
		return shadowMatch;
	}

}
