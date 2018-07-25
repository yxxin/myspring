package com.yx.myspring.test.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.junit.Test;
import org.springframework.asm.ClassReader;

import com.yx.myspring.core.io.support.ClassPathResource;
import com.yx.myspring.core.type.classreading.AnnotationMetadataReadingVisitor;
import com.yx.myspring.core.type.classreading.ClassMetadataReadingVisitor;
import com.yx.myspring.stereotype.Component;

public class ClassReaderTest {

	@Test
	public void testGetClassMetadata() {
		ClassPathResource resource=new ClassPathResource("com/yx/myspring/service/v4/TestYX.class");
		try {
			ClassReader reader=new ClassReader(resource.getInputStream());
			ClassMetadataReadingVisitor visitor=new ClassMetadataReadingVisitor();
			reader.accept(visitor, ClassReader.SKIP_DEBUG);
			
			assertFalse(visitor.isAbstract());
			assertFalse(visitor.isFinal());
			assertFalse(visitor.isInterface());
			assertEquals("com.yx.myspring.service.v4.TestYX", visitor.getClassName());
			assertEquals("java.lang.Object", visitor.getSuperClassName());
			assertEquals(0, visitor.getInterfaceNames().length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAnnotationMetadata() {
		ClassPathResource resource=new ClassPathResource("com/yx/myspring/service/v4/TestYX.class");
		try {
			ClassReader reader=new ClassReader(resource.getInputStream());
			AnnotationMetadataReadingVisitor visitor=new AnnotationMetadataReadingVisitor();
			reader.accept(visitor, ClassReader.SKIP_DEBUG);
			
			String annotationType=Component.class.getName();
			assertTrue(visitor.hasAnnotation(annotationType));
			LinkedHashMap<String, Object> attributes=visitor.getAnnotationAttributes(annotationType);
			assertEquals("yx1", attributes.get("value"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
