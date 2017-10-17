package de.bonprix.vaadin.dialog;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.addons.scrollablepanel.ScrollablePanel;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.I18N;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.provider.UiDialogProvider.BlockingDialog;
import de.bonprix.vaadin.provider.UiExecutorProvider;
import de.bonprix.vaadin.provider.UiExecutorProvider.BackgroundTask;
import de.bonprix.vaadin.provider.UiExecutorProvider.ProgressMonitor;

@SpringViewComponent
public class ProgressWindow extends AbstractBaseDialog implements ProgressMonitor, BlockingDialog {

	@Autowired
	private UiExecutorProvider uiExecutorProvider;

	private int progress = 0;
	private int targetProgress = 100;

	private boolean autoClose = false;
	private boolean finished = false;
	private boolean interruptionRequested = false;

	private LogMessageLevel worstLogLevel = LogMessageLevel.SUCCESS;

	private Label progressStepLabel;
	private Label progressLabel;
	private ProgressBar progressBar;
	private Button detailButton;

	private Label spacerLabel;

	private VerticalLayout logLayout;
	private ScrollablePanel logWrapper;

	private BackgroundTask task;

	public ProgressWindow() {
		super(new DialogConfigurationBuilder()	.withHeadline("Caption")
												.withButton(DialogButton.CANCEL)
												.withPrimaryButton(DialogButton.OK)
												.withWidth(600)
												.withHeight(250)
												.withModal(true)
												.withCloseOnAnyButton(false)
												.withCloseOnCancelButton(false)
												.withCloseOnCloseButton(false)
												.build());

		addStyleName("progress-window");
	}

	@Override
	public void close() {
		super.close();

		this.task.onClose();
	}

	@Override
	protected Component layout() {
		setClosable(false);
		getButton(DialogButton.CANCEL).setEnabled(this.task.isInterruptible());
		getButton(DialogButton.OK).setEnabled(false);
		addButtonListener(DialogButton.CANCEL, (event) -> this.interruptionRequested = true);
		addButtonListener(DialogButton.OK, event -> {
			if (ProgressWindow.this.finished) {
				close();
			}
		});

		this.progressStepLabel = new Label("", ContentMode.HTML);
		this.progressLabel = new Label("0/100");
		this.progressBar = new ProgressBar();
		this.progressBar.setWidth(100, Unit.PERCENTAGE);

		this.detailButton = new Button(I18N.get("SHOW_DETAILS"));
		this.detailButton.addClickListener(event -> showDetails(!ProgressWindow.this.logWrapper.isVisible()));

		this.logLayout = new VerticalLayout();
		this.logLayout.setMargin(true);

		this.logWrapper = new ScrollablePanel(this.logLayout);
		this.logWrapper.setSizeFull();
		this.logWrapper.setStyleName("progress-window-log-wrapper");

		this.spacerLabel = new Label();
		this.spacerLabel.setSizeFull();

		showDetails(false);

		return FluentUI	.vertical()
						.sizeFull()
						.spacing()
						.margin()
						.add(FluentUI	.horizontal()
										.add(this.progressStepLabel)
										.add(this.progressLabel)
										.get())
						.add(this.progressBar)
						.add(this.detailButton)
						.add(this.logWrapper, 1)
						.add(this.spacerLabel, 1)
						.get();
	}

	@Override
	public void setProgressTitle(final String progressTitle) {
		this.uiExecutorProvider.executeInUiContext(() -> setCaption(progressTitle));
	}

	@Override
	public void setCurrentProgressStep(final String progressStep) {
		this.uiExecutorProvider.executeInUiContext(() -> ProgressWindow.this.progressStepLabel.setValue(progressStep));
	}

	@Override
	public void setProgress(final int progress) {
		this.uiExecutorProvider.executeInUiContext(() -> {
			ProgressWindow.this.progress = progress;
			updateProgress();
		});
	}

	@Override
	public void setTargetProgress(final int targetProgress) {
		this.uiExecutorProvider.executeInUiContext(() -> {
			ProgressWindow.this.targetProgress = targetProgress;
			updateProgress();
		});
	}

