package de.bonprix.showcase.cases.field;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;

import de.bonprix.model.Example;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.mvp.SpringViewComponent;

/**
 * The BeanItemComboBox is an extension to the default vaadin ComboBox. This implementation uses a bean item container as default container and some useful
 * methods to modify the container directly.
 *
 * @author tmeissne
 *
 */
@SuppressWarnings("serial")
@SpringViewComponent
public class BeanItemComboboxDemo extends ShowcaseWrapper {

    public BeanItemComboboxDemo() {
        super("BEANITEM", "BEANITEMCOMBOBOXDEMO");
    }

    @Override
    protected Component createLayout() {

        // Create the BeanItemCombobox
        final BeanItemComboBox<Example> combobox = new BeanItemComboBox<>(Example.class, "Continent");
        combobox.setWidth(300, Unit.PIXELS);
        // Sets the item caption property
        combobox.setItemCaptionPropertyId("name");
        // Add Listener to the Combobox
        combobox.addValueChangeListener(event -> Notification.show("You select " + combobox.getSelectedItem()
            .getName()));

        // Fill the Combobox with Data
        combobox.replaceAllBeans(createBeanItems());
        // Sort the Combobox by name
        combobox.setBeanComparator((o1, o2) -> o1.getName()
            .compareTo(o2.getName()));

        final FormLayout formLayout = new FormLayout(combobox);
        formLayout.setWidth(100, Unit.PERCENTAGE);
        formLayout.setMargin(true);

        return formLayout;
    }

    // Create the Beans for the Combobox
    private List<Example> createBeanItems() {
        final List<Example> exampleList = new ArrayList<>();

        exampleList.add(new Example(1, "Asia"));
        exampleList.add(new Example(2, "Europe"));
        exampleList.add(new Example(3, "Africa"));
        exampleList.add(new Example(4, "Oceania"));
        exampleList.add(new Example(5, "North america"));
        exampleList.add(new Example(6, "South america"));
        exampleList.add(new Example(7, "Antarctica"));

        return exampleList;

    }

}
