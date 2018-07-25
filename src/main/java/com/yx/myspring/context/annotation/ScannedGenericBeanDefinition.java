package com.yx.myspring.context.annotation;

import com.yx.myspring.beans.factory.annotation.AnnotationBeanDefinition;
import com.yx.myspring.beans.factory.support.GenericBeanDefinition;
import com.yx.myspring.core.type.AnnotationMetadata;

public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotationBeanDefinition {

	private AnnotationMetadata metadata;
	public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
		super();
		this.metadata=metadata;
		setBeanClassName(metadata.getClassName());
	}

	public AnnotationMetadata getMetadata() {
		return this.metadata;
	}

}
