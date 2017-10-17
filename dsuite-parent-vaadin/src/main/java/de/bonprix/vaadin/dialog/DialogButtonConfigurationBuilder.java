package de.bonprix.vaadin.dialog;

/**
 * @author k.suba
 *
 */
public class DialogButtonConfigurationBuilder implements Cloneable {
    protected DialogButtonConfigurationBuilder self;
    protected String captionKey;
    protected DialogButtonAction buttonAction;
    protected boolean closeOnClick = false;

    /**
     * Creates a new {@link DialogButtonConfigurationBuilder}.
     */
    public DialogButtonConfigurationBuilder() {
        this.self = this;
    }

    /**
     * Sets the value for the {captionKey} property.
     *
     * @param value the default value
     * @return this builder
     */
    public DialogButtonConfigurationBuilder withCaptionKey(final String value) {
        this.captionKey = value;
        return this.self;
    }

    /**
     * Sets the value for the {buttonListener} property.
     *
     * @param value the default value
     * @return this builder
     */
    public DialogButtonConfigurationBuilder withButtonAction(final DialogButtonAction action) {
        this.buttonAction = action;
        return this.self;
    }

    /**
     * Sets the value for the {closeOnThisOne} property.
     *
     * @param value the default value
     * @return this builder
     */
    public DialogButtonConfigurationBuilder withCloseOnClick(final boolean value) {
        this.closeOnClick = value;
        return this.self;
    }

    /**
     * Creates a new {@link DialogButtonConfiguration} based on this builder's settings.
     *
     * @return the created DialogButtonConfiguration
     */
    public DialogButtonConfiguration build() {
        try {
            final DialogButtonConfiguration result = new DialogButtonConfiguration();
            result.setCaptionKey(this.captionKey);
            result.setButtonAction(this.buttonAction);
            result.setCloseOnClick(this.closeOnClick);
            return result;
        }
        catch (final RuntimeException ex) {
            throw ex;
        }
        catch (final Exception ex) {
            throw new java.lang.reflect.UndeclaredThrowableException(ex);
        }
    }
}
