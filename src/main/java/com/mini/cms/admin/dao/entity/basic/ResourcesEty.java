package com.mini.cms.admin.dao.entity.basic;

import java.io.Serializable;

import com.mini.cms.admin.dao.basic.BaseEntity;

public class ResourcesEty extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5154044607696504272L;
	private Integer id;
	private Integer nodeId;
	private String menuName;
	private Integer parantNodeID;
	private String icon;
	private String openIcon;
	private String actionPath;
	private Integer menuOrder;
	private java.lang.String isValiDate;
	private String description;
	
	private String type;//url or js class
	private String jsClassFile;//多模块时用;分隔
	private String namespace;//命名空间
	private String mainClass;
	
	public String getMainClass() {
		return mainClass;
	}
	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNodeId() {
		return this.nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public String getMenuName() {
		return this.menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getParantNodeID() {
		return this.parantNodeID;
	}
	public void setParantNodeID(Integer parantNodeID) {
		this.parantNodeID = parantNodeID;
	}

	public String getIcon() {
		return this.icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOpenIcon() {
		return this.openIcon;
	}
	public void setOpenIcon(String openIcon) {
		this.openIcon = openIcon;
	}

	public String getActionPath() {
		return this.actionPath;
	}
	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}

	public Integer getMenuOrder() {
		return this.menuOrder;
	}
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public java.lang.String getIsValiDate() {
		return this.isValiDate;
	}
	public void setIsValiDate(java.lang.String isValiDate) {
		this.isValiDate = isValiDate;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getJsClassFile() {
		return jsClassFile;
	}
	public void setJsClassFile(String jsClassFile) {
		this.jsClassFile = jsClassFile;
	}
	
}