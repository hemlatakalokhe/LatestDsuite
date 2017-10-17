/**
 *
 */
package de.bonprix.database.logging;

import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect to determine the source JPA repository method of an SQL. This aspect
 * will retrieve the main interface class and write the class name into the
 * log4j {@link ThreadContext} key "jpaRepository".
 *
 * @author cthiel
 * @date 11.10.2016
 *
 */
@Aspect
@Component
public class RepositoryLogInfoAspect {

	/**
	 * This aspect will retrieve the main interface class and write the class
	 * name into the log4j {@link ThreadContext} key "jpaRepository".
	 *
	 * @param pjp
	 *            the join point
	 * @return the intercepted return value
	 * @throws Throwable
	 */
	@Around("this(org.springframework.data.jpa.repository.JpaRepository)")
	public Object aroundAnyRepositoryMethode(final ProceedingJoinPoint pjp) throws Throwable {
		Class<?> bpInterfaceClazz = null;
		for (final Class<?> interfaceClazz : pjp.getTarget()
			.getClass()
			.getInterfaces()) {
			// search for the topmost bonprix interface
			if (interfaceClazz.getName()
				.startsWith("de.bonprix.")) {
				bpInterfaceClazz = interfaceClazz;
				break;
			}
		}
		if (bpInterfaceClazz != null) {
			ThreadContext.put("jpaRepository", bpInterfaceClazz.getName() + "." + pjp.getSignature()
				.getName());
		}

		final Object o = pjp.proceed();
		ThreadContext.remove("jpaRepository");

		return o;
	}
}
