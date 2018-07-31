package com.yx.myspring.beans.factory.annotation;

import java.util.List;

public class InjectionMetadata {

	private Class<?> targetClass;
	private List<InjectionElement> injectionElements;
	
	public InjectionMetadata(Class<?> targetClass,List<InjectionElement> injectionElements) {
		this.targetClass=targetClass;
		this.injectionElements=injectionElements;
	}
	
	public List<InjectionElement> getInjectionElements() {
		return injectionElements;
	}
	
	public void inject(Object target) {
		if(this.injectionElements == null || this.injectionElements.size()==0) {
			return;
		}
		for(InjectionElement element: this.injectionElements) {
			element.inject(target);
		}
	}
	
}
