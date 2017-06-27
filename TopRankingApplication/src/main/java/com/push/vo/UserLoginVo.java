package com.push.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "SYS.USER_LOGIN")  
public class UserLoginVo {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="LAST_LOGIN_TIME")
	private Timestamp lastLoginTIme;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Timestamp getLastLoginTIme() {
		return lastLoginTIme;
	}
	public void setLastLoginTIme(Timestamp lastLoginTIme) {
		this.lastLoginTIme = lastLoginTIme;
	}

}
