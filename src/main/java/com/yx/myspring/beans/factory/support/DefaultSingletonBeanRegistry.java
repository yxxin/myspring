package com.yx.myspring.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yx.myspring.beans.factory.config.SingletonBeanRegistry;
import com.yx.myspring.util.Assert;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
	private Map<String,Object> singletonMap=new ConcurrentHashMap<String,Object>();
	public void registerSingleton(String beanID,Object singletonObject) {
		Assert.notNull(beanID,"'beanName' must not be null");
		Object singlton=this.getSingleton(beanID);
		if(singlton!=null) {
			throw new IllegalStateException("Could not register object ["+singletonObject
					+"] ,bean name '"+beanID +"' there is already object ["+singlton+"]");
		}
		this.singletonMap.put(beanID, singletonObject);
	}

	public Object getSingleton(String beanID) {
		return this.singletonMap.get(beanID);
	}

}
