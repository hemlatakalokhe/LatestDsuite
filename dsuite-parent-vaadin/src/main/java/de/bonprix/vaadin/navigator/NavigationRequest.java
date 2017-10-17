package de.bonprix.vaadin.navigator;

import com.vaadin.ui.UI;

/**
 * 
 * @author dmut
 */
public class NavigationRequest {

	private final String viewId;

	public NavigationRequest(final String viewId) {
		this.viewId = viewId;
	}

	/**
	 * proceeds the Navigationrequest
	 */
	public void proceed() {
		UI	.getCurrent()
			.getNavigator()
			.navigateTo(this.viewId);
	}

}
