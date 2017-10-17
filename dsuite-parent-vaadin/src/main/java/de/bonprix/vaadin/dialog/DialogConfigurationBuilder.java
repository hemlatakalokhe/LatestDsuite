
package de.bonprix.vaadin.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.bonprix.I18N;

/**
 * Builder for DialogConfiguration.
 */

/**
 * @author k.suba
 *
 */
public class DialogConfigurationBuilder {

	private Integer width;
	private Integer maxSubLineWidth;
	private Integer height;
	private boolean modal = true;
	private Boolean dragable = true;
	private Boolean closable = true;
	private Boolean resizable = true;
	private String headline;
	private String subline;
	private boolean isWithDetails = false;
	private boolean closeOnCancelButton = true;
	private boolean closeOnCloseButton = true;
	private boolean closeOnAnyButton = false;
	private List<DialogButtonConfiguration> buttonConfigs = new ArrayList<>();
	private final Map<String, DialogButtonConfiguration> buttonConfigsMap = new HashMap<>();
	private DialogButtonConfiguration primaryButtonConfig;

	/**
	 * Set the width of the dialog.
	 *
	 * @param width
	 *            the width
	 * @return itself
	 */
	public DialogConfigurationBuilder withWidth(final int width) {
		this.width = width;
		return this;
	}

	/**
	 * Set the width of the sub line label. After which it starts to wrap
	 *
	 * @param maxSubLineWidth
	 * @return itself
	 */
	public DialogConfigurationBuilder withMaxSubLineWidth(final int maxSubLineWidth) {
		this.maxSubLineWidth = maxSubLineWidth;
		return this;
	}

	/**
	 * Set the height of the dialog.
	 *
	 * @param height
	 *            the height
	 * @return itself
	 */
	public DialogConfigurationBuilder withHeight(final int height) {
		this.height = height;
		return this;
	}

	/**
	 * Set the dialog to be a modal dialog.
	 *
	 * @param modal
	 *            modal
	 * @return itself
	 */
	public DialogConfigurationBuilder withModal(final boolean modal) {
		this.modal = modal;
		return this;
	}

	/**
	 * Set the dragable to be a dragable dialog.
	 * 
	 * @param dragable
	 *            dragable
	 * @return itself
	 */
	public DialogConfigurationBuilder withDragable(final boolean dragable) {
		this.dragable = dragable;
		return this;
	}

	/**
	 * Set the resizable to be a resizable dialog.
	 * 
	 * @param resizable
	 *            resizable
	 * @return itself
	 */
	public DialogConfigurationBuilder withResizable(final boolean resizable) {
		this.resizable = resizable;
		return this;
	}

	/**
	 * Set the closable to be a closable dialog.
	 * 
	 * @param closable
	 *            closable
	 * @return itself
	 */
	public DialogConfigurationBuilder withClosable(final boolean closable) {
		this.closable = closable;
		return this;
	}

	/**
	 * Sets the headline for this dialog window.
	 *
	 * @param headline
	 * @return itself
	 */
	public DialogConfigurationBuilder withHeadline(final String headline) {
		this.headline = headline;
		return this;
	}

	/**
	 * Sets the sub line for this dialog window.
	 *
	 * @param subline
	 * @return itself
	 */
	public DialogConfigurationBuilder withSubline(final String subline) {
		this.subline = subline;
		return this;
	}

	/**
	 * @param detailsArea
	 * @return itself
	 */
	public DialogConfigurationBuilder withDetails(final Boolean details) {
		this.isWithDetails = details;
		return this;
	}

	/**
	 * Will add button configurations to the dialog. The button configuration
	 * will have the button details encapsulated in it <br/>
	 * The buttons will be displayed in the order, the buttons are added to this
	 * builder (equal if primary or normal button). Each button can only be
	 * added once to the dialog. Subsequent calls of this method with the same
	 * argument will have no effect.
	 *
	 * @param buttonConfigs
	 *            configuration of button to be added
	 * @return itself
	 */
	public DialogConfigurationBuilder withButtons(final List<DialogButtonConfiguration> buttonConfigs) {
		this.buttonConfigs = buttonConfigs;
		for (final DialogButtonConfiguration dialogButtonConfiguration : this.buttonConfigs) {
			final String captionKey = dialogButtonConfiguration.getCaptionKey() != null
					? I18N.get(dialogButtonConfiguration.getCaptionKey()) : null;
			this.buttonConfigsMap.put(captionKey, dialogButtonConfiguration);
		}
		return this;
	}

