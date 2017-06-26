package com.push.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "SYS.INPUT_FILE_ERROR_DATA") 
public class FileDataErrorVo {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="FILE_ID")
	private long fileId;
	
	@Column(name="ERROR")
	private String error;
	
	@Column(name="LOAD_TIME")
	private Timestamp loadTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Timestamp getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(Timestamp loadTime) {
		this.loadTime = loadTime;
	}
	
	
}
