package com.yx.myspring.core.type.classreading;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;

public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {

	private String annotationType;
	private LinkedHashMap<String,Object> attributes=new LinkedHashMap<String, Object>(4);
	private Map<String, LinkedHashMap<String, Object>> attributeMap;
	public AnnotationAttributesReadingVisitor(String annotationType,
			Map<String, LinkedHashMap<String, Object>> attributeMap) {
		super(SpringAsmInfo.ASM_VERSION);
		this.annotationType=annotationType;
		this.attributeMap=attributeMap;
	}

	@Override
	public void visit(String attributeName, Object attributeValue) {
		this.attributes.put(attributeName, attributeValue);
	}

	@Override
	public void visitEnd() {
		this.attributeMap.put(this.annotationType, this.attributes);
	}

}

