package de.bonprix.vaadin.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.AbstractErrorMessage;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorMessage;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.AbstractComponent;

/**
 * @author r.desai
 *
 *         BPErrorHandler is a handler class for showing the Error MessageBox.
 *
 */
@Component
@UIScope
public class BPErrorHandler extends DefaultErrorHandler {

	private static final long serialVersionUID = -8613074989461420679L;
	private static final Logger LOGGER = LoggerFactory.getLogger(BPErrorHandler.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void error(final com.vaadin.server.ErrorEvent event) {

		final Throwable t = event.getThrowable();
		// allow validation of fields without error popup
		if (t instanceof InvalidValueException) {
			// Finds the original source of the error/exception
			final AbstractComponent component = DefaultErrorHandler.findAbstractComponent(event);
			if (component != null) {
				// Shows the error in AbstractComponent
				final ErrorMessage errorMessage = AbstractErrorMessage.getErrorMessageForException(t);
				component.setComponentError(errorMessage);
			}
			return;
		}

		final BPErrorMessageBoxPresenter presenter = this.applicationContext.getBean(BPErrorMessageBoxPresenter.class);
		// presenter.createErrorScreenshot();
		presenter.setError(t);
		presenter.open();

		BPErrorHandler.LOGGER.error("Uncaught error occurred while processing the request", event.getThrowable());
	}

}
