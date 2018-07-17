package com.yx.myspring.beans.factory.support;

import java.lang.reflect.Constructor;
import java.util.List;

import com.yx.myspring.beans.BeanDefinition;
import com.yx.myspring.beans.ConstructorArgument;
import com.yx.myspring.beans.ConstructorArgument.ValueHolder;
import com.yx.myspring.beans.SimpleTypeConvert;
import com.yx.myspring.beans.factory.BeanCreateException;
import com.yx.myspring.beans.factory.config.ConfigurableBeanFactory;
import com.yx.myspring.util.ClassUtils;

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

		int weight = 0;
		for (int i = 0; i < constructors.length; i++) {
			Class<?>[] parameterTypes = constructors[i].getParameterTypes();
			if (parameterTypes.length != valueHolders.size()) {
				continue;
			}
			if (argsToUse == null) {
				argsToUse = new Object[parameterTypes.length];
			}
			Object[] returnArgs = new Object[parameterTypes.length];
			int results = valuesMatchTypes(returnArgs, valueHolders, resolver, convert, parameterTypes);
			if (results != -1 && (constructorToUse == null || weight < results)) {
				constructorToUse = constructors[i];
				argsToUse = returnArgs;
				weight = results;
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

	private int valuesMatchTypes(Object[] argsToUse, List<ValueHolder> valueHolders,
			BeanDefinitionValueResolver resolver, SimpleTypeConvert convert, Class<?>[] parameterTypes) {
		int weight = 0;
		for (int i = 0; i < parameterTypes.length; i++) {
			ValueHolder valueHolder = valueHolders.get(i);
			if (valueHolder != null) {
				try {
					Object resolvedValue = resolver.resolveValueIfNecessary(valueHolder.getValue());
					if (ClassUtils.isAssignableValue(parameterTypes[i], resolvedValue)) {
						weight++;
						argsToUse[i] = resolvedValue;
					} else {
						argsToUse[i] = convert.convertIfNecessary(resolvedValue, parameterTypes[i]);
					}
				} catch (Exception e) {
					return -1;
				}
			} else {
				return -1;
			}
		}
		return weight;
	}
}
