/**
 *
 */
package de.bonprix.model.enums;

import de.bonprix.dto.NamedObject;
import de.bonprix.vaadin.FontBonprix;
import de.bonprix.vaadin.FontIconColor;
import de.bonprix.vaadin.IconSize;

/**
 * @author tmeissne
 *
 */
public enum GridStatus implements NamedObject {

	CREATED(1, FontBonprix.DOT.getPngUrl(IconSize.SIZE_16, FontIconColor.BLUE)), RELEASED(2,
			FontBonprix.DOT.getPngUrl(IconSize.SIZE_16, FontIconColor.GREEN)), CANCELLED(3,
					FontBonprix.DOT.getPngUrl(IconSize.SIZE_16, FontIconColor.RED));

	private final long statusId;
	private final String iconName;

	private GridStatus(final long statusId, final String iconName) {
		this.statusId = statusId;
		this.iconName = iconName;
	}

	@Override
	public Long getId() {
		return this.statusId;
	}

	/**
	 * Returns the icon name for this order status.
	 *
	 * @return the icon
	 */
	public String getIconName() {
		return this.iconName;
	}

	/**
	 * Finds the orderStatus with the given ID or returns null if not found.
	 *
	 * @param id
	 * @return the order status with the given id
	 */
	public static GridStatus findById(final long id) {
		for (final GridStatus status : GridStatus.values()) {
			if (status.getId() == id) {
				return status;
			}
		}

		return null;
	}

	@Override
	public String getName() {
		return null;
	}

}
