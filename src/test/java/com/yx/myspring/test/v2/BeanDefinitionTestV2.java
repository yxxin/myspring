package com.yx.myspring.test.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.PropertyValue;
import com.yx.myspring.beans.factory.config.RuntimeBeanReference;
import com.yx.myspring.beans.factory.config.TypedStringValue;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.beans.factory.xml.XMLBeanDefinitionReader;
import com.yx.myspring.core.io.support.ClassPathResource;

public class BeanDefinitionTestV2 {
	
	@Test
	public void testGetBeanDefinition() {
		DefaultBeanFactory factory=new DefaultBeanFactory();
		XMLBeanDefinitionReader reader=new XMLBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("BeansV2.xml"));
		
		BeanDefinition bd=factory.getBeanDefinition("yx1");
		List<PropertyValue> pvs=bd.getPropertyValues();
		assertEquals(4, pvs.size());
		{
			PropertyValue pv=findPV(pvs,"accountDao");
			assertNotNull(pv);
			assertTrue(pv.getValue() instanceof RuntimeBeanReference);
		}
		{
			PropertyValue pv=findPV(pvs,"itemDao");
			assertNotNull(pv);
			assertTrue(pv.getValue() instanceof RuntimeBeanReference);
		}
		{
			PropertyValue pv=findPV(pvs,"name");
			assertNotNull(pv);
			assertTrue( pv.getValue() instanceof TypedStringValue);
			assertEquals("yx", ((TypedStringValue)pv.getValue()).getValue());
		}
	}
	
	public PropertyValue findPV(List<PropertyValue> pvs,String name) {
		for(PropertyValue pv:pvs) {
			if(pv.getName().equals(name)) {
				return pv;
			}
		}
		return null;
	}
	
}
