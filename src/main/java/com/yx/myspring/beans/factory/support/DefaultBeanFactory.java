package com.yx.myspring.beans.factory.support;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.PropertyValue;
import com.yx.myspring.beans.SimpleTypeConvert;
import com.yx.myspring.beans.factory.BeanCreateException;
import com.yx.myspring.beans.factory.config.ConfigurableBeanFactory;
import com.yx.myspring.util.ClassUtils;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
	implements ConfigurableBeanFactory,BeanDefinitionRegistry {
	
	private Map<String,BeanDefinition> beanDefinitionMap=new ConcurrentHashMap<String,BeanDefinition>(64);
	private ClassLoader classLoader;
	private ConstructorResolver resolver=new ConstructorResolver(this);
	
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
		Object bean=instanceBean(bd);
		populateBean(bd, bean);
		return bean;
	}

	private Object instanceBean(BeanDefinition bd) {
		if(!bd.getConstructorArgument().isEmpty()) {
			return resolver.autowireConstructor(bd);
		}else {
			String className=bd.getBeanClassName();
			ClassLoader cl= this.getBeanClassLoader();
			try {
				Class<?> clz= cl.loadClass(className);
				return clz.newInstance();
			} catch (Exception e) {
				throw new BeanCreateException("created bean "+className+" failed",e);
			}
		}
	}
	
	private void populateBean(BeanDefinition bd, Object bean){
		List<PropertyValue> pvs=bd.getPropertyValues();
		if(pvs==null || pvs.isEmpty()) {
			return;
		}
		BeanDefinitionValueResolver resolver=new BeanDefinitionValueResolver(this);
		SimpleTypeConvert convert=new SimpleTypeConvert();
		try {
			for(PropertyValue pv:pvs) {
				String propertyName=pv.getName();
				Object originalValue=pv.getValue();
				Object resolvedValue=resolver.resolveValueIfNecessary(originalValue);
				
				invokeMethod(bean, convert, propertyName, resolvedValue);
				//使用common-beanutils来设置属性
//				BeanUtils.setProperty(bean, propertyName, resolvedValue);
			}
		}catch(Exception ex){
			throw new BeanCreateException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", ex);
		}	
	}

	private void invokeMethod(Object bean, SimpleTypeConvert convert, String propertyName, Object resolvedValue)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		// TODO 这里使用了javabean,spring中使用的是什么呢？
		BeanInfo info=Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] pds=info.getPropertyDescriptors();
		for(PropertyDescriptor pd:pds) {
			if(pd.getName().equals(propertyName)) {
				Object convertedValue = convert.convertIfNecessary(resolvedValue, pd.getPropertyType());
				// TODO spring源码中不是当场就是注入进去的，貌似是放到PropertyValue中的转换对象中，怎么用到还待看？
				pd.getWriteMethod().invoke(bean, convertedValue);
				break;
			}
		}
	}

	public void setBeanClassLoader(ClassLoader cl) {
		this.classLoader=cl;
	}

	public ClassLoader getBeanClassLoader() {
		return (this.classLoader!=null) ? this.classLoader : ClassUtils.getDefaultClassLoader();
	}
	

}
