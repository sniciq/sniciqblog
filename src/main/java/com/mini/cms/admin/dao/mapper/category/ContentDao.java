package com.mini.cms.admin.dao.mapper.category;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.mini.cms.admin.controller.category.CommonComboData;
import com.mini.cms.admin.dao.basic.BaseDao;
import com.mini.cms.admin.dao.entity.category.ContentEty;

public interface ContentDao extends BaseDao<ContentEty> {

	@Select(value="select id,name from content;")
	List<CommonComboData> selectAsCombo();
}