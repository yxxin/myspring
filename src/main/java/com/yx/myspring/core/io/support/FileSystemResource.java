package com.yx.myspring.core.io.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.yx.myspring.core.io.Resource;
import com.yx.myspring.util.Assert;

public class FileSystemResource implements Resource {
	private String path;
	private File file;
	public FileSystemResource(String path) {
		Assert.notNull(path, "Path must be not null");
		this.path=path;
		this.file=new File(path);
	}

	public FileSystemResource(File file) {
		this.file=file;
		this.path=file.getPath();
	}

	public InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}

}
