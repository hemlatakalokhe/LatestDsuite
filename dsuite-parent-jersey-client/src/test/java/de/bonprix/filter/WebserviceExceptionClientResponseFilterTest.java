/**
 *
 */
package de.bonprix.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.core.Response.Status;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.equalTo;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.mock;

import de.bonprix.exception.WebserviceException;

/**
 * @author cthiel
 * @date 24.10.2016
 *
 */
public class WebserviceExceptionClientResponseFilterTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebserviceExceptionClientResponseFilterTest.class);

	@Test
	public void testException() throws IOException {
		final ClientRequestContext requestContext = Mockito.mock(ClientRequestContext.class);
		final ClientResponseContext responseContext = Mockito.mock(ClientResponseContext.class);

		final ObjectMapper om = new ObjectMapper();
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(om.writeValueAsBytes(new TestException()));

		Mockito.when(responseContext.getHeaderString(WebserviceException.HEADER_NAME))
			.thenReturn(TestException.class.getName());
		Mockito.when(responseContext.getEntityStream())
			.thenReturn(inputStream);

		final WebserviceExceptionClientResponseFilter filter = new WebserviceExceptionClientResponseFilter();

		try {
			filter.filter(requestContext, responseContext);
		} catch (final TestException e) {
			WebserviceExceptionClientResponseFilterTest.LOGGER.error(e.getLocalizedMessage());
			MatcherAssert.assertThat(e.getMessage(), Matchers.equalTo("some message"));
		}
		MatcherAssert.assertThat("Excpected exception TestException to be thrown but not happened", true);
	}

	@Test
	public void testNoException() throws IOException {
		final ClientRequestContext requestContext = Mockito.mock(ClientRequestContext.class);
		final ClientResponseContext responseContext = Mockito.mock(ClientResponseContext.class);

		Mockito.when(responseContext.getHeaderString(WebserviceException.HEADER_NAME))
			.thenReturn(null);

		final WebserviceExceptionClientResponseFilter filter = new WebserviceExceptionClientResponseFilter();

		filter.filter(requestContext, responseContext);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testClassNotFound() throws IOException {
		final ClientRequestContext requestContext = Mockito.mock(ClientRequestContext.class);
		final ClientResponseContext responseContext = Mockito.mock(ClientResponseContext.class);

		final ObjectMapper om = new ObjectMapper();
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(om.writeValueAsBytes("{}"));

		Mockito.when(responseContext.getHeaderString(WebserviceException.HEADER_NAME))
			.thenReturn("de.bonprix.SomeWeirdClass");
		Mockito.when(responseContext.getEntityStream())
			.thenReturn(inputStream);

		final WebserviceExceptionClientResponseFilter filter = new WebserviceExceptionClientResponseFilter();

		filter.filter(requestContext, responseContext);
	}

	public static class TestException extends WebserviceException {
		private static final long serialVersionUID = 1L;

		public TestException() {
			super("some message", Status.INTERNAL_SERVER_ERROR);
		}

	}

}
