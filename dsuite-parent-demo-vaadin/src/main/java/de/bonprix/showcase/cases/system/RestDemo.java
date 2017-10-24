package de.bonprix.showcase.cases.system;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;

import de.bonprix.base.demo.dto.Application;
import de.bonprix.base.demo.service.ApplicationService;
import de.bonprix.model.CollectionFilterOperation;
import de.bonprix.model.CollectionPropertyFilter;
import de.bonprix.model.ComplexFilter;
import de.bonprix.model.JunctionFilter;
import de.bonprix.model.StringFilterOperation;
import de.bonprix.model.StringPropertyFilter;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.bean.field.BeanItemComboBoxMultiselect;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;

@SuppressWarnings("serial")
@SpringViewComponent
public class RestDemo extends ShowcaseWrapper {

    @Resource
    private ApplicationService applicationService;

    private BeanItemGrid<Application> applicationsGrid;

    public RestDemo() {
        super("SYSTEM", "REST_DEMO");
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

        this.applicationsGrid = new BeanItemGrid<>(Application.class);
        this.applicationsGrid.setColumns("id", "name", "applicationTypeNameKey");
        this.applicationsGrid.setSizeFull();

        final Label openBracketLabel = new Label("(");

        final Label nameLabel = new Label("name");
        final TextField nameStringFilter = new TextField();
        final BeanItemComboBox<StringFilterOperation> nameOperationComboBox = new BeanItemComboBox<>(StringFilterOperation.class);
        nameOperationComboBox.replaceAllBeans(Arrays.asList(StringFilterOperation.values()));
        nameOperationComboBox.setNullSelectionAllowed(false);
        nameOperationComboBox.select(StringFilterOperation.STARTS_WITH);

        final Label andLabel = new Label("AND");

        final Label applicationTypeNameLabel = new Label("applicationType.nameKey");
        final TextField applicationTypeNameFilter = new TextField();
        final BeanItemComboBox<StringFilterOperation> applicationTypeNameOperationComboBox = new BeanItemComboBox<>(StringFilterOperation.class);
        applicationTypeNameOperationComboBox.replaceAllBeans(Arrays.asList(StringFilterOperation.values()));
        applicationTypeNameOperationComboBox.setNullSelectionAllowed(false);
        applicationTypeNameOperationComboBox.select(StringFilterOperation.STARTS_WITH);

        final Label closeBracketLabel = new Label(")");

        final Label orLabel = new Label("OR");

        final Label idLabel = new Label("id");
        final BeanItemComboBoxMultiselect<Long> idMultiSelect = new BeanItemComboBoxMultiselect<>(Long.class);
        idMultiSelect.replaceAllBeans(Arrays.asList(1L, 2L, 3L, 4L));
        final BeanItemComboBox<CollectionFilterOperation> idOperation = new BeanItemComboBox<>(CollectionFilterOperation.class);
        idOperation.replaceAllBeans(Arrays.asList(CollectionFilterOperation.values()));
        idOperation.setNullSelectionAllowed(false);
        idOperation.select(CollectionFilterOperation.IN);

        final Layout filters = FluentUI.horizontal()
            .spacing()
            .add(openBracketLabel)
            .add(nameLabel)
            .add(nameOperationComboBox)
            .add(nameStringFilter)
            .add(andLabel)
            .add(applicationTypeNameLabel)
            .add(applicationTypeNameOperationComboBox)
            .add(applicationTypeNameFilter)
            .add(closeBracketLabel)
            .add(orLabel)
            .add(idLabel)
            .add(idOperation)
            .add(idMultiSelect)
            .get();

        final Button button = FluentUI.button()
            .captionKey("GET_APPLICATIONS")
            .onClick(event -> {

                final ComplexFilter filter = new ComplexFilter();

                // This is how you define basic filter with AND junctions between all filters

                final StringPropertyFilter nameFilter = StringPropertyFilter.of(Application.NAME, nameOperationComboBox.getSelectedItem(),
                                                                                nameStringFilter.getValue());

                final StringPropertyFilter applicationNameFilter = StringPropertyFilter
                    .of(Application.APPLICATION_TYPE_NAME_KEY, applicationTypeNameOperationComboBox.getSelectedItem(), applicationTypeNameFilter.getValue());

                final CollectionPropertyFilter idsFilter = CollectionPropertyFilter.of(Application.ID, idOperation.getSelectedItem(),
                                                                                       idMultiSelect.getSelectedItems());

                // There are three ways to define junctions
                // 1. Using a static method in JunctionFilter
                // 2. Using a "fluent" api by calling "and" or "or" of a filter
                // 3. By creating a new JunctionFilter and defining the nested filters manually to it afterwards
                filter.setBaseJunctionFilter(JunctionFilter.orOf(nameFilter.and(applicationNameFilter), idsFilter));

                final List<Application> applications = this.applicationService.findByFilter(filter);
                this.applicationsGrid.replaceAllBeans(applications);
            })
            .get();

        return FluentUI.vertical()
            .add(button)
            .add(filters)
            .add(this.applicationsGrid, 1)
            .sizeFull()
            .margin()
            .spacing()
            .get();
    }
}
