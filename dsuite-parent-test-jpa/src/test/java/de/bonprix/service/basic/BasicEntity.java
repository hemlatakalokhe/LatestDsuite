package de.bonprix.service.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import de.bonprix.dto.HasOptlock;
import de.bonprix.service.BasicTableDtoConverter;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@Entity
@Table(name = "BASIC", schema = "TEST")
@SequenceGenerator(schema = "TEST", name = "SEQ_BASIC", sequenceName = "TEST.SEQ_BASIC", allocationSize = 1)
public class BasicEntity implements BasicTableDtoConverter<Basic>, HasOptlock {

	@Id
	@GeneratedValue(generator = "SEQ_BASIC", strategy = GenerationType.SEQUENCE)
	@Column(name = "BASIC_ID")
	private Long id;

	@Column(name = "PROPERTY")
	private Long property;

	@Version
	@Column(name = "OPTLOCK", nullable = false, columnDefinition = "Number(18) default '0'")
	private Long optlock;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	public Long getProperty() {
		return this.property;
	}

	public void setProperty(Long property) {
		this.property = property;
	}

	@Override
	public Long getOptlock() {
		return this.optlock;
	}

	@Override
	public void setOptlock(final Long optlock) {
		this.optlock = optlock;
	}

	@Override
	public void updateDto(Basic containerDto) {
		containerDto.setProperty(getProperty());
	}

	@Override
	public void updateEntity(Basic containerDto) {
		setProperty(containerDto.getProperty());
	}

}
