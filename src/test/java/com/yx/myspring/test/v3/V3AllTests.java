package com.yx.myspring.test.v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ApplicationContextTestV3.class, BeanDefinitionTestV3.class, ConstructorResolverTestV3.class })
public class V3AllTests {
	//0.最终目的：ApplicationContextTestV3测试context
	//1.先需要XMLBeanDefinitionReader中解析相应的结点，保存。使用BeanDefinitionTestV3测试
	//	a.使用ConstructorArgument来存放。BeanDefinition中增加getConstructorArgument()方法。
	//2.新建ConstructorResolver类，调用autowriteConstructor(BeanDefinition bd)从bd中获取构造类。
	//	使用ConstructorResolverTestV3测试此类。
	//	a.testAutowireConstructor()用来测试普通属性。
	//	选择最优构造方法。
	//	新增index属性。
	//	新增type属性。
	//	新增name属性，jdk本身不支持获取方法参数名字，后续引入asm才行。
}
