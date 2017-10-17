package de.bonprix.filter;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.bonprix.exception.FilterException;
import de.bonprix.exception.WebserviceException;

/**
 * This is the ClientResponseFilter class looking for exceptions. The filter
 * simply looks for a custom HTTP header defined in
 * WebserviceException.HEADER_NAME. In this header the classname of the
 * exception is stored in case of a transmitted exception. This way the JSON
 * serializer also knows very easy the target class to deserialize to. <br>
 * <br>
 * Then the defined exception class cannot be found in the classloader, an
 * exception will be throws. <br>
 * <br>
 * After deserialization the created exception will simply be thrown.
 * 
 * @author cthiel
 *
 */
public class WebserviceExceptionClientResponseFilter implements ClientResponseFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebserviceExceptionClientResponseFilter.class);

	@Override
	public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
		String exceptionClassname = responseContext.getHeaderString(WebserviceException.HEADER_NAME);

		if (exceptionClassname != null) {
			WebserviceExceptionClientResponseFilter.LOGGER
				.debug("discovered exception of type " + exceptionClassname + " in service call, parse and throw it");

			try {
				Class<?> clazz = getClass().getClassLoader()
					.loadClass(exceptionClassname);

				ObjectMapper om = new ObjectMapper();

				throw (WebserviceException) om.readValue(responseContext.getEntityStream(), clazz);

			} catch (ClassNotFoundException e) {
				WebserviceExceptionClientResponseFilter.LOGGER
					.error("class " + exceptionClassname + " unknown to classloader");
				throw new FilterException(e);
			}
		}

	}

}
