package de.bonprix.jersey;

import de.bonprix.security.AuthKeyAuthenticator;
import de.bonprix.security.BasicAuthenticator;

public class ClientFactoryConfig {

    public enum ClientSideLogLevel {
        /**
         * Nothing will be logged.
         */
        NONE,
        /**
         * Method name and parameters will be logged.
         */
        METHOD,
        /**
         * Method name and parameters will be logged and also the time spent waiting for the response.
         */
        METHOD_TIME
    }

    /**
     * The default time in milliseconds the httpclient waits for a connection being returned by the connection pool.
     */
    public static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 10000;

    /**
     * The default connection timeout until a connection is established.
     */
    public static final int DEFAULT_CONNECTION_TIMEOUT = -1;

    /**
     * The default socket timeout the client waits for any action on the incoming socket.
     */
    public static final int DEFAULT_SOCKET_REQUEST_TIMEOUT = -1;

    /**
     *
     */
    public static final boolean DEFAULT_ADD_AUTHKEY_AUTHENTICATION = true;
    public static final boolean DEFAULT_ADD_BASIC_AUTHENTICATION = false;
    public static final boolean SUPPRESS_HTTP_COMPLIANCE_VALIDATION = true;

    private int connectionRequestTimeout = ClientFactoryConfig.DEFAULT_CONNECTION_REQUEST_TIMEOUT;
    private int connectionTimeout = ClientFactoryConfig.DEFAULT_CONNECTION_TIMEOUT;
    private int socketTimeout = ClientFactoryConfig.DEFAULT_SOCKET_REQUEST_TIMEOUT;

    private boolean addAuthKeyAuthentication = DEFAULT_ADD_AUTHKEY_AUTHENTICATION;
    private boolean addBasicAuthentication = DEFAULT_ADD_BASIC_AUTHENTICATION;

    private boolean suppressHttpComplianceValidation = SUPPRESS_HTTP_COMPLIANCE_VALIDATION;

    private ClientSideLogLevel clientSideLogging = ClientSideLogLevel.METHOD;

    /**
     * @return the connectionRequestTimeout
     */
    public int getConnectionRequestTimeout() {
        return this.connectionRequestTimeout;
    }

    /**
     * @param connectionRequestTimeout the connectionRequestTimeout to set
     */
    public void setConnectionRequestTimeout(final int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    /**
     * @return the connectionTimeout
     */
    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    /**
     * @param connectionTimeout the connectionTimeout to set
     */
    public void setConnectionTimeout(final int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * @return the socketTimeout
     */
    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    /**
     * @param socketTimeout the socketTimeout to set
     */
    public void setSocketTimeout(final int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    /**
     * @return the clientSideLogging
     */
    public ClientSideLogLevel getClientSideLogging() {
        return this.clientSideLogging;
    }

    /**
     * Specifies the client side log level of the client proxy.
     *
     * @param clientSideLogging the clientSideLogging to set
     */
    public void setClientSideLogging(final ClientSideLogLevel clientSideLogging) {
        this.clientSideLogging = clientSideLogging;
    }

    public boolean isAddAuthKeyAuthentication() {
        return this.addAuthKeyAuthentication;
    }

    /**
     * Adds a {@link AuthKeyAuthenticator} filter to the webservice client. This required username and password to be present.
     *
     * @param addAuthKeyAuthentication
     */
    public void setAddAuthKeyAuthentication(final boolean addAuthKeyAuthentication) {
        this.addAuthKeyAuthentication = addAuthKeyAuthentication;
    }

    public boolean isAddBasicAuthentication() {
        return this.addBasicAuthentication;
    }

    /**
     * Adds a {@link BasicAuthenticator} filter to the webservice client. This required username and password to be present.
     *
     * @param addBasicAuthentication
     */
    public void setAddBasicAuthentication(final boolean addBasicAuthentication) {
        this.addBasicAuthentication = addBasicAuthentication;
    }

    public boolean isSuppressHttpComplianceValidation() {
        return this.suppressHttpComplianceValidation;
    }

    public void setSuppressHttpComplianceValidation(final boolean suppressHttpComplianceValidation) {
        this.suppressHttpComplianceValidation = suppressHttpComplianceValidation;
    }

}
