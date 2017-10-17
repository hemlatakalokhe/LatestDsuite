/**
 *
 */
package de.bonprix.jms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.logging.log4j.ThreadContext;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import de.bonprix.GeneralApplicationInformation;
import de.bonprix.RequestId;
import de.bonprix.configuration.LoggingConfiguration;
import de.bonprix.user.service.AuthenticationService;

/**
 * @author cthiel
 * @date 14.11.2016
 *
 */
public class BonprixMessageListenerContainerTest {

	@Test
	public void setLoggingWithRequestId() throws JMSException {
		final GeneralApplicationInformation appInfo = mock(GeneralApplicationInformation.class);
		final AuthenticationService authenticationService = mock(AuthenticationService.class);
		final String username = "username";
		final String somePwd = "password";
		final String requestId = "3edfgzu765678iko9";
		final String appContextPath = "/somewhere/over/the/rainbow";
		final Message message = Mockito.mock(Message.class);
		when(message.getStringProperty(RequestId.REQUEST_ID_HEADER)).thenReturn(requestId);
		when(appInfo.getApplicationContextPath()).thenReturn(appContextPath);

		final BonprixMessageListenerContainer container = new BonprixMessageListenerContainer(appInfo,
				authenticationService, username, somePwd);

		container.setLoggingProperties(message);

		assertThat(RequestId.getRequestId(), equalTo(requestId));
		assertThat(ThreadContext.get(LoggingConfiguration.REQUEST_ID), equalTo(requestId));
		assertThat(ThreadContext.get(LoggingConfiguration.APPLICATION), equalTo(appContextPath));
	}

	@Test
	public void setLoggingWithoutRequestId() throws JMSException {
		final GeneralApplicationInformation appInfo = mock(GeneralApplicationInformation.class);
		final AuthenticationService authenticationService = mock(AuthenticationService.class);
		final String username = "username";
		final String somePwd = "password";
		final String appContextPath = "/somewhere/over/the/rainbow";
		final Message message = Mockito.mock(Message.class);
		when(appInfo.getApplicationContextPath()).thenReturn(appContextPath);

		final BonprixMessageListenerContainer container = new BonprixMessageListenerContainer(appInfo,
				authenticationService, username, somePwd);

		container.setLoggingProperties(message);

		assertThat(ThreadContext.get(LoggingConfiguration.REQUEST_ID), equalTo(RequestId.getRequestId()));
		assertThat(ThreadContext.get(LoggingConfiguration.APPLICATION), equalTo(appContextPath));
	}

	@Test
	public void unsetLogging() {
		final GeneralApplicationInformation appInfo = mock(GeneralApplicationInformation.class);
		final AuthenticationService authenticationService = mock(AuthenticationService.class);
		final String username = "username";
		final String somePwd = "password";

		final BonprixMessageListenerContainer container = new BonprixMessageListenerContainer(appInfo,
				authenticationService, username, somePwd);

		container.unsetLoggingProperties();
		assertThat(ThreadContext.get(LoggingConfiguration.REQUEST_ID), nullValue());
		assertThat(ThreadContext.get(LoggingConfiguration.APPLICATION), nullValue());
	}

}
