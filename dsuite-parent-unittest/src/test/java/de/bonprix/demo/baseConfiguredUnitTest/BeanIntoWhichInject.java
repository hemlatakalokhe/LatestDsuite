package de.bonprix.demo.baseConfiguredUnitTest;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author vbaghdas
 */
public class BeanIntoWhichInject extends SuperBeanIntoWhichInject {

	private boolean postConstructExecuted = false;

	@Resource
	private BeanToInject beanToInject;

	public BeanToInject getBeanToInject() {
		return this.beanToInject;
	}

	@PostConstruct
	public void executePostConstruct() {
		this.postConstructExecuted = true;
	}

	public boolean getPostConstructExecuted() {
		return this.postConstructExecuted;
	}
}
