/**
 * 
 */
package de.bonprix.vaadin.model;

/**
 * @author cthiel
 *
 */
public class SimpleMultiSizeImage implements MultiSizeImage {
	
	private final String caption;
	private final String url;

	public SimpleMultiSizeImage(String caption, String url) {
		super();
		this.caption = caption;
		this.url = url;
	}

	@Override
	public String getImageURL(ImageSize size) {
		return url;
	}

	@Override
	public String getCaption() {
		return caption;
	}

}
