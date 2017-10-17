package de.bonprix.vaadin.mvp.wizard;

/**
 * Interface for WizardStep onStepFinish
 * 
 * @author dmut
 */
public interface OnStepLeave<STEP extends WizardStep> {

	/**
	 * Method to call on StepFinish
	 * 
	 * @param step
	 */
	void leave(STEP step);
}
