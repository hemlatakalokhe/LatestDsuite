package de.bonprix.forkjoinpool;

import java.util.concurrent.Callable;
import java.util.concurrent.RecursiveTask;

import de.bonprix.exception.RecusiveTaskException;

@SuppressWarnings("serial")
public class RecursiveTaskLambda<T> extends RecursiveTask<T> {

	private final transient Callable<? extends T> callable;

	public RecursiveTaskLambda(Callable<? extends T> callable) {
		this.callable = callable;
	}

	@Override
	protected T compute() {
		try {
			return this.callable.call();
		} catch (Exception e) {
			throw new RecusiveTaskException("Couldn't compute result of callable in recursive task", e);
		}
	}
}