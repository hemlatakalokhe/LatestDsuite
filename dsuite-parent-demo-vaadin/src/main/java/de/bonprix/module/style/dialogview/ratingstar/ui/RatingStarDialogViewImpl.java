package de.bonprix.module.style.dialogview.ratingstar.ui;

import javax.annotation.Resource;

import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.module.login.ui.LoginViewImpl;
import de.bonprix.module.style.dialogview.ratingstar.RatingStarDialogPresenter;
import de.bonprix.module.style.dialogview.ratingstar.RatingStarDialogView;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.mvp.dialog.AbstractMvpDialogView;
import de.bonprix.vaadin.provider.UiNavigationProvider;
import de.bonprix.vaadin.provider.UiNotificationProvider;

@SuppressWarnings("serial")
@SpringViewComponent
public class RatingStarDialogViewImpl extends AbstractMvpDialogView<RatingStarDialogPresenter> implements RatingStarDialogView<RatingStarDialogPresenter> {

    @Resource
    private transient UiNotificationProvider notificationProvider;

    @Resource
    private transient UiNavigationProvider navigationProvider;

    private RatingStars ratingStarsDefault;

    public RatingStarDialogViewImpl() {
        super(new DialogConfigurationBuilder().withButton(DialogButton.CANCEL)
            .withPrimaryButton(DialogButton.OK)
            .withCloseOnButton(DialogButton.OK)
            .withCloseOnButton(DialogButton.CANCEL)
            .withHeadline("RATE_US")
            .withWidth(500)
            .withHeight(500)
            .build());

        addButtonListener(DialogButton.OK, event -> {
            this.ratingStarsDefault.addValueChangeListener((ValueChangeListener) ValueChangeEvent -> this.notificationProvider
                .showInfoNotification("You voted " + ValueChangeEvent.getProperty()
                    .getValue() + " stars!"));
            this.navigationProvider.navigateTo(LoginViewImpl.VIEW_NAME);
        });
    }

    @Override
    protected Component layout() {
        final VerticalLayout starLayout = FluentUI.vertical()
            .add(getRatingStars3())
            .get();
        final VerticalLayout layout = FluentUI.vertical()
            .add(starLayout)
            .get();
        layout.setComponentAlignment(starLayout, Alignment.MIDDLE_CENTER);
        return layout;
    }

    private RatingStars getRatingStarsDefault(final String styleName) {
        this.ratingStarsDefault = new RatingStars();
        this.ratingStarsDefault.setCaption("default");
        if (styleName != null) {
            this.ratingStarsDefault.addStyleName(styleName);
        }
        this.ratingStarsDefault.setMaxValue(5);
        this.ratingStarsDefault.setImmediate(true);
        return this.ratingStarsDefault;
    }

    private RatingStars getRatingStars3() {
        return getRatingStars3(null);
    }

    private RatingStars getRatingStars3(final String styleName) {
        final RatingStars ratingStarsHover = getRatingStarsDefault(styleName);
        ratingStarsHover.setMaxValue(5);
        return ratingStarsHover;
    }
}
