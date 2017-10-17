package de.bonprix.showcase.cases.notification;

/**
 * @author Ivan Slavchev
 */
public class NotificationFieldGroupBean {

	private int delay;
	private String caption;
	private String message;

	public int getDelay() {
		return this.delay;
	}

	public void setDelay(final int delay) {
		this.delay = delay;
	}

	public String getCaption() {
		return this.caption;
	}

	public void setCaption(final String caption) {
		this.caption = caption;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}
}
