package com.yx.myspring.test.v1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BeanFactoryTest.class, ResourceTest.class, ApplicationContextTest.class })
public class AllTests {

}
