package de.bonprix.vaadin.ui.ImageBrowser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.magnifier.Magnifier;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.model.MultiSizeImage;
import de.bonprix.vaadin.model.MultiSizeImage.ImageSize;
import de.bonprix.vaadin.theme.DSuiteTheme;
import de.bonprix.vaadin.ui.enums.ZoomType;
import de.bonprix.vaadin.ui.lazyscroll.AbstractLazyObject;
import de.bonprix.vaadin.ui.lazyscroll.LazyObject;
import de.bonprix.vaadin.ui.lazyscroll.LazyObjectSelectedEvent;
import de.bonprix.vaadin.ui.lazyscroll.LazyVerticalScrollPanel;
import de.bonprix.vaadin.ui.lazyscroll.LazyScrollPanel.LazyObjectSelectedListener;

/**
 * The ImageBrowser is a window for the display of various images.
 *
 * @author s.amin
 *
 */
public class ImageBrowser extends Window {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageBrowser.class);

	private static final String SESSION_ATTRIBUTE_NAME = "bp-imagebrowser-singleton";

	private static final Double IMAGEBROWSE_MAX = 1000D;
	private static final Double IMAGEBROWSE_MIN = 250D;
	private static final String CAPTION_LABEL_WIDTH = "350px";
	private static final Integer SLIDER_WIDTH = 150;
	private static final Integer WINDOW_HEIGTH_SLIDER = 750;
	private static final Integer WINDOW_HEIGTH = 700;
	private static final Integer WINDOW_WIDTH = 1000;

	private final List<MultiSizeImage> urls = new ArrayList<>();

	private Magnifier magnifier;
	private Image mainImage;
	private final LazyVerticalScrollPanel thumbImageList;
	private Slider imgSizeSlider;
	private VerticalLayout imageLayout;
	private final Map<MultiSizeImage, LazyObject> imageObjectMap = new HashMap<>();
	private MultiSizeImage multiSizeImage;
	private VerticalLayout vImageLayout;
	private Label sizeLabel;
	private HorizontalLayout sliderLayout;
	private ZoomType zoomType;
	private Label imageCaptionLabel;

	/**
	 * Creates a new ImageBrowser window and initializes the image list with
	 * images from the given URL list.
	 *
	 * @param urlList
	 *            the list of image URLs
	 */
	public ImageBrowser(final List<MultiSizeImage> urlList) {
		this(urlList, ZoomType.NONE);
	}

	/**
	 * Creates a new ImageBrowser window and initializes the image list with
	 * images from the given URL list.
	 *
	 * @param urlList
	 *            the list of image URLs
	 */
	public ImageBrowser(final List<MultiSizeImage> urlList, final ZoomType zoomType) {
		this.zoomType = zoomType;
		this.urls.addAll(urlList);
		this.imageCaptionLabel = new Label();
		this.imageCaptionLabel.setWidth(ImageBrowser.CAPTION_LABEL_WIDTH);
		final Component zoomComponent = toggleZoomOption(urlList);
		// to set the image caption at the bottom
		this.imageCaptionLabel.setValue(zoomComponent.getId());
		this.vImageLayout = FluentUI.vertical()
			.sizeFull()
			.add(zoomComponent, Alignment.MIDDLE_CENTER)
			.get();

		final HorizontalLayout wrapperHlayout = FluentUI.horizontal()
			.height(500, Unit.PIXELS)
			.add(this.vImageLayout, Alignment.MIDDLE_CENTER)
			.get();

		final boolean singleImage = urlList.size() == 1;

		this.thumbImageList = new LazyVerticalScrollPanel(610, 200, 200);

		for (int i = 0; i < this.urls.size(); i++) {
			final int index = i;

			final LazyObject lo = new AbstractLazyObject("", i, this.thumbImageList) {

				@Override
				public String getPreviousImageUrl() {
					return null; // only display arrows
				}

				@Override

				public String getNextImageUrl() {
					return null; // only display arrows
				}

				@Override
				public void generateContent() {

					final Image image = new Image("", new ExternalResource(ImageBrowser.this.urls.get(index)
						.getImageURL(ImageSize.SMALL)));
					image.setStyleName(DSuiteTheme.BP_IMAGEBORWSER_ASPECT_RATIO);
					getBaseLayout().addComponent(FluentUI.horizontal()
						.sizeFull()
						.add(image, Alignment.MIDDLE_CENTER)
						.get());

				}
			};

			this.imageObjectMap.put(this.urls.get(i), lo);

			this.thumbImageList.addLazyObject(lo);
		}

		this.thumbImageList.addLazyObjectSelectedListener(new LazyObjectSelectedListener() {

			private static final long serialVersionUID = 4524106136439831629L;

			@Override
			public void lazyObjectSelected(final LazyObjectSelectedEvent event) {
				ImageBrowser.this.multiSizeImage = ImageBrowser.this.urls.get((int) event.getLazyObject()
					.getIdentifier());
				if (zoomType.equals(ZoomType.SLIDERZOOM) || zoomType.equals(ZoomType.NONE)) {
					ImageBrowser.this.mainImage
						.setSource(new ExternalResource(ImageBrowser.this.multiSizeImage.getImageURL(ImageSize.LARGE)));
					ImageBrowser.this.imageCaptionLabel.setValue(ImageBrowser.this.multiSizeImage.getCaption());

				} else {
					ImageBrowser.this.magnifier
						.setImageUrl(ImageBrowser.this.multiSizeImage.getImageURL(ImageSize.MEDUIM));
					ImageBrowser.this.magnifier
						.setZoomImageUrl(ImageBrowser.this.multiSizeImage.getImageURL(ImageSize.LARGE));
					ImageBrowser.this.imageCaptionLabel.setValue(ImageBrowser.this.multiSizeImage.getCaption());
				}

				resetSlider();
			}

		});

		if (singleImage) {
			this.imageLayout = FluentUI.vertical()
				.sizeFull()
				.style(DSuiteTheme.BP_IMAGE_BROWSER_WRAP)
				.add(wrapperHlayout, Alignment.MIDDLE_CENTER)
				.get();
		} else {
			this.imageLayout = FluentUI.vertical()
				.style(DSuiteTheme.BP_IMAGE_BROWSER_WRAP)
				.add(wrapperHlayout, Alignment.MIDDLE_CENTER)
				.get();
		}

		sliderZoomToggle();

		setContent(FluentUI.vertical()
			.style("content-margin")
			.spacing(true)
			.margin(true, false, false, false)
			.add(this.sliderLayout, 1, Alignment.MIDDLE_RIGHT)
			.add(FluentUI.horizontal()
				.sizeFull()
				.add(this.thumbImageList, 2)
				.add(this.imageLayout, 7, Alignment.MIDDLE_CENTER)
				.get(), 10)
			.add(FluentUI.horizontal()
				.sizeFull()
				.add(new Label(), 2)
				.add(this.thumbImageList.getPreviousNavigator(), 1, Alignment.MIDDLE_CENTER)
				.add(this.imageCaptionLabel, 5, Alignment.MIDDLE_CENTER)
				.add(this.thumbImageList.getNextNavigator(), 1, Alignment.MIDDLE_CENTER)
				.get(), 1)
			.get());

		if (this.zoomType.equals(ZoomType.SLIDERZOOM)) {
			enableZoom(true);
			setHeight(ImageBrowser.WINDOW_HEIGTH_SLIDER, Unit.PIXELS);
		} else {
			enableZoom(false);
			// since the slider bar is removed
			setHeight(ImageBrowser.WINDOW_HEIGTH, Unit.PIXELS);
		}
		center();

		setWidth(ImageBrowser.WINDOW_WIDTH, Unit.PIXELS);
		setResizable(false);
		ImageBrowser.registerSessionSingleton(this);
		addCloseShortcut(KeyCode.ESCAPE);
		addAttachListener(event -> focus());
	}

	/**
	 * toggles the zoom and magnifier features
	 *
	 * @param urlList
	 * @return
	 */
	private Component toggleZoomOption(final List<MultiSizeImage> urlList) {
		if (this.zoomType.equals(ZoomType.SLIDERZOOM) || this.zoomType.equals(ZoomType.NONE)) {
			this.mainImage = new Image("", new ExternalResource(urlList.get(0)
				.getImageURL(ImageSize.LARGE)));
			this.mainImage.setId(urlList.get(0)
				.getCaption());
			setMaxImgSize((int) ImageBrowser.IMAGEBROWSE_MIN.doubleValue());
			return this.mainImage;
		} else {

			this.magnifier = new Magnifier();
			this.magnifier.setId(urlList.get(0)
				.getCaption());
			// set shown Image
			this.magnifier.setImageUrl(urlList.get(0)
				.getImageURL(ImageSize.MEDUIM));
			// OPTIONAL: Set the ZoomImageUrl
			this.magnifier.setZoomImageUrl(urlList.get(0)
				.getImageURL(ImageSize.LARGE));
			// Set the ZoomFactor
			this.magnifier.setZoomFactor(1.5f);
			setMagnifiedImageHeight((int) ImageBrowser.IMAGEBROWSE_MIN.doubleValue());
			return this.magnifier;
		}

	}

	/**
	 * sets up the slider layouts
	 */
	private void sliderZoomToggle() {
		this.sizeLabel = FluentUI.label()
			.get();
		this.sizeLabel.setValue(ImageBrowser.IMAGEBROWSE_MIN.intValue() + "px");
		this.imgSizeSlider = new Slider(null);
		this.imgSizeSlider.setStyleName(DSuiteTheme.BP_SLIDER_CURSOR);
		this.imgSizeSlider.setImmediate(true);
		this.imgSizeSlider.setMax(ImageBrowser.IMAGEBROWSE_MAX);
		this.imgSizeSlider.setMin(ImageBrowser.IMAGEBROWSE_MIN);
		this.imgSizeSlider.setWidth(ImageBrowser.SLIDER_WIDTH, Unit.PIXELS);
		this.imgSizeSlider.addValueChangeListener(event -> {
			final int size = (int) Math.round(this.imgSizeSlider.getValue());
			setMaxImgSize(size);
			this.sizeLabel.setValue(size + "px");

		});

		this.sliderLayout = FluentUI.horizontal()
			.add(this.sizeLabel)
			.add(this.imgSizeSlider)
			.get();
	}

	/**
	 * sets the window caption to the desired one
	 *
	 * @param windowCaption
	 *
	 */
	public void setWindowCaption(final String windowCaption) {
		this.setCaption(windowCaption);
	}

	/*
	 * Enables or disables the zoom feature
	 */
	public void enableZoom(final boolean enabled) {
		if (!enabled) {
			this.sliderLayout.setVisible(false);
			this.sizeLabel.setValue("");
		} else {
			this.sliderLayout.setVisible(true);
		}
	}

	/**
	 * @param maxZoom
	 */
	public void maxZoomSize(final Double maxZoom) {
		if (maxZoom != null) {
			this.imgSizeSlider.setMax(maxZoom);
		} else {
			/* set the predefined max size */
			this.imgSizeSlider.setMax(ImageBrowser.IMAGEBROWSE_MAX);
		}
	}

	/**
	 * @param maxHeight
	 */
	public void setMaxImgSize(final int maxHeight) {
		this.mainImage.setHeight(maxHeight, Unit.PIXELS);
	}

	/**
	 * @param maxHeight
	 */
	public void setMagnifiedImageHeight(final int maxHeight) {
		this.magnifier.setHeight(maxHeight, Unit.PIXELS);
	}

	/**
	 * @param image
	 */
	public void setSelection(final MultiSizeImage image) {
		if (this.imageObjectMap.containsKey(image)) {
			this.thumbImageList.selectLazyObject(this.imageObjectMap.get(image));
		}
	}

	private static void closeSessionSingleton() {
		final Object obj = VaadinService.getCurrentRequest()
			.getWrappedSession()
			.getAttribute(ImageBrowser.SESSION_ATTRIBUTE_NAME);

		if (obj == null) {
			return;
		}

		if (!(obj instanceof ImageBrowser)) {
			ImageBrowser.LOGGER.warn("Session singleton is not an instance of ImageBrowser");
		}

		final ImageBrowser imgBrowser = (ImageBrowser) obj;
		if (imgBrowser.isAttached()) {
			ImageBrowser.LOGGER.debug("Removing imageBrowser " + obj + " from session");
			imgBrowser.close();
			VaadinService.getCurrentRequest()
				.getWrappedSession()
				.setAttribute(ImageBrowser.SESSION_ATTRIBUTE_NAME, null);
		}
	}

	private static void registerSessionSingleton(final ImageBrowser imageBrowser) {
		ImageBrowser.closeSessionSingleton();

		ImageBrowser.LOGGER.debug("Adding imageBrowser " + imageBrowser + " to session");
		VaadinService.getCurrentRequest()
			.getWrappedSession()
			.setAttribute(ImageBrowser.SESSION_ATTRIBUTE_NAME, imageBrowser);

		imageBrowser.addCloseListener(event -> ImageBrowser.closeSessionSingleton());
	}

	private void resetSlider() {
		this.imgSizeSlider.clear();
	}
}
