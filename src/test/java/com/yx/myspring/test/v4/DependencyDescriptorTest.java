package com.yx.myspring.test.v4;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Test;

import com.yx.myspring.beans.factory.config.DependencyDescriptor;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.beans.factory.xml.XMLBeanDefinitionReader;
import com.yx.myspring.core.io.support.ClassPathResource;
import com.yx.myspring.dao.v4.AccountDao;
import com.yx.myspring.service.v4.TestYX;

public class DependencyDescriptorTest {

	@Test
	public void testResolveDependency() throws Exception {
		DefaultBeanFactory factory=new DefaultBeanFactory();
		XMLBeanDefinitionReader reader=new XMLBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("BeansV4.xml"));
		
		Field field= TestYX.class.getDeclaredField("accountDao");
		DependencyDescriptor descriptor=new DependencyDescriptor(field, true);
		Object obj=factory.resolveDependency(descriptor);
		assertTrue(obj instanceof AccountDao);
	}

}
