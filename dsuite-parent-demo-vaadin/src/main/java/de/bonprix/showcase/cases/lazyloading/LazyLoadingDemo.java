package de.bonprix.showcase.cases.lazyloading;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.addons.lazyload.LazyImage;
import org.vaadin.addons.lazyload.LazyImageLoader;
import org.vaadin.addons.lazyload.LazyLoadManager;
import org.vaadin.addons.lazyload.client.lazyimage.LazyImageTooltip;
import org.vaadin.addons.scrollablepanel.ScrollablePanel;
import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.common.dummydata.DummyAssets;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * Shows Lazyloading for Images.
 */
@SuppressWarnings("serial")
@SpringViewComponent
public class LazyLoadingDemo extends ShowcaseWrapper {
    @SuppressWarnings("unused")
    private LazyLoadManager lazyLoadManager;
    private CssLayout box;
    private String assetName;
    private static final String BP_LAZYIMAGE_CONTAINER_MARGIN = "bp-lazyimage-container-margin";
    private static final String BP_LAZYIMAGE_CONTAINER_FRAME_RED = "bp-lazyimage-container-frame-red";
    private static final String BP_LAZYIMAGE_CONTAINER_FRAME_GREEN = "bp-lazyimage-container-frame-green";
    private static final String BP_LAZYIMAGE_CONTAINER_FRAME_YELLOW = "bp-lazyimage-container-frame-yellow";
    private static final String BP_CHECK_BUTTON_NAME = "overlay-button-2";
    private static final int LENGTH_OF_ASSET_NAME = 12;

