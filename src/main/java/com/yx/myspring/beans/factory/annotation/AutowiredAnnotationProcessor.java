package com.yx.myspring.beans.factory.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.yx.myspring.beans.BeanException;
import com.yx.myspring.beans.factory.BeanCreateException;
import com.yx.myspring.beans.factory.config.AutowireCapableBeanFactory;
import com.yx.myspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.yx.myspring.core.annotation.AnnotationUtils;
import com.yx.myspring.util.ReflectionUtils;

public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {

	private AutowireCapableBeanFactory beanFactory;
	private Set<Class<? extends Annotation>> autowiredAnnotationTypes=new LinkedHashSet<Class<? extends Annotation>>();
	private String requiredParameterName = "required";
	private boolean requiredParameterValue = true;
	
	public AutowiredAnnotationProcessor() {
		this.autowiredAnnotationTypes.add(Autowired.class);
	}
	
	public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
		List<InjectionElement> elements=new LinkedList<InjectionElement>();
		Class<?> targetClass=clazz;
		do {
			List<InjectionElement> currentEles=new LinkedList<InjectionElement>();
			for(Field field:clazz.getDeclaredFields()) {
				//如果field有Autowired注解则创建，并且获取required属性值
				Annotation ann=findAutowiredAnnotation(field);
				if(ann != null) {
					if(Modifier.isStatic(field.getModifiers())) {
						continue;
					}
					boolean required=determineRequiredStatus(ann);
					InjectionElement element=new AutowiredFieldElement(field, required, beanFactory);
					currentEles.add(element);
				}
			}
			elements.addAll(0, currentEles);
			targetClass=targetClass.getSuperclass();
		}while(targetClass != null && targetClass != Object.class);
		return new InjectionMetadata(clazz, elements);
	}
	
	private Annotation findAutowiredAnnotation(AccessibleObject ao) {
		for(Class<? extends Annotation> type:this.autowiredAnnotationTypes) {
			Annotation ann=AnnotationUtils.getAnnotation(ao, type);
			if(ann != null) {
				return ann;
			}
		}
		return null;
	}
	
	private boolean determineRequiredStatus(Annotation ann) {
		Method method=ReflectionUtils.findMethod(ann.annotationType(), this.requiredParameterName);
		if(method == null) {
			return true;
		}
		return (this.requiredParameterValue == (Boolean)ReflectionUtils.invokeMethod(method, ann));
	}

	public Object beforeInitialization(Object bean, String beanName) throws BeanException {
		return bean;
	}

	public Object afterInitialization(Object bean, String beanName) throws BeanException {
		return bean;
	}

	public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
		return null;
	}

	public boolean afterInstantiation(Object bean, String beanName) throws BeanException {
		return true;
	}

	public void postProcessPropertyValues(Object bean, String beanName) throws BeanException {
		InjectionMetadata metadata=buildAutowiringMetadata(bean.getClass());
		try {
			metadata.inject(bean);
		}catch(Throwable ex) {
			throw new BeanCreateException(beanName, "Injection of autowired dependencies failed", ex);
		}
	}
}
