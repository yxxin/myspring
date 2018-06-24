package com.yx.myspring.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.factory.BeanCreateException;
import com.yx.myspring.beans.factory.BeanFactory;
import com.yx.myspring.beans.factory.config.ConfigurableBeanFactory;
import com.yx.myspring.util.ClassUtils;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
	implements ConfigurableBeanFactory,BeanDefinitionRegistry {
	
	private Map<String,BeanDefinition> beanDefinitionMap=new ConcurrentHashMap<String,BeanDefinition>(64);
	private ClassLoader classLoader;
	
	public BeanDefinition getBeanDefinition(String beanID) {
		return this.beanDefinitionMap.get(beanID);
	}

	public void registerBeanDefinition(String beanID, BeanDefinition bd) {
		this.beanDefinitionMap.put(beanID, bd);
	}

	public Object getBean(String beanID) {
		BeanDefinition bd=this.getBeanDefinition(beanID);
		if(bd==null) {
			throw new BeanCreateException("Bean Definition does not exist");
		}
		if(bd.isSingleton()) {
			Object obj=this.getSingleton(beanID);
			if(obj==null) {
				obj=createBean(bd);
				this.registerSingleton(beanID, obj);
			}
			return obj;
		}
		return createBean(bd);
	}

	private Object createBean(BeanDefinition bd) {
		String className=bd.getBeanClassName();
		ClassLoader cl= this.getBeanClassLoader();
		try {
			Class<?> clz= cl.loadClass(className);
			return clz.newInstance();
		} catch (Exception e) {
			throw new BeanCreateException("created bean "+className+" failed",e);
		}
	}

	public void setBeanClassLoader(ClassLoader cl) {
		this.classLoader=cl;
	}

	public ClassLoader getBeanClassLoader() {
		return (this.classLoader!=null) ? this.classLoader : ClassUtils.getDefaultClassLoader();
	}
	

}