    public LazyLoadingDemo() {
        super("INTERACTION", "LAZYLOADINGDEMO");
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
        // init LoadManager is needed for lazyImage
        new LazyImageLoader(this);
        this.box = new CssLayout();
        this.box.setSizeFull();
        final ScrollablePanel scrollPanel = new ScrollablePanel();
        scrollPanel.setWidth("100%");
        scrollPanel.setHeight(580, Unit.PIXELS);
        scrollPanel.setContent(this.box);
        final CheckBox tooltipCheckBox = new CheckBox("Show tooltip");
        final CheckBox selectableCheckBox = new CheckBox("Images are selectable");
        final CheckBox iconButtonsCheckBox = new CheckBox("Show Clickable icons over the Images");
        final CheckBox starsAndNameCheckBox = new CheckBox("Shows Imagename and Stars");
        final CheckBox lazyImageFrameColoredCheckBox = new CheckBox("Shows Frame-Color");
        final ComboBox numberOfButtons = new ComboBox("Select number of Buttons");
        numberOfButtons.setImmediate(true);
        numberOfButtons.setNullSelectionAllowed(false);
        numberOfButtons.addItems(1, 2, 3, 4, 5, 6);
        numberOfButtons.setValue(6);
        numberOfButtons.addValueChangeListener(event -> refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(), iconButtonsCheckBox.getValue(),
                                                                      starsAndNameCheckBox.getValue(), lazyImageFrameColoredCheckBox.getValue(),
                                                                      (int) numberOfButtons.getValue()));
        tooltipCheckBox.addValueChangeListener(event -> refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(), iconButtonsCheckBox.getValue(),
                                                                      starsAndNameCheckBox.getValue(), lazyImageFrameColoredCheckBox.getValue(),
                                                                      (int) numberOfButtons.getValue()));
        selectableCheckBox.addValueChangeListener(event -> refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(),
                                                                         iconButtonsCheckBox.getValue(), starsAndNameCheckBox.getValue(),
                                                                         lazyImageFrameColoredCheckBox.getValue(), (int) numberOfButtons.getValue()));
        iconButtonsCheckBox.addValueChangeListener(event -> refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(),
                                                                          iconButtonsCheckBox.getValue(), starsAndNameCheckBox.getValue(),
                                                                          lazyImageFrameColoredCheckBox.getValue(), (int) numberOfButtons.getValue()));
        starsAndNameCheckBox.addValueChangeListener(event -> refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(),
                                                                           iconButtonsCheckBox.getValue(), starsAndNameCheckBox.getValue(),
                                                                           lazyImageFrameColoredCheckBox.getValue(), (int) numberOfButtons.getValue()));
        lazyImageFrameColoredCheckBox
            .addValueChangeListener(event -> refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(), iconButtonsCheckBox.getValue(),
                                                           starsAndNameCheckBox.getValue(), lazyImageFrameColoredCheckBox.getValue(),
                                                           (int) numberOfButtons.getValue()));
        refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(), iconButtonsCheckBox.getValue(), starsAndNameCheckBox.getValue(),
                      lazyImageFrameColoredCheckBox.getValue(), (int) numberOfButtons.getValue());
        return FluentUI.vertical()
            .margin()
            .spacing()
            .sizeFull()
            .add(FluentUI.horizontal()
                .spacing()
                .add(tooltipCheckBox, selectableCheckBox, iconButtonsCheckBox, starsAndNameCheckBox, lazyImageFrameColoredCheckBox, numberOfButtons)
                .get())
            .add(FluentUI.panel()
                .sizeUndefined()
                .style(DSuiteTheme.PANEL_INVERTED)
                .content(FluentUI.horizontal()
                    .spacing()
                    .margin()
                    .add(scrollPanel)
                    .get())
                .get(), 1)
            .get();
    }

    private void refreshImages(final Boolean showTooltip, final Boolean selectable, final Boolean iconButtonsCheckBox, final Boolean starsAndNameCheckBox,
            final Boolean lazyImageFrameColoredCheckBox, final int selectedNumberOfButtons) {
        this.box.removeAllComponents();
        final List<String> iconUrlList = new ArrayList<>();
        iconUrlList.add("https://digistyle.bonprix.net/design/icons/png/32/zoom-in_white_32.png");
        iconUrlList.add("https://digistyle.bonprix.net/design/icons/png/32/ok_white_32.png");
        iconUrlList.add("https://digistyle.bonprix.net/design/icons/png/32/add_to_card_white_32.png");
        iconUrlList.add("https://digistyle.bonprix.net/design/icons/png/32/link_white_32.png");
        iconUrlList.add("https://digistyle.bonprix.net/design/icons/png/32/delete_white_32.png");
        iconUrlList.add("https://digistyle.bonprix.net/design/icons/png/32/create_white_32.png");

        for (final String assetUrl : DummyAssets.getAssetUrls()) {
            // Image Name
            this.assetName = assetUrl.substring(assetUrl.length() - LazyLoadingDemo.LENGTH_OF_ASSET_NAME)
                .replace("/", "");
            final Label label = new Label(this.assetName);
            final LazyImage lazyImage = new LazyImage().withImageUrl(assetUrl);
            if (showTooltip) {
                lazyImage.setTooltip(new LazyImageTooltip("Asset", "a little subline").addAttribute("key1", "value 1")
                    .addAttribute("key2", "value 2")
                    .addAttribute("key3", "value 3")
                    .addAttribute("key4", "value 4"));
            }
            if (selectable) {
                lazyImage.withSelectListener(event -> Notification.show("selection changed to: " + event.isSelected() + " for asset: " + event.getComponent()
                    .getImageUrl(), Type.HUMANIZED_MESSAGE))
                    .widthCoverDisplay(false);
            }
            if (iconButtonsCheckBox) {
                lazyImage.setIconUrlList(iconUrlList.subList(0, selectedNumberOfButtons));
            }
            if (starsAndNameCheckBox) {
                // Put all components together in one box
                this.box.addComponent(new LazyImageInfoComponent(lazyImage, lazyImageFrameColoredCheckBox, label, getRatingStars()));
            }
            else {
                this.box.addComponent(new LazyImageInfoComponent(lazyImage, lazyImageFrameColoredCheckBox, null, null));
            }
            lazyImage.addMouseDownListener(event -> {
                if (iconButtonsCheckBox) {
                    Notification.show("You clicked Button " + event.getClickedImageButton() + " on image " + LazyLoadingDemo.this.assetName + "!");
                    if (LazyLoadingDemo.BP_CHECK_BUTTON_NAME.equals(event.getClickedImageButton())) {
                        lazyImage.withSelectListener(event1 -> {//
                        });
                    }
                }
            });
        }
    }

    public class LazyImageInfoComponent extends VerticalLayout {
        public LazyImageInfoComponent(final LazyImage lazyImage, final boolean showFrameColor) {
            this(lazyImage, showFrameColor, null, null);
        }

        public LazyImageInfoComponent(final LazyImage lazyImage, final boolean showFrameColor, final Label label, final RatingStars ratingStars) {
            final VerticalLayout vLayout = new VerticalLayout();
            vLayout.setImmediate(true);
            if (showFrameColor) {
                final int zahl = (int) ((Math.random()) * 3 + 1);
                switch (zahl) {
                    case 1:
                        lazyImage.setStyleName(LazyLoadingDemo.BP_LAZYIMAGE_CONTAINER_FRAME_RED);
                        break;
                    case 2:
                        lazyImage.setStyleName(LazyLoadingDemo.BP_LAZYIMAGE_CONTAINER_FRAME_GREEN);
                        break;
                    case 3:
                        lazyImage.setStyleName(LazyLoadingDemo.BP_LAZYIMAGE_CONTAINER_FRAME_YELLOW);
                        break;
                }
            }
            vLayout.addComponent(lazyImage);
            if (label != null) {
                vLayout.addComponent(label);
                vLayout.setComponentAlignment(label, Alignment.BOTTOM_CENTER);
            }
            if (ratingStars != null) {
                vLayout.addComponent(ratingStars);
                vLayout.setComponentAlignment(ratingStars, Alignment.BOTTOM_CENTER);
            }
            setImmediate(true);
            setStyleName(LazyLoadingDemo.BP_LAZYIMAGE_CONTAINER_MARGIN);
            addComponent(vLayout);
        }
    }

    private RatingStars getRatingStars() {
        final RatingStars ratingStars = new RatingStars();
        ratingStars.setMaxValue(5);
        ratingStars.setImmediate(true);
        ratingStars.addValueChangeListener((ValueChangeListener) ValueChangeEvent -> Notification.show("You voted " + ValueChangeEvent.getProperty()
            .getValue() + " stars for image " + this.assetName + "!"));
        return ratingStars;
    }
}