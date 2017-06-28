package com.push.vo;

import java.io.Serializable;

public class ViewBean implements Comparable<ViewBean> ,Serializable{
	private static final long serialVersionUID = 1L;
	public int id;
	public String siteName;
	public long visit;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public long getVisit() {
		return visit;
	}

	public void setVisit(long visit) {
		this.visit = visit;
	}

	public ViewBean(int id,String siteName,long visit){
		this.id=id;
		this.siteName=siteName;
		this.visit = visit;
	}
	
	@Override
	public int compareTo(ViewBean st){  
		if(visit==st.visit)  
		return 0;  
		else if(visit>st.visit)  
		return -1;  
		else  
		return 1;  
		}

	
}
