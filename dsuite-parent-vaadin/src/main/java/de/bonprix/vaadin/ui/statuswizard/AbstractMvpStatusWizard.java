package de.bonprix.vaadin.ui.statuswizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.scrollablepanel.ScrollablePanel;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.I18N;
import de.bonprix.vaadin.IconSize;
import de.bonprix.vaadin.dialog.DialogConfiguration;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.dialog.AbstractMvpDialogView;
import de.bonprix.vaadin.mvp.dialog.MvpDialogPresenter;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * The AbstractStatusWizard is a base class for general StatusWizard window. The
 * Wizard offers datainput via multiple steps and has a status overview for each
 * WizardSteps
 *
 * @author dmut
 *
 */
public abstract class AbstractMvpStatusWizard<P extends MvpDialogPresenter> extends AbstractMvpDialogView<P> {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMvpStatusWizard.class);

	private final List<StatusWizardStep> wizardStepList;
	private final Map<StatusWizardStep, Button> overviewButtonMap;
	private final Map<StatusWizardStep, Image> overviewButtonImageMap;
	private final Map<StatusWizardStep, Image> overviewSecondButtonImageMap;
	private static final Integer BUTTON_FULL_WIDTH = 100;

	private CurrentPanel currentPanel;
	private StatusWizardStep currentStep;

	private StausWizardButtonBar buttonBar;

	private String caption;

	private StatusWizardMode mode = StatusWizardMode.CREATE;

	/**
	 * @param configuration
	 */
	public AbstractMvpStatusWizard(final DialogConfiguration configuration) {
		super(configuration);

		this.wizardStepList = new ArrayList<>();
		this.overviewButtonMap = new HashMap<>();
		this.overviewButtonImageMap = new HashMap<>();
		this.overviewSecondButtonImageMap = new HashMap<>();
		center();
	}

	public AbstractMvpStatusWizard(final DialogConfiguration configuration, StatusWizardMode mode) {
		super(configuration);

		this.wizardStepList = new ArrayList<>();
		this.overviewButtonMap = new HashMap<>();
		this.overviewButtonImageMap = new HashMap<>();
		this.overviewSecondButtonImageMap = new HashMap<>();
		this.mode = mode;
		center();
	}

	/**
	 * Builds the complete Layout of the StatusWizard
	 */
	@Override
	public Component layout() {
		addStyleName(DSuiteTheme.STATUS_WIZARD);
		setModal(true);
		initChangeListenersOfFieldGroupItems();

		this.currentPanel = new CurrentPanel(getCurrentStep());
		final VerticalLayout left = FluentUI.vertical()
			.add(overviewLayout(), 0.92f)
			.spacing()
			.margin()
			.sizeFull()
			.style(DSuiteTheme.STATUS_WIZARD_LEFT_LAYOUT)
			.get();

		final VerticalLayout right = FluentUI.vertical()
			.add(this.currentPanel, 0.9f)
			.add(renderButtonPanel(), 0.1f, Alignment.BOTTOM_CENTER)
			.spacing()
			.margin(true, true, false, true)
			.style(DSuiteTheme.STATUS_WIZARD_RIGHT_LAYOUT)
			.sizeFull()
			.get();

		return FluentUI.horizontal()
			.add(left, 0.25f)
			.add(right, 0.75f)
			.sizeFull()
			.style(DSuiteTheme.STATUS_WIZARD_MAIN)
			.get();
	}

	/**
	 * Builds the overview panel
	 *
	 * @return Component
	 */
	public Component overviewLayout() {
		final VerticalLayout overviewLayout = FluentUI.vertical()
			.add(FluentUI.label()
				.value(I18N.get("OVERVIEW"))
				.style(DSuiteTheme.STATUS_WIZARD_LABEL_HEADER)
				.get())
			.spacing()
			.get();

		final ScrollablePanel panel = new ScrollablePanel();
		panel.setHorizontalScrollingEnabled(Boolean.FALSE);
		panel.setSizeFull();

		GridLayout gridLayout = new GridLayout(3, this.getWizardStepList()
			.size());

		int row = 0;
		for (final StatusWizardStep step : this.wizardStepList) {

			final Button button = FluentUI.button()
				.caption(step.getCaption())
				.onClick(event -> updatePanel(step))
				.width(AbstractMvpStatusWizard.BUTTON_FULL_WIDTH, Unit.PERCENTAGE)
				.get();

			final Image image = FluentUI.image()
				.source(step.getStepStatus()
					.getValue())
				.height(IconSize.SIZE_32.getSize(), Unit.PIXELS)
				.width(IconSize.SIZE_32.getSize(), Unit.PIXELS)
				.onClick(event -> updatePanel(step))
				.get();

			image.setDescription("Mandatory");

			final Image secondImage = FluentUI.image()
				.source(step.getSecondStepStatus()
					.getValue())
				.height(IconSize.SIZE_32.getSize(), Unit.PIXELS)
				.width(IconSize.SIZE_32.getSize(), Unit.PIXELS)
				.onClick(event -> updatePanel(step))
				.get();

			secondImage.setDescription("Optional");

			if (step.hasMandatoryFields()) {
				gridLayout.addComponent(image, 0, row);
			}
			if (step.getOptionalFileds()
				.size() > 0) {
				gridLayout.addComponent(secondImage, 1, row);
			}

			gridLayout.addComponent(button, 2, row++);

			this.overviewButtonMap.put(step, button);
			this.overviewButtonImageMap.put(step, image);
			this.overviewSecondButtonImageMap.put(step, secondImage);
			overviewLayout.addComponent(gridLayout);

		}
		markCurrendStepInOverview();
		panel.setContent(overviewLayout);
		return panel;
	}

	/**
	 * Creates the buttonPanel with buttons prev., next, finished, save and
	 * cancel
	 *
	 * @return Component The ButtonPanel
	 */
	public Component renderButtonPanel() {

		this.buttonBar = new StausWizardButtonBar();

		this.buttonBar.getPrevious()
			.setEnabled(this.backAllowed());

		this.buttonBar.getPrevious()
			.addClickListener(event -> back());

		this.buttonBar.getNext()
			.addClickListener(event -> next());

		this.buttonBar.getCancel()
			.addClickListener(event -> cancel());

		this.buttonBar.getSave()
			.addClickListener(event -> save());

		return this.buttonBar.layout();
	}

	public void cancel() {
		close();
	}

	public void finishCurrentStep() {
		try {
			this.currentStep.getFieldGroup()
				.commit();
			this.currentStep.getBean();
			this.calculateStepStatus(this.getCurrentStep());
			this.calculateSecondStepStatus(this.getCurrentStep());

		} catch (final CommitException e) {
			AbstractMvpStatusWizard.LOGGER.info(e.getLocalizedMessage() + ": Mandatory field empty");
		}
		return;
	}

	public WizardStepStatus calculateStepStatus(StatusWizardStep step) {
		if (step.getFieldGroup()
			.isValid()) {
			this.updateStepStatus(step, WizardStepStatus.FINISHED);
		} else {
			this.updateStepStatus(step, WizardStepStatus.MANDATORY_NOT_FILLED);
		}
		if (allFieldsEmpty(step)) {
			this.updateStepStatus(step, WizardStepStatus.EMPTY);
		}

		return step.getStepStatus();
	}

	public WizardStepStatus calculateSecondStepStatus(StatusWizardStep step) {

		if (this.allOptionalFieldsFilled(step)) {
			this.updateSecondStepStatus(step, WizardStepStatus.FINISHED);
		} else if (allOptionalFieldsEmpty(step)) {
			this.updateSecondStepStatus(step, WizardStepStatus.EMPTY);
		} else if (this.atLeastOneOptionalFieldNotEmpty(step) && this.atLeastOneOptionalFieldEmpty(step)) {
			this.updateSecondStepStatus(step, WizardStepStatus.STARTED);
		}
		return step.getStepStatus();
	}

	private boolean atLeastOneOptionalFieldNotEmpty(final StatusWizardStep step) {
		if (step.getOptionalFileds()
			.size() > 1) {
			return step.getOptionalFileds()
				.stream()
				.anyMatch(field -> !field.isEmpty());
		} else {
			return false;
		}
	}

	private boolean atLeastOneOptionalFieldEmpty(final StatusWizardStep step) {

		if (step.getOptionalFileds()
			.size() > 1) {
			return step.getOptionalFileds()
				.stream()
				.anyMatch(Field::isEmpty);
		} else {
			return false;
		}
	}

	private boolean allFieldsEmpty(final StatusWizardStep step) {
		return step.getFieldGroup()
			.getFields()
			.stream()
			.allMatch(Field::isEmpty);

	}

	private boolean allOptionalFieldsEmpty(final StatusWizardStep step) {
		return step.getOptionalFileds()
			.stream()
			.allMatch(Field::isEmpty);
	}

	private boolean allOptionalFieldsFilled(final StatusWizardStep step) {
		return step.getOptionalFileds()
			.stream()
			.noneMatch(Field::isEmpty);
	}

	public void save() {
		this.finishCurrentStep();
		Notification.show("saved");
		close();
	}

	public void back() {
		if (backAllowed()) {
			updatePanel(this.wizardStepList.get(this.wizardStepList.indexOf(this.currentStep) - 1));
		}
	}

	public void next() {
		if (nextAllowed()) {
			updatePanel(this.wizardStepList.get(this.wizardStepList.indexOf(this.currentStep) + 1));
		}
	}

	public boolean allFinished() {
		for (final StatusWizardStep step : this.wizardStepList) {
			if (!step.getStepStatus()
				.equals(WizardStepStatus.FINISHED)) {
				return false;
			}
		}
		return true;
	}

	public boolean backAllowed() {
		return !getCurrentStep().equals(this.wizardStepList.get(0));
	}

	public boolean nextAllowed() {
		return !getCurrentStep().equals(this.wizardStepList.get(this.wizardStepList.size() - 1));
	}

	/**
	 * Updates the stepStatus and calls statusIcon update of the overview
	 *
	 * @param step
	 * @param status
	 */
	public void updateStepStatus(final StatusWizardStep step, final WizardStepStatus status) {
		step.setStepStatus(status);
		updateButtonState(step);
	}

	/**
	 * Updates the secondStepStatus and calls statusIcon update of the overview
	 *
	 * @param step
	 * @param status
	 */
	public void updateSecondStepStatus(final StatusWizardStep step, final WizardStepStatus status) {
		step.setSecondStepStatus(status);
		updateButtonState(step);
	}

	/**
	 * Updates the color of the statusIcons in the overviewPanel
	 *
	 * @param step
	 */
	public void updateButtonState(final StatusWizardStep step) {

		if (getOverviewButtonImageMap().containsKey(step)) {

			getOverviewButtonImageMap().get(step)
				.setSource(step.getStepStatus()
					.getValue());

		}
		if (getOverviewSecondButtonImageMap().containsKey(step)) {

			getOverviewSecondButtonImageMap().get(step)
				.setSource(step.getSecondStepStatus()
					.getValue());
		}
	}

	/**
	 * Marks the selected step
	 */
	public void markCurrendStepInOverview() {

		for (final Entry<StatusWizardStep, Button> entry : getOverviewButtonMap().entrySet()) {
			entry.getValue()
				.removeStyleName("selected");
		}

		if (getOverviewButtonMap().containsKey(this.currentStep)) {
			getOverviewButtonMap().get(this.currentStep)
				.addStyleName("selected");
		}
	}

	/**
	 * Updates Panel content. The Panel itself, the panelheader and marks the
	 * currend selected step in the overview.
	 *
	 * @param step
	 *            The StatusWizardStep
	 */
	public void updatePanel(final StatusWizardStep step) {
		this.finishCurrentStep();
		this.calculateStepStatus(this.currentStep);
		this.calculateSecondStepStatus(this.currentStep);
		setCurrentStep(step);
		this.getCurrentPanel()
			.updatePanel(step);
		markCurrendStepInOverview();
		this.buttonBar.getPrevious()
			.setEnabled(backAllowed());
		this.buttonBar.getNext()
			.setEnabled(nextAllowed());
	}

	/**
	 * Appends ValueChangeListeners to all FieldGroup Fields to update the
	 * status overview
	 */
	private void initChangeListenersOfFieldGroupItems() {
		for (final StatusWizardStep tmpStep : this.wizardStepList) {
			for (final Field field : tmpStep.getFieldGroup()
				.getFields()) {
				if (field instanceof TextField) {
					final TextField textField = (TextField) field;
					textField.addTextChangeListener(event -> {

						if (field.isRequired()) {
							updateStepStatus(tmpStep, WizardStepStatus.STARTED);
						} else {
							updateSecondStepStatus(tmpStep, WizardStepStatus.STARTED);
						}

					});
				} else {
					field.addValueChangeListener(event -> {
						if (field.isEmpty()) {
							return;
						}
						if (field.isRequired()) {
							updateStepStatus(tmpStep, WizardStepStatus.STARTED);
						} else {
							updateSecondStepStatus(tmpStep, WizardStepStatus.STARTED);
						}
					});
				}
			}
		}
	}

	public void addStep(final StatusWizardStep step) {
		if (this.getMode()
			.equals(StatusWizardMode.EDIT)) {
			this.calculateStepStatus(step);
			this.calculateSecondStepStatus(step);
		}
		if (this.currentStep == null) {
			this.currentStep = step;
		}
		this.wizardStepList.add(step);
	}

	public List<StatusWizardStep> getWizardStepList() {
		return this.wizardStepList;
	}

	public StatusWizardStep getCurrentStep() {
		return this.currentStep;
	}

	public void setCurrentStep(final StatusWizardStep newCurrentStep) {
		this.currentStep = newCurrentStep;
	}

	public CurrentPanel getCurrentPanel() {
		return this.currentPanel;
	}

	public void setCurrentPanel(final CurrentPanel currentPanel) {
		this.currentPanel = currentPanel;
	}

	public Map<StatusWizardStep, Button> getOverviewButtonMap() {
		return this.overviewButtonMap;
	}

	public String getCaption() {
		return this.caption;
	}

	@Override
	public void setCaption(final String caption) {
		this.caption = caption;
	}

	public Map<StatusWizardStep, Image> getOverviewButtonImageMap() {
		return this.overviewButtonImageMap;
	}

	public Map<StatusWizardStep, Image> getOverviewSecondButtonImageMap() {
		return this.overviewSecondButtonImageMap;
	}

	public StatusWizardMode getMode() {
		return this.mode;
	}

	public void setMode(StatusWizardMode mode) {
		this.mode = mode;
	}
}
