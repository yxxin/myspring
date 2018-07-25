package com.yx.myspring.test.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;

import org.junit.Test;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.beans.factory.xml.XMLBeanDefinitionReader;
import com.yx.myspring.context.annotation.ScannedGenericBeanDefinition;
import com.yx.myspring.core.io.support.ClassPathResource;
import com.yx.myspring.core.type.AnnotationMetadata;
import com.yx.myspring.stereotype.Component;

public class XMLBeanDefinitionReaderTest {

	@Test
	public void testParseScanedBean() {
		DefaultBeanFactory factory =new DefaultBeanFactory();
		XMLBeanDefinitionReader reader=new XMLBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("BeansV4.xml"));
		
		TestUtils.testBeanDefinition(factory);
	}

}
