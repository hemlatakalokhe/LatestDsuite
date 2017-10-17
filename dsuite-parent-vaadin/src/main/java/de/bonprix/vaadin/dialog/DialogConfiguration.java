package de.bonprix.vaadin.dialog;

import java.util.List;
import java.util.Map;

/**
 * The DialogConfiguration is the configuration for the AbstractBaseDialog.
 *
 */
/**
 * @author k.suba
 *
 */

/**
 * @author r.desai
 *
 */
public class DialogConfiguration {

	private List<DialogButtonConfiguration> buttonConfigs;
	private Map<String, DialogButtonConfiguration> buttonConfigsMap;
	private DialogButtonConfiguration primaryButtonConfig;
	private Integer width;
	private Integer maxSubLineWidth;
	private Integer height;
	private boolean modal = true;
	private boolean dragable = true;
	private boolean resizable = true;
	private boolean closable = true;
	private String headline;
	private String subline;
	private boolean withDetails = false;
	private boolean closeOnCancelButton = true;
	private boolean closeOnCloseButton = true;
	private boolean closeOnAnyButton = false;

	/**
	 *
	 * @returns the primaryButtonConfig
	 */
	public DialogButtonConfiguration getPrimaryButtonConfig() {
		return this.primaryButtonConfig;
	}

	/**
	 *
	 * @param primaryButtonConfig
	 */
	public void setPrimaryButtonConfig(final DialogButtonConfiguration primaryButtonConfig) {
		this.primaryButtonConfig = primaryButtonConfig;
	}

	/**
	 *
	 * @returns the buttonConfigs
	 */
	public List<DialogButtonConfiguration> getButtonConfigs() {
		return this.buttonConfigs;
	}

	/**
	 *
	 * @param buttonConfigs
	 */
	public void setButtonConfigs(final List<DialogButtonConfiguration> buttonConfigs) {
		this.buttonConfigs = buttonConfigs;
	}

	/**
	 *
	 * @return the width
	 */
	public Integer getWidth() {
		return this.width;
	}

	/**
	 *
	 * @param width
	 */
	public void setWidth(final Integer width) {
		this.width = width;
	}

	/**
	 *
	 * @return height
	 */
	public Integer getHeight() {
		return this.height;
	}

	/**
	 *
	 * @param height
	 */
	public void setHeight(final Integer height) {
		this.height = height;
	}

	/**
	 *
	 * @return the headline
	 */
	public String getHeadline() {
		return this.headline;
	}

	/**
	 *
	 * @param headline
	 */
	public void setHeadline(final String headline) {
		this.headline = headline;
	}

	/**
	 *
	 * @return the subline
	 */
	public String getSubline() {
		return this.subline;
	}

	/**
	 *
	 * @param subline
	 */
	public void setSubline(final String subline) {
		this.subline = subline;
	}

	/**
	 * @return the modal
	 */
	public boolean isModal() {
		return this.modal;
	}

	/**
	 * @param modal
	 *            the modal to set
	 */
	public void setModal(final boolean modal) {
		this.modal = modal;
	}

	/**
	 *
	 * @return Integer maxSubLineWidth
	 */
	public Integer getMaxSubLineWidth() {
		return this.maxSubLineWidth;
	}

	/**
	 *
	 * @param maxSubLineWidth
	 *            the width after which the sub line would wrap
	 */
	public void setMaxSubLineWidth(final Integer maxSubLineWidth) {
		this.maxSubLineWidth = maxSubLineWidth;
	}

	public boolean isWithDetails() {
		return this.withDetails;
	}

	public void setWithDetails(final boolean withDetails) {
		this.withDetails = withDetails;
	}

	/**
	 * @param buttonConfigsMap
	 */
	public void setButtonConfigsMap(final Map<String, DialogButtonConfiguration> buttonConfigsMap) {
		this.buttonConfigsMap = buttonConfigsMap;
	}

	/**
	 *
	 * @return buttonConfigsMap<String, DialogButtonConfiguration>
	 */
	public Map<String, DialogButtonConfiguration> getButtonConfigsMap() {
		return this.buttonConfigsMap;
	}

	public boolean isCloseOnCancelButton() {
		return this.closeOnCancelButton;
	}

	public void setCloseOnCancelButton(final boolean closeOnCancelButton) {
		this.closeOnCancelButton = closeOnCancelButton;
	}

	public boolean isCloseOnCloseButton() {
		return this.closeOnCloseButton;
	}

	public void setCloseOnCloseButton(final boolean closeOnCloseButton) {
		this.closeOnCloseButton = closeOnCloseButton;
	}

	public boolean isCloseOnAnyButton() {
		return this.closeOnAnyButton;
	}

	public void setCloseOnAnyButton(final boolean closeOnAnyButton) {
		this.closeOnAnyButton = closeOnAnyButton;
	}

	public boolean isDragable() {
		return this.dragable;
	}

	public void setDragable(boolean dragable) {
		this.dragable = dragable;
	}

	public boolean isResizable() {
		return this.resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public boolean isClosable() {
		return this.closable;
	}

	public void setClosable(boolean closable) {
		this.closable = closable;
	}

}