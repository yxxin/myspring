package com.yx.myspring.test.v5;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.yx.myspring.aop.aspectj.AspectJAfterReturningAdvice;
import com.yx.myspring.aop.aspectj.AspectJAfterThrowingAdvice;
import com.yx.myspring.aop.aspectj.AspectJBeforeAdvice;
import com.yx.myspring.aop.aspectj.AspectJExpressionPointCut;
import com.yx.myspring.aop.framework.AopConfig;
import com.yx.myspring.aop.framework.AopConfigSupport;
import com.yx.myspring.aop.framework.CglibProxyFactory;
import com.yx.myspring.service.v5.TestYX;
import com.yx.myspring.tx.TransactionManager;
import com.yx.myspring.util.MessageTracker;

public class CglibAopProxyTest {

	private AspectJBeforeAdvice beforeAdvice;
	private AspectJAfterReturningAdvice returningAdvice;
	private AspectJAfterThrowingAdvice throwingAdvice;
	private AspectJExpressionPointCut pc;
	private TransactionManager tx;
	
	@Before
	public void setUp() throws Exception {
		tx = new TransactionManager();
		pc = new AspectJExpressionPointCut();
		String expression = "execution(* com.yx.myspring.service.v5.*.test1(..))";
		pc.setExpression(expression);
		beforeAdvice = new AspectJBeforeAdvice(
				TransactionManager.class.getMethod("start"), tx, pc);
		returningAdvice = new AspectJAfterReturningAdvice(
				TransactionManager.class.getMethod("commit"), tx, pc);
		throwingAdvice = new AspectJAfterThrowingAdvice(
				TransactionManager.class.getMethod("rollback"), tx, pc);
		MessageTracker.clearMessage();
	}
	
	@Test
	public void testGetProxy() {
		AopConfig config = new AopConfigSupport();
		config.addAdvice(beforeAdvice);
		config.addAdvice(returningAdvice);
		config.addAdvice(throwingAdvice);
		config.setTargetObject(new TestYX());
		
		CglibProxyFactory factory = new CglibProxyFactory(config);
		TestYX yxproxy = (TestYX)factory.getProxy();
		yxproxy.test1();
		
		List<String> msgs=MessageTracker.getMessage();
		Assert.assertEquals(3, msgs.size());
		Assert.assertEquals("start tx", msgs.get(0));	
		Assert.assertEquals("testyx 1", msgs.get(1));	
		Assert.assertEquals("commit tx", msgs.get(2));	
		
		//如果CglibProxyFactory中的拦截器对所有方法都处理则这个方法也会加上
		yxproxy.toString();
	}

}
