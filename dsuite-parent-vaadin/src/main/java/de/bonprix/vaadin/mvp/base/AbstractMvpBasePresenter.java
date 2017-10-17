package de.bonprix.vaadin.mvp.base;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Presenter part of mvp that provides the basic functionality that is normally used with this model-view-presenter framework.
 *
 * @author thacht
 *
 * @param <VIEW> view interface
 */
public abstract class AbstractMvpBasePresenter<VIEW extends MvpBaseView> implements MvpBasePresenter {

    @Autowired
    private VIEW view;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void init() {
        getView().setPresenter(this);
    }

    /**
     * Returns corresponding view of mvp
     * 
     * @return view
     */
    public VIEW getView() {
        return this.view;
    }

    /**
     * Creates a new instance of the given presenter class.
     *
     * @param clazz the presenter class
     * @return the new instance of the presenter
     */
    protected <E extends MvpBasePresenter> E createPresenter(final Class<E> clazz) {
        return this.applicationContext.getBean(clazz);
    }
}
