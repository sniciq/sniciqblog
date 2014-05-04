package com.mini.cms.admin.dao.entity.basic;

import java.io.Serializable;

import com.mini.cms.admin.dao.basic.BaseEntity;

public class RoleEty extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 277772936582428889L;
	private Integer id;
	private String roleName;
	private String describle;
	
	private String roleAuth;
	
	public String getRoleAuth() {
		return roleAuth;
	}
	public void setRoleAuth(String roleAuth) {
		this.roleAuth = roleAuth;
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return this.roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescrible() {
		return this.describle;
	}
	public void setDescrible(String describle) {
		this.describle = describle;
	}

}