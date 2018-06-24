package com.yx.myspring.beans.factory.config;

public interface SingletonBeanRegistry {
	void registerSingleton(String beanID,Object singletonObject);
	Object getSingleton(String beanID);
}
