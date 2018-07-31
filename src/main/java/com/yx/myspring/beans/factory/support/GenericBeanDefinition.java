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
	private Class<?> beanClass;
	
	public GenericBeanDefinition(String beanID, String className) {
		this.beanID = beanID;
		this.className = className;
	}

	public GenericBeanDefinition() {
	}

	public String getBeanClassName() {
		return this.className;
	}
	public void setBeanClassName(String className) {
		this.className=className;
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
	public void setID(String beanID) {
		this.beanID = beanID;
	}

	public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
		String className=this.getBeanClassName();
		if(className == null) {
			return null;
		}
		Class<?> resolveClass=classLoader.loadClass(className);
		this.beanClass=resolveClass;
		return resolveClass;
	}

	public Class<?> getBeanClass() throws IllegalStateException {
		if(this.beanClass == null){
			throw new IllegalStateException(
					"Bean class name [" + this.getBeanClassName() + "] has not been resolved into an actual Class");
		}	
		return this.beanClass;
	}

	public boolean hasBeanClass() {
		return this.beanClass != null;
	}

}
