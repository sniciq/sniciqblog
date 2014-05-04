package com.mini.cms.admin.dao.mapper.category;

import java.util.List;

import com.mini.cms.admin.controller.category.ContentItemData;
import com.mini.cms.admin.dao.basic.BaseDao;
import com.mini.cms.admin.dao.entity.category.ContentItemEty;

public interface ContentItemDao extends BaseDao<ContentItemEty> {
	
	ContentItemEty testSelectById(int i);

	int selectDataLimitCount(ContentItemEty contentItemEty);

	List<ContentItemData> selectDataByLimit(ContentItemEty contentItemEty);
}