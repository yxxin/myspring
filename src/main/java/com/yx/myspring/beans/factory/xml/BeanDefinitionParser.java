package com.yx.myspring.beans.factory.xml;

public abstract class BeanDefinitionParser {
	public static final String ID_ATTRIBUTE="id";
	public static final String CLASS_ATTRIBUTE = "class";
	public static final String SCOPE_ATTRIBUTE = "scope";
	public static final String PROPERTY_ATTRIBUTE = "property";
	public static final String NAME_ATTRIBUTE = "name";
	public static final String TYPE_ATTRIBUTE = "type";
	public static final String INDEX_ATTRIBUTE = "index";
	public static final String VALUE_ATTRIBUTE = "value";
	public static final String REF_ATTRIBUTE = "ref";
	public static final String CONSTRUCTOR_ARG_ATTRIBUTE = "constructor-arg";
	public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";
	public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";
	public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
}
