package com.yx.myspring.context.annotation;

import java.util.LinkedHashSet;
import java.util.Set;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.factory.BeanDefinitionException;
import com.yx.myspring.beans.factory.support.BeanDefinitionRegistry;
import com.yx.myspring.beans.factory.support.BeanNameGenerator;
import com.yx.myspring.core.io.Resource;
import com.yx.myspring.core.io.support.PackageResourceLoader;
import com.yx.myspring.core.type.classreading.MetadataReader;
import com.yx.myspring.core.type.classreading.SimpleMetadataReader;
import com.yx.myspring.stereotype.Component;
import com.yx.myspring.util.StringUtils;

public class ClassPathBeanDefinitionScanner {

	private BeanDefinitionRegistry registry;
	private PackageResourceLoader loader=new PackageResourceLoader();
	private BeanNameGenerator generator=new AnnotationBeanNameGenerator();
	public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
		this.registry=registry;
	}
	
	public Set<BeanDefinition> doScan(String packagesToScan) {
		String[] basePackages=StringUtils.tokenizeToStringArray(packagesToScan, ",");
		Set<BeanDefinition> beanDefinitions=new LinkedHashSet<BeanDefinition>();
		for(String basePackage:basePackages) {
			Resource[] resources=loader.getResources(basePackage);
			for(Resource resource: resources) {
				try {
					MetadataReader reader=new SimpleMetadataReader(resource);
					String annotationType=Component.class.getName();
					if(reader.getAnnotationMetadata().hasAnnotation(annotationType)) {
						ScannedGenericBeanDefinition sbd=new ScannedGenericBeanDefinition(reader.getAnnotationMetadata());
						String beanName=generator.generateBeanName(sbd);
						sbd.setID(beanName);
						beanDefinitions.add(sbd);
						this.registry.registerBeanDefinition(beanName, sbd);
					}
				} catch (Throwable ex) {
					throw new BeanDefinitionException(
							"Failed to read candidate component class: " + resource, ex);
				}
			}
		}
		return beanDefinitions;
	}
}
