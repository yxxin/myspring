package com.yx.myspring.test.v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.yx.myspring.dao.v3.AccountDao;
import com.yx.myspring.dao.v3.ItemDao;

@RunWith(Suite.class)
@SuiteClasses({ ApplicationContextTestV3.class, BeanDefinitionTestV3.class, ConstructorResolverTestV3.class })
public class V3AllTests {
	//0.����Ŀ�ģ�ApplicationContextTestV3����context
	//1.����ҪXMLBeanDefinitionReader�н�����Ӧ�Ľ�㣬���档ʹ��BeanDefinitionTestV3����
	//	a.ʹ��ConstructorArgument����š�BeanDefinition������getConstructorArgument()������
	//2.�½�ConstructorResolver�࣬����autowriteConstructor(BeanDefinition bd)��bd�л�ȡ�����ࡣ
	//	ʹ��ConstructorResolverTestV3���Դ��ࡣ
	//	a.testAutowireConstructor()����������ͨ���ԡ�
	//	b.ѡ�����Ź��췽��������TestYX(AccountDao accountDao, ItemDao itemDao, String name, String name2)
	//		Ŀǰѡ�����TestYX(AccountDao accountDao, ItemDao itemDao, String name, int num)��
	//		��ʵ�������Ĺ��췽��ƥ��ȸ��ߡ�������������������޸�ConstructorResolverѡ�����Ź��췽�����ò�������ͨ����
	//	����index���ԡ�
	//	����type���ԡ�
	//	����name���ԣ�jdk����֧�ֻ�ȡ�����������֣���������asm���С�
}
