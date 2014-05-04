package com.mini.cms.admin.dao.entity.basic;

import java.io.Serializable;

import com.mini.cms.admin.dao.basic.BaseEntity;

public class RoleResourceEty extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -8084926436779844197L;
	private Integer id;
	private Integer roleId;
	private Integer nodeId;

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return this.roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
}
