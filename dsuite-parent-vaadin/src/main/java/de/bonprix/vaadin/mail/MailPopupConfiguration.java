package de.bonprix.vaadin.mail;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bonprix.exception.MailEncodingException;

/**
 * The BPErrorMailConfiguration is the configuration for setting the parameters
 * while sending Error Mail.
 *
 * @author r.desai
 *
 */
public class MailPopupConfiguration {
	private final List<String> to;
	private final List<String> cc;
	private final List<String> bcc;
	private final String subject;
	private final String body;

	private static final Logger LOGGER = LoggerFactory.getLogger(MailPopupConfiguration.class);

	public MailPopupConfiguration(final List<String> to, final List<String> cc, final List<String> bcc,
			final String subject, final String body) {
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.subject = subject;
		this.body = body;
	}

	public List<String> getMailTo() {
		return this.to;
	}

	public List<String> getMailCc() {
		return this.cc;
	}

	public List<String> getMailBcc() {
		return this.bcc;
	}

	public String getMailSubject() {
		return this.subject;
	}

	public String getMailBody() {
		return this.body;
	}

	/**
	 * @return constructs the url to open default mail client
	 */
	public String getUrl() {
		boolean isFirst = true;
		final StringBuilder buffer = new StringBuilder();

		addMailTo(buffer, getMailTo());
		isFirst = addParameter(buffer, "cc", getMailCc(), isFirst);
		isFirst = addParameter(buffer, "bcc", getMailBcc(), isFirst);
		isFirst = addParameter(buffer, "subject", MailPopupConfiguration.urlEncode(getMailSubject()), isFirst);
		addParameter(buffer, "body", MailPopupConfiguration.urlEncode(getMailBody()), isFirst);

		return buffer.toString();
	}

	private void addMailTo(final StringBuilder buffer, final List<String> mailTo) {
		buffer.append("mailto:");
		if (mailTo != null && !mailTo.isEmpty()) {
			buffer.append(String.join(", ", mailTo));
		}
	}

	private boolean addParameter(final StringBuilder buffer, final String parameterName,
			final List<String> parameterValue, final Boolean isFirst) {
		if (parameterValue != null && !parameterValue.isEmpty()) {
			return addParameter(buffer, parameterName, String.join(",", parameterValue), isFirst);
		}
		return isFirst;
	}

	/**
	 * @return if parameter was added
	 */
	private boolean addParameter(final StringBuilder buffer, final String parameterName, final String parameterValue,
			final Boolean isFirst) {
		if (parameterValue != null && !parameterValue.isEmpty()) {
			if (isFirst) {
				buffer.append("?");
			} else {
				buffer.append("&");
			}
			buffer.append(parameterName + "=" + parameterValue);
			return false;
		}
		return isFirst;
	}

	private static String urlEncode(final String str) {

		if (str == null) {
			return null;
		}

		try {
			return URLEncoder.encode(str, "UTF-8")
				.replace("+", "%20");
		} catch (final UnsupportedEncodingException e) {
			MailPopupConfiguration.LOGGER.error(e.getLocalizedMessage(), e);
			throw new MailEncodingException(e.getLocalizedMessage(), e);
		}
	}

}
