package de.bonprix.vaadin.ui.statuswizard;

import com.vaadin.server.ExternalResource;

import de.bonprix.vaadin.FontBonprix;
import de.bonprix.vaadin.FontIconColor;
import de.bonprix.vaadin.IconSize;

public enum WizardStepStatus {

	EMPTY(new ExternalResource(FontBonprix.DOT.getPngUrl(IconSize.SIZE_32, FontIconColor.WHITE))), STARTED(
			new ExternalResource(FontBonprix.DOT.getPngUrl(IconSize.SIZE_32, FontIconColor.YELLOW))), FINISHED(
					new ExternalResource(
							FontBonprix.DOT.getPngUrl(IconSize.SIZE_32, FontIconColor.GREEN))), MANDATORY_NOT_FILLED(
									new ExternalResource(
											FontBonprix.DOT.getPngUrl(IconSize.SIZE_32, FontIconColor.RED)));

	private ExternalResource resource;

	private WizardStepStatus(final ExternalResource resource) {
		this.resource = resource;
	}

	public ExternalResource getValue() {
		return this.resource;
	}

}
