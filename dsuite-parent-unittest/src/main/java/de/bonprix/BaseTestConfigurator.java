package de.bonprix;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Utility class used by @BaseConfiguredUnitTest
 * and @BaseConfiguredTransactionalUnitTest to make following:
 *
 * 1) setting of dummy client security
 *
 * 2) injection of mocks (marked as @Mock in the test class) into the object
 * under test (marked as @InjectMocks in the test class)
 *
 * 3) For all fields annotated with @InjectMocks in the test case (also super
 * classes) and in proper classes any method annotated with @PostConstruct are
 * search and executed
 *
 * @author vbaghdas
 */
public final class BaseTestConfigurator {

	public static void initTests(Object testClass)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		SecurityContextSetter.setDefaultSecurityContext();
		MockitoAnnotations.initMocks(testClass);
		executePostConstructs(testClass);
	}

	private static void executePostConstructs(Object testClass)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// search for all fields annotated with @InjectMocks in this test case
		// (also super classes) and in proper classes search for any method
		// annotated with @PostConstruct. If found, execute it.
		Class<?> clazz = testClass.getClass();

		while (clazz != Object.class) {
			for (final Field field : clazz.getDeclaredFields()) {
				if (null != field.getAnnotation(InjectMocks.class)) {
					Class<?> fieldClazz = field.getType();
					field.setAccessible(true);
					while (fieldClazz != null && fieldClazz != Object.class) {
						for (final Method method : fieldClazz.getDeclaredMethods()) {
							if (method.isAnnotationPresent(PostConstruct.class)
									&& method.getParameterTypes().length == 0) {
								method.setAccessible(true);
								method.invoke(field.get(testClass));
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
