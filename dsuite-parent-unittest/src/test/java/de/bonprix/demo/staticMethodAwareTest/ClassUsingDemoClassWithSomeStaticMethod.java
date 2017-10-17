package de.bonprix.demo.staticMethodAwareTest;

/**
 * @author vbaghdas
 */
public class ClassUsingDemoClassWithSomeStaticMethod {

	private DemoClassWithSomeStaticMethod demoClassWithSomeStaticMethod = new DemoClassWithSomeStaticMethod();

	public String callMethodsOnDemoClass() {
		this.demoClassWithSomeStaticMethod.doNoStatic();
		return DemoClassWithSomeStaticMethod.doStatic();
	}

}
