package com.yx.myspring.beans;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yx.myspring.util.Assert;
import com.yx.myspring.util.ClassUtils;

public class ConstructorArgument {
	private List<ValueHolder> argumentValues = new LinkedList<ValueHolder>();
	private Map<Integer, ValueHolder> indexArgumentValues = new HashMap<Integer, ValueHolder>();

	public ValueHolder getValueHolder(int index, Class<?> requiredType, Set<ValueHolder> usedValueHolders) {
		Assert.isTrue(index >= 0, "Index must not be negative");
		ValueHolder valueHolder = this.getIndexArgumentValue(index, requiredType);
		if (valueHolder == null) {
			valueHolder = this.getArgumentValue(requiredType, usedValueHolders);
		}
		return valueHolder;
	}

	public ValueHolder getArgumentValue(Class<?> requiredType, Set<ValueHolder> usedValueHolders) {
		for (ValueHolder valueHolder : this.argumentValues) {
			if (usedValueHolders != null && usedValueHolders.contains(valueHolder)) {
				continue;
			}
			if (valueHolder.getType() != null
					&& (requiredType == null || !ClassUtils.matchesTypeName(requiredType, valueHolder.getType()))) {
				continue;
			}
			if (requiredType != null && valueHolder.getType() == null
					&& !ClassUtils.isAssignableValue(requiredType, valueHolder.getValue())) {
				continue;
			}
			return valueHolder;
		}
		return null;
	}

	public List<ValueHolder> getArgumentValues() {
		return Collections.unmodifiableList(this.argumentValues);
	}

	public void addArgumentValue(ValueHolder vh) {
		this.argumentValues.add(vh);
	}

	public ValueHolder getIndexArgumentValue(int index, Class<?> requiredType) {
		Assert.isTrue(index >= 0, "Index must not be negative");
		ValueHolder valueHolder = this.indexArgumentValues.get(index);
		if (valueHolder != null && (valueHolder.getType() == null
				|| (requiredType != null && ClassUtils.matchesTypeName(requiredType, valueHolder.getType())))) {
			return valueHolder;
		}
		return null;
	}

	public Map<Integer, ValueHolder> getIndexArgumentValues() {
		return Collections.unmodifiableMap(this.indexArgumentValues);
	}

	public void addIndexArgumentValue(int index, ValueHolder vh) {
		this.indexArgumentValues.put(index, vh);
	}

	public boolean hasIndexArgumentValues(int index) {
		return this.indexArgumentValues.containsKey(index);
	}

	public int getValueHoldersCount() {
		return this.argumentValues.size() + this.indexArgumentValues.size();
	}

	public boolean isEmpty() {
		return this.argumentValues.isEmpty() && this.indexArgumentValues.isEmpty();
	}

	public void clear() {
		this.argumentValues.clear();
		this.indexArgumentValues.clear();
	}

	public static class ValueHolder {
		private String type;
		private String name;
		private Object value;

		public ValueHolder(Object value) {
			this.value = value;
		}

		public ValueHolder(String type, Object value) {
			this.type = type;
			this.value = value;
		}

		public ValueHolder(String type, String name, Object value) {
			this.type = type;
			this.name = name;
			this.value = value;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}
}
