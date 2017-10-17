package de.bonprix.showcase.cases.statuswizard;

import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;

import de.bonprix.model.Country;
import de.bonprix.model.Item;
import de.bonprix.model.Orders;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.showcase.cases.statuswizard.steps.CountryStep;
import de.bonprix.showcase.cases.statuswizard.steps.ItemStep;
import de.bonprix.showcase.cases.statuswizard.steps.OrdersStep;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.mvp.dialog.MvpDialogPresenter;
import de.bonprix.vaadin.ui.statuswizard.AbstractMvpStatusWizard;
import de.bonprix.vaadin.ui.statuswizard.StatusWizardMode;
import de.bonprix.vaadin.ui.statuswizard.StatusWizardStep;

@SpringViewComponent
public class StatusWizardDemo extends ShowcaseWrapper {

	private static final String COUNTRY = "Country";
	private static final String ORDERS = "Orders";
	private static final String NAME = "name:";

	/**
	 * @param parentKey
	 * @param captionKey
	 */
	public StatusWizardDemo() {
		super("STRUCTURE_HIERARCHY", "STATUSWIZARDDEMO");
	}

	@Override
	protected Component createLayout() {

		return FluentUI.vertical()
			.margin()
			.add(FluentUI.button()
				.captionKey("open CREATE StatusWizard")
				.onClick(event -> getUI().addWindow(new SampleStausWizardCreate()))
				.get())
			.add(FluentUI.button()
				.captionKey("open EDIT StatusWizard")
				.onClick(event -> getUI().addWindow(new SampleStausWizardEdit()))
				.get())
			.get();
	}

	@SuppressWarnings("serial")
	private static class SampleStausWizardCreate extends AbstractMvpStatusWizard<MvpDialogPresenter> {

		public SampleStausWizardCreate() {
			super(new DialogConfigurationBuilder().withWidth(800)
				.withHeight(500)
				.build(), StatusWizardMode.CREATE);

			CountryStep countystep = new CountryStep(Country.class, new Country("", "", null, 0),
					StatusWizardDemo.COUNTRY,
					"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam");
			addStep(countystep);

			final Item item = new Item();
			item.setItemNo("");
			item.setAssetUrl("");
			ItemStep itemStep = new ItemStep(Item.class, item, "Item",
					"Lorem ipsum dolor sit amet, consetetur sadipscing elitrf");
			addStep(itemStep);

			final Orders orders = new Orders();
			orders.setName("");
			orders.setDescription("");

			addStep(new OrdersStep(Orders.class, orders, StatusWizardDemo.ORDERS,
					"Lorem ipsum dolor sit amet, conseteture sadipscing elitr"));
		}

		@Override
		public void save() {
			super.finishCurrentStep();
			if (super.allFinished()) {
				final StringBuilder msg = new StringBuilder();
				for (final StatusWizardStep step : getWizardStepList()) {
					if (step.getBean() instanceof Item) {
						msg.append("Item" + "\n");
						msg.append(("ItemNo: " + ((Item) step.getBean()).getItemNo()) + "\n");
						msg.append(("AssetUrl: " + ((Item) step.getBean()).getAssetUrl()) + "\n\n");
					}
					if (step.getBean() instanceof Country) {
						msg.append(StatusWizardDemo.COUNTRY + "\n");
						msg.append((StatusWizardDemo.NAME + ((Country) step.getBean()).getName()) + "\n");
						msg.append(("IsoCode: " + ((Country) step.getBean()).getIsoCode()) + "\n\n");
					}

					if (step.getBean() instanceof Orders) {
						msg.append(StatusWizardDemo.ORDERS + "\n");
						msg.append((StatusWizardDemo.NAME + ((Orders) step.getBean()).getName()) + "\n");
						msg.append(("description: " + ((Orders) step.getBean()).getDescription()) + "\n\n");
					}

					Notification.show(msg.toString());
					step.getBean();
				}
				close();
			}
		}
	}

	@SuppressWarnings("serial")
	private static class SampleStausWizardEdit extends AbstractMvpStatusWizard<MvpDialogPresenter> {

		public SampleStausWizardEdit() {
			super(new DialogConfigurationBuilder().withWidth(800)
				.withHeight(500)
				.build(), StatusWizardMode.EDIT);

			CountryStep countystep = new CountryStep(Country.class, new Country("test", "test", null, 0),
					StatusWizardDemo.COUNTRY,
					"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam");
			addStep(countystep);

			final Item item = new Item();
			item.setItemNo("");
			item.setAssetUrl("");
			ItemStep itemStep = new ItemStep(Item.class, item, "Item",
					"Lorem ipsum dolor sit amet, consetetur sadipscing elitr");
			addStep(itemStep);

			final Orders orders = new Orders();
			orders.setName("test");
			orders.setDescription("");

			addStep(new OrdersStep(Orders.class, orders, StatusWizardDemo.ORDERS,
					"Lorem ipsum dolor sit amet, consetetur sadipscing elitr"));

		}

		@Override
		public void save() {
			super.finishCurrentStep();
			if (super.allFinished()) {
				final StringBuilder msg = new StringBuilder();
				for (final StatusWizardStep step : getWizardStepList()) {
					if (step.getBean() instanceof Item) {
						msg.append("Item" + "\n");
						msg.append(("ItemNo: " + ((Item) step.getBean()).getItemNo()) + "\n");
						msg.append(("AssetUrl: " + ((Item) step.getBean()).getAssetUrl()) + "\n\n");
					}
					if (step.getBean() instanceof Country) {
						msg.append(StatusWizardDemo.COUNTRY + "\n");
						msg.append((StatusWizardDemo.NAME + ((Country) step.getBean()).getName()) + "\n");
						msg.append(("IsoCode: " + ((Country) step.getBean()).getIsoCode()) + "\n\n");
					}

					if (step.getBean() instanceof Orders) {
						msg.append(StatusWizardDemo.ORDERS + "\n");
						msg.append((StatusWizardDemo.NAME + ((Orders) step.getBean()).getName()) + "\n");
						msg.append(("description: " + ((Orders) step.getBean()).getDescription()) + "\n\n");
					}

					Notification.show(msg.toString());
					step.getBean();
				}
				close();
			}
		}
	}
}
