package com.mini.cms.admin.dao.entity.msg;

import java.io.Serializable;
import java.util.Date;

import com.mini.cms.admin.dao.basic.BaseEntity;

public class MessageEty extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5829157525931895167L;
	private Integer id;
	private String name;
	private String phone;
	private String email;
	private String message;
	private String ip;
	private Date createDate;
	private String haveReBack;	//是否回访处理

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

	public String getPhone() {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getIp() {
		return this.ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCreateDate() {
		return this.createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	* 得到 是否回访处理
	* @return 是否回访处理 : String
	*/
	public String getHaveReBack() {
		return this.haveReBack;
	}
	/**
	 * 设置 是否回访处理
	 * @param haveReBack, 是否回访处理 : String
	*/
	public void setHaveReBack(String haveReBack) {
		this.haveReBack = haveReBack;
	}

}