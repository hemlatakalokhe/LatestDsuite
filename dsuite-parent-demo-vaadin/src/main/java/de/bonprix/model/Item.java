package de.bonprix.model;

/**
 * @author tmeissne
 *
 */
public class Item {
	private String itemNo;
	private String assetUrl;

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(final String itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * @return the assetUrl
	 */
	public String getAssetUrl() {
		return this.assetUrl;
	}

	/**
	 * @param assetUrl
	 *            the assetUrl to set
	 */
	public void setAssetUrl(final String assetUrl) {
		this.assetUrl = assetUrl;
	}
}
