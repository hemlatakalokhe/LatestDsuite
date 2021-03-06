package de.bonprix.showcase.cases.languagegrid;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import de.bonprix.model.LanguageI18NElementWithDescription;
import de.bonprix.model.SimpleI18NLanguageContainer;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.provider.UiNotificationProvider;

/**
 * Language grid showcase
 * 
 * @author d.kolev
 *
 */
@SpringViewComponent
public class LanguageGridDemo extends ShowcaseWrapper {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -6335304728377620194L;

    @Autowired
    private UiNotificationProvider notificationProvider;

    public LanguageGridDemo() {
        super("CUSTOM_COMPONENTS", "LANGUAGEGRID");
    }

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    protected Component createLayout() {
        final SimpleI18NLanguageContainer container = new SimpleI18NLanguageContainer();
        final LanguageI18NElementWithDescription firstElement = new LanguageI18NElementWithDescription();
        firstElement.setId(1l);
        firstElement.setName("TEST_NAME");
        firstElement.setDescription("TEST DESCRIPTION");
        firstElement.setLanguageId(299l);
        container.addI18NLanguageElement(firstElement);

        final BeanFieldGroup<SimpleI18NLanguageContainer> fieldGroupContainer = new BeanFieldGroup<>(SimpleI18NLanguageContainer.class);
        fieldGroupContainer.setItemDataSource(container);

        final Button clickMe = new Button("CLICK_ME");
        clickMe.addClickListener(event -> {
            final StringBuilder result = new StringBuilder();
            for (final LanguageI18NElementWithDescription el : container.getLanguageElements()) {
                result.append("languageId:")
                    .append(el.getLanguageId())
                    .append(" Name value:")
                    .append(el.getName())
                    .append(" Description:")
                    .append(el.getDescription())
                    .append(';');
            }

            LanguageGridDemo.this.notificationProvider.showInfoNotification("INFO", result.toString(), 15000);
        });

        return FluentUI.vertical()
            .margin()
            .add(FluentUI.languageGrid(LanguageI18NElementWithDescription.class, fieldGroupContainer)
                .languageNameKey("LANGUAGE")
                .bind("name", "NAME")
                .bind("description", "DESCRIPTION")
                .width(50f, Unit.PERCENTAGE)
                .height(30f, Unit.PERCENTAGE)
                .expandRatios(1, 2, 2)
                .filter(true, "FILTER")
                .get())
            .add(clickMe)
            .get();
    }

}
