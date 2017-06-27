package com.push.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "SYS.USER_DETAILS")  
public class UserDetailsVo {
	
	@Id
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="EMAIL_ID")
	private String emailId;
	
	@Column(name="PHONE_NUMBER")
	private BigDecimal number;
	
	@Column(name="COUNTRY")
	private String country;
	
	@Column(name="SEQUERY_QUESTION")
	private String sequerityQuestion;
	
	@Column(name="SEQUERY_ANSWER")
	private String sequerityAnswer;

	@Column(name="CREATED_TIME")
	private Timestamp createdTime;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSequerityQuestion() {
		return sequerityQuestion;
	}

	public void setSequerityQuestion(String sequerityQuestion) {
		this.sequerityQuestion = sequerityQuestion;
	}

	public String getSequerityAnswer() {
		return sequerityAnswer;
	}

	public void setSequerityAnswer(String sequerityAnswer) {
		this.sequerityAnswer = sequerityAnswer;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
}
