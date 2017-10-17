package de.bonprix.vaadin.eventbus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This is the base type for every {@link EventHandler} that handles events. A {@link EventHandler} must be registered to the {@link EventBus} to receive
 * events. This is done by {@link EventBus#addHandler(Object)}
 * 
 * @author Hannes Dorfmann
 *
 */
@Retention(
        value = RetentionPolicy.RUNTIME)
public @interface EventHandler {

}
