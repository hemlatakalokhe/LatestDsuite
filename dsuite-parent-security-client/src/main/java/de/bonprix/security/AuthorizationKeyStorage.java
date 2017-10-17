package de.bonprix.security;

/**
 * Local thread storage for saving authentication key between calls; is used for
 * forwarding authentication key from one (webpage / webservice) call to another
 * call
 */
public class AuthorizationKeyStorage {

	/**
	 * Authkey header property of the authenticated user to use the application
	 */
	public static final String AUTHENTICATION_KEY_ATTRIBUTE = "authKey";

	/**
	 * Authkey header for the root user originally authenticated by the first
	 * dsuite application in the authentication chain (i.E. when a Vaain
	 * application calls a webservice, this header stored the authenticated user
	 * of the Vaadin application and not the system user authenticated to the
	 * webservice).
	 */
	public static final String ROOT_AUTHENTICATION_KEY_ATTRIBUTE = "root-authKey";

	private static ThreadLocal<String> authKeyLocal = new ThreadLocal<>();
	private static ThreadLocal<String> rootAuthKeyLocal = new ThreadLocal<>();

	private AuthorizationKeyStorage() {

	}

	public static String getAuthorizationKey() {
		return AuthorizationKeyStorage.authKeyLocal.get();
	}

	public static void setAuthorizationKey(final String authKey) {
		AuthorizationKeyStorage.authKeyLocal.set(authKey);
	}

	public static String getRootAuthorizationKey() {
		return AuthorizationKeyStorage.rootAuthKeyLocal.get();
	}

	public static void setRootAuthorizationKey(final String authKey) {
		AuthorizationKeyStorage.rootAuthKeyLocal.set(authKey);
	}
}
