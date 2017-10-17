/**
 * 
 */
package de.bonprix.vaadin.model;

/**
 * A MultiSizeImage is an image object which provides multiple sizes/resolutions of an image.
 * 
 * @author cthiel
 * 
 */
public interface MultiSizeImage {

	/**
	 * Defines the custom size class of an image. The real resolution and
	 * boundaries of the images are not specified here but left to be defined by
	 * the image.
	 * 
	 * @author cthiel
	 * 
	 */
	public static enum ImageSize {
		SMALL, MEDUIM, LARGE;
	}

	/**
	 * Generates the image url based on the given size.
	 * @param size the size
	 * @return the image url
	 */
	String getImageURL(ImageSize size);

	/**
	 * Returns the caption of the image.
	 * @return the caption
	 */
	String getCaption();
}
