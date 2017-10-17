package de.bonprix.base.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")

@Entity
@Table(name = "clients",schema = "DS_GLOBAL")
public class ClientEntity {
	@Id
	@Column(name = "client_id")
	private Long id;

	@Column(name = "client_name")
	private String clientName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Override
	public String toString() {
		return clientName;
	}
	
}
