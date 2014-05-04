package com.mini.cms.admin.dao.entity.basic;

import java.io.Serializable;

import com.mini.cms.admin.dao.basic.BaseEntity;

public class UserRoleEty extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7962646515413978257L;
	private Integer id;
	private Integer userId;
	private Integer roleId;

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}