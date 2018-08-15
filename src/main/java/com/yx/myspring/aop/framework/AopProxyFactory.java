package com.yx.myspring.aop.framework;

public interface AopProxyFactory {
	Object getProxy();
	Object getProxy(ClassLoader cl);
}