	@Override
	public void setAutoClose(final boolean autoClose) {
		this.autoClose = autoClose;
	}

	@Override
	public void addLog(final String log) {
		addLog(log, null);
	}

	@Override
	public void incrementProgress(final int progress) {
		setProgress(this.progress + progress);
	}

	@Override
	public void addLog(final String log, final LogMessageLevel result) {
		if (result == LogMessageLevel.ERROR) {
			this.worstLogLevel = LogMessageLevel.ERROR;
		} else if (result == LogMessageLevel.WARNING && this.worstLogLevel != LogMessageLevel.ERROR) {
			this.worstLogLevel = LogMessageLevel.WARNING;
		}

		this.uiExecutorProvider.executeInUiContext(() -> {

			final Label entry = new Label(log);

			if (result != null) {
				switch (result) {
				case SUCCESS:
					entry.setStyleName("success");
					break;
				case WARNING:
					entry.setStyleName("warning");
					break;
				case ERROR:
					entry.setStyleName("failure");
					break;
				}
			}

			ProgressWindow.this.logLayout.addComponent(entry);
			ProgressWindow.this.logWrapper.setScrollTop(10000);
		});
	}

	@Override
	public void finish() {
		this.uiExecutorProvider.executeInUiContext(() -> {
			ProgressWindow.this.finished = true;

			if (!ProgressWindow.this.autoClose || ProgressWindow.this.worstLogLevel != LogMessageLevel.SUCCESS) {
				// if window is not set on autoclose OR there was an error,
				// do not close the window and show some result text
				setProgressResultLabelContent(ProgressWindow.this.worstLogLevel);
				getButton(DialogButton.OK).setEnabled(true);
			} else {
				getButton(DialogButton.OK).setEnabled(true);
				getButton(DialogButton.OK).click();
			}
		});
	}

	private void setProgressResultLabelContent(final LogMessageLevel level) {
		switch (ProgressWindow.this.worstLogLevel) {
		case SUCCESS:
			if (this.interruptionRequested && this.progress < this.targetProgress) {
				ProgressWindow.this.progressLabel.setValue(I18N.get("PROCESSING_INTERRUPTED"));
			} else {
				ProgressWindow.this.progressLabel.setValue(I18N.get("PROCESSING_FINISHED_SUCCESSFULLY"));
			}
			break;
		case WARNING:
			ProgressWindow.this.progressLabel.setValue(I18N.get("WARNINGS_OCCURED_DURING_PROCESSING_SEE_DETAILS"));
			ProgressWindow.this.progressLabel.setStyleName("warning");
			break;
		case ERROR:
			ProgressWindow.this.progressLabel.setValue(I18N.get("ERRORS_OCCURED_DURING_PROCESSING_SEE_DETAILS"));
			ProgressWindow.this.progressLabel.setStyleName("error");
			break;
		}
	}

	private void updateProgress() {
		this.progressLabel.setValue(this.progress + "/" + this.targetProgress);

		// catch division by zero
		if (this.targetProgress <= 0) {
			this.progressBar.setValue(0f);
		} else {
			this.progressBar.setValue(Math.min(	Math.max(((float) this.progress) / ((float) this.targetProgress), 0),
												1));
		}
	}

	private void showDetails(final boolean showDetails) {
		if (showDetails) {
			ProgressWindow.this.logWrapper.setVisible(true);
			this.spacerLabel.setVisible(false);
			setHeight(500, Unit.PIXELS);
			setWidth(700, Unit.PIXELS);
			this.detailButton.setCaption(I18N.get("HIDE_DETAILS"));
		} else {
			ProgressWindow.this.logWrapper.setVisible(false);
			this.spacerLabel.setVisible(true);
			setHeight(250, Unit.PIXELS);
			setWidth(700, Unit.PIXELS);
			this.detailButton.setCaption(I18N.get("SHOW_DETAILS"));
		}
	}

	public BackgroundTask getTask() {
		return this.task;
	}

	public void setTask(final BackgroundTask task) {
		this.task = task;
	}

	@Override
	public boolean interruptionRequested() {
		return this.interruptionRequested;
	}

}