/**
 *
 */
package de.bonprix.vaadin.provider;

/**
 * @author cthiel
 * @date 22.11.2016
 *
 */
public interface UiExecutorProvider {

    /**
     * Executes the given runnable in a background thread. As long as the background thread is executed, the UI is blocked by the {@link UiDialogProvider}s
     * blockUi method so no further action is possible.
     *
     * @param runnable the runnable to execute
     */
    void executeBlocking(Runnable runnable);

    /**
     * @param messageKey
     * @param runnable
     */
    void executeBlocking(String messageKey, Runnable runnable);

    /**
     * Executes the given backgroundTask in a background thread. For the execution of the background task a progress monitor is created and attached to the UI.
     * This progress monitor is modal and blocks the UI for further interactions until the background task is finished and the window is closed.
     *
     * @param backgroundTask the background task to execute
     */
    void executeBlocking(BackgroundTask backgroundTask);

    /**
     * Executes the given runnable in the context of the current threads UI and locks the UI for this.
     */
    void executeInUiContext(Runnable runnable);

    /**
     * A background task to execute.
     *
     * @author cthiel
     * @date 22.11.2016
     *
     */
    public static interface BackgroundTask {

        /**
         * Runs the background task with the given progressMonitor.
         *
         * @param progressMonitor
         */
        void run(ProgressMonitor progressMonitor);

        /**
         * If the implemented background process is interruptible or not. The interruption must be managed by the process itself within the run method using the
         * procressMonitor#interruptionRequested() method. The thread itself will not be interrupted.
         *
         * @return if the task is interruptible
         */
        boolean isInterruptible();

        /**
         * This method is called in the moment the dialog is closed.
         */
        void onClose();
    }

    /**
     * The ProgressMonitor is an interface to visualize the progress of a longer running background task, that runs outside the usual Vaadin request
     * environment.
     *
     * @author cthiel
     *
     */
    public static interface ProgressMonitor {

        public enum LogMessageLevel {
            SUCCESS,
            WARNING,
            ERROR
        }

        /**
         * Sets the window title of the long running operation.
         *
         * @param progressTitle the progressTitle to set
         */
        void setProgressTitle(String progressTitle);

        /**
         * Sets the target progress of the background task. If the progress set by setProgress or incrementProgress reaches this value, the task will be set as
         * finished in the monitor.<br/>
         * <br/>
         * The default value is 100.
         *
         * @param maxProgress the maxProgress to set
         */
        void setTargetProgress(int targetProgress);

        /**
         * Sets the current progress of the operation. This value has to be greater equal to 0 and cannot be greater than the value set to setTargetProgress. If
         * the resulting progress is equal to the target progress, the task will be set as finished in the monitor.
         *
         * @param progress the progress to set
         */
        void setProgress(int progress);

        /**
         * Increments the current progress of the operation by the given amount. The given increment value has to be greater equal to 0. The resulting progress
         * cannot be greater than the value set to setTargetProgress. If the resulting progress is equal to the target progress, the task will be set as
         * finished in the monitor.
         *
         * @param progressIncrement the increment
         */
        void incrementProgress(int progressIncrement);

        /**
         * Sets the description of the current progress step.
         *
         * @param progressStep step description
         */
        void setCurrentProgressStep(String progressStep);

        /**
         * Sets the autoClose option in the monitor. If this option is set on <code>true</code>, the monitor window will close itself automatically, as soon as
         * the task is marked as finished and no error or warning was submitted via the addLog() method.<br/>
         * <br/>
         * The default setting is <code>false</code>
         *
         * @param autoClose the autoClose option to set
         */
        void setAutoClose(boolean autoClose);

        /**
         * Adds a log entry to the logging panel in this monitor.
         *
         * @param logMessage the log string to add
         */
        void addLog(String logMessage);

        /**
         * Adds a log entry to the logging panel in this monitor with the given message type.
         *
         * @param logMessage the me
         * @param result
         */
        void addLog(String logMessage, LogMessageLevel result);

        /**
         * Marks the task of this progress monitor as finished. If the monitor is set on autoClose and no error or warning was submitted via the addLog()
         * method, this window will close automatically.
         */
        void finish();

        /**
         *
         * @return if a cancel of the background request has been requested by the user or not.
         */
        boolean interruptionRequested();

    }

}
