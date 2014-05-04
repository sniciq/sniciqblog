package org.mybatis.plug.anno.test;

import java.util.Date;

import org.mybatis.plug.anno.Column;
import org.mybatis.plug.anno.Table;

@Table(tableName="contentitem")
public class TestEty {
	
	@Column(columnName="id")
	private Integer id;
	
	@Column(columnName="name")
	private String name;
	
	@Column(columnName="contentId")
	private Integer contentId;
	
	@Column(columnName="itemType")
	private Integer itemType;
	
	@Column(columnName="itemOrder")
	private Double itemOrder;	//项目顺序
	
	@Column(columnName="remark")
	private String remark;
	
	@Column(columnName="createDate")
	private Date createDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public Integer getItemType() {
		return itemType;
	}
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	public Double getItemOrder() {
		return itemOrder;
	}
	public void setItemOrder(Double itemOrder) {
		this.itemOrder = itemOrder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
