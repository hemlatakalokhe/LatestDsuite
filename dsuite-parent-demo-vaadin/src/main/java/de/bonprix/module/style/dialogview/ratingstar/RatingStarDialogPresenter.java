package de.bonprix.module.style.dialogview.ratingstar;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import de.bonprix.model.DialogEvent;
import de.bonprix.vaadin.eventbus.EventBus;
import de.bonprix.vaadin.eventbus.EventHandler;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.dialog.AbstractMvpDialogPresenter;

@SpringPresenter
public class RatingStarDialogPresenter extends AbstractMvpDialogPresenter<RatingStarDialogView> implements RatingStarDialogView.Presenter {

    @Resource
    private EventBus localEventBus;

    @PostConstruct
    private void init() {
        this.localEventBus.addHandler(this);
    }

    @EventHandler
    private void eventHandler(final DialogEvent event) {
        //
    }
}
