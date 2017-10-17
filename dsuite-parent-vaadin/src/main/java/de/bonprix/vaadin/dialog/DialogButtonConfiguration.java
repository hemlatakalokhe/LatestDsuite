package de.bonprix.vaadin.dialog;

/**
 * The DialogButtonConfiguration is the configuration for the DialogButtonConfiguration.
 *
 * @author Kartik Suba
 *
 */
public class DialogButtonConfiguration {

    private String captionKey;
    private DialogButtonAction buttonAction;
    private boolean closeOnClick = false;

    public String getCaptionKey() {
        return this.captionKey;
    }

    public void setCaptionKey(final String captionKey) {
        this.captionKey = captionKey;
    }

    public DialogButtonAction getButtonAction() {
        return this.buttonAction;
    }

    public void setButtonAction(final DialogButtonAction buttonAction) {
        this.buttonAction = buttonAction;
    }

    public boolean isCloseOnClick() {
        return this.closeOnClick;
    }

    public void setCloseOnClick(final boolean closeOnClick) {
        this.closeOnClick = closeOnClick;
    }

}
