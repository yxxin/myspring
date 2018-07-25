package com.yx.myspring.beans.factory.annotation;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.core.type.AnnotationMetadata;

public interface AnnotationBeanDefinition extends BeanDefinition {
	AnnotationMetadata getMetadata();
}
