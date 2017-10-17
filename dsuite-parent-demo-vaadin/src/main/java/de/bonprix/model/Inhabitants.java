package de.bonprix.model;

import java.util.Date;
import java.util.Set;

import com.google.gwt.i18n.server.testing.Gender;
import com.vaadin.server.Resource;

import de.bonprix.dto.NamedEntity;
import de.bonprix.model.enums.GridStatus;

public class Inhabitants {
	private long id;
	private Gender gender;
	private String name;
	private double bodySize;
	private Date birthday;
	private Date dateTime;
	private boolean onFacebook;
	private Country country;
	private Set<Country> visitedCountries;
	private String imageUrl;
	private GridStatus status;
	private Resource icon;
	private boolean checked;

	public Inhabitants() {
		super();
	}

	public Inhabitants(final long id, final Gender gender) {
		super();
		this.id = id;
		this.gender = gender;
	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(final Gender gender) {
		this.gender = gender;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public double getBodySize() {
		return this.bodySize;
	}

	public void setBodySize(final double bodySize) {
		this.bodySize = bodySize;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(final Date birthday) {
		this.birthday = birthday;
	}

	public boolean isOnFacebook() {
		return this.onFacebook;
	}

	public void setOnFacebook(final boolean onFacebook) {
		this.onFacebook = onFacebook;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(final Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Inhabitants [id=" + this.id + ", name=" + this.name + ", birthday=" + this.birthday + ", onFacebook="
				+ this.onFacebook + ", country=" + this.country + ", visitedCountries=" + this.visitedCountries
				+ ", status=" + this.status + "]";
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public GridStatus getStatus() {
		return this.status;
	}

	public void setStatus(GridStatus status) {
		this.status = status;
	}

	public Set<Country> getVisitedCountries() {
		return this.visitedCountries;
	}

	public void setVisitedCountries(Set<Country> visitedCountries) {
		this.visitedCountries = visitedCountries;
	}

	public static class Country extends NamedEntity {
		public Country(Long id, String name) {
			super(id, name);
		}
	}

	public Resource getIcon() {
		return this.icon;
	}

	public void setIcon(Resource icon) {
		this.icon = icon;
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public boolean isChecked() {
		return this.checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
