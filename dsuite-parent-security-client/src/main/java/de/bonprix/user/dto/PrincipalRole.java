package de.bonprix.user.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class PrincipalRole implements Serializable {
	private static final long serialVersionUID = 7727599571298745513L;
	@XmlAttribute(name = "id")
	private Long id;
	@XmlAttribute(name = "name")
	private String name;

	public PrincipalRole() {
	}

	public PrincipalRole(final Long id) {
		this.id = id;
	}

	public PrincipalRole(final Long id, final String name) {
		this.id = id;
		this.name = name;
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
		final PrincipalRole other = (PrincipalRole) obj;
		return Objects.equals(this.id, other.id);
	}

	@Override
	public String toString() {
		return "PrincipalRole [id=" + this.id + ", name=" + this.name + "]";
	}
}