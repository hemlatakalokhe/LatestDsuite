package de.bonprix.vaadin.messagebox;

import de.bonprix.vaadin.dialog.DialogConfiguration;

/**
 * @author k.suba
 *
 */
public class MessageBoxConfiguration {

    private DialogConfiguration abstractBaseDialogConfiguration;

    private MessageBoxIcon messageBoxIcon;
    private Integer iconWidth = 64;
    private Integer iconHeight = 64;

    private String htmlMessage;

    /**
     * 
     * @return DialogConfiguration
     */
    public DialogConfiguration getAbstractBaseDialogConfiguration() {
        return this.abstractBaseDialogConfiguration;
    }

    /**
     * 
     * @param abstractBaseDialogConfiguration which configures the abstract dialog box
     */
    public void setAbstractBaseDialogConfiguration(final DialogConfiguration abstractBaseDialogConfiguration) {
        this.abstractBaseDialogConfiguration = abstractBaseDialogConfiguration;
    }

    /**
     * 
     * @return Integer iconWidth
     */
    public Integer getIconWidth() {
        return this.iconWidth;
    }

    /**
     * 
     * @param iconWidth
     */
    public void setIconWidth(final Integer iconWidth) {
        this.iconWidth = iconWidth;
    }

    /**
     * 
     * @return Integer iconHeight
     */
    public Integer getIconHeight() {
        return this.iconHeight;
    }

    /**
     * 
     * @param iconHeight
     */
    public void setIconHeight(final Integer iconHeight) {
        this.iconHeight = iconHeight;
    }

    /**
     * 
     * @return MessageBoxIcon messageBoxIcon
     */
    public MessageBoxIcon getMessageBoxIcon() {
        return this.messageBoxIcon;
    }

    /**
     * 
     * @param messageBoxIcon
     */
    public void setMessageBoxIcon(final MessageBoxIcon messageBoxIcon) {
        this.messageBoxIcon = messageBoxIcon;
    }

    /**
     * 
     * @return String htmlMessage
     */
    public String getHtmlMessage() {
        return this.htmlMessage;
    }

    /**
     * 
     * @param htmlMessage
     */
    public void setHtmlMessage(final String htmlMessage) {
        this.htmlMessage = htmlMessage;
    }

    /**
     * 
     * @param plainMessage
     */
    public void setPlainMessage(final String plainMessage) {
        this.htmlMessage = encodeToHtml(plainMessage);
    }

    /**
     * Translates plain text to HTML formatted text with corresponding escape sequences.
     * 
     * @param plainText The plain text to translates.
     * @return The HTML formatted text.
     */
    public static String encodeToHtml(final String plainText) {
        final StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for (final char c : plainText.toCharArray()) {
            if (c == ' ') {
                if (previousWasASpace) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            }
            else {
                previousWasASpace = false;
            }
            switch (c) {
                case '<':
                    builder.append("&lt;");
                    break;
                case '>':
                    builder.append("&gt;");
                    break;
                case '&':
                    builder.append("&amp;");
                    break;
                case '"':
                    builder.append("&quot;");
                    break;
                case '\n':
                    builder.append("<br>");
                    break;
                case '\t':
                    builder.append("&nbsp; &nbsp; &nbsp;");
                    break;
                default:
                    if (c < 128) {
                        builder.append(c);
                    }
                    else {
                        builder.append("&#")
                                .append((int) c)
                                .append(";");
                    }
            }
        }
        return builder.toString();
    }

}
