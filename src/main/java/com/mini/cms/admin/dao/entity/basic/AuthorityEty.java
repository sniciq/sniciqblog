package com.mini.cms.admin.dao.entity.basic;

import java.io.Serializable;

import com.mini.cms.admin.dao.basic.BaseEntity;

public class AuthorityEty extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -647943436431647757L;
	private Integer id;
	private String name;
	private String code;	//编码
	private String describle;	//描述

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
	* 得到 编码
	* @return 编码 : String
	*/
	public String getCode() {
		return this.code;
	}
	/**
	 * 设置 编码
	 * @param code, 编码 : String
	*/
	public void setCode(String code) {
		this.code = code;
	}

	/**
	* 得到 描述
	* @return 描述 : String
	*/
	public String getDescrible() {
		return this.describle;
	}
	/**
	 * 设置 描述
	 * @param describle, 描述 : String
	*/
	public void setDescrible(String describle) {
		this.describle = describle;
	}

}