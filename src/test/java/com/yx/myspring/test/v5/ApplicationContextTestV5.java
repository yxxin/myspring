package com.yx.myspring.test.v5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.yx.myspring.context.ApplicationContext;
import com.yx.myspring.context.support.ClassPathXmlApplicationContext;
import com.yx.myspring.service.v5.TestYX;
import com.yx.myspring.util.MessageTracker;

public class ApplicationContextTestV5 {

	@Before
	public void begin() {
		MessageTracker.clearMessage();
	}
	@Test
	public void testYX1() {
		ApplicationContext context=new ClassPathXmlApplicationContext("BeansV5.xml");
		TestYX yx=(TestYX)context.getBean("yx1");
		assertNotNull(yx.getAccountDao());
		assertNotNull(yx.getItemDao());
		
		yx.test1();
		assertEquals(3, MessageTracker.getMessage().size());
		assertEquals("start tx", MessageTracker.getMessage().get(0));
		assertEquals("testyx 1", MessageTracker.getMessage().get(1));
		assertEquals("commit tx", MessageTracker.getMessage().get(2));
	}

}
