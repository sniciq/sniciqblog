package com.mini.cms.admin.dao.entity.category;

import java.io.Serializable;

import com.mini.cms.admin.dao.basic.BaseEntity;

public class CategoryEty extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1443630093967022337L;
	private Integer id;
	private String name;
	private Integer parentId;
	private java.lang.Double sort;	//排序

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

	public Integer getParentId() {
		return this.parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	* 得到 排序
	* @return 排序 : java.lang.Double
	*/
	public java.lang.Double getSort() {
		return this.sort;
	}
	/**
	 * 设置 排序
	 * @param sort, 排序 : java.lang.Double
	*/
	public void setSort(java.lang.Double sort) {
		this.sort = sort;
	}

}