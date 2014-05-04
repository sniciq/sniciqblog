package com.mini.cms.admin.dao.entity.category;

import java.io.Serializable;
import java.util.Date;

import com.mini.cms.admin.dao.basic.BaseEntity;

public class ContentEty extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -4114321493767924389L;
	private Integer id;
	private String name;
	private Integer groupId;	//内容组ID
	private Integer displayTypeId;	//展现形式
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

	/**
	* 得到 内容组ID
	* @return 内容组ID : Integer
	*/
	public Integer getGroupId() {
		return this.groupId;
	}
	/**
	 * 设置 内容组ID
	 * @param groupId, 内容组ID : Integer
	*/
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	/**
	* 得到 展现形式
	* @return 展现形式 : Integer
	*/
	public Integer getDisplayTypeId() {
		return this.displayTypeId;
	}
	/**
	 * 设置 展现形式
	 * @param displayTypeId, 展现形式 : Integer
	*/
	public void setDisplayTypeId(Integer displayTypeId) {
		this.displayTypeId = displayTypeId;
	}

	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}