	/**
	 * Will add a button configuration to the dialog. This method takes an enum
	 * as an input thereby allowing buttons to be added with ease <br/>
	 * The buttons will be displayed in the order, the buttons are added to this
	 * builder (equal if primary or normal button). Each button can only be
	 * added once to the dialog. Subsequent calls of this method with the same
	 * argument will have no effect.
	 *
	 * @param enumCaption
	 *            the button to add
	 * @return itself
	 */
	public DialogConfigurationBuilder withButton(final DialogButton enumCaption) {
		withButton(enumCaption.name());
		return this;
	}

	/**
	 * Will add a button configuration to the dialog. This method takes an
	 * captionKey as an input thereby allowing custom buttons to be added with
	 * ease <br/>
	 * The buttons will be displayed in the order, the buttons are added to this
	 * builder (equal if primary or normal button). Each button can only be
	 * added once to the dialog. Subsequent calls of this method with the same
	 * argument will have no effect.
	 *
	 * @param captionKey
	 *            the button to add
	 * @return itself
	 */
	public DialogConfigurationBuilder withButton(final String captionKey) {

		if (this.buttonConfigs == null) {
			this.buttonConfigs = new ArrayList<>();
		}
		if (!this.buttonConfigsMap.containsKey(captionKey)) {
			final DialogButtonConfiguration config = new DialogButtonConfigurationBuilder()	.withCaptionKey(captionKey)
																							.build();
			this.buttonConfigs.add(config);
			this.buttonConfigsMap.put(captionKey, config);
		}
		return this;
	}

	/**
	 * Will add a button configuration to the dialog. This method takes an enum
	 * as an input thereby allowing buttons to be added with ease <br/>
	 * The buttons will be displayed in the order, the buttons are added to this
	 * builder (equal if primary or normal button). Each button can only be
	 * added once to the dialog. Subsequent calls of this method with the same
	 * argument will have no effect.
	 *
	 * @param enumCaption
	 *            the button to add
	 * @param listener
	 *            user defined click listener
	 * @return itself
	 */
	public DialogConfigurationBuilder withButton(final DialogButton enumCaption, final DialogButtonAction action) {
		withButton(enumCaption.name(), action);
		return this;
	}

	/**
	 * Will add a button configuration to the dialog. This method takes an
	 * captionKey as an input thereby allowing custom buttons to be added with
	 * ease <br/>
	 * The buttons will be displayed in the order, the buttons are added to this
	 * builder (equal if primary or normal button). Each button can only be
	 * added once to the dialog. Subsequent calls of this method with the same
	 * argument will have no effect.
	 *
	 * @param captionKey
	 *            the button to add
	 * @param action
	 *            user defined click listener
	 * @return itself
	 */
	public DialogConfigurationBuilder withButton(final String captionKey, final DialogButtonAction action) {
		withButton(captionKey);
		this.buttonConfigsMap	.get(captionKey)
								.setButtonAction(action);
		return this;
	}

	/**
	 * Will add a primary button to the dialog, if not already added. By default
	 * this button has no action assigned.<br/>
	 * <br/>
	 * <ul>
	 * <li>the primary button will have a highlighted style</li>
	 * <li>only one primary button is allowed in a dialog. Subsequent calls of
	 * this method will remove the <i>primary</i> style on the previous primary
	 * button</li>
	 * <li>if the given button already exists in this configuration, that button
	 * will get the <i>primary</i> status</li>
	 * </ul>
	 *
	 * @param captionKey
	 *            the captionKey for the primary button
	 * @return itself
	 */
	public DialogConfigurationBuilder withPrimaryButton(final String captionKey) {

		final DialogButtonConfiguration tempConfig = new DialogButtonConfigurationBuilder()	.withCaptionKey(captionKey)
																							.build();
		withPrimaryButton(tempConfig);
		return this;
	}

