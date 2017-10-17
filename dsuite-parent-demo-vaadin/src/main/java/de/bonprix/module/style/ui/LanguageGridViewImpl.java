package de.bonprix.module.style.ui;

import javax.annotation.Resource;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import de.bonprix.VaadinUI;
import de.bonprix.languagegrid.LanguageContainer;
import de.bonprix.languagegrid.LanguageElement;
import de.bonprix.module.style.LanguageGridPresenter;
import de.bonprix.module.style.LanguageGridView;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.provider.UiNotificationProvider;



@SpringView(
            name = LanguageGridViewImpl.VIEW_NAME,
            ui = { VaadinUI.class },
            isDefault = false,
            order = 30)
public class LanguageGridViewImpl extends AbstractMvpView<LanguageGridPresenter> implements LanguageGridView{

    /**
     * 
     */
    private static final long serialVersionUID = -8291506048526624339L;


    @Resource
    private UiNotificationProvider notificationProvider;


    public static final String VIEW_NAME = "Language Grid";

    @Override
    protected void initializeUI() {

        final LanguageContainer languageContainer=new LanguageContainer();
        final LanguageElement languageElement=new LanguageElement();
        languageElement.setId(1L);
        languageElement.setLanguageId(301L);


        //        final LanguageElement languageElement2=new LanguageElement();
        //        languageElement2.setId(2L);
        //        languageElement2.setLanguageId(299l);
        languageContainer.addI18NLanguageElement(languageElement);
        //        languageContainer.addI18NLanguageElement(languageElement2);
        final BeanFieldGroup<LanguageContainer> fieldGroup=new BeanFieldGroup<>(LanguageContainer.class);
        fieldGroup.setItemDataSource(languageContainer);

        final Button language = new Button("Language");
        language.addClickListener(event -> {
            final StringBuilder result = new StringBuilder();

            for(final LanguageElement languageElements : languageContainer.getLanguageElements()){
                if(languageElements.getName()!=null || languageElements.getDescription()!=null) {
                    result.append(" language Id: ").append(languageElements.getLanguageId()).append("Name: ").append(languageElements.getName());
                }
            }

            this.notificationProvider.showInfoNotification("INFO", result.toString(), 15000);
        });

        setCompositionRoot(FluentUI.vertical().add(FluentUI.languageGrid(LanguageElement.class, fieldGroup)
                                                   .languageNameKey("LANGUAGE")
                                                   .bind("name", "NAME")
                                                   .bind("description", "DESCRIPTION")
                                                   .width(50f, Unit.PERCENTAGE)
                                                   .height(30f, Unit.PERCENTAGE)
                                                   .expandRatios(1, 2, 2)
                                                   .filter(true, "FILTER")
                                                   .get())
                           .add(language)
                           .get());
    }

}
