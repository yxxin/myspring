package com.yx.myspring.test.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.factory.support.ConstructorResolver;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.beans.factory.xml.XMLBeanDefinitionReader;
import com.yx.myspring.core.io.support.ClassPathResource;
import com.yx.myspring.service.v3.TestYX;

public class ConstructorResolverTestV3 {
	private DefaultBeanFactory factory;
	@Before
	public void init() {
		factory=new DefaultBeanFactory();
		XMLBeanDefinitionReader reader=new XMLBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("BeansV3.xml"));
	}
	@Test
	public void testAutowireConstructor() {
		BeanDefinition bd=factory.getBeanDefinition("yx1");
		ConstructorResolver resolver=new ConstructorResolver(factory);
		TestYX yx=(TestYX)resolver.autowireConstructor(bd);
		assertNotNull(yx.getAccountDao());
		assertNotNull(yx.getItemDao());
		assertEquals("yx", yx.getName());
		assertEquals("28", yx.getName2());
		assertEquals(0, yx.getNum());
	}
	@Test
	public void testAutowireConstrutorIndex() {
		BeanDefinition bd=factory.getBeanDefinition("yx2");
		ConstructorResolver resolver=new ConstructorResolver(factory);
		TestYX yx=(TestYX)resolver.autowireConstructor(bd);
		assertNotNull(yx.getAccountDao());
		assertNotNull(yx.getItemDao());
		assertEquals("28", yx.getName());
		assertEquals("yx2", yx.getName2());
	}
	@Test
	public void testAutowireConstructorType() {
		BeanDefinition bd=factory.getBeanDefinition("yx4");
		ConstructorResolver resolver=new ConstructorResolver(factory);
		TestYX yx=(TestYX)resolver.autowireConstructor(bd);
		assertNotNull(yx.getAccountDao());
		assertNotNull(yx.getItemDao());
		assertEquals("yx4", yx.getName());
		assertEquals(28, yx.getNum());
	}
	@Test
	public void testAutowireConstructorIndexAndType() {
		BeanDefinition bd=factory.getBeanDefinition("yx5");
		ConstructorResolver resolver=new ConstructorResolver(factory);
		TestYX yx=(TestYX)resolver.autowireConstructor(bd);
		assertNotNull(yx.getAccountDao());
		assertNotNull(yx.getItemDao());
		assertEquals("yx5", yx.getName());
		assertEquals(28, yx.getNum());
	}
}
