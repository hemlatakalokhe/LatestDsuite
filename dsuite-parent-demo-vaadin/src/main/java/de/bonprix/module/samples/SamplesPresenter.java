package de.bonprix.module.samples;

import javax.annotation.Resource;

import de.bonprix.base.demo.service.PlanperiodService;
import de.bonprix.model.Paged;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

@SpringPresenter
public class SamplesPresenter extends AbstractMvpViewPresenter<SamplesViewImpl> implements SamplesView.Presenter {

    @Resource
    private PlanperiodService planperiodService;

    @Override
    public void init() {
        // empty
    }

    @Override
    public void onViewEnter() {

        getView().setAllBean(this.planperiodService.findAll(new Paged(0, 100)));

        // empty
    }

    @Override
    public void tryNavigateTo(final NavigationRequest request) {
    }

    @Override
    public void proceedCheckBox(final Boolean value, final NavigationRequest request) {
        if (!value) {
            super.tryNavigateTo(request);
            return;
        }

    }

}