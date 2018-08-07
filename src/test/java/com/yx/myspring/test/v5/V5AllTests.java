package com.yx.myspring.test.v5;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.yx.myspring.aop.MethodMatcher;
import com.yx.myspring.aop.PointCut;

@RunWith(Suite.class)
@SuiteClasses({ 
	ApplicationContextTestV5.class, 
	PointCutTest.class,
	MethodLocatingFactoryTest.class})
public class V5AllTests {
	//1.检查表达式是否匹配方法。测试用例PointCutTest
	//	PointCut,MethodMatcher两个接口，
	//	一个实现类AspectJExpressionPointCut，使用aspectj来实现。
	//		PointcutParser--》PointcutExpression--》ShadowMatch
	//2.定位Method：给定factory,beanid,method返回对应的Method。测试用例MethodLocatingFactoryTest
	//	MethodLoactingFactory: setTargetBeanName,setMethodName,setBeanFactory,getObject
	//	BeanFactory增加getType(String beanID):Class<?>。DefaultBeanFactory中实现。
	//	获取Class之后通过反射获取Method。setMethodName不仅是方法名，还可以把参数带进去。
	//	获取规则如下：
	//	1.只给了方法名称，没有参数的。如果有多个同名的方法取参数个数最少的那个方法，如果最少的有多个则抛异常。
	//	2.有参数的，直接对应参数的获取。
}
