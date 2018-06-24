package com.yx.myspring.context.support;

import com.yx.myspring.core.io.Resource;
import com.yx.myspring.core.io.support.UrlResource;

public class UrlXmlApplicationContext extends AbstractApplicationContext {

	public UrlXmlApplicationContext(String path) {
		super(path);
	}
	
	public UrlXmlApplicationContext(String path,ClassLoader cl) {
		super(path,cl);
	}

	@Override
	protected Resource getResourceByPath(String path) {
		return new UrlResource(path);
	}

}
