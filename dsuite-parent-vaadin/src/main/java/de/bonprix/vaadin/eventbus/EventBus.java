package de.bonprix.vaadin.eventbus;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Events can be fired to the {@link EventBus} and the {@link EventBus} is the component, that deliver / dispatch the Events to the registered
 * {@link EventHandler}.
 * 
 * @author Hannes Dorfmann
 * 
 */
@SuppressWarnings("serial")
@Component
public class EventBus implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventBus.class);

    private static final EventMethodCache eventMethodChache = new EventMethodCache();
    private final Map<Class<?>, Set<EventDispatcher>> handlerMap;

    private static boolean caching = true;

    public EventBus() {
        this.handlerMap = new ConcurrentHashMap<Class<?>, Set<EventDispatcher>>();
    }

    /**
     * Enable or disable caching
     * 
     * @param caching
     */
    public void setUseCache(final boolean caching) {
        EventBus.caching = caching;
    }

    /**
     * Get the Class of the Event. The passed {@link Method} must be a valid {@link EventHandler}-annotated method with exactly one parameter (the Event). This
     * method is a little helper method and is only used by the {@link EventBus} internally.
     * 
     * @param m
     * @return
     */
    private static Class<?> getEventClass(final Method m) {
        return m.getParameterTypes()[0];
    }

    /**
     * Registers an {@link EventHandler}
     * 
     * @param handler
     */
    public void addHandler(final Object handler) {
        EventBus.LOGGER.trace("Adding " + handler.getClass()
            .getCanonicalName());

        boolean added = false;

        if (EventBus.caching) {
            if (!EventBus.eventMethodChache.isClassCached(handler.getClass())) {
                added = scanHandlerAndCreateEventDispatcher(handler); // This class is not cached, so scan the class
            }
            else {
                // This class has been cached (has been already scanned), so build the EventDispatcher from Cache
                added = createEventDispatchersFromCache(handler);
            }
        }
        else {
            added = scanHandlerAndCreateEventDispatcher(handler);
        }

        if (!added) {
            EventBus.LOGGER.warn("No @EventHandler annotated Method found in " + handler.getClass()
                .getName());
        }
    }

    private boolean scanHandlerAndCreateEventDispatcher(final Object handler) {
        boolean added = false;
        for (final Method m : handler.getClass()
            .getDeclaredMethods()) {
            if (!m.isAnnotationPresent(EventHandler.class)) {
                continue;
            }

            final Class<?> params[] = m.getParameterTypes();
            if (params.length == 1) {
                // This Method is a Valid EventHandler
                final EventDispatcher disp = new EventDispatcher(handler, m);
                addEventDispatcher(EventBus.getEventClass(m), disp);
                added = true;

                if (EventBus.caching) {
                    EventBus.eventMethodChache.addMethodToCache(handler.getClass(), m);
                }
            }
            else {
                throw new Error("You have annotated the Method " + m.getName() + " with @EventHandler, "
                        + "but this method did not match the required one Parameter (exactly one) of the type Event");
            }
        }

        return added;
    }

    /**
     * Creates {@link EventDispatcher}s by unsing the {@link EventMethodCache}. That means, that the class of the passed handler has already be scanned for
     * {@link EventHandler} annotations and all information about building the {@link EventDispatcher}s are present in the {@link EventMethodCache}.
     * 
     * @param handler
     */
    private boolean createEventDispatchersFromCache(final Object handler) {

        final Set<Method> methods = EventBus.eventMethodChache.getMethods(handler.getClass());

        if (methods == null) {
            throw new Error("The class " + handler.getClass()
                .getName() + " has not been cached until now. However the EventBus tries to create a EventDispatcher from the cache.");
        }

        for (final Method m : methods) {
            final EventDispatcher disp = new EventDispatcher(handler, m);
            addEventDispatcher(EventBus.getEventClass(m), disp);
        }

        return !methods.isEmpty();
    }

    /**
     * Add a {@link EventDispatcher} for the passed Event-Class
     * 
     * @param eventClass
     * @param disp
     */
    private void addEventDispatcher(final Class<?> eventClass, final EventDispatcher disp) {

        Set<EventDispatcher> dispatchers = this.handlerMap.get(eventClass);

        if (dispatchers == null) {
            dispatchers = new LinkedHashSet<EventDispatcher>();
            this.handlerMap.put(eventClass, dispatchers);
        }

        dispatchers.add(disp);
    }

    /**
     * Removes an Handler (a Object with {@link EventHandler} annotated methods) from the {@link EventBus}. That means, that future fired Events are no longer
     * dispatched / delivered to the passed handler
     * 
     * @param handler
     */
    public void removeHandler(final Object handler) {

        final Set<EventDispatcher> toRemove = new LinkedHashSet<EventDispatcher>();
        for (final Set<EventDispatcher> dispatchers : this.handlerMap.values()) {
            for (final EventDispatcher d : dispatchers) {
                if (d.getTarget() == handler) {
                    toRemove.add(d);
                }
            }

            dispatchers.removeAll(toRemove);
            toRemove.clear();
        }
    }

    /**
     * Fires a Event to the EventBus to inform all registered EventHandlers about this Event
     * 
     * @param event
     * @return true if at least one {@link EventHandler} is registered and has received the passed event, otherwise false
     */
    public boolean fireEvent(final Object event) {
        final Set<EventDispatcher> dispatchers = this.handlerMap.get(event.getClass());
        if (dispatchers == null || dispatchers.isEmpty()) {
            return false;
        }

        for (final EventDispatcher disp : dispatchers) {
            disp.dispatchEvent(event);
        }

        return true;
    }

}
