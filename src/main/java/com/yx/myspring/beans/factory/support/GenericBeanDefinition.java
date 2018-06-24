package com.yx.myspring.beans.factory.support;

import com.yx.myspring.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {
	private String beanID;
	private String className;
	private String scope=SCOPE_DEFAULT;
	private static final String SCOPE_DEFAULT="";
	
	public GenericBeanDefinition(String beanID, String className) {
		this.beanID = beanID;
		this.className = className;
	}

	public String getBeanClassName() {
		return this.className;
	}

	public void setScope(String scope) {
		this.scope=scope;
	}

	public String getScope() {
		return this.scope;
	}

	public boolean isSingleton() {
		return (SCOPE_SINGLETON.equals(this.scope)) || (SCOPE_DEFAULT.equals(this.scope));
	}

	public boolean isPrototype() {
		return SCOPE_PROTOTYPE.equals(this.scope);
	}

}
