package de.bonprix.vaadin.ui.statuswizard;

import java.util.List;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;

/**
 * @author dmut
 */

public interface StatusWizardStep {

	String getCaption();

	String getExplanation();

	/**
	 * Creates the layout
	 * 
	 * @return
	 */
	Component layout();

	/**
	 * Returns the layout without creating a new one
	 * 
	 * @return
	 */
	Component getLayout();

	/**
	 * Returns the the step status for the mandatory fields
	 * 
	 * @return
	 */
	WizardStepStatus getStepStatus();

	/**
	 * Sets the the step status for the mandatory fields
	 * 
	 * @return
	 */
	void setStepStatus(WizardStepStatus stepStatus);

	/**
	 * Returns the the step status for the optional fields
	 * 
	 * @return
	 */
	WizardStepStatus getSecondStepStatus();

	/**
	 * Sets the the step status for the optional fields
	 * 
	 * @return
	 */
	void setSecondStepStatus(WizardStepStatus stepStatus);

	FieldGroup getFieldGroup();

	<BEANTYPE> BEANTYPE getBean();

	float getExpandRatioHeader();

	float getExpandRatioLayout();

	/**
	 * Checks for mandatory fields
	 * 
	 * @return
	 */
	boolean hasMandatoryFields();

	List<Field> getOptionalFileds();

}
