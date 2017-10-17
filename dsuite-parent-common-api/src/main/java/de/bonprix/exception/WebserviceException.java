package de.bonprix.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This is the root class for all our custom exceptions. It contains some json
 * serialization features to ignore some unimportant fields (or fields making
 * problems). <br>
 * <ul>
 * <li><b>response</b>: MUST be ignored because the class
 * javax.ws.rs.core.Response is an abstract class and no implementation of the
 * actual class can be provided. Also the field is not required for exception
 * handling
 * <li><b>cause</b>: including this field will cause an IllegalStateException on
 * client side because the field Throwable#cause will then be initialized twice,
 * which is forbidden in the default implementation of Throwable. Also the field
 * is not required and should also be skipped for security reasons
 * <li><b>stackTrace</b>: skipped for security reasons
 * <li><b>suppressedExceptions</b>: skipped for security reasons
 * </ul>
 * 
 * @author cthiel
 *
 */
@JsonIgnoreProperties({ "response", "cause", "stackTrace", "suppressedExceptions" })
public abstract class WebserviceException extends WebApplicationException {
	private static final long serialVersionUID = -8084120880689241805L;

	/**
	 * HTTP header field used to store the classname of the serialized
	 * exception.
	 */
	public static final String HEADER_NAME = "x-bonprix-exception-class";

	/**
	 * @param status
	 *            HTTP status code
	 */
	public WebserviceException(Status status) {
		super(status);
	}

	/**
	 * @param message
	 *            custom error message
	 * @param status
	 *            HTTP status code
	 */
	public WebserviceException(String message, Status status) {
		super(message, status);
	}

}
