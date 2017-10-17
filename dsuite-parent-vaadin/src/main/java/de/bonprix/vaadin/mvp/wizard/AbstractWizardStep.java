package de.bonprix.vaadin.mvp.wizard;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.theme.DSuiteTheme;

public abstract class AbstractWizardStep<STEP extends AbstractWizardStep<STEP>> extends CustomComponent
		implements WizardStep {

	private static final long serialVersionUID = 1L;

	private String caption;
	private String headline;
	private String description;
	private OnStepEnter<STEP> onStepEnter;
	private OnStepLeave<STEP> onStepLeave;
	private VerticalLayout header;

	public AbstractWizardStep(final String caption, final String description, OnStepEnter<STEP> onStepEnter,
			OnStepLeave<STEP> onStepLeave) {
		this.caption = caption;
		this.setHeadline(caption);
		this.onStepEnter = onStepEnter;
		this.onStepLeave = onStepLeave;
		setDescription(description);
		createHeader();
		setCompositionRoot(layout());
	}

	private void createHeader() {
		this.header = new VerticalLayout();

		if (getHeadline() != null && getDescription() != null) {
			this.header = FluentUI	.vertical()
									.add(FluentUI	.label()
													.value(getHeadline())
													.style((DSuiteTheme.DIALOG_HEADLINE))
													.get())
									.add(FluentUI	.label()
													.value(getDescription())
													.get())
									.sizeFull()
									.get();
		}

	}

	public abstract com.vaadin.ui.Component layout();

	@Override
	public com.vaadin.ui.Component getContent() {
		return this;
	}

	@Override
	public String getCaption() {
		return this.caption;
	}

	@Override
	public boolean backAllowed() {
		return true;
	}

	@Override
	public boolean nextAllowed() {
		return true;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	protected VerticalLayout getHeader() {
		return this.header;
	}

	private String getHeadline() {
		return this.headline;
	}

	private void setHeadline(final String headline) {
		this.headline = headline;
	}

	@Override
	public void onStepEnter() {
		this.onStepEnter.enter((STEP) this);
	}

	@Override
	public void onStepFinish() {
		this.onStepLeave.leave((STEP) this);
	}

	public OnStepEnter<STEP> getOnStepEnter() {
		return this.onStepEnter;
	}

	public OnStepLeave<STEP> getOnStepLeave() {
		return this.onStepLeave;
	}

}
