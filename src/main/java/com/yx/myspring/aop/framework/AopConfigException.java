package com.yx.myspring.aop.framework;

public class AopConfigException extends RuntimeException {

	public AopConfigException(String msg) {
		super(msg);
	}
	
	public AopConfigException(String msg,Throwable t) {
		super(msg,t);
	}
	
}
	