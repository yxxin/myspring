package com.yx.myspring.beans.factory.support;

import java.lang.reflect.Constructor;
import java.util.List;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.ConstructorArgument;
import com.yx.myspring.beans.ConstructorArgument.ValueHolder;
import com.yx.myspring.beans.SimpleTypeConvert;
import com.yx.myspring.beans.factory.BeanCreateException;
import com.yx.myspring.beans.factory.config.ConfigurableBeanFactory;

public class ConstructorResolver {
	private ConfigurableBeanFactory factory;

	public ConstructorResolver(ConfigurableBeanFactory factory) {
		this.factory = factory;
	}

	public Object autowireConstructor(BeanDefinition bd) {
		Class<?> beanClass = null;
		Object[] argsToUse = null;
		Constructor<?> constructorToUse = null;
		try {
			beanClass = this.factory.getBeanClassLoader().loadClass(bd.getBeanClassName());
		} catch (ClassNotFoundException e) {
			throw new BeanCreateException(bd.getID(), "Instantiation of bean failed, can't resolve class", e);
		}
		ConstructorArgument args = bd.getConstructorArgument();
		List<ValueHolder> valueHolders = args.getArgumentValues();
		BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this.factory);
		SimpleTypeConvert convert = new SimpleTypeConvert();
		Constructor<?>[] constructors = beanClass.getConstructors();
		for (int i = 0; i < constructors.length; i++) {
			Class<?>[] parameterTypes = constructors[i].getParameterTypes();
			if (parameterTypes.length != valueHolders.size()) {
				continue;
			}
			argsToUse = new Object[parameterTypes.length];
			boolean results = valuesMatchTypes(argsToUse, valueHolders, resolver, convert, parameterTypes);
			if (results) {
				constructorToUse = constructors[i];
				break;
			}
		}
		if (constructorToUse == null) {
			throw new BeanCreateException(bd.getID(), "can't find a apporiate constructor");
		}
		try {
			return constructorToUse.newInstance(argsToUse);
		} catch (Exception e) {
			throw new BeanCreateException(bd.getID(), "can't find a create instance using " + constructorToUse);
		}
	}

	private boolean valuesMatchTypes(Object[] argsToUse, List<ValueHolder> valueHolders,
			BeanDefinitionValueResolver resolver, SimpleTypeConvert convert, Class<?>[] parameterTypes) {
		for (int i = 0; i < parameterTypes.length; i++) {
			ValueHolder valueHolder = valueHolders.get(i);
			if (valueHolder != null) {
				try {
					Object resolvedValue = resolver.resolveValueIfNecessary(valueHolder.getValue());
					argsToUse[i] = convert.convertIfNecessary(resolvedValue, parameterTypes[i]);
				} catch (Exception e) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
}
