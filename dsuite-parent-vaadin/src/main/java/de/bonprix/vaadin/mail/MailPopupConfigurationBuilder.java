package de.bonprix.vaadin.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Builder for BPErrorMailConfiguration
 * 
 * @author r.desai
 *
 */
public class MailPopupConfigurationBuilder {

	private List<String> to = new ArrayList<>();
	private List<String> cc = new ArrayList<>();
	private List<String> bcc = new ArrayList<>();
	private String subject;
	private String body;

	/**
	 * @param tos
	 * @return builder
	 */
	public MailPopupConfigurationBuilder withTo(final String... tos) {
		this.to.addAll(Arrays.asList(tos));
		return this;
	}

	/**
	 * @param ccs
	 * @return builder
	 */
	public MailPopupConfigurationBuilder withCc(final String... ccs) {
		this.cc.addAll(Arrays.asList(ccs));
		return this;
	}

	/**
	 * @param bccs
	 * @return builder
	 */
	public MailPopupConfigurationBuilder withBcc(final String... bccs) {
		this.bcc.addAll(Arrays.asList(bccs));
		return this;
	}

	/**
	 * @param subject
	 * @return builder
	 */
	public MailPopupConfigurationBuilder withSubject(final String subject) {
		this.subject = subject;
		return this;
	}

	/**
	 * @param body
	 * @return builder
	 */
	public MailPopupConfigurationBuilder withBody(final String body) {
		this.body = body;
		return this;
	}

	/**
	 * /** Builds the configuration and returns it.
	 *
	 * @return configuration
	 */

	public MailPopupConfiguration build() {
		return new MailPopupConfiguration(this.to, this.cc, this.bcc, this.subject, this.body);
	}
}
