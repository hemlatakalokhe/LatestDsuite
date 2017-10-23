package de.bonprix.showcase.cases.imagebrowser;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import de.bonprix.common.dummydata.DummyAssets;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.model.MultiSizeImage;
import de.bonprix.vaadin.model.SimpleMultiSizeImage;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.theme.DSuiteTheme;
import de.bonprix.vaadin.ui.ImageBrowser.ImageBrowser;
import de.bonprix.vaadin.ui.enums.ZoomType;

/**
 * @author s.amin
 *
 */
@SuppressWarnings("serial")
@SpringViewComponent
public class ImageBrowserDemo extends ShowcaseWrapper {

    public ImageBrowserDemo() {
        super("CUSTOM_COMPONENTS", "IMAGEBROWSER");
    }

    @Override
    protected Component createLayout() {
        return FluentUI.vertical()
            .margin()
            .add(FluentUI.button()
                .style(DSuiteTheme.BUTTON_PRIMARY)
                .captionKey("show Image Browser(Slider)")
                .onClick(event -> openImageBrowser(getMultiSizeImages(), ZoomType.SLIDERZOOM))
                .get())
            .add(FluentUI.button()
                .style(DSuiteTheme.BUTTON_PRIMARY)
                .captionKey("show Image Browser(Magnifier)")
                .onClick(event -> openImageBrowser(getMultiSizeImages(), ZoomType.MAGNIFIER))
                .get())
            .get();
    }

    private List<MultiSizeImage> getMultiSizeImages() {
        final List<MultiSizeImage> urlList = new ArrayList<>();
        int count = 0;
        for (final String urls : DummyAssets.getAssetUrls()) {
            final MultiSizeImage images = new SimpleMultiSizeImage("Image with some caption loooooooooooooooooooooooooong textttttttttttt" + (++count), urls);
            urlList.add(images);
        }
        return urlList;
    }

    public void openImageBrowser(final List<MultiSizeImage> urlList, final ZoomType zoomFunction) {
        final ImageBrowser imageBrowser = new ImageBrowser(urlList, zoomFunction);
        imageBrowser.maxZoomSize(1850D);
        UI.getCurrent()
            .addWindow(imageBrowser);
    }

}
