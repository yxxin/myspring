package com.yx.myspring.test.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.factory.config.TypedStringValue;
import com.yx.myspring.beans.factory.support.ConstructorResolver;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.beans.factory.xml.XMLBeanDefinitionReader;
import com.yx.myspring.core.io.support.ClassPathResource;
import com.yx.myspring.service.v3.TestYX;

public class ConstructorResolverTestV3 {

	@Test
	public void testAutowireConstructor() {
		DefaultBeanFactory factory=new DefaultBeanFactory();
		XMLBeanDefinitionReader reader=new XMLBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("BeansV3.xml"));
		
		BeanDefinition bd=factory.getBeanDefinition("yx1");
		ConstructorResolver resolver=new ConstructorResolver(factory);
		TestYX yx=(TestYX)resolver.autowireConstructor(bd);
		assertNotNull(yx.getAccountDao());
		assertNotNull(yx.getItemDao());
		assertEquals("yx", yx.getName());
		assertEquals(28, yx.getNum());
	}
	
}
