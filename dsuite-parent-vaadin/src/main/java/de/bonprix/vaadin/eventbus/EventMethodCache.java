package de.bonprix.vaadin.eventbus;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Hannes Dorfmann
 * 
 */
@SuppressWarnings("serial")
public class EventMethodCache implements Serializable {

    private final Map<Class<?>, Set<Method>> methodMap;

    public EventMethodCache() {
        this.methodMap = new ConcurrentHashMap<Class<?>, Set<Method>>();
    }
    /**
     * Adds a Method to the methodsMap
     * 
     * @param c
     * @param m
     */
    public void addMethodToCache(final Class<?> c, final Method m) {
        Set<Method> methods = this.methodMap.get(c);

        if (methods == null) {
            methods = new LinkedHashSet<Method>();
            this.methodMap.put(c, methods);
        }

        methods.add(m);
    }

    public void clear() {
        this.methodMap.clear();
    }

    public void removeCachedOf(final Class<?> c) {
        this.methodMap.remove(c);
    }

    public Set<Method> getMethods(final Class<?> c) {
        return this.methodMap.get(c);
    }

    public boolean isClassCached(final Class<?> c) {
        return this.methodMap.containsKey(c);
    }

}
