package com.yx.myspring.test.v5;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.NoOp;

import com.yx.myspring.service.v5.TestYX;
import com.yx.myspring.tx.TransactionManager;

public class CGLibTest {

	@Test
	public void testCallBack() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(TestYX.class);
		enhancer.setCallback(new TransactionInterceptor());
		TestYX yx = (TestYX)enhancer.create();
		yx.test1();
	}
	
	public static class TransactionInterceptor implements MethodInterceptor{
		TransactionManager tx = new TransactionManager();
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			tx.start();
			Object result = proxy.invokeSuper(obj, args);
			tx.commit();
			return result;
		}
		
	}
	
	@Test
	public void testFilter() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(TestYX.class);
		Callback[] callbacks = new Callback[] {new TransactionInterceptor(),NoOp.INSTANCE};
		enhancer.setCallbacks(callbacks);
		enhancer.setCallbackFilter(new ProxyCallbackFilter());
		enhancer.setInterceptDuringConstruction(false);
		
		TestYX yx = (TestYX)enhancer.create();
		yx.test1();
		yx.toString();
	}

	public static class ProxyCallbackFilter implements CallbackFilter{

		public int accept(Method method) {
			if(method.getName().startsWith("test1")) {
				return 0;
			}else {
				return 1;
			}
		}
		
	}
	
}
