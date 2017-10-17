package de.bonprix.model.enums;

import de.bonprix.I18N;
import de.bonprix.dto.NamedObject;

/**
 * @author cthiel
 *
 */
public enum OrderStatus implements NamedObject {
	CREATED(2, "orderStatus.created", "icons-sonstige/ds-04-01-angelegt/png/ds-04-01-angelegt_dklblau.png",
			true), ASSIGNED(3, "orderStatus.assigned",
					"icons-sonstige/ds-04-01-in-bearbeitung/png/ds-04-01-in-bearbeitung_dklblau.png",
					true), DELIVERED(4, "orderStatus.delivered",
							"icons-sonstige/ds-04-01-freizugeben/png/ds-04-01-freizugeben.png",
							false), CORRECTION_ASSIGNED(5, "orderStatus.correction",
									"icons-sonstige/ds-04-01-in-korrektur/png/ds-04-01-in-korrektur_dklblau.png",
									true), RELEASED(6, "orderStatus.released",
											"icons-sonstige/ds-04-01-ok/png/ds-04-01-ok.png", false), PUBLISHED(8,
													"orderStatus.published",
													"icons-sonstige/ds-04-01-veroeffentlicht/png/ds-04-01-veroeffentlicht_dklblau.png",
													false), DECLINED(9, "orderStatus.declined",
															"icons-sonstige/ds-04-01-abgelehnt/png/ds-04-01-abgelehnt_dklblau.png",
															false), CANCELLED(10, "orderStatus.cancelled",
																	"icons-sonstige/ds-04-01-storniert/png/ds-04-01-storniert.png",
																	false), SUBMITTED(13, "orderStatus.submitted", "",
																			false), FINISHED(15, "orderStatus.finished",
																					"flag_finish", false), ERROR(99,
																							"orderStatus.error",
																							"icons-sonstige/ds-04-01-fehler/png/ds-04-01-fehler.png",
																							false), ORDER_DELIVERY(101,
																									"orderStatus.delivery",
																									"icons-sonstige/ds-04-01-lieferungsvorbereitung/png/ds-04-01-lieferungsvorbereitung.png",
																									false), CORRECTION_DELIVERY(
																											102,
																											"orderStatus.correction_delivery",
																											"icons-sonstige/ds-04-01-veroeffentlicht/png/ds-04-01-veroeffentlicht_dklblau.png",
																											false),;
	private final long statusId;
	private final String nameKey;
	private final String iconName;
	private final boolean isEditable;

	private OrderStatus(final long statusId, final String nameKey, final String iconName, final boolean isEditable) {
		this.statusId = statusId;
		this.nameKey = nameKey;
		this.iconName = iconName;
		this.isEditable = isEditable;
	}

	@Override
	public Long getId() {
		return this.statusId;
	}

	@Override
	public String getName() {
		return I18N.get(this.nameKey);
	}

	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Returns the icon name for this order status.
	 *
	 * @return the icon
	 */
	public String getIconName() {
		return "https://digistyle.bonprix.net/dsuite-icons/" + this.iconName;
	}

	/**
	 * Returns true if orders of this type are editable by the user.
	 *
	 * @return if the order type is editable
	 */
	public boolean isEditable() {
		return this.isEditable;
	}

	/**
	 * Finds the orderStatus with the given ID or returns null if not found.
	 *
	 * @param id
	 * @return the order status with the given id
	 */
	public static OrderStatus findById(final long id) {
		for (final OrderStatus status : OrderStatus.values()) {
			if (status.getId() == id) {
				return status;
			}
		}
		return null;
	}
}