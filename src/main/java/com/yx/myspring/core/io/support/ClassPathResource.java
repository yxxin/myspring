package com.yx.myspring.core.io.support;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.yx.myspring.core.io.Resource;
import com.yx.myspring.util.ClassUtils;

public class ClassPathResource implements Resource {
	private String path;
	private ClassLoader classLoader;
	public ClassPathResource(String path) {
		this(path,null);
	}
	public ClassPathResource(String path,ClassLoader classLoader) {
		this.path=path;
		this.classLoader=(classLoader!=null) ? classLoader : ClassUtils.getDefaultClassLoader();
	}
	public InputStream getInputStream() throws IOException {
		InputStream in=this.classLoader.getResourceAsStream(path);
		if(in==null) {
			throw new FileNotFoundException(path+" cannot be open");
		}
		return in;
	}

}
