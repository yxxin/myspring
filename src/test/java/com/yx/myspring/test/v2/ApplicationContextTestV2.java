package com.yx.myspring.test.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.yx.myspring.context.ApplicationContext;
import com.yx.myspring.context.support.ClassPathXmlApplicationContext;
import com.yx.myspring.dao.v2.AccountDao;
import com.yx.myspring.dao.v2.ItemDao;
import com.yx.myspring.service.v2.TestYX;

public class ApplicationContextTestV2 {
	
	@Test
	public void testGetBean() {
		ApplicationContext context=new ClassPathXmlApplicationContext("BeansV2.xml");
		TestYX yx=(TestYX)context.getBean("yx1");
		AccountDao accountDao=(AccountDao)context.getBean("accountDao");
		ItemDao itemDao=(ItemDao)context.getBean("itemDao");
		assertNotNull(yx);
		assertNotNull(accountDao);
		assertNotNull(itemDao);
		
		assertNotNull(yx.getAccountDao());
		assertNotNull(yx.getItemDao());
		assertNotNull(yx.getName());
		assertNotNull(yx.getNum());
		
		assertEquals(1, yx.getNum());
		assertEquals("yx", yx.getName());
	}
	
}
