package de.bonprix.exception;

import de.bonprix.vaadin.bean.grid.RendererProperties;

@SuppressWarnings("serial")
public class CapabilityException extends RuntimeException {

	public CapabilityException(String capabilityKey, String propertyId) {
		super("Capability key/property missing. CapabilityKey: " + capabilityKey + " Property: " + propertyId);
	}

	public CapabilityException(RendererProperties<?> rendererProperties, String propertyId) {
		super("RendererProperty missing/incompatible for property: " + propertyId);
	}
}
