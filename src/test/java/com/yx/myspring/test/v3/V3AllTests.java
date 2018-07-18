package com.yx.myspring.test.v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.yx.myspring.dao.v3.AccountDao;
import com.yx.myspring.dao.v3.ItemDao;

@RunWith(Suite.class)
@SuiteClasses({ ApplicationContextTestV3.class, BeanDefinitionTestV3.class, ConstructorResolverTestV3.class })
public class V3AllTests {
	//0.最终目的：ApplicationContextTestV3测试context
	//1.先需要XMLBeanDefinitionReader中解析相应的结点，保存。使用BeanDefinitionTestV3测试
	//	a.使用ConstructorArgument来存放。BeanDefinition中增加getConstructorArgument()方法。
	//2.新建ConstructorResolver类，调用autowriteConstructor(BeanDefinition bd)从bd中获取构造类。
	//	使用ConstructorResolverTestV3测试此类。
	//	a.testAutowireConstructor()用来测试普通属性。
	//	b.选择最优构造方法。新增TestYX(AccountDao accountDao, ItemDao itemDao, String name, String name2)
	//		目前选择的是TestYX(AccountDao accountDao, ItemDao itemDao, String name, int num)，
	//		而实际新增的构造方法匹配度更高。新增后测试用例报错，修改ConstructorResolver选择最优构造方法，让测试用例通过。
	//	c.新增index属性。使用testAutowireConstrutorIndex()测试。
	//		问题：index属性重复的问题，负数问题，非数字问题，超过了参数个数问题。ConstructorArgument新增index相关的map。
	//		(1)异常测试（index属性重复的问题，负数问题，非数字问题，超过了参数个数问题）：BeansV3.xml中的yx3。
	//		(2)ConstructorArgument中argumentValues，indexArgumentValues增加获取单个ValueHolder的方法。以及一个getValueHolder方法。
	//			ConstructorResolver中获取ValueHolder的方式也改下。
	//	新增type属性。
	//	新增name属性，jdk本身不支持获取方法参数名字，后续引入asm才行。
}
