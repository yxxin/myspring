package com.yx.myspring.core.type;

import java.util.LinkedHashMap;
import java.util.Set;

public interface AnnotationMetadata extends ClassMetadata {
	boolean hasAnnotation(String annotationType);
	Set<String> getAnnotationTypes();
	LinkedHashMap<String,Object> getAnnotationAttributes(String annotationType);
}
