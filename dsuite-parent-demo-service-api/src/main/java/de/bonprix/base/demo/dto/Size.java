package de.bonprix.base.demo.dto;

import de.bonprix.dto.Entity;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage= "*.builder")
public class Size extends Entity{
	

	private String sizeCode;
		
	
	public String getSizeCode() {
		return sizeCode;
	}
	public void setSizeCode(String sizeCode) {
		this.sizeCode = sizeCode;
	}
	@Override
	public String toString() {
		return sizeCode;
	}
	
	

	
}
