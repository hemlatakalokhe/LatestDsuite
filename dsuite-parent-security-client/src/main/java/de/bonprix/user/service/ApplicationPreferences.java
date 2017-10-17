package de.bonprix.user.service;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@XmlRootElement(name = "ApplicationPreferences")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicationPreferences {

	@XmlAttribute(name = "applicationId")
	private Long applicationId;

	@XmlAttribute(name = "principalId")
	private Long principalId;

	@XmlAttribute(name = "clientId")
	private Long clientId;

	@XmlElement(name = "keyValue")
	@XmlElementWrapper(name = "keyValues")
	private Map<String, String> keyValues = new HashMap<String, String>();

	public Long getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(final Long applicationId) {
		this.applicationId = applicationId;
	}

	public Map<String, String> getKeyValues() {
		return this.keyValues;
	}

	public void setKeyValues(final Map<String, String> keyValues) {
		this.keyValues = keyValues;
	}

	public Long getPrincipalId() {
		return this.principalId;
	}

	public void setPrincipalId(final Long principalId) {
		this.principalId = principalId;
	}

	public Long getClientId() {
		return this.clientId;
	}

	public void setClientId(final Long clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "ApplicationPreferences [applicationId=" + this.applicationId + ", principalId=" + this.principalId
				+ ", clientId=" + this.clientId + ", keyValues=" + this.keyValues + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.applicationId == null) ? 0 : this.applicationId.hashCode());
		result = prime * result + ((this.clientId == null) ? 0 : this.clientId.hashCode());
		result = prime * result + ((this.keyValues == null) ? 0 : this.keyValues.hashCode());
		result = prime * result + ((this.principalId == null) ? 0 : this.principalId.hashCode());
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
		final ApplicationPreferences other = (ApplicationPreferences) obj;
		if (this.applicationId == null) {
			if (other.applicationId != null) {
				return false;
			}
		} else if (!this.applicationId.equals(other.applicationId)) {
			return false;
		}
		if (this.clientId == null) {
			if (other.clientId != null) {
				return false;
			}
		} else if (!this.clientId.equals(other.clientId)) {
			return false;
		}
		if (this.keyValues == null) {
			if (other.keyValues != null) {
				return false;
			}
		} else if (!this.keyValues.equals(other.keyValues)) {
			return false;
		}
		if (this.principalId == null) {
			if (other.principalId != null) {
				return false;
			}
		} else if (!this.principalId.equals(other.principalId)) {
			return false;
		}
		return true;
	}
}