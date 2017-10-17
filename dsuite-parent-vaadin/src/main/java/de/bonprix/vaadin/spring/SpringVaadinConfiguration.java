package de.bonprix.vaadin.spring;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.spring.annotation.UIScope;

import de.bonprix.vaadin.eventbus.EventBus;

/**
 * @author Dominic Mut
 */
@Configuration
public class SpringVaadinConfiguration {

    /**
     * EventBus ist UI-Scoped. When you simply write<br>
     * <code>@Resource(name="localEventBus") EventBus eventBus</code> or <br>
     * <code>@Resource EventBus localEventBus;</code> *
     *
     * @return the local eventbus
     */
    @Bean
    @UIScope
    public EventBus localEventBus() {
        return new EventBus();
    }

    /**
     * There will only exist one instance of EventBus. Use this for events between all opened application instances<br>
     * <code>@Resource(name="globalEventBus") EventBus eventBus</code> or <br>
     * <code>@Resource EventBus globalEventBus</code>
     *
     * @return the global eventbus
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public EventBus globalEventBus() {
        return new EventBus();
    }

    /**
     * Per each session instance (also different opend tabs) only one instance will exist<br>
     * <code>@Resource(name="sessionEventBus") EventBus sessionEventBus</code> or <br>
     * <code>@Resource EventBus sessionEventBus</code>
     *
     * @return the session eventbus
     */
    @Bean
    @Scope(WebApplicationContext.SCOPE_SESSION)
    public EventBus sessionEventBus() {
        return new EventBus();
    }

}
