package de.bonprix.vaadin.messagebox;

import java.util.List;

import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogButtonAction;
import de.bonprix.vaadin.dialog.DialogButtonConfiguration;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;

public class MessageBoxConfigurationBuilder {

	private final DialogConfigurationBuilder abstractBaseDialogConfigurationBuilder = new DialogConfigurationBuilder();

	private MessageBoxIcon messageBoxIcon;
	private Integer iconWidth;
	private Integer iconHeight;

	private String htmlMessage;

	public MessageBoxConfigurationBuilder withIconWidth(final Integer iconWidth) {
		this.iconWidth = iconWidth;
		return this;
	}

	public MessageBoxConfigurationBuilder withIconHeight(final Integer iconHeight) {
		this.iconHeight = iconHeight;
		return this;
	}

	public MessageBoxConfigurationBuilder withMessageBoxIcon(final MessageBoxIcon messageBoxIcon) {
		this.messageBoxIcon = messageBoxIcon;
		return this;
	}

	public MessageBoxConfigurationBuilder withHtmlMessage(final String htmlMessage) {
		this.htmlMessage = htmlMessage;
		return this;
	}

	public MessageBoxConfigurationBuilder withPlainMessage(final String plainMessage) {
		this.htmlMessage = MessageBoxConfiguration.encodeToHtml(plainMessage);
		return this;
	}

	/**
	 * Will add a normal button to the message box. By default this button has
	 * no action assigned.<br/>
	 * <br/>
	 * The buttons will be displayed in the order, the buttons are added to this
	 * builder (equal if primary or normal button). Each button can only be
	 * added once to the message box. Subsequent calls of this method with the
	 * same argument will have no effect.
	 *
	 * @param enumCaption
	 *            the button to add
	 * @return itself
	 */
	public MessageBoxConfigurationBuilder withButton(final DialogButton enumCaption) {
		this.abstractBaseDialogConfigurationBuilder.withButton(enumCaption);
		return this;
	}

	/**
	 * Will add a normal button to the message box. By default this button has
	 * no action assigned.<br/>
	 * <br/>
	 * The buttons will be displayed in the order, the buttons are added to this
	 * builder (equal if primary or normal button). Each button can only be
	 * added once to the message box. Subsequent calls of this method with the
	 * same argument will have no effect.
	 *
	 * @param enumCaption
	 *            the button to add
	 * @param listener
	 *            user defined listener
	 * @return itself
	 */
	public MessageBoxConfigurationBuilder withButton(final DialogButton enumCaption,
			final DialogButtonAction listener) {
		this.abstractBaseDialogConfigurationBuilder.withButton(enumCaption, listener);
		return this;
	}

	/**
	 * Will add a normal button to the message box. By default this button has
	 * no action assigned.<br/>
	 * <br/>
	 * The buttons will be displayed in the order, the buttons are added to this
	 * builder (equal if primary or normal button). Each button can only be
	 * added once to the message box. Subsequent calls of this method with the
	 * same argument will have no effect.
	 *
	 * @param captionKey
	 *            the button to add
	 * @return itself
	 */
	public MessageBoxConfigurationBuilder withButton(final String captionKey) {
		this.abstractBaseDialogConfigurationBuilder.withButton(captionKey);
		return this;
	}

	/**
	 * Will add a normal button to the message box. By default this button has
	 * no action assigned.<br/>
	 * <br/>
	 * The buttons will be displayed in the order, the buttons are added to this
	 * builder (equal if primary or normal button). Each button can only be
	 * added once to the message box. Subsequent calls of this method with the
	 * same argument will have no effect.
	 *
	 * @param captionKey
	 *            the button to add
	 * @param listener
	 *            user defined listener
	 * @return itself
	 */
	public MessageBoxConfigurationBuilder withButton(final String captionKey, final DialogButtonAction listener) {
		this.abstractBaseDialogConfigurationBuilder.withButton(captionKey, listener);
		return this;
	}

