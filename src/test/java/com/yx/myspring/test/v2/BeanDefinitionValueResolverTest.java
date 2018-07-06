package com.yx.myspring.test.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.yx.myspring.beans.factory.config.RuntimeBeanReference;
import com.yx.myspring.beans.factory.config.TypedStringValue;
import com.yx.myspring.beans.factory.support.BeanDefinitionValueResolver;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.beans.factory.xml.XMLBeanDefinitionReader;
import com.yx.myspring.core.io.support.ClassPathResource;
import com.yx.myspring.dao.v2.AccountDao;

public class BeanDefinitionValueResolverTest {
	private BeanDefinitionValueResolver resolver;
	@Before
	public void begin() {
		DefaultBeanFactory factory=new DefaultBeanFactory();
		XMLBeanDefinitionReader reader=new XMLBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("BeansV2.xml"));
		resolver=new BeanDefinitionValueResolver(factory);
	}
	@Test
	public void testResolveRuntimeBeanReference() {
		RuntimeBeanReference ref=new RuntimeBeanReference("accountDao");
		Object value=resolver.resolveValueIfNecessary(ref);
		
		assertNotNull(value);
		assertTrue(value instanceof AccountDao);
	}
	
	@Test
	public void testTypedStringValue() {
		TypedStringValue sv=new TypedStringValue("test");
		Object value=resolver.resolveValueIfNecessary(sv);
		
		assertNotNull(value);
		assertEquals("test", value);
	}
	
}
