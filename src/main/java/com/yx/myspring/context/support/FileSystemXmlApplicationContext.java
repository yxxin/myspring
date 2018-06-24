package com.yx.myspring.context.support;

import com.yx.myspring.core.io.Resource;
import com.yx.myspring.core.io.support.FileSystemResource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

	public FileSystemXmlApplicationContext(String path) {
		super(path);
	}

	@Override
	protected Resource getResourceByPath(String path) {
		return new FileSystemResource(path);
	}

}
