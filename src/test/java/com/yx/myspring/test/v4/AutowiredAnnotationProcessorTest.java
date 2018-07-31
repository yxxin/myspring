package com.yx.myspring.test.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.yx.myspring.beans.factory.annotation.AutowiredAnnotationProcessor;
import com.yx.myspring.beans.factory.annotation.AutowiredFieldElement;
import com.yx.myspring.beans.factory.annotation.InjectionElement;
import com.yx.myspring.beans.factory.annotation.InjectionMetadata;
import com.yx.myspring.beans.factory.config.DependencyDescriptor;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.dao.v4.AccountDao;
import com.yx.myspring.dao.v4.ItemDao;
import com.yx.myspring.service.v4.TestYX;

public class AutowiredAnnotationProcessorTest {

	AccountDao accountDao=new AccountDao();
	ItemDao itemDao=new ItemDao();
	DefaultBeanFactory factory=new DefaultBeanFactory() {

		@Override
		public Object resolveDependency(DependencyDescriptor descriptor) {
			if(descriptor.getDependencyType() == AccountDao.class) {
				return accountDao;
			}
			if(descriptor.getDependencyType() == ItemDao.class) {
				return itemDao;
			}
			throw new RuntimeException("can't support types except AccountDao and ItemDao");
		}
		
	};
	
	@Test
	public void testGetInjectionMetadata() {
		
		AutowiredAnnotationProcessor processor=new AutowiredAnnotationProcessor();
		processor.setBeanFactory(factory);
		InjectionMetadata metadata=processor.buildAutowiringMetadata(TestYX.class);
		List<InjectionElement> elements=metadata.getInjectionElements();
		assertEquals(2, elements.size());
		assertFieldExists(elements, "accountDao");
		assertFieldExists(elements, "itemDao");
		
		TestYX yx=new TestYX();
		metadata.inject(yx);
		assertTrue(yx.getAccountDao() instanceof AccountDao);
		assertTrue(yx.getItemDao() instanceof ItemDao);
	}
	
	public void assertFieldExists(List<InjectionElement> elements,String fieldName) {
		for(InjectionElement ele: elements) {
			AutowiredFieldElement field=(AutowiredFieldElement)ele;
			if(field.getField().getName().equals(fieldName)) {
				return;
			}
		}
		fail(fieldName + " does not exist!");
	}

}
