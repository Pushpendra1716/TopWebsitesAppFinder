package com.push.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "SYS.INPUT_FILES")
public class InputFileVo {
	
	@Id
	@Column(name="FILE_ID")
	private long fileId;
	
	@Column(name="FILE_NAME")
	private String fileName;
	
	@Column(name="LOAD_TIME")
	private Timestamp loadTime;

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Timestamp getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(Timestamp loadTime) {
		this.loadTime = loadTime;
	}

	
}
