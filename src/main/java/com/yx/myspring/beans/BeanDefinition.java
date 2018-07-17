package com.yx.myspring.beans;

import java.util.List;

public interface BeanDefinition {
	public String getBeanClassName();
	//����scope���ԣ�������ԭ��
	public static final String SCOPE_SINGLETON="singleton";
	public static final String SCOPE_PROTOTYPE="prototype";
	public void setScope(String scope);
	public String getScope();
	public boolean isSingleton();
	public boolean isPrototype();
	//�����������ע��Ľṹ
	List<PropertyValue> getPropertyValues();
	//������Ź���ע��Ľṹ
	ConstructorArgument getConstructorArgument();
	String getID();
}
