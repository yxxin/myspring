package com.yx.myspring.beans;

import com.yx.myspring.util.ClassUtils;

public class SimpleTypeConvert implements TypeConverter {

	public static final String VALUE_TRUE = "true";
	public static final String VALUE_FALSE = "false";

	public static final String VALUE_ON = "on";
	public static final String VALUE_OFF = "off";

	public static final String VALUE_YES = "yes";
	public static final String VALUE_NO = "no";

	public static final String VALUE_1 = "1";
	public static final String VALUE_0 = "0";
	
	@SuppressWarnings("unchecked")
	public <T> T convertIfNecessary(Object value, Class<T> requireType) {
		//���requireType��value����ͬ���ͻ��������ĳ��࣬��ֱ�ӷ��أ�����ת��һ�¼��ɡ�
		//�������������ͬ�Ļ������ͻ��߰�װ��ͬ�ϴ���
		if(ClassUtils.isAssignableValue(requireType, value)) {
			return (T)value;
		}else {
			// TODO ֻ֧��string
			if(value instanceof String) {
				return (T)typeStringConvert((String)value,requireType);
			}else {
				throw new RuntimeException("Todo : can't convert value for "+value +" class:"+requireType);
			}
		}
	}
	
	public static Object typeStringConvert(String value, Class requireType) {
		// TODO ת�����ã����Ľ�
		if(requireType == int.class || requireType == Integer.class) {
			return Integer.parseInt(value);
		}
		if(requireType == byte.class || requireType == Byte.class) {
			return Byte.parseByte(value);
		}
		if(requireType == short.class || requireType == Short.class) {
			return Short.parseShort(value);
		}
		if(requireType == float.class || requireType == Float.class) {
			return Float.parseFloat(value);
		}
		if(requireType == double.class || requireType == Double.class) {
			return Double.parseDouble(value);
		}
		if(requireType == long.class || requireType == Long.class) {
			return Long.parseLong(value);
		}
		if(requireType == boolean.class || requireType == Boolean.class) {
			if ((VALUE_TRUE.equalsIgnoreCase(value) || VALUE_ON.equalsIgnoreCase(value) ||
						VALUE_YES.equalsIgnoreCase(value) || VALUE_1.equals(value))) {
				return Boolean.TRUE;
			}
			else if ((VALUE_FALSE.equalsIgnoreCase(value) || VALUE_OFF.equalsIgnoreCase(value) ||
						VALUE_NO.equalsIgnoreCase(value) || VALUE_0.equals(value))) {
				return Boolean.FALSE;
			}
			else {
				throw new IllegalArgumentException("Invalid boolean value [" + value + "]");
			}
		}
		throw new RuntimeException("Todo : "+requireType +" has not been implemented");
	}

}
