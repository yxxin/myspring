package com.yx.myspring.aop.framework;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.yx.myspring.aop.Advice;
import com.yx.myspring.util.Assert;

public class CglibProxyFactory implements AopProxyFactory {

	private static final int AOP_PROXY = 0;
	private AopConfig config;
	
	public CglibProxyFactory(AopConfig config) {
		Assert.notNull(config, "AdvisedSupport must not be null");
		if (config.getAdvices().size() == 0) {
			throw new AopConfigException("No advisors and no TargetSource specified");
		}
		this.config = config;
	}

	public Object getProxy() {
		return getProxy(null);
	}

	public Object getProxy(ClassLoader cl) {
		Enhancer enhancer = new Enhancer();
		if(cl != null) {
			enhancer.setClassLoader(cl);
		}
		enhancer.setSuperclass(this.config.getTargetClass());
		enhancer.setInterceptDuringConstruction(false);
		enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);// "BySpringCGLIB"
		Callback[] callbacks = getCallbacks();
		Class<?>[] types = new Class<?>[callbacks.length];
		for(int i=0;i<callbacks.length;i++) {
			types[i] = callbacks[i].getClass();
		}
		enhancer.setCallbacks(callbacks);
		enhancer.setCallbackTypes(types);
		enhancer.setCallbackFilter(new ProxyCallbackFilter());
		
		return enhancer.create();
	}
	
	private Callback[] getCallbacks() {
		Callback aopInterceptor = new DynamicAdvisedInterceptor(this.config);
		Callback[] callbacks = new Callback[] {aopInterceptor};
		return callbacks;
	}
	
	private static class DynamicAdvisedInterceptor implements MethodInterceptor {

		private AopConfig config;
		
		public DynamicAdvisedInterceptor(AopConfig config) {
			this.config = config;
		}

		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
			Object target = config.getTargetObject();
			List<Advice> advices = config.getAdvices(method);
//			List<Advice> advices = config.getAdvices();所有方法都处理
			Object result = null;
			if(advices.isEmpty() && Modifier.isPublic(method.getModifiers())) {
				result = methodProxy.invoke(target, args);
			}else {
				List<org.aopalliance.intercept.MethodInterceptor> interceptors = new ArrayList<org.aopalliance.intercept.MethodInterceptor>();
				interceptors.addAll(advices);
				ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(method, target, args, interceptors);
				result = invocation.proceed();
			}
			return result;
		}
		
	}
	
	private static class ProxyCallbackFilter implements CallbackFilter {

		public int accept(Method arg0) {
			return AOP_PROXY;
		}
		
	}

}
