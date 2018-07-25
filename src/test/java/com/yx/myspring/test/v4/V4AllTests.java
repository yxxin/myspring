package com.yx.myspring.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.yx.myspring.core.type.classreading.AnnotationAttributesReadingVisitor;

@RunWith(Suite.class)
@SuiteClasses({ 
	ApplicationContextTestV4.class,
	PackageResourceLoaderTest.class,
	ClassReaderTest.class,
	MetadataReaderTest.class,
	ClassPathBeanDefinitionScannerTest.class,
	XMLBeanDefinitionReaderTest.class})
public class V4AllTests {
	//1.读取包中class文件的功能。PackageResourceLoaderTest--PackageResourceLoader
	//2.使用asm读取class文件信息。ClassReaderTest
	//	类信息：ClassMetadata,ClassMetadataReadingVisitor
	//	注解信息：AnnotationMetadata,AnnotationMetadataReadingVisitor,AnnotationAttributesReadingVisitor
	//3.封装上面的那些东西。MetadataReaderTest--MetadataReader,SimpleMetadataReader
	//4.扫描指定包下的文件，有@Component注解的就注册如BeanFactory中。ClassPathBeanDefinitionScannerTest
	//	AnnotationBeanDefinition,ScannedGenericBeanDefinition,ScannedGenericBeanDefinition
	//  BeanNameGenerator,AnnotationBeanNameGenerator
	//5.修改XMLBeanDefinitionReader，兼容读取context:component-scan。XMLBeanDefinitionReaderTest
}
