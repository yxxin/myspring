package com.yx.myspring.test.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;

import org.junit.Test;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.context.annotation.ClassPathBeanDefinitionScanner;
import com.yx.myspring.context.annotation.ScannedGenericBeanDefinition;
import com.yx.myspring.core.type.AnnotationMetadata;
import com.yx.myspring.stereotype.Component;

public class ClassPathBeanDefinitionScannerTest {

	@Test
	public void testScannedBean() {
		DefaultBeanFactory factory=new DefaultBeanFactory();
		String basePackages="com.yx.myspring.service.v4,com.yx.myspring.dao.v4";
		ClassPathBeanDefinitionScanner scanner=new ClassPathBeanDefinitionScanner(factory);
		scanner.doScan(basePackages);
		
		TestUtils.testBeanDefinition(factory);
	}

}
