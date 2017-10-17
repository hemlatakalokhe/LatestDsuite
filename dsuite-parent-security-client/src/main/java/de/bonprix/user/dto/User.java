package de.bonprix.user.dto;

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * Basic Principal informations
 *
 *
 * @author Niels Schelbach
 *
 */
@GeneratePojoBuilder(intoPackage = "*.builder")
@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
	@XmlAttribute(name = "key")
	private String authorizationKey;
	@XmlAttribute(name = "id")
	private Long id;
	@XmlAttribute(name = "username")
	private String username;
	@XmlAttribute(name = "fullname")
	private String fullname;
	@XmlAttribute(name = "languageId")
	private Long languageId;
	@XmlAttribute(name = "languageCode")
	private String languageCode;
	@XmlAttribute(name = "clientId")
	private long clientId;
	@XmlElement(name = "Role")
	@XmlElementWrapper(name = "Roles")
	private Set<Role> roles = new HashSet<>();
	@XmlElement(name = "Group")
	@XmlElementWrapper(name = "Groups")
	private Set<Group> groups = new HashSet<>();
	@XmlElement(name = "Capability")
	@XmlElementWrapper(name = "Capabilities")
	private Set<Capability> capabilities = new HashSet<>();

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(final Set<Role> roles) {
		this.roles = roles;
	}

	public String getAuthorizationKey() {
		return this.authorizationKey;
	}

	public void setAuthorizationKey(final String authorizationKey) {
		this.authorizationKey = authorizationKey;
	}

	public Long getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getLanguageCode() {
		return this.languageCode;
	}

	public void setLanguageCode(final String languageCode) {
		this.languageCode = languageCode;
	}

	public long getClientId() {
		return this.clientId;
	}

	public void setClientId(final long clientId) {
		this.clientId = clientId;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(final String fullname) {
		this.fullname = fullname;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Set<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(final Set<Group> groups) {
		this.groups = groups;
	}

	public Set<Capability> getCapabilities() {
		return this.capabilities;
	}

	public void setCapabilities(final Set<Capability> capabilities) {
		this.capabilities = capabilities;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (this.clientId ^ (this.clientId >>> 32));
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if (this.clientId != other.clientId) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "User [authorizationKey=" + this.authorizationKey + ", id=" + this.id + ", username=" + this.username
				+ ", fullName=" + this.fullname + ", clientId=" + this.clientId + ", roles=" + this.roles + ", groups="
				+ this.groups + "]";
	}
}