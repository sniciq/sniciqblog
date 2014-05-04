package com.mini.cms.admin.dao.entity.category;

import java.io.Serializable;
import java.util.Date;

import com.mini.cms.admin.dao.basic.BaseEntity;

public class ContentItemEty extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7343842655536197868L;
	private Integer id;
	private String name;
	private Integer contentId;
	private Integer itemType;
	private Double itemOrder;	//项目顺序
	private String remark;
	private Date createDate;
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getContentId() {
		return this.contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public Integer getItemType() {
		return this.itemType;
	}
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	/**
	* 得到 项目顺序
	* @return 项目顺序 : Integer
	*/
	public Double getItemOrder() {
		return this.itemOrder;
	}
	/**
	 * 设置 项目顺序
	 * @param itemOrder, 项目顺序 : Integer
	*/
	public void setItemOrder(Double itemOrder) {
		this.itemOrder = itemOrder;
	}

	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}