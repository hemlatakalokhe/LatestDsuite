package de.bonprix.showcase.cases.magnifier;

import java.util.Arrays;
import java.util.List;

import org.vaadin.addons.magnifier.Magnifier;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.model.MagnifierImageUrl;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.mvp.SpringViewComponent;

@SuppressWarnings("serial")
@SpringViewComponent
public class MagnifierDemo extends ShowcaseWrapper {

    private static final List<MagnifierImageUrl> IMAGE_URLS = Arrays
        .asList(new MagnifierImageUrl("9/3/3/3/1869333.jpg"), new MagnifierImageUrl("4/6/1/3/12104613.jpg"), new MagnifierImageUrl("4/6/1/3/15154613.jpg"));

    public MagnifierDemo() {
        super("INTERACTION", "MAGNIFIER");
    }

    @SuppressWarnings("deprecation")
    @Override
    protected com.vaadin.ui.Component createLayout() {

        // Create Magnifier and set Size and ImageURL
        final Magnifier magnifier = new Magnifier();
        magnifier.setHeight(600, Unit.PIXELS);
        magnifier.setWidth(400, Unit.PIXELS);
        // Set the ZoomFactor
        magnifier.setZoomFactor(1.5f);

        // Left side Images
        final VerticalLayout imageLayout = new VerticalLayout();
        imageLayout.setMargin(true);
        imageLayout.setSpacing(true);

        boolean firstTime = true;
        Image image = null;

        for (final MagnifierImageUrl imageUrl : MagnifierDemo.IMAGE_URLS) {
            // Left side images
            image = new Image(null, new ExternalResource(imageUrl.getImageUrl()));
            image.setImmediate(true);
            image.setHeight(300, Unit.PIXELS);
            image.setWidth(200, Unit.PIXELS);
            image.addClickListener(event -> {
                magnifier.setImageUrl(imageUrl.getImageUrl());
                magnifier.setZoomImageUrl(imageUrl.getImageZoomUrl());
            });
            imageLayout.addComponents(image);

            // set default magnifier image
            if (firstTime) {
                magnifier.setImageUrl(imageUrl.getImageUrl());
                // OPTIONAL: Set the ZoomImageUrl
                magnifier.setZoomImageUrl(imageUrl.getImageZoomUrl());
                firstTime = false;
            }
        }

        final VerticalLayout magnifierLayout = new VerticalLayout();
        magnifierLayout.setStyleName("demoContentLayout");
        magnifierLayout.setSizeFull();
        magnifierLayout.addComponent(magnifier);
        magnifierLayout.setComponentAlignment(magnifier, Alignment.MIDDLE_CENTER);

        final HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
        hsplit.setFirstComponent(imageLayout);
        hsplit.setSecondComponent(magnifierLayout);

        // Set the position of the splitter as percentage
        hsplit.setSplitPosition(20, Sizeable.UNITS_PERCENTAGE);

        return hsplit;

    }

}
