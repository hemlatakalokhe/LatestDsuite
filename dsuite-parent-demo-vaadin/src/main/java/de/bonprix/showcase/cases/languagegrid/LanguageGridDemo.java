package de.bonprix.showcase.cases.languagegrid;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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
    protected Component createLayout() {
        SimpleI18NLanguageContainer container = new SimpleI18NLanguageContainer();
        LanguageI18NElementWithDescription firstElement = new LanguageI18NElementWithDescription();
        firstElement.setId(1l);
        firstElement.setName("TEST_NAME");
        firstElement.setDescription("TEST DESCRIPTION");
        firstElement.setLanguageId(299l);
        container.addI18NLanguageElement(firstElement);

        BeanFieldGroup<SimpleI18NLanguageContainer> fieldGroupContainer = new BeanFieldGroup<SimpleI18NLanguageContainer>(SimpleI18NLanguageContainer.class);
        fieldGroupContainer.setItemDataSource(container);

        Button clickMe = new Button("CLICK_ME");
        clickMe.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                StringBuilder result = new StringBuilder();
                for (LanguageI18NElementWithDescription el : container.getLanguageElements()) {
                    result.append("languageId:")
                            .append(el.getLanguageId())
                            .append(" Name value:")
                            .append(el.getName())
                            .append(" Description:")
                            .append(el.getDescription())
                            .append(';');
                }

                notificationProvider.showInfoNotification("INFO", result.toString(), 15000);
            }
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
