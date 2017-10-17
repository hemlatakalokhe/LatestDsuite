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
@Table(name = "userdata", schema = "DS_GLOBAL")
public class UserDataEntity {

	@Override
	public String toString() {
		return "UserDataEntity [id=" + id + ", mobileNo=" + mobileNo + ", name=" + name + ", email=" + email
				+ ", password=" + password + "]";
	}
	@Id
	@GeneratedValue(generator = "SEQ_MD_APPLICATION",
	strategy = GenerationType.SEQUENCE)
	@Column(name="id")
	private long id;
	
	@Column(name="mobileNo")
	private long mobileNo;
	
	@Column(name="username")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password ;
	
	@Column(name="confirm_password")
	private String cpassword ;
	
	public String getCpassword() {
		return cpassword;
	}
	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
