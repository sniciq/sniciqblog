package com.mini.cms.admin.dao.basic;

import com.mini.cms.admin.util.ExtLimit;

public class BaseEntity {
	private ExtLimit extLimit;
	
	private Integer extLimitLimit;//显示记录数
	private Integer extLimitStart;//开始
	private String extLimitSort;//排序列
	private String extLimitDir;//排序方式 ASC DESC
	
	public ExtLimit getExtLimit() {
		return extLimit;
	}

	public void setExtLimit(ExtLimit extLimit) {
		this.extLimit = extLimit;
	}

	public Integer getExtLimitLimit() {
		if(extLimit != null)
			return extLimit.getLimit();
		return extLimitLimit;
	}

	public void setExtLimitLimit(Integer extLimitLimit) {
		this.extLimitLimit = extLimitLimit;
	}

	public Integer getExtLimitStart() {
		if(extLimit != null)
			return extLimit.getStart();
		return extLimitStart;
	}

	public void setExtLimitStart(Integer extLimitStart) {
		this.extLimitStart = extLimitStart;
	}

	public String getExtLimitSort() {
		if(extLimit != null)
			return extLimit.getSort();
		
		return extLimitSort;
	}

	public void setExtLimitSort(String extLimitSort) {
		this.extLimitSort = extLimitSort;
	}

	public String getExtLimitDir() {
		if(extLimit != null)
			return extLimit.getDir();
		return extLimitDir;
	}

	public void setExtLimitDir(String extLimitDir) {
		this.extLimitDir = extLimitDir;
	}

}
