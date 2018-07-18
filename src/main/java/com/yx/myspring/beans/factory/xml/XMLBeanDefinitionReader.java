package com.yx.myspring.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.ConstructorArgument;
import com.yx.myspring.beans.ConstructorArgument.ValueHolder;
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
				parseConstructorArgElements(ele,bd);
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
	
	private void parseConstructorArgElements(Element beanEle, BeanDefinition bd) {
		Iterator<?> iter=beanEle.elementIterator(BeanDefinitionParser.CONSTRUCTOR_ARG_ATTRIBUTE);
		while(iter.hasNext()) {
			Element conEle= (Element)iter.next();
			parseConstructorArgElement(conEle,bd);
		}
		checkConstructorArgIndex(bd);
	}
	private void parseConstructorArgElement(Element conEle, BeanDefinition bd) {
		String typeAttr=conEle.attributeValue(BeanDefinitionParser.TYPE_ATTRIBUTE);
		String nameAttr=conEle.attributeValue(BeanDefinitionParser.NAME_ATTRIBUTE);
		String indexAttr=conEle.attributeValue(BeanDefinitionParser.INDEX_ATTRIBUTE);
		ConstructorArgument args=bd.getConstructorArgument();
		
		if(StringUtils.hasText(indexAttr)) {
			int index=0;
			try {
				index=Integer.parseInt(indexAttr);
			}catch(Exception e) {
				throw new RuntimeException("Attribute 'index' of tag 'constructor-arg' must be an integer");
			}
			if(index<0) {
				throw new RuntimeException("'index' cannot be lower than 0");
			}
			if(args.hasIndexArgumentValues(index)) {
				throw new RuntimeException("Ambiguous constructor-arg entries for index " + index);
			}
			Object val=parsePropertyValue(conEle,null);
			ValueHolder valueHolder = new ValueHolder(val);
			if(StringUtils.hasText(typeAttr)) {
				valueHolder.setType(typeAttr);
			}
			if(StringUtils.hasText(nameAttr)) {
				valueHolder.setName(nameAttr);
			}
			args.addIndexArgumentValue(index, valueHolder);
		}else {
			Object val=parsePropertyValue(conEle,null);
			ValueHolder valueHolder = new ValueHolder(val);
			if(StringUtils.hasText(typeAttr)) {
				valueHolder.setType(typeAttr);
			}
			if(StringUtils.hasText(nameAttr)) {
				valueHolder.setName(nameAttr);
			}
			args.addArgumentValue(valueHolder);
		}
	}
	private void checkConstructorArgIndex(BeanDefinition bd) {
		Set<Integer> indexs= bd.getConstructorArgument().getIndexArgumentValues().keySet();
		if(indexs==null || indexs.isEmpty()) {
			return;
		}
		Object[] obj = indexs.toArray();
		Arrays.sort(obj);
		if(((Integer)obj[obj.length-1]+1) > bd.getConstructorArgument().getValueHoldersCount()) {
			throw new RuntimeException("配置构造参数个数"+bd.getConstructorArgument().getValueHoldersCount()+" �����������ƥ��"+obj[obj.length - 1] );
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
		String elementName = (propName != null) ? "<property> element for property '" + propName + "'"
				: "<constructor-arg> element";
		String propValue= propEle.attributeValue(BeanDefinitionParser.VALUE_ATTRIBUTE);
		String propRef= propEle.attributeValue(BeanDefinitionParser.REF_ATTRIBUTE);

		if(propRef!=null) {
			if(!StringUtils.hasText(propRef)) {
				logger.error(elementName+" contains empty 'ref' attribute");
			}
			RuntimeBeanReference rtBean=new RuntimeBeanReference(propRef);
			return rtBean;
		}else if(propValue!=null) {
			TypedStringValue tv=new TypedStringValue(propValue);
			return tv;
		}else {
			throw new RuntimeException(elementName+" must specify a ref or value");
		}
	}
}
