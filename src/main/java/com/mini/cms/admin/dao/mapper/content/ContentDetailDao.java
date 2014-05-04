package com.mini.cms.admin.dao.mapper.content;

import org.apache.ibatis.annotations.Select;

import com.mini.cms.admin.dao.basic.BaseDao;
import com.mini.cms.admin.dao.entity.content.ContentDetailEty;

public interface ContentDetailDao extends BaseDao<ContentDetailEty> {

	@Select(value="select * from contentdetail where itemId=#{value}")
	ContentDetailEty selectByItemId(int id);

}
