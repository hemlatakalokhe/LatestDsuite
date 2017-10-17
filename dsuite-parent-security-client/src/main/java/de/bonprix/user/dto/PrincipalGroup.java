package de.bonprix.user.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class PrincipalGroup implements Serializable {
	private static final long serialVersionUID = -1127207672666366806L;
	@XmlAttribute(name = "id")
	private final Long id;
	@XmlAttribute(name = "name")
	private final String name;

	public PrincipalGroup(final Long id, final String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final PrincipalGroup other = (PrincipalGroup) obj;
		return Objects.equals(this.id, other.id);
	}

	@Override
	public String toString() {
		return "PrincipalGroup [id=" + this.id + ", name=" + this.name + "]";
	}
}