package com.yx.myspring.context.annotation;

import java.beans.Introspector;
import java.util.LinkedHashMap;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.factory.annotation.AnnotationBeanDefinition;
import com.yx.myspring.beans.factory.support.BeanNameGenerator;
import com.yx.myspring.core.type.AnnotationMetadata;
import com.yx.myspring.util.ClassUtils;
import com.yx.myspring.util.StringUtils;

public class AnnotationBeanNameGenerator implements BeanNameGenerator {

	public String generateBeanName(BeanDefinition bd) {
		if(bd instanceof AnnotationBeanDefinition) {
			String beanName=determineBeanNameFromAnnotation((AnnotationBeanDefinition)bd);
			if(StringUtils.hasText(beanName)) {
				return beanName;
			}
		}
		return buildDefaultBeanName(bd);
	}
	
	private String determineBeanNameFromAnnotation(AnnotationBeanDefinition abd) {
		AnnotationMetadata metadata=abd.getMetadata();
		String beanName=null;
		for(String type: metadata.getAnnotationTypes()) {
			LinkedHashMap<String, Object> attributes=metadata.getAnnotationAttributes(type);
			//TODO 这里循环了所有的注解，而不是Component注解的value值，是不是有问题？
			if(attributes.get("value") != null) {
				Object value=attributes.get("value");
				if(value instanceof String) {
					String name=(String)value;
					if(StringUtils.hasLength(name)) {
						beanName=name;
					}
				}
			}
		}
		return beanName;
	}

	private String buildDefaultBeanName(BeanDefinition bd) {
		String className=bd.getBeanClassName();
		String shortName=ClassUtils.getShortName(className);
		return Introspector.decapitalize(shortName);
	}
	
}
