package com.yx.myspring.test.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.context.annotation.ScannedGenericBeanDefinition;
import com.yx.myspring.core.type.AnnotationMetadata;
import com.yx.myspring.stereotype.Component;

public class TestUtils {
	public static void testBeanDefinition(DefaultBeanFactory factory) {
		String annotationType=Component.class.getName();
		{
			BeanDefinition bd=factory.getBeanDefinition("yx1");
			assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition sbd=(ScannedGenericBeanDefinition)bd;
			assertEquals("yx1", sbd.getID());
			AnnotationMetadata metadata=sbd.getMetadata();
			assertTrue(metadata.hasAnnotation(annotationType));
			LinkedHashMap<String, Object> attributes=metadata.getAnnotationAttributes(annotationType);
			assertEquals("yx1", attributes.get("value"));
		}
		{
			BeanDefinition bd=factory.getBeanDefinition("accountDao");
			assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition sbd=(ScannedGenericBeanDefinition)bd;
			assertTrue(sbd.getMetadata().hasAnnotation(annotationType));
		}
		{
			BeanDefinition bd=factory.getBeanDefinition("itemDao");
			assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition sbd=(ScannedGenericBeanDefinition)bd;
			assertTrue(sbd.getMetadata().hasAnnotation(annotationType));
		}
	}
}
