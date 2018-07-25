package com.yx.myspring.core.type.classreading;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.asm.ClassReader;

import com.yx.myspring.core.io.Resource;
import com.yx.myspring.core.type.AnnotationMetadata;
import com.yx.myspring.core.type.ClassMetadata;

public class SimpleMetadataReader implements MetadataReader {

	private Resource resource;
	private ClassMetadata classMetadata;
	private AnnotationMetadata annotationMetadata;
	public SimpleMetadataReader(Resource resource) throws IOException {
		InputStream in=new BufferedInputStream(resource.getInputStream());
		ClassReader reader;
		try{
			reader=new ClassReader(in);
		}finally {
			in.close();
		}
		AnnotationMetadataReadingVisitor visitor=new AnnotationMetadataReadingVisitor();
		reader.accept(visitor, ClassReader.SKIP_DEBUG);
		
		this.resource=resource;
		this.classMetadata=visitor;
		this.annotationMetadata=visitor;
	}
	public Resource getResource() {
		return this.resource;
	}

	public ClassMetadata getClassMetadata() {
		return this.classMetadata;
	}

	public AnnotationMetadata getAnnotationMetadata() {
		return this.annotationMetadata;
	}

}
