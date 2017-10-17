package de.bonprix.vaadin.eventbus;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The EventDispatcher is responsible for dispatching / delivering a Event to the corresponding {@link EventHandler} - Method. This is realized by using
 * reflections, especially {@link Method#invoke(Object, Object...)}
 * 
 * @author Hannes Dorfmann
 * 
 */
@SuppressWarnings("serial")
public class EventDispatcher implements Serializable {

    private final Object target;
    private final Method method;

    public EventDispatcher(final Object target, final Method method) {
        this.target = target;
        this.method = method;
        this.method.setAccessible(true);
    }

    public Object getTarget() {
        return this.target;
    }

    public void dispatchEvent(final Object event) {
        try {
            this.method.invoke(this.target, new Object[] { event });
        }
        catch (final IllegalArgumentException e) {
            throw new RuntimeException("Method rejected target/argument: " + event, e);
        }
        catch (final IllegalAccessException e) {
            throw new RuntimeException("Method became inaccessible: " + event, e);
        }
        catch (final InvocationTargetException e) {
            throw new RuntimeException("Event handler throws exception while processing event: " + event, e);
        }
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        return (PRIME + this.method.hashCode()) * PRIME + System.identityHashCode(this.target);
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof EventDispatcher) {
            final EventDispatcher other = (EventDispatcher) o;
            return this.target == other.target && this.method.equals(other.method);
        }

        return false;
    }
}