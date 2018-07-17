package com.yx.myspring.test.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.yx.myspring.context.ApplicationContext;
import com.yx.myspring.context.support.ClassPathXmlApplicationContext;
import com.yx.myspring.service.v3.TestYX;

public class ApplicationContextTestV3 {

	@Test
	public void testGetBeanConstructor() {
		ApplicationContext context=new ClassPathXmlApplicationContext("BeansV3.xml");
		TestYX yx=(TestYX)context.getBean("yx1");
		assertNotNull(yx.getAccountDao());
		assertNotNull(yx.getItemDao());
		assertEquals("yx", yx.getName());
		assertEquals(28, yx.getNum());
	}

}
