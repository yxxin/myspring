package com.yx.myspring.test.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.junit.Test;

import com.yx.myspring.core.io.support.ClassPathResource;
import com.yx.myspring.core.type.AnnotationMetadata;
import com.yx.myspring.core.type.classreading.MetadataReader;
import com.yx.myspring.core.type.classreading.SimpleMetadataReader;
import com.yx.myspring.stereotype.Component;

public class MetadataReaderTest {

	@Test
	public void testGetMetadata() throws IOException {
		ClassPathResource resource=new ClassPathResource("com/yx/myspring/service/v4/TestYX.class");
		MetadataReader reader=new SimpleMetadataReader(resource);
		
		AnnotationMetadata amd=reader.getAnnotationMetadata();
		
		assertFalse(amd.isAbstract());
		assertFalse(amd.isFinal());
		assertFalse(amd.isInterface());
		assertEquals("com.yx.myspring.service.v4.TestYX", amd.getClassName());
		assertEquals("java.lang.Object", amd.getSuperClassName());
		assertEquals(0, amd.getInterfaceNames().length);
		
		String annotationType=Component.class.getName();
		assertTrue(amd.hasAnnotation(annotationType));
		LinkedHashMap<String, Object> attributes=amd.getAnnotationAttributes(annotationType);
		assertEquals("yx1", attributes.get("value"));
	}

}
