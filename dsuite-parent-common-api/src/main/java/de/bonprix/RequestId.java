package de.bonprix;

import java.util.UUID;

public class RequestId {

	public static final String REQUEST_ID_HEADER = "requestid";

	private static ThreadLocal<String> localId = new ThreadLocal<String>() {

		@Override
		protected String initialValue() {
			return RequestId.generateRequestId();
		}

	};

	private RequestId() {
		// private constructor to hide implicit public one
	}

	/**
	 * Returns the String representation of an UUID when it is set prior
	 * otherwise null
	 *
	 * @return
	 */
	public static String getRequestId() {
		return RequestId.localId.get();
	}

	public static void setRequestId(final String id) {
		if (id == null) {
			RequestId.localId.remove();
		} else {
			RequestId.localId.set(id);
		}
	}

	public static String generateRequestId() {
		return UUID.randomUUID()
			.toString();

	}

}
