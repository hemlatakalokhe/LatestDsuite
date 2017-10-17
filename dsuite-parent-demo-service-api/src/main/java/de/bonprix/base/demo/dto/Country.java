package de.bonprix.base.demo.dto;

import de.bonprix.dto.Entity;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage= "*.builder")
public class Country extends Entity
{
	
	private String isoCode;

	private String name;


	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return  name ;
	}
	

}
