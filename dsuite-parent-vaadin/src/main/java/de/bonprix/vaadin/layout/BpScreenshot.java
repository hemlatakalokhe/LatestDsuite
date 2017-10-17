package de.bonprix.vaadin.layout;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.addons.screenshot.Screenshot;
import org.vaadin.addons.screenshot.ScreenshotImage;
import org.vaadin.addons.screenshot.ScreenshotListener;
import org.vaadin.simplefiledownloader.SimpleFileDownloader;

import com.vaadin.server.StreamResource;

import de.bonprix.information.ApplicationProvider;

@Component
@com.vaadin.spring.annotation.UIScope
public class BpScreenshot extends Screenshot {

	@Autowired
	private ApplicationProvider applicationProvider;

	private ScreenshotImage screenshotImage = null;
	private boolean withFileDownload = true;

	public BpScreenshot() {

		addScreenshotListener(new ScreenshotListener() {
			public void screenshotComplete(ScreenshotImage image) {
				BpScreenshot.this.screenshotImage = image;

				String appName = BpScreenshot.this.applicationProvider.getApplication()
					.getName() + "_screenshot.png";

				StreamResource resource = new StreamResource(() -> {
					return new ByteArrayInputStream(image.getImageData());
				}, appName);

				if (BpScreenshot.this.withFileDownload) {
					SimpleFileDownloader downloader = new SimpleFileDownloader();
					addExtension(downloader);
					downloader.setFileDownloadResource(resource);
					downloader.download();
				}
				BpScreenshot.this.withFileDownload = true;
			}
		});
	}

	@Override
	public void takeScreenshot() {
		super.focus();
		super.takeScreenshot();
	}

	public void takeScreenshotWithoutDownload() {
		this.withFileDownload = false;
		this.takeScreenshot();
	}

	public ScreenshotImage getScreenshotResource() {
		return this.screenshotImage;
	}

}