package de.bonprix.demo.baseConfiguredUnitTest;

import javax.annotation.PostConstruct;

/**
 * @author vbaghdas
 */
public class SuperBeanIntoWhichInject {

	private boolean superPostConstructExecuted = false;

	@PostConstruct
	public void executePostConstructOfSuperclass() {
		this.superPostConstructExecuted = true;
	}

	public boolean getSuperPostConstructExecuted() {
		return this.superPostConstructExecuted;
	}

}
