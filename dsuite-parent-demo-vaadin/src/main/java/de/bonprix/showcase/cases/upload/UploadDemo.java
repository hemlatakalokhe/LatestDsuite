package de.bonprix.showcase.cases.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.easyuploads.MultiFileUpload;
import org.vaadin.easyuploads.UploadField;

import com.vaadin.ui.Component;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

import de.bonprix.I18N;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.provider.UiNotificationProvider;

@SuppressWarnings("serial")
@SpringViewComponent
public class UploadDemo extends ShowcaseWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadDemo.class);

    public static final String UPLOAD = "UPLOAD";
    private static final int MAX_FILE_SIZE = 100000;
    private static final String ACCEPT_FILTER = "image/*";
    private static final int MAX_NUMBER_OF_FILES = 5;
    private static final String SUCCESS = "SUCCESS";
    private static final String FILE_NAME = "FILE_NAME";
    private static final String MIME_TYPE = "MIME_TYPE";

    @Resource
    private UiNotificationProvider uiNotificationProvider;

    public UploadDemo() {
        super("STRUCTURE_HIERARCHY", UploadDemo.UPLOAD);
    }

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    protected Component createLayout() {
        /**
         * This is the default Upload component provided by Vaadin.
         */
        final ImageReceiver receiverVaadinUpload = new ImageReceiver();
        final Upload upload = new Upload(I18N.get("VAADIN_UPLOAD"), receiverVaadinUpload);
        upload.setButtonCaption(I18N.get(UploadDemo.UPLOAD));
        upload.addSucceededListener(receiverVaadinUpload);
        // This is to make the upload button and browse button as one.
        upload.setImmediate(true);

        /**
         * This component is from easyUpload addon which uploads single file at a time. It uses vaadin upload internally.
         */
        final UploadField addOnSingleUpload = new UploadField();
        addOnSingleUpload.setCaption(I18N.get("ADDON_SINGLE_UPLOAD"));
        addOnSingleUpload.setButtonCaption(I18N.get(UploadDemo.UPLOAD));
        // This is to disable the details of the file uploaded below the upload
        // button
        addOnSingleUpload.setDisplayUpload(false);
        addOnSingleUpload.addValueChangeListener(listener -> this.uiNotificationProvider
            .showInfoNotification(I18N.get(UploadDemo.SUCCESS) + " : " + I18N.get(UploadDemo.MIME_TYPE) + " : " + addOnSingleUpload.getLastMimeType() + " "
                    + I18N.get(UploadDemo.FILE_NAME) + " : " + addOnSingleUpload.getLastFileName()));

        /**
         * In this single file upload component of easyupload addon we can limit the maxFilesize, limit which type of files user can select from browser.
         */
        final UploadField addOnLimitSingleUpload = new UploadField();
        addOnLimitSingleUpload.setCaption(I18N.get("ADDON_SINGLE_UPLOAD_LIMITED"));
        addOnLimitSingleUpload.setAcceptFilter(UploadDemo.ACCEPT_FILTER);
        addOnLimitSingleUpload.setMaxFileSize(UploadDemo.MAX_FILE_SIZE);
        addOnLimitSingleUpload.setDisplayUpload(false);
        addOnLimitSingleUpload.setButtonCaption(I18N.get(UploadDemo.UPLOAD));
        addOnLimitSingleUpload.addValueChangeListener(listener -> this.uiNotificationProvider
            .showInfoNotification(I18N.get(UploadDemo.SUCCESS) + " : " + I18N.get(UploadDemo.MIME_TYPE) + " : " + addOnLimitSingleUpload.getLastMimeType() + " "
                    + I18N.get(UploadDemo.FILE_NAME) + " : " + addOnLimitSingleUpload.getLastFileName()));

        /**
         * This is a MultiFileUpload component with Drag n Drop functionality is enabled by default
         */
        final MultiFileUpload multiFileUpload = new MultiFileUpload() {

            private static final long serialVersionUID = -2516211720449733063L;

            @Override
            protected void handleFile(final File file, final String fileName, final String mimeType, final long length) {
                UploadDemo.this.uiNotificationProvider.showInfoNotification(I18N.get(UploadDemo.SUCCESS) + " : " + I18N.get(UploadDemo.MIME_TYPE) + " : "
                        + mimeType + " " + I18N.get(UploadDemo.FILE_NAME) + " : " + fileName);
            }
        };
        multiFileUpload.setCaption(I18N.get("MULTI_FILE_UPLOAD_DRAG_N_DROP"));
        multiFileUpload.setUploadButtonCaption(I18N.get(UploadDemo.UPLOAD));

        /**
         * In this MultiFileUpload component we can limit the maxFilesize, limit which type of files user can select from browser and limit the number of files
         * as well
         */
        final MultiFileUpload multiFileUploadLimited = new MultiFileUpload() {

            private static final long serialVersionUID = 1083703193975376598L;

            @Override
            protected void handleFile(final File file, final String fileName, final String mimeType, final long length) {
                UploadDemo.this.uiNotificationProvider.showInfoNotification(I18N.get(UploadDemo.SUCCESS) + " : " + I18N.get(UploadDemo.MIME_TYPE) + " : "
                        + mimeType + " " + I18N.get(UploadDemo.FILE_NAME) + " : " + fileName);
            }
        };
        multiFileUploadLimited.setCaption(I18N.get("MULTI_FILE_UPLOAD_LIMITTED"));
        multiFileUploadLimited.setMaxFileSize(UploadDemo.MAX_FILE_SIZE);
        multiFileUploadLimited.setAcceptFilter(UploadDemo.ACCEPT_FILTER);
        multiFileUploadLimited.setMaxFileCount(UploadDemo.MAX_NUMBER_OF_FILES);
        multiFileUploadLimited.setUploadButtonCaption(I18N.get(UploadDemo.UPLOAD));

        /**
         * In this MultiFileUpload component the Drag n Drop functionality is disabled
         */
        final MultiFileUpload multiFileUploadNoDragNDrop = new MultiFileUpload() {

            private static final long serialVersionUID = -6986945293331141704L;

            @Override
            protected void handleFile(final File file, final String fileName, final String mimeType, final long length) {
                UploadDemo.this.uiNotificationProvider.showInfoNotification(I18N.get(UploadDemo.SUCCESS) + " : " + I18N.get(UploadDemo.MIME_TYPE) + " : "
                        + mimeType + " " + I18N.get(UploadDemo.FILE_NAME) + " : " + fileName);
            }

            @Override
            protected boolean supportsFileDrops() {
                return false;
            }

        };
        multiFileUploadNoDragNDrop.setCaption(I18N.get("MULTI_FILE_UPLOAD_NO_DRAG_N_DROP"));
        multiFileUploadNoDragNDrop.setUploadButtonCaption(I18N.get(UploadDemo.UPLOAD));

        return FluentUI.vertical()
            .add(FluentUI.horizontal()
                .add(upload)
                .add(addOnSingleUpload)
                .add(addOnLimitSingleUpload)
                .spacing()
                .sizeFull()
                .get())
            .add(FluentUI.horizontal()
                .add(multiFileUploadNoDragNDrop)
                .add(multiFileUpload)
                .add(multiFileUploadLimited)
                .sizeFull()
                .spacing()
                .get())
            .spacing()
            .margin()
            .sizeFull()
            .get();
    }

    class ImageReceiver implements Receiver, SucceededListener {

        private static final long serialVersionUID = 5714489729246035420L;
        private File fileToUpload;
        private transient FileOutputStream fileOutputStream = null;

        @Override
        public OutputStream receiveUpload(final String filename, final String mimeType) {
            try {
                this.fileToUpload = new File(filename);
                this.fileOutputStream = new FileOutputStream(this.fileToUpload);
            }
            catch (final FileNotFoundException e) {
                UploadDemo.LOGGER.error(e.getLocalizedMessage(), e);
                return null;
            }
            return this.fileOutputStream;
        }

        @Override
        public void uploadSucceeded(final SucceededEvent event) {
            UploadDemo.this.uiNotificationProvider.showInfoNotification(I18N.get(UploadDemo.SUCCESS) + " : " + I18N.get(UploadDemo.MIME_TYPE) + " : "
                    + event.getMIMEType() + " " + I18N.get("FILE_NAME") + " : " + event.getFilename());
        }
    }

}
