package de.bonprix.vaadin.mvp.wizard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import de.bonprix.vaadin.dialog.AbstractBaseDialog;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.dialog.AbstractMvpDialogPresenter;

/**
 * Presenter part of mvp for {@link AbstractBaseDialog}
 * 
 * @author dmut
 *
 * @param <VIEW>
 *            view interface
 */
@SuppressWarnings("rawtypes")
@SpringPresenter
public abstract class AbstractMvpWizardPresenter<VIEW extends MvpWizardView> extends AbstractMvpDialogPresenter<VIEW>
		implements MvpWizardPresenter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMvpWizardPresenter.class);

	@Autowired
	private ApplicationContext applicationContext;

	private WizardStep currentWizard;
	private final List<WizardStep> wizards = new LinkedList<>();
	private List<String> buttonList = new ArrayList<>();

	@PostConstruct
	public void postContruct() {
		getView().setButtonListToPresenter();
	}

	@Override
	public void next() {
		if (nextAllowed()) {
			this.currentWizard.onStepFinish();
			this.currentWizard = this.wizards.get(this.wizards.indexOf(this.currentWizard) + 1);
			stepChanged();
		}
	}

	@Override
	public void back() {
		if (backAllowed()) {
			this.currentWizard = this.wizards.get(this.wizards.indexOf(this.currentWizard) - 1);
			stepChanged();
		}
	}

	@Override
	public void finish() {
		if (this.getCurrentWizard()
			.nextAllowed()) {
			if (onFinished()) {
				close();
			}
		}
	}

	/**
	 * get called when last step is finished
	 * 
	 * @return true or false if the window should get closed
	 */
	public abstract boolean onFinished();

	@Override
	public void stepChanged() {
		this.getView()
			.setStepPanelContent(this.currentWizard.getContent());
		final double oneStep = 1.0 / (this.wizards.size());
		this.getView()
			.setProgressBarValue((float) ((oneStep / 2.0) + (oneStep * (this.wizards.indexOf(this.currentWizard)))));
		this.currentWizard.onStepEnter();

		if (getButtonList().contains(DialogButton.BACK.name())) {
			getView().setButtonEnabled(DialogButton.BACK.name(), !isFirstStep());
		}

		if (getButtonList().contains(DialogButton.NEXT.name())) {
			getView().setButtonEnabled(DialogButton.NEXT.name(), !isLastStep());
		}

		if (getButtonList().contains(DialogButton.FINISH.name())) {
			getView().setButtonEnabled(DialogButton.FINISH.name(), isLastStep());
		}
	}

	/**
	 * Adds a step and set this as WizardDialog
	 * 
	 * @param step
	 */
	public <STEP extends AbstractWizardStep<?>> void addWizardStep(AbstractWizardStep step) {
		try {
			if (step.getOnStepEnter() != null && step.getOnStepLeave() != null) {
				this.wizards.add(step);
				if (this.currentWizard == null) {
					this.currentWizard = step;
				}
			}
		} catch (IllegalArgumentException | SecurityException e) {
			AbstractMvpWizardPresenter.LOGGER.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void renderProgressInfo() {
		getView().renderProgressInfo(getWizards());
	}

	private boolean isFirstStep() {
		final int index = this.wizards.indexOf(this.currentWizard);
		return index == 0;
	}

	private boolean isLastStep() {
		return this.wizards.indexOf(this.currentWizard) == this.wizards.size() - 1;
	}

	public boolean nextAllowed() {
		return this.currentWizard.nextAllowed() && this.wizards.size() > (this.wizards.indexOf(this.currentWizard) - 1);
	}

	public boolean backAllowed() {
		return this.currentWizard.backAllowed() && this.wizards.indexOf(this.currentWizard) > 0;
	}

	public WizardStep getCurrentWizard() {
		return this.currentWizard;
	}

	public void setCurrentWizard(WizardStep currentWizard) {
		this.currentWizard = currentWizard;
	}

	public List<WizardStep> getWizards() {
		return this.wizards;
	}

	public List<String> getButtonList() {
		return this.buttonList;
	}

	@Override
	public void setButtonList(List<String> buttonMap) {
		this.buttonList = buttonMap;
	}
}
