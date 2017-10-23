package de.bonprix.module.style.wizard.mvp;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.vaadin.ui.Notification;

import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.service.ClientService;
import de.bonprix.base.demo.service.CountryService;
import de.bonprix.base.demo.service.SeasonService;
import de.bonprix.base.demo.service.StyleService;
import de.bonprix.model.DialogEvent;
import de.bonprix.model.DialogModeEvent;
import de.bonprix.model.enums.Mode;
import de.bonprix.module.style.dialogview.update.UpdateMvpDialogPresenter;
import de.bonprix.vaadin.eventbus.EventBus;
import de.bonprix.vaadin.eventbus.EventHandler;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.wizard.AbstractMvpWizardPresenter;

@SpringPresenter
public class AddStyleMvpWizardPresenter extends AbstractMvpWizardPresenter<AddStyleMvpWizardView> implements AddStyleMvpWizardView.Presenter {

    @Resource
    private StyleService styleService;

    @Resource
    private ClientService clientService;

    @Resource
    private CountryService countryService;

    @Resource
    private SeasonService seasonService;

    @Resource
    private EventBus localEventBus;

    private StyleWizardPojo styleWizardPojo;

    @PostConstruct
    public void initialize() {
        this.localEventBus.addHandler(this);
    }

    @Override
    public boolean onFinished() {
        return true;
    }

    @EventHandler
    public void init(final DialogEvent event) {
        initializeData();
        addWizardStep(new AddStyleWizardStepOne(this.styleWizardPojo, step -> Notification.show("Step One"),
                step -> saveStyle(((AddStyleWizardStepOne) step).getStyleData())));
        addWizardStep(new AddStyleWizardTwo(this.styleWizardPojo, steo -> {//
        }, step -> {//
        }));
        addWizardStep(new AddStyleWizardStepThree(this.styleWizardPojo, step -> Notification.show("Step Two"), step -> Notification.show("Step Three")));
    }

    private void initializeData() {
        this.styleWizardPojo = new StyleWizardPojo();
        this.styleWizardPojo.setClients(this.clientService.findAll());
        this.styleWizardPojo.setCountries(this.countryService.findAll());
        this.styleWizardPojo.setSeasons(this.seasonService.findAll());
        this.styleWizardPojo.setStyles(this.styleService.findAll());
    }

    @Override
    public void saveStyle(final Style style) {
        this.styleWizardPojo.setStyle(this.styleService.saveStyle(style));
        fireEvent(Mode.EDIT);
    }

    public void fireEvent(final Mode mode) {
        final UpdateMvpDialogPresenter updateMvpDialogPresenter = createPresenter(UpdateMvpDialogPresenter.class);
        this.localEventBus.fireEvent(new DialogModeEvent(mode));
        updateMvpDialogPresenter.open();
        this.localEventBus.fireEvent(new DialogEvent(mode, this.styleWizardPojo.getStyle()));
    }

}
