package de.bonprix.user.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@XmlAccessorType(XmlAccessType.FIELD)
public class Capability {
	@XmlAttribute
	private Long applicationId;
	@XmlAttribute
	private String capabilityKey;
	@XmlAttribute
	private PermissionType permissionType;

	public PermissionType getPermissionType() {
		return this.permissionType;
	}

	public void setPermissionType(final PermissionType permissionType) {
		this.permissionType = permissionType;
	}

	public Long getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(final Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getCapabilityKey() {
		return this.capabilityKey;
	}

	public void setCapabilityKey(final String capabilityKey) {
		this.capabilityKey = capabilityKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.applicationId == null) ? 0 : this.applicationId.hashCode());
		result = prime * result + ((this.capabilityKey == null) ? 0 : this.capabilityKey.hashCode());
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
		final Capability other = (Capability) obj;
		if (this.applicationId == null) {
			if (other.applicationId != null) {
				return false;
			}
		} else if (!this.applicationId.equals(other.applicationId)) {
			return false;
		}
		if (this.capabilityKey == null) {
			if (other.capabilityKey != null) {
				return false;
			}
		} else if (!this.capabilityKey.equals(other.capabilityKey)) {
			return false;
		}
		return true;
	}
}