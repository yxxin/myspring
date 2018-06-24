package com.yx.myspring.test.v1;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

import com.yx.myspring.TestYX;
import com.yx.myspring.context.ApplicationContext;
import com.yx.myspring.context.support.ClassPathXmlApplicationContext;
import com.yx.myspring.context.support.FileSystemXmlApplicationContext;
import com.yx.myspring.context.support.UrlXmlApplicationContext;

public class ApplicationContextTest {

	@Test
	public void testClassPathXmlApplicationContext() {
		ApplicationContext context=new ClassPathXmlApplicationContext("Beans.xml");
		TestYX testyx=(TestYX)context.getBean("testyx");
		assertNotNull(testyx);
	}
	@Test
	public void testFileSystemXmlApplicationContext() {
		ApplicationContext context=new FileSystemXmlApplicationContext("src\\test\\resources\\Beans.xml");
		TestYX testyx=(TestYX)context.getBean("testyx");
		assertNotNull(testyx);
	}
	@Test
	public void testUrlXmlApplicationContext() {
		String path="jar:file:/D:/yx/code/workspace/springcreateworkspace/tdd-demo.jar!/User.xml";
		String jarFilePath = "D:/yx/code/workspace/springcreateworkspace/tdd-demo.jar";  
		
		URL jarUrl;
		try {
			jarUrl = new File(jarFilePath).toURI().toURL();
			URL[] urls = new URL[] { jarUrl };  
	        URLClassLoader cl = new URLClassLoader(urls); 
	        
	        ApplicationContext context=new UrlXmlApplicationContext(path, cl);
	        Object useryx=context.getBean("user");
	        assertNotNull(useryx);
	        
	        Method methodSay=useryx.getClass().getDeclaredMethod("say");
			Method methodSet=useryx.getClass().getDeclaredMethod("setName",String.class);
			methodSet.invoke(useryx, "testExternalApplicationContext:	yxtest");
			methodSay.invoke(useryx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
