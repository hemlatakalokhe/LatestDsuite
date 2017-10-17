/**
 *
 */
package de.bonprix.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author cthiel
 * @date 24.10.2016
 *
 */
public class WebserviceExceptionMapperTest {

    @Test
    public void testExceptionMapping() {

        final WebserviceExceptionMapper mapper = new WebserviceExceptionMapper();

        final Response response = mapper.toResponse(new TestException());

        assertThat(response.getStatus(), equalTo(Status.GATEWAY_TIMEOUT.getStatusCode()));
        assertThat(response.getEntity(), instanceOf(TestException.class));
    }

    public static class TestException extends WebserviceException {
        private static final long serialVersionUID = 1L;

        public TestException() {
            super("some message", Status.GATEWAY_TIMEOUT);
        }

    }

}
