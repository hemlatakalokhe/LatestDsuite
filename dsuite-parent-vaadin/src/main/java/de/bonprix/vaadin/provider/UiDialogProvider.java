package de.bonprix.vaadin.provider;

public interface UiDialogProvider {

    /**
     * Marker interface that some class is a dialog.
     *
     * @author cthiel
     * @date 17.11.2016
     *
     */
    public static interface Dialog {

    }

    /**
     * Marker interface that some class is a dialog.
     *
     * @author cthiel
     * @date 17.11.2016
     *
     */
    public static interface BlockingDialog extends Dialog {

    }

    /**
     * Attaches the given dialog window to the UI. If this method previously attached a dialog to the UI, that one will be replaced by the given new dialog (if
     * the existing one is still open and attached).
     *
     * @param dialog
     */
    void openDialog(Dialog dialog);

    /**
     * Blocks the UI with a small modal window. This window has no close buttons and cannot be closed or hidden by the user. This window wcan only be closed by
     * the method <code>unblockUi</code>.
     */
    void blockUi();

    /**
     * Blocks the UI with a small modal window. This window has no close buttons and cannot be closed or hidden by the user. This window can only be closed by
     * the method <code>unblockUi</code>.
     *
     * @param messageKey a message to display
     */
    void blockUi(String messageKey);

    /**
     * Blocks the UI with the given window. The given dialog has to take care about the unblocking by itself as it is not controlled by the dialogProvider.
     *
     * @param dialog the dialog to block the UI with
     */
    void blockUi(BlockingDialog dialog);

    /**
     * Closes the current blocking window and unblocks the UI. If the UI is not blocked by a previous call to blockUI(), calling this method has no effect. Any
     * currently open {@link Dialog} opened by openDialog() wil be removed from the UI.
     */
    void unblockUi();

    /**
     * Returns <code>true</code> if this UI is currently blocked by a blocking window triggered by a previous call to <code>blockUi()</code>.
     *
     * @return if the UI is blocked
     */
    boolean uiIsBlocked();

    /**
     * Closes the current attached dialog window if existing. If no dialog is open and attached, calling this method has no effect.
     *
     */
    void closeCurrentDialog();

}