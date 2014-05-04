package com.mini.cms.admin.dao.entity.category.test.copy;

import java.util.Date;

public class ContentItemTestEty {
	
	private Integer id;
	private String name;
	private Integer contentId;
	private Integer itemType;
	private Double itemOrder;	//项目顺序
	private String remark;
	private Date createDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public Integer getItemType() {
		return itemType;
	}
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	public Double getItemOrder() {
		return itemOrder;
	}
	public void setItemOrder(Double itemOrder) {
		this.itemOrder = itemOrder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
