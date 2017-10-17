package de.bonprix.sqlcount.concurrent.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * ReflectionUtils - ReflectionUtils
 *
 * @author Vlad Mihalcea
 */
class ReflectionUtils {

	private ReflectionUtils() {
	}

	public static <T extends Annotation> T getAnnotation(ProceedingJoinPoint pjp, Class<T> annotationClass)
			throws NoSuchMethodException {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		T annotation = AnnotationUtils.findAnnotation(method, annotationClass);

		if (annotation != null) {
			return annotation;
		}

		@SuppressWarnings("rawtypes")
		Class[] argClasses = new Class[pjp.getArgs().length];
		for (int i = 0; i < pjp.getArgs().length; i++) {
			argClasses[i] = pjp.getArgs()[i].getClass();
		}
		method = pjp.getTarget()
			.getClass()
			.getMethod(pjp.getSignature()
				.getName(), argClasses);
		return AnnotationUtils.findAnnotation(method, annotationClass);
	}
}