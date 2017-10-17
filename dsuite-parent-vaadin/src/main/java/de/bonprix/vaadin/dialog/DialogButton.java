package de.bonprix.vaadin.dialog;

/**
 * The AbstractBaseDialogButton is an enum containing all keys with their i18n captionKey.
 *
 *
 *
 */

/**
 * @author k.suba
 *
 */
public enum DialogButton {

    OK("OK"),
    SAVE("SAVE"),
    APPLY("APPLY"),
    CANCEL("CANCEL"),
    BACK("BACK"),
    NEXT("NEXT"),
    YES("YES"),
    NO("NO"),
    FINISH("FINISH"),
    CLOSE("CLOSE"),
    RETRY("RETRY"),
    IGNORE("IGNORE"),
    RESET("RESET"),
    CONTINUE_ANYWAYS("CONTINUE_ANYWAYS"),
    CONTINUE("CONTINUE"),
    HELP("HELP"),
    SAVE_AND_NEW("SAVE_AND_NEW"),
    PRINT("PRINT"),
    CONTACT_SUPPORT("CONTACT_SUPPORT"),
    CREATE_JIRA_ISSUE("CREATE_JIRA_ISSUE");

    private final String captionKey;

    DialogButton(final String captionKey) {
        this.captionKey = captionKey;
    }

    /**
     * @return the captionKey
     */
    public String getCaptionKey() {
        return this.captionKey;
    }

}
