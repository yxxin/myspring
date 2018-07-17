package com.yx.myspring.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ConstructorArgument {
	private List<ValueHolder> argumentValues = new LinkedList<ValueHolder>();
	public List<ValueHolder> getArgumentValues(){
		return Collections.unmodifiableList(this.argumentValues);
	}
	public void addArgumentValue(ValueHolder vh) {
		this.argumentValues.add(vh);
	}
	public int getArugmentCount() {
		return this.argumentValues.size();
	}
	public boolean isEmpty() {
		return this.argumentValues.isEmpty();
	}
	public void clear() {
		this.argumentValues.clear();
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
