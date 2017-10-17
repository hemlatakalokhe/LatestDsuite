package de.bonprix.vaadin.mvp.wizard;

import java.util.List;

import com.vaadin.ui.Component;

import de.bonprix.vaadin.mvp.dialog.MvpDialogView;

/**
 * Interface for {@link AbstractMvpWizardView} to be called by
 * {@link MvpWizardPresenter}
 * 
 * @author dmut
 */
public interface MvpWizardView<PRESENTER extends MvpWizardPresenter> extends MvpDialogView<PRESENTER> {

	/**
	 * Sets the content for the current step
	 * 
	 * @param content
	 */
	void setStepPanelContent(Component content);

	/**
	 * Sets the value for the progressbar
	 * 
	 * @param newValue
	 */
	void setProgressBarValue(Float newValue);

	/**
	 * Enables/Disables a button by given name an value
	 * 
	 * @param buttonName
	 * @param enabled
	 */
	void setButtonEnabled(String buttonName, Boolean enabled);

	/**
	 * Sets visibility of a button by given name an value
	 * 
	 * @param buttonName
	 * @param visible
	 */
	void setButtonVisible(String buttonName, Boolean visible);

	/**
	 * Renders the progressbar
	 * 
	 * @param wizardSteps
	 */
	void renderProgressInfo(List<WizardStep> wizardSteps);

	/**
	 * Sets the list of buttonnames to the presenter
	 */
	void setButtonListToPresenter();

}
