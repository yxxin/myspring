package com.yx.myspring.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.yx.myspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.yx.myspring.core.type.classreading.AnnotationAttributesReadingVisitor;

@RunWith(Suite.class)
@SuiteClasses({ 
	ApplicationContextTestV4.class,
	PackageResourceLoaderTest.class,
	ClassReaderTest.class,
	MetadataReaderTest.class,
	ClassPathBeanDefinitionScannerTest.class,
	XMLBeanDefinitionReaderTest.class,
	DependencyDescriptorTest.class,
	InjectionMetadataTest.class,
	AutowiredAnnotationProcessorTest.class})
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
	
	//1.增加Autowired注解。在DefaultBeanFactory.populateBean中增加解析该注解的方法，先放着。
	//	目前只支持属性注入field。增加DependencyDescriptor用于描述属性、方法等信息。
	//	BeanFactory系列增加resolveDependency(DependencyDescriptor)方法用于获取相应实例。
	//	使用DependencyDescriptorTest测试。
	//2.实现注入的类InjectionElement，AutowiredFieldElement。InjectionMetadata存储了一个InjectionElement列表，遍历执行inject方法。
	//	使用InjectionMetadataTest测试。
	//3.实现AutowiredAnnotationProcessor，调用buildAutowiringMetadata(Class<?> clazz)即可以生成一个InjectionMetadata。
	//	使用AutowiredAnnotationProcessorTest测试。
	//4.在合适的地方调用上面生成的InjectionMetadata.inject方法。在DefaultBeanFactory.populateBean中增加上面3的东西即可。
	//5.增加生命周期相关的方式：增加BeanPostProcessor,InstantiationAwareBeanPostProcessor。
	//	然后AutowiredAnnotationProcessor实现它。实现postProcessPropertyValues方法，调用inject方法。
	//	a.ConfigurableBeanFactory增加addBeanPostProcessor和getBeanPostProcessor方法。
	//  	DefaultBeanFactory中实现这两个方法。
	//	b.AbstractApplicationContext.registerBeanPostProcessor(ConfigurableBeanFactory)中创建AutowiredAnnotationProcessor并注册进factory中。
	//		AbstractApplicationContext构造方法中调用registerBeanPostProcessor方法。
	//	c.在DefaultBeanFactory中的populateBean中解析BeanPostProcessor，并执行InstantiationAwareBeanPostProcessor的postProcessPropertyValues方法实现注入。
}
