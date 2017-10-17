package de.bonprix;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;

/**
 * Base class enabling mocking static method via powermock
 *
 * @author vbaghdas
 *
 */
@PowerMockIgnore("javax.management.*")
public abstract class StaticMethodAwareUnitTest extends PowerMockTestCase {

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new PowerMockObjectFactory();
    }

    @Override
    @BeforeMethod
    public void beforePowerMockTestMethod() throws Exception {
        super.beforePowerMockTestMethod();

        SecurityContextSetter.setDefaultSecurityContext();
        MockitoAnnotations.initMocks(this);
        executePostConstructs();
    }

    private void executePostConstructs() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // search for all fields annotated with @InjectMocks in this test case
        // (also super classes) and in proper classes search for any method
        // annotated with @PostConstruct. If found, execute it.
        Class<?> clazz = this.getClass();

        while (clazz != Object.class) {
            for (final Field field : clazz.getDeclaredFields()) {
                if (null != field.getAnnotation(InjectMocks.class)) {
                    Class<?> fieldClazz = field.getType();
                    field.setAccessible(true);
                    while (fieldClazz != null && fieldClazz != Object.class) {
                        for (final Method method : fieldClazz.getDeclaredMethods()) {
                            if (method.isAnnotationPresent(PostConstruct.class) && method.getParameterTypes().length == 0) {
                                method.setAccessible(true);
                                method.invoke(field.get(this));
                            }
                        }
                        fieldClazz = fieldClazz.getSuperclass();
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

}
