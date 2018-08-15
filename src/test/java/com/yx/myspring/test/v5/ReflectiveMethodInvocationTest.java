package com.yx.myspring.test.v5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.yx.myspring.aop.aspectj.AspectJAfterReturningAdvice;
import com.yx.myspring.aop.aspectj.AspectJAfterThrowingAdvice;
import com.yx.myspring.aop.aspectj.AspectJBeforeAdvice;
import com.yx.myspring.aop.framework.ReflectiveMethodInvocation;
import com.yx.myspring.service.v5.TestYX;
import com.yx.myspring.tx.TransactionManager;
import com.yx.myspring.util.MessageTracker;

public class ReflectiveMethodInvocationTest {

	private TestYX yx;
	private TransactionManager tx;
	private AspectJBeforeAdvice beforeAdvice;
	private AspectJAfterReturningAdvice afterAdvice;
	private AspectJAfterThrowingAdvice throwingAdvice;

	@Before
	public void setUp() throws Exception {
		yx = new TestYX();
		tx = new TransactionManager();
		beforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"), tx, null);
		afterAdvice = new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit"), tx, null);
		throwingAdvice = new AspectJAfterThrowingAdvice(TransactionManager.class.getMethod("rollback"), tx, null);

		MessageTracker.clearMessage();
	}

	@Test
	public void testMethodInvocation() throws Throwable {
		Method targetMethod = TestYX.class.getMethod("test1");
		List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
		interceptors.add(beforeAdvice);
		interceptors.add(afterAdvice);

		ReflectiveMethodInvocation rmi = new ReflectiveMethodInvocation(targetMethod, yx, new Object[0], interceptors);
		rmi.proceed();

		List<String> msgs = MessageTracker.getMessage();
		assertEquals(3, msgs.size());
		Assert.assertEquals("start tx", msgs.get(0));
		Assert.assertEquals("testyx 1", msgs.get(1));
		Assert.assertEquals("commit tx", msgs.get(2));
	}

	@Test
	public void testMethodInvocation2() throws Throwable {
		Method targetMethod = TestYX.class.getMethod("test1");
		List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
		interceptors.add(afterAdvice);
		interceptors.add(beforeAdvice);

		ReflectiveMethodInvocation rmi = new ReflectiveMethodInvocation(targetMethod, yx, new Object[0], interceptors);
		rmi.proceed();

		List<String> msgs = MessageTracker.getMessage();
		assertEquals(3, msgs.size());
		Assert.assertEquals("start tx", msgs.get(0));
		Assert.assertEquals("testyx 1", msgs.get(1));
		Assert.assertEquals("commit tx", msgs.get(2));
	}
	
	@Test
	public void testAfterThrowing() throws Throwable {
		Method targetMethod = TestYX.class.getMethod("test1Exception");
		List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
		interceptors.add(afterAdvice);
		interceptors.add(beforeAdvice);
		interceptors.add(throwingAdvice);

		ReflectiveMethodInvocation rmi = new ReflectiveMethodInvocation(targetMethod, yx, new Object[0], interceptors);
		try {
			rmi.proceed();
		}catch(Throwable t) {
			List<String> msgs = MessageTracker.getMessage();
			assertEquals(2, msgs.size());
			Assert.assertEquals("start tx", msgs.get(0));
			Assert.assertEquals("rollback tx", msgs.get(1));
			return;
		}

		fail("No Exception throw");
	}

}
