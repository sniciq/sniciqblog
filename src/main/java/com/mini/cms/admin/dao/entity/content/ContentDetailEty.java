package com.mini.cms.admin.dao.entity.content;

import java.io.Serializable;

import com.mini.cms.admin.dao.basic.BaseEntity;

public class ContentDetailEty extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 8451816622880301828L;
	private Integer id;
	private Integer itemId;
	private String detail;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
