package com.yx.myspring.beans.factory.config;

import java.lang.reflect.Field;

import com.yx.myspring.util.Assert;

public class DependencyDescriptor {

	private Field field;
	private boolean required;
	
	public DependencyDescriptor(Field field,boolean required) {
		Assert.notNull(field, "Field must be not null");
		this.field=field;
		this.required=required;
	}
	
	public Class<?> getDependencyType(){
		if(this.field != null) {
			return this.field.getType();
		}
		throw new RuntimeException("only support field dependency");
	}
	
	public boolean isRequired() {
		return this.required;
	}
}
