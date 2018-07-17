package com.yx.myspring.beans.factory.support;

import java.util.ArrayList;
import java.util.List;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.ConstructorArgument;
import com.yx.myspring.beans.PropertyValue;

public class GenericBeanDefinition implements BeanDefinition {
	private String beanID;
	private String className;
	private String scope=SCOPE_DEFAULT;
	private static final String SCOPE_DEFAULT="";
	private List<PropertyValue> propertyValues=new ArrayList<PropertyValue>();
	private ConstructorArgument constructorArgument=new ConstructorArgument();
	
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

	public List<PropertyValue> getPropertyValues() {
		return this.propertyValues;
	}

	public ConstructorArgument getConstructorArgument() {
		return this.constructorArgument;
	}

	public String getID() {
		return this.beanID;
	}

}
