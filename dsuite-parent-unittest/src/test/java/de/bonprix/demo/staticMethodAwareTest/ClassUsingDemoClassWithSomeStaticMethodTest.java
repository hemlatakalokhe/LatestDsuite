package de.bonprix.demo.staticMethodAwareTest;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.bonprix.StaticMethodAwareUnitTest;

/**
 * @author vbaghdas
 */

@PrepareForTest(DemoClassWithSomeStaticMethod.class)
public class ClassUsingDemoClassWithSomeStaticMethodTest extends StaticMethodAwareUnitTest {

    private final ClassUsingDemoClassWithSomeStaticMethod underTest = new ClassUsingDemoClassWithSomeStaticMethod();

    @Test
    public void testStaticMethod() {
        PowerMockito.spy(DemoClassWithSomeStaticMethod.class);
        Mockito.when(DemoClassWithSomeStaticMethod.doStatic())
            .thenReturn("mockResult");

        final String actual = this.underTest.callMethodsOnDemoClass();

        Assert.assertEquals(actual, "mockResult");
    }

}
