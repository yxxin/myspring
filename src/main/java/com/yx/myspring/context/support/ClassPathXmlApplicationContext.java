package com.yx.myspring.context.support;

import com.yx.myspring.core.io.Resource;
import com.yx.myspring.core.io.support.ClassPathResource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

	public ClassPathXmlApplicationContext(String path) {
		super(path);
	}
	
	public ClassPathXmlApplicationContext(String path,ClassLoader cl) {
		super(path,cl);
	}

	@Override
	protected Resource getResourceByPath(String path) {
		return new ClassPathResource(path,this.getBeanClassLoader());
	}

}
