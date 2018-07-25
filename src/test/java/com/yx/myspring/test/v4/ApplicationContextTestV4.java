package com.yx.myspring.test.v4;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.yx.myspring.context.ApplicationContext;
import com.yx.myspring.context.support.ClassPathXmlApplicationContext;
import com.yx.myspring.service.v4.TestYX;

public class ApplicationContextTestV4 {

	@Test
	public void testGetBeanProperty() {
		ApplicationContext context=new ClassPathXmlApplicationContext("BeansV4.xml");
		TestYX yx1=(TestYX)context.getBean("yx1");
		assertNotNull(yx1.getAccountDao());
		assertNotNull(yx1.getItemDao());
	}

}
