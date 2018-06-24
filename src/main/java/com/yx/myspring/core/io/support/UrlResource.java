package com.yx.myspring.core.io.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.yx.myspring.core.io.Resource;
import com.yx.myspring.util.Assert;

public class UrlResource implements Resource {
	private String path;
	private URL url;
	public UrlResource(String path) {
		Assert.notNull(path, "Path must be not null");
		this.path=path;
		try {
			this.url=new URL(path);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public InputStream getInputStream() throws IOException {
		URLConnection conn=this.url.openConnection();
		return conn.getInputStream();
	}

}
