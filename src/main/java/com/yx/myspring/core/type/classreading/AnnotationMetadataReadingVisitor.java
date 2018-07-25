package com.yx.myspring.core.type.classreading;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Type;

import com.yx.myspring.core.type.AnnotationMetadata;

public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata {

	private Set<String> annotationTypes=new LinkedHashSet<String>(4);
	private Map<String, LinkedHashMap<String,Object>> attributeMap=new LinkedHashMap<String, LinkedHashMap<String,Object>>(4);
	public AnnotationMetadataReadingVisitor() {
	}
	
	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		Type type=Type.getType(desc);
		String className=type.getClassName();
		this.annotationTypes.add(className);
		return new AnnotationAttributesReadingVisitor(className, this.attributeMap);
	}

	public boolean hasAnnotation(String annotationType) {
		return this.annotationTypes.contains(annotationType);
	}

	public Set<String> getAnnotationTypes() {
		return this.annotationTypes;
	}

	public LinkedHashMap<String, Object> getAnnotationAttributes(String annotationType) {
		return this.attributeMap.get(annotationType);
	}

}
