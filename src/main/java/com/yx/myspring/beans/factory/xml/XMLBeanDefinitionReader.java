package com.yx.myspring.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.factory.BeanDefinitionException;
import com.yx.myspring.beans.factory.support.BeanDefinitionRegistry;
import com.yx.myspring.beans.factory.support.GenericBeanDefinition;
import com.yx.myspring.core.io.Resource;

public class XMLBeanDefinitionReader {
	private BeanDefinitionRegistry registry;
	public XMLBeanDefinitionReader(BeanDefinitionRegistry registry) {
		this.registry=registry;	
	}
	public void loadBeanDefinitions(Resource rs) {
		// TODO Auto-generated method stub
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
				this.registry.registerBeanDefinition(id, bd);
			}
		} catch (Exception e) {
			throw new BeanDefinitionException("",e);
		} finally {
			if(in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
