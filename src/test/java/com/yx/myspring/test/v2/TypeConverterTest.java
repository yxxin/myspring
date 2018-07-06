package com.yx.myspring.test.v2;

import static org.junit.Assert.*;

import org.junit.Test;

import com.yx.myspring.beans.SimpleTypeConvert;
import com.yx.myspring.beans.TypeConverter;

public class TypeConverterTest {

	@Test
	public void testConvertStringToXX() {
		TypeConverter convert=new SimpleTypeConvert();
		Integer i=convert.convertIfNecessary("11", int.class);
		assertEquals(11, i.intValue());
		Short s=convert.convertIfNecessary("12", Short.class);
		assertEquals(12, s.intValue());
		Boolean b=convert.convertIfNecessary("true", Boolean.class);
		assertEquals(true, b.booleanValue());
		b=convert.convertIfNecessary("no", Boolean.class);
		assertEquals(false, b.booleanValue());
		try {
			System.out.println(convert.convertIfNecessary("abc", Boolean.class));
		}catch(Exception e) {
			return;
		}
		fail();
	}

}
