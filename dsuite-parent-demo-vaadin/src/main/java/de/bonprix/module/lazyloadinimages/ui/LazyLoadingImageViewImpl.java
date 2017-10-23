package de.bonprix.module.lazyloadinimages.ui;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.addons.lazyload.LazyImage;
import org.vaadin.addons.lazyload.LazyImageLoader;
import org.vaadin.addons.lazyload.LazyLoadManager;
import org.vaadin.addons.lazyload.client.lazyimage.LazyImageTooltip;
import org.vaadin.addons.scrollablepanel.ScrollablePanel;
import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.VaadinUI;
import de.bonprix.common.dummydata.DummyAssets;
import de.bonprix.module.lazyloadinimages.LazyLoadingImageView;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringView(
    name = LazyLoadingImageViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 30)
public class LazyLoadingImageViewImpl extends AbstractMvpView<LazyLoadingImageView.Presenter> implements LazyLoadingImageView {

    /**
     * 
     */
    private static final long serialVersionUID = 3394519511246640382L;
    @SuppressWarnings("unused")
    private LazyLoadManager lazyLoadManager;
    private CssLayout box;
    private String assetName;
    private static final String BP_LAZYIMAGE_CONTAINER_MARGIN = "bp-lazyimage-container-margin";
    private static final String BP_LAZYIMAGE_CONTAINER_FRAME_RED = "bp-lazyimage-container-frame-red";
    private static final String BP_LAZYIMAGE_CONTAINER_FRAME_GREEN = "bp-lazyimage-container-frame-green";
    private static final String BP_LAZYIMAGE_CONTAINER_FRAME_YELLOW = "bp-lazyimage-container-frame-yellow";
    @SuppressWarnings("unused")
    private static final String BP_CHECK_BUTTON_NAME = "overlay-button-2";
    private static final int LENGTH_OF_ASSET_NAME = 12;
    private static final double DEFAULT_SLIDER_VALUE = 300.0;
    private static final double DEFAULT_MIN_SLIDER_VALUE = 1.0;
    private static final double DEFAULT_MAX_SLIDER_VALUE = 600.0;
    public static final String VIEW_NAME = "LazyLoading";

    private double currentSliderValue = LazyLoadingImageViewImpl.DEFAULT_SLIDER_VALUE;

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

        final ComboBox selectDeselectComboBox = new ComboBox("Select/Deselect Images");
        selectDeselectComboBox.setImmediate(true);
        selectDeselectComboBox.addItems("Select", "Deselect");
        selectDeselectComboBox.setEnabled(false);

        numberOfButtons.setImmediate(true);
        numberOfButtons.setNullSelectionAllowed(false);
        numberOfButtons.addItems(1, 2, 3, 4, 5, 6);
        numberOfButtons.setValue(6);

        final Slider assetSizeSlider = new Slider();
        assetSizeSlider.setMin(LazyLoadingImageViewImpl.DEFAULT_MIN_SLIDER_VALUE);
        assetSizeSlider.setMax(LazyLoadingImageViewImpl.DEFAULT_MAX_SLIDER_VALUE);
        assetSizeSlider.setValue(LazyLoadingImageViewImpl.DEFAULT_SLIDER_VALUE);

