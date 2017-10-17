package de.bonprix.demo.inMemoryDbAwareUnitTest;

import java.time.ZonedDateTime;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author vbaghdas
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "demoOrders", schema = "ds_prototype")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "orderCache")
public class DemoOrder {
	@Version
	@Column(name = "version")
	private Long version;

	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private Long id;
	@Column(name = "order_name")
	private String name;
	@Column(name = "order_desc")
	private String description;

	@Column(name = "order_date")
	private ZonedDateTime orderDate;
	@Column(name = "delivery_date")
	private ZonedDateTime deliveryDate;
	@Column(name = "delivery_date_plan")
	private ZonedDateTime deliveryDatePlan;

	@Column(name = "spread_id")
	private Long spreadId;

	@Column(name = "primary_taskowner_id")
	private Long primaryTaskownerId;

	@Column(name = "primary_taskowner_resp_id")
	private Long primaryTaskownerResponsibleId;

	@Column(name = "secondary_taskowner_id")
	private Long secondaryTaskownerId;

	@Column(name = "secondary_taskowner_resp_id")
	private Long secondaryTaskownerResponsibleId;

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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Long getSpreadId() {
		return this.spreadId;
	}

	public void setSpreadId(final Long spreadId) {
		this.spreadId = spreadId;
	}

	public ZonedDateTime getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(final ZonedDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public ZonedDateTime getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(final ZonedDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public ZonedDateTime getDeliveryDatePlan() {
		return this.deliveryDatePlan;
	}

	public void setDeliveryDatePlan(final ZonedDateTime deliveryDatePlan) {
		this.deliveryDatePlan = deliveryDatePlan;
	}

	public Long getPrimaryTaskownerId() {
		return this.primaryTaskownerId;
	}

	public void setPrimaryTaskownerId(final Long primaryTaskownerId) {
		this.primaryTaskownerId = primaryTaskownerId;
	}

	public Long getPrimaryTaskownerResponsibleId() {
		return this.primaryTaskownerResponsibleId;
	}

	public void setPrimaryTaskownerResponsibleId(final Long primaryTaskownerResponsibleId) {
		this.primaryTaskownerResponsibleId = primaryTaskownerResponsibleId;
	}

	public Long getSecondaryTaskownerId() {
		return this.secondaryTaskownerId;
	}

	public void setSecondaryTaskownerId(final Long secondaryTaskownerId) {
		this.secondaryTaskownerId = secondaryTaskownerId;
	}

	public Long getSecondaryTaskownerResponsibleId() {
		return this.secondaryTaskownerResponsibleId;
	}

	public void setSecondaryTaskownerResponsibleId(final Long secondaryTaskownerResponsibleId) {
		this.secondaryTaskownerResponsibleId = secondaryTaskownerResponsibleId;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(final Long version) {
		this.version = version;
	}
}
