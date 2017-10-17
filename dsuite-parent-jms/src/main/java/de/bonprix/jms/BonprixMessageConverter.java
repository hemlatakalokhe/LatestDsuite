/**
 *
 */
package de.bonprix.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import de.bonprix.RequestId;

/**
 * Custom message converter for bonprix. Features:
 * <ul>
 * <li>adding requestId to message metadata</li>
 * </ul>
 *
 * @author cthiel
 * @date 14.11.2016
 *
 */
public class BonprixMessageConverter extends SimpleMessageConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(BonprixMessageConverter.class);

    @Override
    public Message toMessage(final Object object, final Session session) throws JMSException, MessageConversionException {
        final Message message = super.toMessage(object, session);

        message.setStringProperty(RequestId.REQUEST_ID_HEADER, RequestId.getRequestId());
        BonprixMessageConverter.LOGGER.debug("setting requestId " + RequestId.getRequestId() + " to message");

        return message;
    }

    @Override
    public Object fromMessage(final Message message) throws JMSException, MessageConversionException {
        return super.fromMessage(message);
    }

}
