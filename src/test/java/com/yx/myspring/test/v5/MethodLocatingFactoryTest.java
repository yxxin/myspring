package com.yx.myspring.test.v5;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Test;

import com.yx.myspring.aop.config.MethodLocatingFactory;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.beans.factory.xml.XMLBeanDefinitionReader;
import com.yx.myspring.core.io.support.ClassPathResource;
import com.yx.myspring.tx.TransactionManager;

public class MethodLocatingFactoryTest {

	@Test
	public void testGetMethod() throws Exception {
		DefaultBeanFactory factory=new DefaultBeanFactory();
		XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("BeansV5.xml"));
		
		MethodLocatingFactory methodLocatingFactory = new MethodLocatingFactory();
		methodLocatingFactory.setTargetBeanName("tx");
		methodLocatingFactory.setMethodName("start");
		methodLocatingFactory.setBeanFactory(factory);
		
		Method method=methodLocatingFactory.getObject();
		assertTrue(TransactionManager.class.equals(method.getDeclaringClass()));
		assertTrue(TransactionManager.class.getMethod("start").equals(method));
	}

}
