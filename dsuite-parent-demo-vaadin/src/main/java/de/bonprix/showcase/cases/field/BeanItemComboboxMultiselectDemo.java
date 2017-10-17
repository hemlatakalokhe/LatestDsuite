package de.bonprix.showcase.cases.field;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.vaadin.addons.comboboxmultiselect.ComboBoxMultiselect.ShowButton;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;

import de.bonprix.model.Example;
import de.bonprix.model.FtOt;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.bean.field.BeanItemComboBoxMultiselect;
import de.bonprix.vaadin.mvp.SpringViewComponent;

/**
 * 
 * @author ppries
 *
 */

@SuppressWarnings("serial")
@SpringViewComponent
public class BeanItemComboboxMultiselectDemo extends ShowcaseWrapper {

	public BeanItemComboboxMultiselectDemo() {
		super("BEANITEM", "BEANITEMCOMBOBOXMULTISELECTDEMO");
	}

	@Override
	protected Component createLayout() {

		final BeanItemComboBoxMultiselect<Example> comboboxContinent = genContinentComboBox();
		final BeanItemComboBoxMultiselect<FtOt> comboboxFtOt = genFtOtComboBox();

		final FormLayout formLayout = new FormLayout(comboboxContinent, comboboxFtOt);
		formLayout.setWidth(100, Unit.PERCENTAGE);
		formLayout.setMargin(true);

		return formLayout;
	}

	private BeanItemComboBoxMultiselect<Example> genContinentComboBox() {
		// Create the BeanItemCombobox
		final BeanItemComboBoxMultiselect<Example> comboboxContinent = new BeanItemComboBoxMultiselect<>(Example.class,
				"Continent");
		comboboxContinent.setWidth(300, Unit.PIXELS);
		// Sets the item caption property
		comboboxContinent.setItemCaptionPropertyId("name");
		// Add Listener to the Combobox
		comboboxContinent.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {

				HashSet<Example> set = new HashSet<>(comboboxContinent.getSelectedItems());
				StringBuilder str = new StringBuilder();

				Iterator<Example> iterator = set.iterator();
				while (iterator.hasNext()) {
					Example item = iterator.next();
					str.append(item.getName());
					if (iterator.hasNext()) {
						str.append(", ");
					}
				}
				Notification.show("You select " + str.toString());
			}
		});

		// Fill the Combobox with Data
		comboboxContinent.replaceAllBeans(createContinentBeanItems());
		// Sort the Combobox by name
		comboboxContinent.setBeanComparator(new Comparator<Example>() {

			@Override
			public int compare(final Example o1, final Example o2) {
				int order = comboboxContinent.getSelectedValueOrder(o1, o2);
				if (order != 0) {
					return order;
				}
				return o1.getName()
					.compareTo(o2.getName());
			}
		});
		return comboboxContinent;
	}

	private BeanItemComboBoxMultiselect<FtOt> genFtOtComboBox() {
		// Create the BeanItemCombobox
		final BeanItemComboBoxMultiselect<FtOt> comboboxFTOT = new BeanItemComboBoxMultiselect<>(FtOt.class, "FtOt");
		// Sets the
		comboboxFTOT.setShowSelectAllButton(new ShowButton() {

			@Override
			public boolean isShow(final String filter, final int page) {
				return true;
			}
		});
		// Sets the item caption property
		comboboxFTOT.setItemCaptionPropertyId("name");
		// Sets the combobox filterin option
		comboboxFTOT.setFilteringMode(FilteringMode.CONTAINS);
		// Add Listener to the Combobox
		comboboxFTOT.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {

				HashSet<FtOt> set = new HashSet<>(comboboxFTOT.getSelectedItems());
				StringBuilder str = new StringBuilder();

				Iterator<FtOt> iterator = set.iterator();
				while (iterator.hasNext()) {
					FtOt item = iterator.next();
					str.append(item.getName());
					if (iterator.hasNext()) {
						str.append(", ");
					}
				}
				Notification.show("You select " + str.toString());
			}
		});

		// Fill the Combobox with Data
		comboboxFTOT.replaceAllBeans(createFtOtBeanItems());
		// Sort the Combobox by name
		comboboxFTOT.setBeanComparator(new Comparator<FtOt>() {

			@Override
			public int compare(final FtOt o1, final FtOt o2) {
				int order = comboboxFTOT.getSelectedValueOrder(o1, o2);
				if (order != 0) {
					return order;
				}
				return o1.getName()
					.compareTo(o2.getName());
			}
		});
		return comboboxFTOT;
	}

	// Create the Beans for the Combobox
	private List<Example> createContinentBeanItems() {
		List<Example> exampleList = new ArrayList<>();

		exampleList.add(new Example(1, "Asia"));
		exampleList.add(new Example(2, "Europe"));
		exampleList.add(new Example(3, "Africa"));
		exampleList.add(new Example(4, "Oceania"));
		exampleList.add(new Example(5, "North america"));
		exampleList.add(new Example(6, "South america"));
		exampleList.add(new Example(7, "Antarctica"));

		return exampleList;

	}

	// Create the Beans for the Combobox
	private List<FtOt> createFtOtBeanItems() {
		List<FtOt> exampleList = new ArrayList<>();

		exampleList.add(new FtOt(11, 2));
		exampleList.add(new FtOt(11, 3));
		exampleList.add(new FtOt(11, 5));
		exampleList.add(new FtOt(11, 9));

		exampleList.add(new FtOt(13, 2));
		exampleList.add(new FtOt(13, 3));
		exampleList.add(new FtOt(13, 5));
		exampleList.add(new FtOt(13, 9));

		exampleList.add(new FtOt(23, 2));
		exampleList.add(new FtOt(23, 3));
		exampleList.add(new FtOt(23, 5));
		exampleList.add(new FtOt(23, 9));

		return exampleList;

	}

}
