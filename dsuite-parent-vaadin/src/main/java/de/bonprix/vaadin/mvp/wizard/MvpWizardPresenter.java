package de.bonprix.vaadin.mvp.wizard;

import java.util.List;
import de.bonprix.vaadin.mvp.dialog.MvpDialogPresenter;

/**
 * Interface for {@link AbstractMvpWizardPresenter} to be called by
 * {@link MvpWizardView}
 * 
 * @author dmut
 */
public interface MvpWizardPresenter extends MvpDialogPresenter {

	/**
	 * Handles behaviour of wizard buttons and progressbar
	 */
	void stepChanged();

	/**
	 * Action for the next button
	 */
	void next();

	/**
	 * Action for the back button
	 */
	void back();

	/**
	 * Action for the finish button
	 */
	void finish();

	/**
	 * Sets the list of buttonnames
	 * 
	 * @param buttonList
	 */
	void setButtonList(List<String> buttonList);

	/**
	 * renders the progressbar
	 */
	void renderProgressInfo();

}