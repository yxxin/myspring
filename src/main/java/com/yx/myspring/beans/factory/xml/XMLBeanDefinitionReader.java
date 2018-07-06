package com.yx.myspring.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.PropertyValue;
import com.yx.myspring.beans.factory.BeanDefinitionException;
import com.yx.myspring.beans.factory.config.RuntimeBeanReference;
import com.yx.myspring.beans.factory.config.TypedStringValue;
import com.yx.myspring.beans.factory.support.BeanDefinitionRegistry;
import com.yx.myspring.beans.factory.support.GenericBeanDefinition;
import com.yx.myspring.core.io.Resource;
import com.yx.myspring.util.StringUtils;

public class XMLBeanDefinitionReader {
	private BeanDefinitionRegistry registry;
	private Log logger=LogFactory.getLog(this.getClass()); 
	public XMLBeanDefinitionReader(BeanDefinitionRegistry registry) {
		this.registry=registry;	
	}
	public void loadBeanDefinitions(Resource rs) {
		InputStream in=null;
		try {
			SAXReader reader=new SAXReader();
			in=rs.getInputStream();
			Document document=reader.read(in);
			Element note=document.getRootElement();
			Iterator<Element> iter=note.elementIterator();
			while(iter.hasNext()) {
				Element ele=iter.next();
				String id=ele.attributeValue(BeanDefinitionParser.ID_ATTRIBUTE);
				String className=ele.attributeValue(BeanDefinitionParser.CLASS_ATTRIBUTE);
				String scope=ele.attributeValue(BeanDefinitionParser.SCOPE_ATTRIBUTE);
				BeanDefinition bd=new GenericBeanDefinition(id, className);
				if(scope!=null) {
					bd.setScope(scope);
				}
				parsePropertyElement(ele,bd);
				this.registry.registerBeanDefinition(id, bd);
			}
		} catch (Exception e) {
			throw new BeanDefinitionException("",e);
		} finally {
			if(in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void parsePropertyElement(Element beanEle, BeanDefinition bd) {
		Iterator<?> iter=beanEle.elementIterator(BeanDefinitionParser.PROPERTY_ATTRIBUTE);
		while(iter.hasNext()) {
			Element propEle= (Element)iter.next();
			String propName= propEle.attributeValue(BeanDefinitionParser.NAME_ATTRIBUTE);
			if(!StringUtils.hasText(propName)) {
				logger.fatal("Tag 'property' must have a 'name' attribute");
				return;
			}
			Object val=parsePropertyValue(propEle, propName);
			PropertyValue pv=new PropertyValue(propName, val);
			bd.getPropertyValues().add(pv);
		}
	}
	private Object parsePropertyValue(Element propEle, String propName) {
		String propValue= propEle.attributeValue(BeanDefinitionParser.VALUE_ATTRIBUTE);
		String propRef= propEle.attributeValue(BeanDefinitionParser.REF_ATTRIBUTE);

		if(propRef!=null) {
			if(!StringUtils.hasText(propRef)) {
				logger.error("<property> "+propName+" contains empty 'ref' attribute");
			}
			RuntimeBeanReference rtBean=new RuntimeBeanReference(propRef);
			return rtBean;
		}else if(propValue!=null) {
			TypedStringValue tv=new TypedStringValue(propValue);
			return tv;
		}else {
			throw new RuntimeException("<property> "+propName+" must specify a ref or value");
		}
	}
}
