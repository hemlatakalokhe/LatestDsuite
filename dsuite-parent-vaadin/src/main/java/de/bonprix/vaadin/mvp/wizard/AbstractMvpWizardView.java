package de.bonprix.vaadin.mvp.wizard;

import java.util.List;
import java.util.stream.Collectors;

import org.vaadin.addons.scrollablepanel.ScrollablePanel;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogConfiguration;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.dialog.AbstractMvpDialogView;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * View part of mvp for the wizard
 * 
 * @author dmut
 *
 * @param <PRESENTER>
 *            presenter interface
 */
public abstract class AbstractMvpWizardView<PRESENTER extends MvpWizardPresenter>
		extends AbstractMvpDialogView<PRESENTER> implements MvpWizardView<PRESENTER> {

	private ScrollablePanel stepPanel;
	private VerticalLayout progressLayout;
	private ProgressBar progressBar;

	public AbstractMvpWizardView(final DialogConfiguration dialogConfiguration) {
		super(dialogConfiguration);

		addButtonListener(DialogButton.BACK, event -> getPresenter().back());
		addButtonListener(DialogButton.NEXT, event -> getPresenter().next());
		addButtonListener(DialogButton.FINISH, event -> getPresenter().finish());
		addButtonListener(DialogButton.CANCEL, event -> close());
	}

	public <T> T cast(final Class<T> type) {
		return type.cast(this);
	}

	@Override
	protected Component layout() {
		this.stepPanel = new ScrollablePanel();
		this.stepPanel.setSizeFull();
		this.stepPanel.addStyleName(DSuiteTheme.DIALOG_PANEL_WHITE);

		getPresenter().renderProgressInfo();
		getPresenter().stepChanged();

		return FluentUI	.vertical()
						.add(this.progressLayout)
						.add(this.stepPanel, 1)
						.margin()
						.spacing()
						.sizeFull()
						.get();
	}

	public void renderProgressInfo(List<WizardStep> wizardSteps) {
		final HorizontalLayout captionLayout = FluentUI	.horizontal()
														.widthFull()
														.height(20, Unit.PIXELS)
														.get();
		if (wizardSteps != null) {
			for (final WizardStep wizardStep : wizardSteps) {
				final Label wizardLabel = FluentUI	.label()
													.value(String.format(	"<center>%s</center>",
																			wizardStep.getCaption()))
													.contentMode(ContentMode.HTML)
													.style("wizardStepCaption")
													.sizeFull()
													.get();
				captionLayout.addComponent(wizardLabel);
				captionLayout.setExpandRatio(wizardLabel, 1);
			}
		}

		this.progressBar = new ProgressBar(0);
		this.progressBar.setStyleName("wizardStepProgress");
		this.progressBar.setWidth(100, Unit.PERCENTAGE);
		this.progressBar.setHeight(5, Unit.PIXELS);

		this.progressLayout = FluentUI	.vertical()
										.add(captionLayout)
										.add(this.progressBar, Alignment.MIDDLE_CENTER)
										.widthFull()
										.height(30, Unit.PIXELS)
										.spacing()
										.get();
	}

	public void setButtonListToPresenter() {
		List<String> buttonNames = getButtonMap()	.entrySet()
													.stream()
													.map(e -> e.getKey())
													.collect(Collectors.toList());

		getPresenter().setButtonList(buttonNames);
	}

	@Override
	public void setButtonEnabled(String buttonName, Boolean enabled) {
		getButton(buttonName).setEnabled(enabled);
	}

	@Override
	public void setButtonVisible(String buttonName, Boolean visible) {
		getButton(buttonName).setVisible(visible);
	}

	@Override
	public void setProgressBarValue(Float newValue) {
		this.progressBar.setValue(newValue);
	}

	@Override
	public void setStepPanelContent(Component content) {
		this.stepPanel.setContent(content);
	}

	public void setProgressLayoutVisible(final boolean visible) {
		this.progressLayout.setVisible(visible);
	}
}
