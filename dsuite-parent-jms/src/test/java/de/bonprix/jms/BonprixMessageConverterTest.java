/**
 *
 */
package de.bonprix.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.util.ByteSequence;
import org.mockito.Mockito;
import org.springframework.jms.support.converter.MessageConversionException;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.mock;

import de.bonprix.RequestId;

/**
 * @author cthiel
 * @date 14.11.2016
 *
 */
public class BonprixMessageConverterTest {

    @Test
    public void testConvertToMessage() throws MessageConversionException, JMSException {
        final BonprixMessageConverter converter = new BonprixMessageConverter();

        final String someTestObject = "some test string";
        final String requestId = "qwertzuiop";

        RequestId.setRequestId(requestId);

        final Session session = mock(Session.class);
        Mockito.when(session.createTextMessage(Mockito.anyString()))
            .then(invocation -> {
                final ActiveMQTextMessage message = new ActiveMQTextMessage();
                message.setContent(new ByteSequence(invocation.getArgumentAt(0, String.class)
                    .getBytes()));

                return new ActiveMQTextMessage();
            });

        final Message message = converter.toMessage(someTestObject, session);

        assertThat(message, notNullValue());
        assertThat(message.getStringProperty(RequestId.REQUEST_ID_HEADER), equalTo(requestId));
    }

}
