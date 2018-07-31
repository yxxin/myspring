package com.yx.myspring.beans.factory.annotation;

import java.lang.reflect.Field;

import com.yx.myspring.beans.factory.BeanCreateException;
import com.yx.myspring.beans.factory.config.AutowireCapableBeanFactory;
import com.yx.myspring.beans.factory.config.DependencyDescriptor;
import com.yx.myspring.util.ReflectionUtils;

public class AutowiredFieldElement extends InjectionElement {

	private boolean required;
	public AutowiredFieldElement(Field field, boolean required, AutowireCapableBeanFactory factory) {
		super(field, factory);
		this.required=required;
	}
	
	public Field getField() {
		return (Field)this.member;
	}

	@Override
	public void inject(Object target) {
		Field field=this.getField();
		try {
			DependencyDescriptor descriptor=new DependencyDescriptor(field, this.required);
			Object value=this.factory.resolveDependency(descriptor);
			if(value != null) {
				ReflectionUtils.makeAccessible(field);
				field.set(target, value);
			}
		} catch (Throwable ex) {
			throw new BeanCreateException("Could not autowire field: " + field, ex);
		}
	}

}
