package de.bonprix.vaadin.fluentui;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;

import de.bonprix.security.PrincipalSecurityContext;
import de.bonprix.user.dto.PermissionType;

/**
 * @author a.solanki
 * @param <CONFIG>
 */
public class TabSheets<CONFIG extends TabSheets<?>> extends Components<TabSheet, CONFIG> {

	protected TabSheets(final TabSheet component) {
		super(component);
	}

	public CONFIG add(final Tab tab) {
		return add(tab, null);
	}

	/**
	 * Adding a simple tab along with capability key to resolve its access.
	 * 
	 * @param tab
	 * @param capabilityKey
	 */
	@SuppressWarnings("unchecked")
	public CONFIG add(final Tab tab, final String capabilityKey) {
		final Tab internalTab = get().addTab(tab.getComponent());
		internalTab.setId(tab.getId());
		internalTab.setCaption(tab.getCaption());
		internalTab.setStyleName(tab.getStyleName());
		internalTab.setDescription(tab.getDescription());
		internalTab.setEnabled(tab.isEnabled());
		internalTab.setVisible(tab.isVisible());
		internalTab.setClosable(tab.isClosable());
		internalTab.setComponentError(tab.getComponentError());
		internalTab.setIcon(tab.getIcon());
		internalTab.setIconAlternateText(tab.getIconAlternateText());
		internalTab.setDefaultFocusComponent(tab.getDefaultFocusComponent());

		if (StringUtils.isEmpty(capabilityKey)) {
			return (CONFIG) this;
		}

		if (PrincipalSecurityContext.hasPermission(capabilityKey, PermissionType.NONE)) {
			internalTab.setVisible(false);
			return (CONFIG) this;
		}
		if (!PrincipalSecurityContext.hasPermission(capabilityKey, PermissionType.EDIT)) {
			internalTab.setEnabled(false);
			return (CONFIG) this;
		}
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG onSelectedTab(final SelectedTabChangeListener listener) {
		get().addSelectedTabChangeListener(listener);
		return (CONFIG) this;
	}

}