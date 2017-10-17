package de.bonprix.eventbus;

/**
 * @author Dominic Mut
 */
public class TestMessageChangeEvent {

	private final String testMessage;
	
	public TestMessageChangeEvent(String message){
		this.testMessage = message;
	}
	
	public String getTestMessage(){
		return this.testMessage;
	}
}
