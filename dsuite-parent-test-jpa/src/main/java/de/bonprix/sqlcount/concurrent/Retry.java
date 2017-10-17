package de.bonprix.sqlcount.concurrent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Retry - mark a given method for retrying.
 *
 * @author Vlad Mihalcea
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Retry {

	/**
	 * Declare the exception types the retry will be issued on.
	 *
	 * @return exception types causing a retry
	 */
	Class<? extends Exception>[] on();

	/**
	 * The number of retry attempts
	 *
	 * @return retry attempts
	 */
	int times() default 1;

	/**
	 * Fail if the current thread is enlisted in a running transaction.
	 *
	 * @return fail in case of a running transaction
	 */
	boolean failInTransaction() default true;
}
