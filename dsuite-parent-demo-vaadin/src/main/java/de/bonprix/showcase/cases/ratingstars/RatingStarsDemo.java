package de.bonprix.showcase.cases.ratingstars;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.data.Property.ValueChangeListener;

import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.provider.UiNotificationProvider;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SuppressWarnings("serial")
@SpringViewComponent
public class RatingStarsDemo extends ShowcaseWrapper {

    @Autowired
    private transient UiNotificationProvider notificationProvider;

    private static Map<Integer, String> valueCaptions = new HashMap<>(5);

    static {
        RatingStarsDemo.valueCaptions.put(1, "Epic Fail");
        RatingStarsDemo.valueCaptions.put(2, "Poor");
        RatingStarsDemo.valueCaptions.put(3, "OK");
        RatingStarsDemo.valueCaptions.put(4, "Good");
        RatingStarsDemo.valueCaptions.put(5, "Excellent");
    }

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public RatingStarsDemo() {
        super("INTERACTION", "RATINGSTARSDEMO");
    }

    @Override
    protected com.vaadin.ui.Component createLayout() {
        return FluentUI.horizontal()
            .spacing()
            .margin()
            .add(FluentUI.panel()
                .style(DSuiteTheme.PANEL_INVERTED)
                .content(FluentUI.vertical()
                    .spacing()
                    .margin()
                    .add(getRatingStarsDefault())
                    .add(getRatingStars3())
                    .add(getRatingStarsTiny())
                    .add(getRatingStarsHover())
                    .add(getRatingStarsReadOnly())
                    .add(FluentUI.panel()
                        .content(FluentUI.vertical()
                            .spacing()
                            .margin()
                            .add(getRatingStarsDefault(DSuiteTheme.RATINGSTARS_INVERTED))
                            .add(getRatingStars3(DSuiteTheme.RATINGSTARS_INVERTED))
                            .add(getRatingStarsTiny(DSuiteTheme.RATINGSTARS_INVERTED))
                            .add(getRatingStarsHover(DSuiteTheme.RATINGSTARS_INVERTED))
                            .add(getRatingStarsReadOnly(DSuiteTheme.RATINGSTARS_INVERTED))
                            .get())
                        .get())
                    .get())
                .get())
            .get();
    }

    private RatingStars getRatingStarsDefault() {
        return getRatingStarsDefault(null);
    }

    private RatingStars getRatingStarsDefault(final String styleName) {
        final RatingStars ratingStarsDefault = new RatingStars();
        ratingStarsDefault.setCaption("default");
        if (styleName != null) {
            ratingStarsDefault.addStyleName(styleName);
        }
        ratingStarsDefault.setMaxValue(5);
        ratingStarsDefault.setImmediate(true);
        ratingStarsDefault.addValueChangeListener((ValueChangeListener) ValueChangeEvent -> this.notificationProvider
            .showInfoNotification("You voted " + ValueChangeEvent.getProperty()
                .getValue() + " stars!"));
        return ratingStarsDefault;
    }

    private RatingStars getRatingStars3() {
        return getRatingStars3(null);
    }

    private RatingStars getRatingStars3(final String styleName) {
        final RatingStars ratingStarsHover = getRatingStarsDefault(styleName);
        ratingStarsHover.setCaption("only 3 Stars");
        ratingStarsHover.setMaxValue(3);
        return ratingStarsHover;
    }

    private RatingStars getRatingStarsHover() {
        return getRatingStarsHover(null);
    }

    private RatingStars getRatingStarsHover(final String styleName) {
        final RatingStars ratingStarsHover = getRatingStarsDefault(styleName);
        ratingStarsHover.setCaption("hover");
        ratingStarsHover.setValueCaption(RatingStarsDemo.valueCaptions.values()
            .toArray(new String[5]));
        return ratingStarsHover;
    }

    private RatingStars getRatingStarsTiny() {
        return getRatingStarsTiny(null);
    }

    private RatingStars getRatingStarsTiny(final String styleName) {
        final RatingStars ratingStarsTiny = getRatingStarsDefault(styleName);
        ratingStarsTiny.setCaption("tiny");
        ratingStarsTiny.addStyleName("tiny");
        return ratingStarsTiny;
    }

    private RatingStars getRatingStarsReadOnly() {
        return getRatingStarsReadOnly(null);
    }

    private RatingStars getRatingStarsReadOnly(final String styleName) {
        final RatingStars ratingStarsReadonly = new RatingStars();
        ratingStarsReadonly.setCaption("read-only");
        if (styleName != null) {
            ratingStarsReadonly.addStyleName(styleName);
        }
        ratingStarsReadonly.setValue(2.5);
        ratingStarsReadonly.setReadOnly(true);
        return ratingStarsReadonly;
    }
}
