package com.yx.myspring.test.v3;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.ConstructorArgument;
import com.yx.myspring.beans.ConstructorArgument.ValueHolder;
import com.yx.myspring.beans.factory.config.RuntimeBeanReference;
import com.yx.myspring.beans.factory.config.TypedStringValue;
import com.yx.myspring.beans.factory.support.DefaultBeanFactory;
import com.yx.myspring.beans.factory.xml.XMLBeanDefinitionReader;
import com.yx.myspring.core.io.support.ClassPathResource;

public class BeanDefinitionTestV3 {

	@Test
	public void testConstructorArgument() {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("BeansV3.xml"));

		BeanDefinition bd = factory.getBeanDefinition("yx1");
		assertEquals("com.yx.myspring.service.v3.TestYX", bd.getBeanClassName());
		ConstructorArgument args=bd.getConstructorArgument();
		List<ValueHolder> valueHolders=args.getArgumentValues();
		assertEquals(4, args.getValueHoldersCount());
		RuntimeBeanReference ref1=(RuntimeBeanReference)valueHolders.get(0).getValue();
		assertEquals("accountDao", ref1.getBeanName());
		RuntimeBeanReference ref2=(RuntimeBeanReference)valueHolders.get(1).getValue();
		assertEquals("itemDao", ref2.getBeanName());
		TypedStringValue ref3=(TypedStringValue)valueHolders.get(2).getValue();
		assertEquals("yx", ref3.getValue());
		TypedStringValue ref4=(TypedStringValue)valueHolders.get(3).getValue();
		assertEquals("28", ref4.getValue());
	}

}
