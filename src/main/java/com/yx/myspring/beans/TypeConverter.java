package com.yx.myspring.beans;

public interface TypeConverter {
	<T> T convertIfNecessary(Object value,Class<T> requireType);
}
