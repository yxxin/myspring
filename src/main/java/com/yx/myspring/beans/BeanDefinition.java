package com.yx.myspring.beans;

public interface BeanDefinition {
	public String getBeanClassName();
	//增加scope属性，单利和原型
	public static final String SCOPE_SINGLETON="singleton";
	public static final String SCOPE_PROTOTYPE="prototype";
	public void setScope(String scope);
	public String getScope();
	public boolean isSingleton();
	public boolean isPrototype();
}
