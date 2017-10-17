package de.bonprix.base.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@Entity
@SequenceGenerator(schema = "DS_GLOBAL",
name = "SEQ_MD_APPLICATION",
sequenceName = "DS_GLOBAL.SEQ_MD_APPLICATION",
allocationSize = 1)
@Table(name = "sizes",schema = "DS_GLOBAL")
public class SizeEntity {
	
	@GeneratedValue(generator = "SEQ_MD_APPLICATION",
			strategy = GenerationType.SEQUENCE)
	@Column(name = "size_id")
	@Id
	private Long sizeId;
	@Column(name="size_code")
	private String sizeCode;
		
	public Long getSizeId() {
		return sizeId;
	}
	public void setSizeId(Long sizeId) {
		this.sizeId = sizeId;
	}
	public String getSizeCode() {
		return sizeCode;
	}
	public void setSizeCode(String sizeCode) {
		this.sizeCode = sizeCode;
	}
	
	@Override
	public String toString() {
		return sizeCode ;
	}
	
	
	
	
}
