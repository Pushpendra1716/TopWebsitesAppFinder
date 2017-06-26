package com.push.vo;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "SYS.INPUT_FILE_DATA")  
public class InputFileData {

	@Id
	@Column(name="FIELD_DATA_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int fileDataId;
	
	@Column(name="LOAD_DATE")
	private Date date;
	
	@Column(name="WEB_SITE")
	private String website;
	
	@Column(name="VISIT_COUNT")
	private long visits;
	
	@Column(name="LOAD_TIME")
	private Timestamp loadTime;
	
	public int getFileDataId() {
		return fileDataId;
	}
	public void setFileDataId(int fileDataId) {
		this.fileDataId = fileDataId;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public long getVisits() {
		return visits;
	}
	public void setVisits(long visits) {
		this.visits = visits;
	}
	public Timestamp getLoadTime() {
		return loadTime;
	}
	public void setLoadTime(Timestamp loadTime) {
		this.loadTime = loadTime;
	}
	
	public InputFileData() {
	}
	public InputFileData(Date date, String website, long visits) {
		this.date = date;
		this.website = website;
		this.visits = visits;
	}
	
}
