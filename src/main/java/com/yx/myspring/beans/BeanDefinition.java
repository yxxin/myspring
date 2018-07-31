package com.yx.myspring.beans;

import java.util.List;

public interface BeanDefinition {
	public String getBeanClassName();
	//增加scope属性，单利和原型
	public static final String SCOPE_SINGLETON="singleton";
	public static final String SCOPE_PROTOTYPE="prototype";
	public void setScope(String scope);
	public String getScope();
	public boolean isSingleton();
	public boolean isPrototype();
	//用来存放属性注入的结构
	List<PropertyValue> getPropertyValues();
	//用来存放构造注入的结构
	ConstructorArgument getConstructorArgument();
	String getID();
	
	public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException;
	public Class<?> getBeanClass() throws IllegalStateException ;
	public boolean hasBeanClass();
}