        numberOfButtons.addValueChangeListener(event -> refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(), iconButtonsCheckBox.getValue(),
                                                                      starsAndNameCheckBox.getValue(), lazyImageFrameColoredCheckBox.getValue(),
                                                                      (int) numberOfButtons.getValue(), selectDeselectComboBox.getValue()));

        tooltipCheckBox.addValueChangeListener(event -> refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(), iconButtonsCheckBox.getValue(),
                                                                      starsAndNameCheckBox.getValue(), lazyImageFrameColoredCheckBox.getValue(),
                                                                      (int) numberOfButtons.getValue(), selectDeselectComboBox.getValue()));

        iconButtonsCheckBox.addValueChangeListener(event -> {
            if (!iconButtonsCheckBox.getValue()) {
                selectDeselectComboBox.clear();
            }
            selectDeselectComboBox.setEnabled(iconButtonsCheckBox.getValue());
            refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(), iconButtonsCheckBox.getValue(), starsAndNameCheckBox.getValue(),
                          lazyImageFrameColoredCheckBox.getValue(), (int) numberOfButtons.getValue(), selectDeselectComboBox.getValue());
        });

        starsAndNameCheckBox
            .addValueChangeListener(event -> refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(), iconButtonsCheckBox.getValue(),
                                                           starsAndNameCheckBox.getValue(), lazyImageFrameColoredCheckBox.getValue(),
                                                           (int) numberOfButtons.getValue(), selectDeselectComboBox.getValue()));

        lazyImageFrameColoredCheckBox
            .addValueChangeListener(event -> refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(), iconButtonsCheckBox.getValue(),
                                                           starsAndNameCheckBox.getValue(), lazyImageFrameColoredCheckBox.getValue(),
                                                           (int) numberOfButtons.getValue(), selectDeselectComboBox.getValue()));

        assetSizeSlider.addValueChangeListener(event -> {
            setCurrentSliderValue(assetSizeSlider.getValue());
            refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(), iconButtonsCheckBox.getValue(), starsAndNameCheckBox.getValue(),
                          lazyImageFrameColoredCheckBox.getValue(), (int) numberOfButtons.getValue(), selectDeselectComboBox.getValue());
        });

        selectDeselectComboBox
            .addValueChangeListener(event -> refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(), iconButtonsCheckBox.getValue(),
                                                           starsAndNameCheckBox.getValue(), lazyImageFrameColoredCheckBox.getValue(),
                                                           (int) numberOfButtons.getValue(), selectDeselectComboBox.getValue()));

        refreshImages(tooltipCheckBox.getValue(), selectableCheckBox.getValue(), iconButtonsCheckBox.getValue(), starsAndNameCheckBox.getValue(),
                      lazyImageFrameColoredCheckBox.getValue(), (int) numberOfButtons.getValue(), selectDeselectComboBox.getValue());

        final VerticalLayout showImageButtonLayout = new VerticalLayout(iconButtonsCheckBox, selectDeselectComboBox);
        showImageButtonLayout.setSizeUndefined();

        return FluentUI.vertical()
            .margin()
            .spacing()
            .sizeFull()
            .add(FluentUI.horizontal()
                .spacing()
                .add(tooltipCheckBox, showImageButtonLayout, starsAndNameCheckBox, lazyImageFrameColoredCheckBox, numberOfButtons, assetSizeSlider)
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

    @SuppressWarnings("unchecked")
    private void refreshImages(final Boolean showTooltip, final Boolean selectable, final Boolean iconButtonsCheckBox, final Boolean starsAndNameCheckBox,
            final Boolean lazyImageFrameColoredCheckBox, final int selectedNumberOfButtons, final Object selectDeselectComboBox) {

        this.box.removeAllComponents();

        @SuppressWarnings("rawtypes")
        final List iconUrlList = new ArrayList<>();
        iconUrlList.add("https://digistyle.bonprix.net/design/icons/png/32/zoom-in_white_32.png");
        iconUrlList.add("https://digistyle.bonprix.net/design/icons/png/32/ok_white_32.png");
        iconUrlList.add("https://digistyle.bonprix.net/design/icons/png/32/add_to_card_white_32.png");
        iconUrlList.add("https://digistyle.bonprix.net/design/icons/png/32/link_white_32.png");
        iconUrlList.add("https://digistyle.bonprix.net/design/icons/png/32/delete_white_32.png");
        iconUrlList.add("https://digistyle.bonprix.net/design/icons/png/32/create_white_32.png");

        for (final String assetUrl : DummyAssets.getAssetUrls()) {

            // Image Name
            this.assetName = assetUrl.substring(assetUrl.length() - LazyLoadingImageViewImpl.LENGTH_OF_ASSET_NAME)
                .replace("/", "");
            final Label label = new Label(this.assetName);

            final LazyImage lazyImage = new LazyImage().withImageUrl(assetUrl);

            lazyImage.addMouseDownListener(event -> {
                if (iconButtonsCheckBox) {
                    Notification.show("You clicked Button " + event.getClickedImageButton() + " on image " + this.assetName + "!");
                }
            });

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

            if (selectDeselectComboBox != null && selectDeselectComboBox == "Select") {
                if (!lazyImage.isSelected()) {
                    lazyImage.setSelected(true);
                }
                else {
                    lazyImage.setSelected(false);
                }
            }
        }
    }

    public class LazyImageInfoComponent extends VerticalLayout {
        /**
         * 
         */
        private static final long serialVersionUID = -2088079358163194660L;

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
                        lazyImage.setStyleName(LazyLoadingImageViewImpl.BP_LAZYIMAGE_CONTAINER_FRAME_RED);
                        break;
                    case 2:
                        lazyImage.setStyleName(LazyLoadingImageViewImpl.BP_LAZYIMAGE_CONTAINER_FRAME_GREEN);
                        break;
                    case 3:
                        lazyImage.setStyleName(LazyLoadingImageViewImpl.BP_LAZYIMAGE_CONTAINER_FRAME_YELLOW);
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
            setStyleName(LazyLoadingImageViewImpl.BP_LAZYIMAGE_CONTAINER_MARGIN);
            addComponent(vLayout);
        }
    }

    private RatingStars getRatingStars() {
        final RatingStars ratingStars = new RatingStars();
        ratingStars.setMaxValue(5);
        ratingStars.setImmediate(true);
        ratingStars.addValueChangeListener((ValueChangeListener) (ValueChangeEvent) -> Notification.show("You voted " + ValueChangeEvent.getProperty()
            .getValue() + " stars for image " + this.assetName + "!"));
        return ratingStars;
    }

    public Double getCurrentSliderValue() {
        return this.currentSliderValue;
    }

    public void setCurrentSliderValue(final Double currentSliderValue) {
        this.currentSliderValue = currentSliderValue;
    }

    @Override
    public void checkCheckBox(final NavigationRequest request) {
        //
    }

    @Override
    protected void initializeUI() {
        setCompositionRoot(createLayout());
    }
}
