package de.bonprix.vaadin.mvp.wizard;

/**
 * Interface for WizardStep onStepEnter
 * 
 * @author dmut
 */
public interface OnStepEnter<STEP extends WizardStep> {

	/**
	 * Method to call on StepEnter
	 * 
	 * @param step
	 */
	void enter(STEP step);
}
