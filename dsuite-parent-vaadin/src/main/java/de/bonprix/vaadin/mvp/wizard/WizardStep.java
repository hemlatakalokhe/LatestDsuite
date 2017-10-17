package de.bonprix.vaadin.mvp.wizard;

import com.vaadin.ui.Component;

/**
 * Interface for {@link AbstractWizardStep}
 * 
 * @author dmut
 */
public interface WizardStep {

	/**
	 * returns the caption of the step
	 * 
	 * @return String
	 */
	String getCaption();

	/**
	 * Returns the step
	 * 
	 * @return Component
	 */
	Component getContent();

	/**
	 * Flag. If false, user can not proceed to the next step
	 * 
	 * @return boolean
	 */
	boolean nextAllowed();

	/**
	 * Flag. If false, user can not return to the previous step
	 * 
	 * @return boolean
	 */
	boolean backAllowed();

	/**
	 * Gets called when step get's active
	 */
	void onStepEnter();

	/**
	 * Gets called when step get's inactive
	 */
	void onStepFinish();

}
