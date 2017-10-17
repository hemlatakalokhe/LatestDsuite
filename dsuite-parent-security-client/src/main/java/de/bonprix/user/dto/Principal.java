package de.bonprix.user.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@SuppressWarnings("serial")
public class Principal implements Serializable {
	private Long id;
	private String name;
	private String fullname;
	private String languageCode;
	private Long languageId;
	private Long clientId;
	private final Set<PrincipalRole> principalRoles = new HashSet<>();
	@XmlElementWrapper(name = "PrincipalGroups")
	@XmlElement(name = "PrincipalGroup")
	private final Set<PrincipalGroup> principalGroups = new HashSet<>();
	private final Map<String, PermissionType> capabilityMap = new TreeMap<>();

	public Principal(final Long id, final String name, final String fullname) {
		this.id = id;
		this.name = name;
		this.fullname = fullname;
	}

	public Principal(final Long id, final String name) {
		this.id = id;
		this.name = name;
		this.fullname = name;
	}

	public Principal(final String name) {
		this.name = name;
		this.fullname = name;
	}

	public Principal() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(final String fullname) {
		this.fullname = fullname;
	}

	public Set<PrincipalRole> getPrincipalRoles() {
		return this.principalRoles;
	}

	public void addRole(final PrincipalRole principalRole) {
		this.principalRoles.add(principalRole);
	}

	/**
	 * Returns true if this principal has the given role. Otherwise returns
	 * false.
	 *
	 * @param principalRole
	 *            the role to check
	 * @return if the principal has the given role
	 */
	public boolean hasRole(final PrincipalRole principalRole) {
		return this.principalRoles.contains(principalRole);
	}

	/**
	 * Returns true if (and only if) this principal has all the given roles.
	 * Otherwise returns false.
	 *
	 * @param roles
	 *            the roles to check
	 * @return if the principal has all the given roles
	 */
	public boolean hasAllRoles(final PrincipalRole... roles) {
		for (final PrincipalRole role : roles) {
			if (!this.principalRoles.contains(role)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns true if (and only if) this principal has any of the given roles.
	 * Otherwise returns false.
	 *
	 * @param roles
	 *            the roles to check
	 * @return if the principal has any the given roles
	 */
	public boolean hasAnyRoles(final PrincipalRole... roles) {
		for (final PrincipalRole role : roles) {
			if (this.principalRoles.contains(role)) {
				return true;
			}
		}
		return false;
	}

	public Set<PrincipalGroup> getPrincipalGroups() {
		return this.principalGroups;
	}

	public void addGroup(final PrincipalGroup group) {
		this.principalGroups.add(group);
	}

	public Long getClientId() {
		return this.clientId;
	}

	public void setClientId(final Long clientId) {
		this.clientId = clientId;
	}

	/**
	 * convert's languageCode to Locale
	 *
	 * @return null when not found or not possible to detect from string
	 */
	public Locale getLocale() {
		if (getLanguageCode() != null) {
			final Locale locale = Locale.forLanguageTag(getLanguageCode().toLowerCase());
			// when not found it will still return a filled locale object with
			// language and country = ""
			if (locale != null && locale.getLanguage() != null && locale.getLanguage()
				.length() > 0) {
				return locale;
			} else {
				return null;
			}
		}
		return null;
	}

	public String getLanguageCode() {
		return this.languageCode;
	}

	public void setLanguageCode(final String languageCode) {
		this.languageCode = languageCode;
	}

	public Map<String, PermissionType> getCapabilityMap() {
		return this.capabilityMap;
	}

	public void addCapability(final String capabilityKey, final PermissionType permissionType) {
		this.capabilityMap.put(capabilityKey, permissionType);
	}

	public Boolean hasCapability(final String capabilityKey) {
		return this.capabilityMap.containsKey(capabilityKey);
	}

	/**
	 * @param capabilityKey
	 * @return If a particular capability doesn't exist give the lease
	 *         privilege.
	 */
	public PermissionType getPermissionType(final String capabilityKey) {
		return this.capabilityMap.containsKey(capabilityKey) ? this.capabilityMap.get(capabilityKey)
				: PermissionType.NONE;
	}

	@Override
	public String toString() {
		return "Principal [id=" + this.id + ", name=" + this.name + ", fullname=" + this.fullname + ", languageCode="
				+ this.languageCode + ", clientId=" + this.clientId + ", principalRoles=" + this.principalRoles
				+ ", principalGroups=" + this.principalGroups + "]";
	}

	public Long getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
}