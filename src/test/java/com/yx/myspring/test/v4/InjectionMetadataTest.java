package com.yx.myspring.test.v4;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.yx.myspring.beans.factory.annotation.AutowiredFieldElement;
import com.yx.myspring.beans.factory.annotation.InjectionElement;
import com.yx.myspring.beans.factory.annotation.InjectionMetadata;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.beans.factory.xml.XMLBeanDefinitionReader;
import com.yx.myspring.core.io.support.ClassPathResource;
import com.yx.myspring.dao.v4.AccountDao;
import com.yx.myspring.dao.v4.ItemDao;
import com.yx.myspring.service.v4.TestYX;

public class InjectionMetadataTest {

	@Test
	public void testInjectionMetadata() throws Exception {
		DefaultBeanFactory factory=new DefaultBeanFactory();
		XMLBeanDefinitionReader reader=new XMLBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("BeansV4.xml"));
		
		List<InjectionElement> elements=new LinkedList<InjectionElement>();
		{
			Field field=TestYX.class.getDeclaredField("accountDao");
			AutowiredFieldElement element=new AutowiredFieldElement(field, true, factory);
			elements.add(element);
		}
		{
			Field field=TestYX.class.getDeclaredField("itemDao");
			AutowiredFieldElement element=new AutowiredFieldElement(field, true, factory);
			elements.add(element);
		}
		Class<?> targetClass=TestYX.class;
		InjectionMetadata metadata=new InjectionMetadata(targetClass,elements);
		TestYX yx=new TestYX();
		metadata.inject(yx);
		assertTrue(yx.getAccountDao() instanceof AccountDao);
		assertTrue(yx.getItemDao() instanceof ItemDao);
	}

}