	/**
	 * Will add a primary button to the dialog, if not already added. By default
	 * this button has no action assigned.<br/>
	 * <br/>
	 * <ul>
	 * <li>the primary button will have a highlighted style</li>
	 * <li>only one primary button is allowed in a dialog. Subsequent calls of
	 * this method will remove the <i>primary</i> style on the previous primary
	 * button</li>
	 * <li>if the given button already exists in this configuration, that button
	 * will get the <i>primary</i> status</li>
	 * </ul>
	 *
	 * @param captionKey
	 *            the captionKey for the primary button
	 * @param listener
	 *            user defined click listener
	 * @return itself
	 */
	public DialogConfigurationBuilder withPrimaryButton(final String captionKey, final DialogButtonAction action) {

		final DialogButtonConfiguration tempConfig = new DialogButtonConfigurationBuilder()	.withCaptionKey(captionKey)
																							.withButtonAction(action)
																							.build();
		withPrimaryButton(tempConfig);
		return this;
	}

	/**
	 * Will add a primary button to the dialog, if not already added. By default
	 * this button has no action assigned.<br/>
	 * <br/>
	 * <ul>
	 * <li>the primary button will have a highlighted style</li>
	 * <li>only one primary button is allowed in a dialog. Subsequent calls of
	 * this method will remove the <i>primary</i> style on the previous primary
	 * button</li>
	 * <li>if the given button already exists in this configuration, that button
	 * will get the <i>primary</i> status</li>
	 * </ul>
	 *
	 * @param enumCaption
	 *            the captionKey for the primary button
	 * @return itself
	 */
	public DialogConfigurationBuilder withPrimaryButton(final DialogButton enumCaption) {

		final DialogButtonConfiguration tempConfig = new DialogButtonConfigurationBuilder()	.withCaptionKey(enumCaption.name())
																							.build();
		withPrimaryButton(tempConfig);
		return this;
	}

	/**
	 * Will add a primary button to the dialog, if not already added. By default
	 * this button has no action assigned.<br/>
	 * <br/>
	 * <ul>
	 * <li>the primary button will have a highlighted style</li>
	 * <li>only one primary button is allowed in a dialog. Subsequent calls of
	 * this method will remove the <i>primary</i> style on the previous primary
	 * button</li>
	 * <li>if the given button already exists in this configuration, that button
	 * will get the <i>primary</i> status</li>
	 * </ul>
	 *
	 * @param enumCaption
	 *            the captionKey for the primary button
	 * @param listener
	 *            user defined click listener
	 * @return itself
	 */
	public DialogConfigurationBuilder withPrimaryButton(final DialogButton enumCaption,
			final DialogButtonAction action) {

		final DialogButtonConfiguration tempConfig = new DialogButtonConfigurationBuilder()	.withCaptionKey(enumCaption.name())
																							.withButtonAction(action)
																							.build();
		withPrimaryButton(tempConfig);
		return this;
	}

	/**
	 * Will add a primary button to the dialog. By default this button has no
	 * action assigned.<br/>
	 * <br/>
	 * <ul>
	 * <li>the primary button will have a highlighted style</li>
	 * <li>only one primary button is allowed in a dialog. Subsequent calls of
	 * this method will remove the <i>primary</i> style on the previous primary
	 * button</li>
	 * <li>if the given button already exists in this configuration, that button
	 * will get the <i>primary</i> status</li>
	 * </ul>
	 *
	 * @param primaryButtonConfig
	 *            the DialogButtonConfiguration for the primary button
	 * @return itself
	 */
	public DialogConfigurationBuilder withPrimaryButton(final DialogButtonConfiguration primaryButtonConfig) {
		if (!this.buttonConfigsMap.containsKey(primaryButtonConfig.getCaptionKey())) {
			this.buttonConfigs.add(primaryButtonConfig);
			this.buttonConfigsMap.put(primaryButtonConfig.getCaptionKey(), primaryButtonConfig);
		}
		this.primaryButtonConfig = primaryButtonConfig;
		return this;
	}

