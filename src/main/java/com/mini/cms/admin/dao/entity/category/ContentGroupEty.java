package com.mini.cms.admin.dao.entity.category;

import java.io.Serializable;

import com.mini.cms.admin.dao.basic.BaseEntity;

public class ContentGroupEty extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -2013396004921480098L;
	private Integer id;
	private String name;
	private String logo;
	private String remark;
	private Integer defaultDisplayTypeId;	//项目组的默认展现方式

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

	public String getLogo() {
		return this.logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	* 得到 项目组的默认展现方式
	* @return 项目组的默认展现方式 : Integer
	*/
	public Integer getDefaultDisplayTypeId() {
		return this.defaultDisplayTypeId;
	}
	/**
	 * 设置 项目组的默认展现方式
	 * @param defaultDisplayTypeId, 项目组的默认展现方式 : Integer
	*/
	public void setDefaultDisplayTypeId(Integer defaultDisplayTypeId) {
		this.defaultDisplayTypeId = defaultDisplayTypeId;
	}

}