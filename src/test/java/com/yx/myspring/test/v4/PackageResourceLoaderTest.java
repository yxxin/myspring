package com.yx.myspring.test.v4;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.yx.myspring.core.io.Resource;
import com.yx.myspring.core.io.support.PackageResourceLoader;

public class PackageResourceLoaderTest {

	@Test
	public void testGetResources() {
		PackageResourceLoader loader=new PackageResourceLoader();
		Resource[] resources=loader.getResources("com.yx.myspring.dao.v4");
		assertEquals(2, resources.length);
	}

}
