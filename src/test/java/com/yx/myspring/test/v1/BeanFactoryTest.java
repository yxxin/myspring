package com.yx.myspring.test.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

import com.yx.myspring.TestYX;
import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.beans.factory.xml.XMLBeanDefinitionReader;
import com.yx.myspring.core.io.Resource;
import com.yx.myspring.core.io.support.ClassPathResource;
import com.yx.myspring.core.io.support.UrlResource;

public class BeanFactoryTest {

	@Test
	public void testBean() {
		DefaultBeanFactory factory=new DefaultBeanFactory();
//		factory.setBeanClassLoader(cl);
		XMLBeanDefinitionReader reader=new XMLBeanDefinitionReader(factory);
		Resource rs=new ClassPathResource("Beans.xml");
		reader.loadBeanDefinitions(rs);
		TestYX testyx=(TestYX)factory.getBean("testyx");
		assertNotNull(testyx);
		BeanDefinition bd=factory.getBeanDefinition("testyx");
		assertNotNull(bd);
		assertEquals("com.yx.myspring.TestYX", bd.getBeanClassName());
	}
	
	@Test
	public void testScope() {
		DefaultBeanFactory factory=new DefaultBeanFactory();
		XMLBeanDefinitionReader reader=new XMLBeanDefinitionReader(factory);
		Resource rs=new ClassPathResource("Beans.xml");
		reader.loadBeanDefinitions(rs);
		TestYX testyx1=(TestYX)factory.getBean("testyx");
		TestYX testyx2=(TestYX)factory.getBean("testyx");
		assertTrue(testyx1==testyx2);
		TestYX testyx21=(TestYX)factory.getBean("testyx2");
		TestYX testyx22=(TestYX)factory.getBean("testyx2");
		assertTrue(testyx21!=testyx22);
	}

	
	@Test
	public void testExternalBean() {
		DefaultBeanFactory factory=new DefaultBeanFactory();
		XMLBeanDefinitionReader reader=new XMLBeanDefinitionReader(factory);
		String path="jar:file:/D:/yx/code/workspace/springcreateworkspace/tdd-demo.jar!/User.xml";
		Resource rs=new UrlResource(path);
		reader.loadBeanDefinitions(rs);
		//加载外部xml文件
		BeanDefinition bd=factory.getBeanDefinition("user");
		assertNotNull(bd);
		assertEquals("com.yx.test.external.User", bd.getBeanClassName());
		
		//加载外部class文件
		String jarFilePath = "D:/yx/code/workspace/springcreateworkspace/tdd-demo.jar";  
        URL jarUrl;
		try {
			jarUrl = new File(jarFilePath).toURI().toURL();
	        URL[] urls = new URL[] { jarUrl };  
	        URLClassLoader cl = new URLClassLoader(urls);  
	        factory.setBeanClassLoader(cl);
			
			Object useryx=factory.getBean("user");
			assertNotNull(useryx);
			Method methodSay=useryx.getClass().getDeclaredMethod("say");
			Method methodSet=useryx.getClass().getDeclaredMethod("setName",String.class);
			methodSet.invoke(useryx, "yxtest");
			methodSay.invoke(useryx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
