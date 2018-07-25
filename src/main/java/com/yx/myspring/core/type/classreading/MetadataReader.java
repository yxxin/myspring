package com.yx.myspring.core.type.classreading;

import com.yx.myspring.core.io.Resource;
import com.yx.myspring.core.type.AnnotationMetadata;
import com.yx.myspring.core.type.ClassMetadata;

public interface MetadataReader {
	Resource getResource();
	ClassMetadata getClassMetadata();
	AnnotationMetadata getAnnotationMetadata();
}