	/**
	 * Will add a normal buttons to the message box. The button configs will
	 * have the configurations for each button encapsulated<br/>
	 * <br/>
	 * The buttons will be displayed in the order, the buttons are added to this
	 * builder (equal if primary or normal button). Each button can only be
	 * added once to the message box. Subsequent calls of this method with the
	 * same argument will have no effect.
	 *
	 * @param buttonConfigs
	 *            the buttonConfigs for the buttons to add
	 * @return itself
	 */
	public MessageBoxConfigurationBuilder withButtons(final List<DialogButtonConfiguration> buttonConfigs) {
		this.abstractBaseDialogConfigurationBuilder.withButtons(buttonConfigs);
		return this;
	}

	/**
	 * Will add a primary button to the message box, if not already a added. The
	 * button config will have the configurations of that button encapsulated
	 * <br/>
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
	 * @param buttonConfig
	 *            the buttonConfig for the primary button
	 * @return itself
	 */
	public MessageBoxConfigurationBuilder withPrimaryButton(final DialogButtonConfiguration buttonConfig) {
		this.abstractBaseDialogConfigurationBuilder.withPrimaryButton(buttonConfig);
		return this;
	}

	/**
	 * Will add a primary button to the message box, if not already a added. The
	 * button config will have the configurations of that button encapsulated
	 * <br/>
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
	 *            the user defined click listener
	 * @return itself
	 */
	public MessageBoxConfigurationBuilder withPrimaryButton(final String captionKey,
			final DialogButtonAction listener) {
		this.abstractBaseDialogConfigurationBuilder.withPrimaryButton(captionKey, listener);
		return this;
	}

	/**
	 * Will add a primary button to the message box, if not already a added. The
	 * button config will have the configurations of that button encapsulated
	 * <br/>
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
	public MessageBoxConfigurationBuilder withPrimaryButton(final DialogButton enumCaption) {
		this.abstractBaseDialogConfigurationBuilder.withPrimaryButton(enumCaption);
		return this;
	}

	/**
	 * Will add a primary button to the message box, if not already a added. The
	 * button config will have the configurations of that button encapsulated
	 * <br/>
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
	 *            the user defined click listener
	 * @return itself
	 */
	public MessageBoxConfigurationBuilder withPrimaryButton(final DialogButton enumCaption,
			final DialogButtonAction listener) {
		this.abstractBaseDialogConfigurationBuilder.withPrimaryButton(enumCaption, listener);
		return this;
	}

	/**
	 * Will add a primary button to the message box, if not already a added. The
	 * button config will have the configurations of that button encapsulated
	 * <br/>
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
	public MessageBoxConfigurationBuilder withPrimaryButton(final String captionKey) {
		this.abstractBaseDialogConfigurationBuilder.withPrimaryButton(captionKey);
		return this;
	}

	/**
	 * Will show or hide the Collapsible Details Panel
	 *
	 * @param detailsArea
	 * @return
	 */
	public MessageBoxConfigurationBuilder withDetails(final Boolean details) {
		this.abstractBaseDialogConfigurationBuilder.withDetails(details);
		return this;
	}

	public MessageBoxConfigurationBuilder withWidth(final int width) {
		this.abstractBaseDialogConfigurationBuilder.withWidth(width);
		return this;
	}

	public MessageBoxConfigurationBuilder withHeight(final int height) {
		this.abstractBaseDialogConfigurationBuilder.withHeight(height);
		return this;
	}

	public MessageBoxConfigurationBuilder withModal(final boolean modal) {
		this.abstractBaseDialogConfigurationBuilder.withModal(modal);
		return this;
	}

	/**
	 * To configure close on all buttons of the message box
	 *
	 * @return itself
	 */
	public MessageBoxConfigurationBuilder withCloseOnAnyButton(final boolean flag) {
		this.abstractBaseDialogConfigurationBuilder.withCloseOnAnyButton(flag);
		return this;
	}

	public MessageBoxConfiguration build() {
		final MessageBoxConfiguration result = new MessageBoxConfiguration();

		result.setAbstractBaseDialogConfiguration(this.abstractBaseDialogConfigurationBuilder.build());
		result.setMessageBoxIcon(this.messageBoxIcon);
		if (this.iconWidth != null) {
			result.setIconWidth(this.iconWidth);
		}
		if (this.iconHeight != null) {
			result.setIconHeight(this.iconHeight);
		}
		if (this.htmlMessage != null) {
			result.setHtmlMessage(this.htmlMessage);
		}

		return result;
	}

}