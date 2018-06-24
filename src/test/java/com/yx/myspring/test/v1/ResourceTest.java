package com.yx.myspring.test.v1;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.yx.myspring.core.io.Resource;
import com.yx.myspring.core.io.support.ClassPathResource;
import com.yx.myspring.core.io.support.FileSystemResource;

public class ResourceTest {

	@Test
	public void testClassPathResource() {
		Resource resource=new ClassPathResource("Beans.xml");
		InputStream in=null;
		try {
			in=resource.getInputStream();
			assertNotNull(in);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} finally {
			if(in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	@Test
	public void testFileSystemResource() {
		Resource resource=new FileSystemResource("src\\test\\resources\\Beans.xml");
		InputStream in=null;
		try {
			in=resource.getInputStream();
			assertNotNull(in);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} finally {
			if(in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
