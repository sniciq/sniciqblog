package com.mini.cms.admin.controller.basic;

import java.io.Serializable;

import com.mini.cms.admin.dao.entity.basic.ResourcesEty;

public class RoleAuthorityData extends ResourcesEty implements Serializable {
	
	private static final long serialVersionUID = -8189581220960136524L;
	private Boolean hasAuthority;
	
	public Boolean getHasAuthority() {
		return hasAuthority;
	}
	public void setHasAuthority(Boolean hasAuthority) {
		this.hasAuthority = hasAuthority;
	}
	private Integer childCount;
	
	public Integer getChildCount() {
		return childCount;
	}
	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

}
