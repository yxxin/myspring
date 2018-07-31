package com.yx.myspring.context.support;

import com.yx.myspring.beans.factory.annotation.AutowiredAnnotationProcessor;
import com.yx.myspring.beans.factory.annotation.InjectionMetadata;
import com.yx.myspring.beans.factory.config.ConfigurableBeanFactory;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.beans.factory.xml.XMLBeanDefinitionReader;
import com.yx.myspring.context.ApplicationContext;
import com.yx.myspring.core.io.Resource;
import com.yx.myspring.util.ClassUtils;

public abstract class AbstractApplicationContext implements ApplicationContext {
	private DefaultBeanFactory factory;
	private ClassLoader classLoader;
	public AbstractApplicationContext(String path) {
		this(path,null);
	}
	public AbstractApplicationContext(String path,ClassLoader classLoader) {
		this.factory=new DefaultBeanFactory();
		this.classLoader=classLoader;
		factory.setBeanClassLoader(getBeanClassLoader());
		XMLBeanDefinitionReader reader=new XMLBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(this.getResourceByPath(path));
		registerBeanPostProcessors(factory);
	}
	protected abstract Resource getResourceByPath(String path);
	public Object getBean(String beanID) {
		return this.factory.getBean(beanID);
	}
	
	public void setBeanClassLoader(ClassLoader cl) {
		this.classLoader=cl;
	}

	public ClassLoader getBeanClassLoader() {
		return (this.classLoader!=null) ? this.classLoader : ClassUtils.getDefaultClassLoader();
	}
	
	public void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {
		AutowiredAnnotationProcessor processor=new AutowiredAnnotationProcessor();
		processor.setBeanFactory(beanFactory);
		beanFactory.addBeanPostProcessor(processor);
	}
}