	/**
	 * Allows setting the CloseOnThisOne flag for the buttonConfig with the
	 * passed key
	 *
	 * @param enumKey
	 * @returns itself
	 */
	public DialogConfigurationBuilder withCloseOnButton(final DialogButton enumKey) {
		withCloseOnButton(enumKey.name());
		return this;
	}

	/**
	 * Allows setting the CloseOnThisOne flag for the buttonConfig with the
	 * passed key
	 *
	 * @param enumKey
	 * @returns itself
	 */
	public DialogConfigurationBuilder withoutCloseOnButton(final DialogButton enumKey) {
		withoutCloseOnButton(enumKey.name());
		return this;
	}

	/**
	 * Allows setting the CloseOnThisOne flag for the buttonConfig with the
	 * passed key
	 *
	 * @param captionkey
	 * @returns itself
	 */
	public DialogConfigurationBuilder withCloseOnButton(final String captionkey) {
		if (this.buttonConfigs != null) {
			this.buttonConfigs	.stream()
								.filter(p -> p	.getCaptionKey()
												.equals(captionkey))
								.forEach(config -> config.setCloseOnClick(Boolean.TRUE));
		}
		return this;
	}

	/**
	 * Allows setting the CloseOnThisOne flag for the buttonConfig with the
	 * passed key
	 *
	 * @param captionkey
	 * @returns itself
	 */
	public DialogConfigurationBuilder withoutCloseOnButton(final String captionkey) {
		if (this.buttonConfigs != null) {
			this.buttonConfigs	.stream()
								.filter(p -> p	.getCaptionKey()
												.equals(captionkey))
								.forEach(config -> config.setCloseOnClick(Boolean.FALSE));
		}
		return this;
	}

	/**
	 * If the dialog should be automatically closed on invocation of the cancel
	 * button.
	 *
	 * @param flag
	 *            if to close on cancel button
	 * @returns itself
	 */
	public DialogConfigurationBuilder withCloseOnCancelButton(final boolean flag) {
		this.closeOnCancelButton = flag;
		return this;
	}

	/**
	 * If the dialog should be automatically closed on invocation of the close
	 * button.
	 *
	 * @param flag
	 *            if to close on close button
	 * @returns itself
	 */
	public DialogConfigurationBuilder withCloseOnCloseButton(final boolean flag) {
		this.closeOnCloseButton = flag;
		return this;
	}

	/**
	 * If the dialog should be automatically closed on invocation of any button.
	 *
	 * @param flag
	 *            if to close on any button click
	 * @returns itself
	 */
	public DialogConfigurationBuilder withCloseOnAnyButton(final boolean flag) {
		this.closeOnAnyButton = flag;
		return this;
	}

	/**
	 * Builds the configuration and returns it.
	 *
	 * @return the dialog configuration
	 */
	public DialogConfiguration build() {
		final DialogConfiguration result = new DialogConfiguration();

		result.setHeadline(this.headline);
		result.setSubline(this.subline);
		result.setWithDetails(this.isWithDetails);
		result.setCloseOnCancelButton(this.closeOnCancelButton);
		result.setCloseOnCloseButton(this.closeOnCloseButton);
		result.setCloseOnAnyButton(this.closeOnAnyButton);
		result.setButtonConfigs(this.buttonConfigs);
		result.setButtonConfigsMap(this.buttonConfigsMap);
		result.setPrimaryButtonConfig(this.primaryButtonConfig);
		result.setWidth(this.width);
		result.setMaxSubLineWidth(this.maxSubLineWidth);
		result.setHeight(this.height);
		result.setModal(this.modal);
		result.setDragable(this.dragable);
		result.setResizable(this.resizable);
		result.setClosable(this.closable);

		return result;
	}
}